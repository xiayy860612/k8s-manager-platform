import { createApi } from "@reduxjs/toolkit/query/react";
import baseQuery from "../base-query";

export enum K8sProviderName {
  K3D = "K3D",
}

export interface CreateClusterRequest {
  provider: string;
  name: string;
  workerCount: number
}

export interface CreateClusterResponse {
  id: number;
  name: string;
}

export const CLUSTER_API = "/api/clusters";
export const CREATE_CLUSTER_API = CLUSTER_API;

export const clusterApi = createApi({
  reducerPath: "clusterApi",
  baseQuery: baseQuery(),
  endpoints: (builder) => ({
    createCluster: builder.mutation<CreateClusterResponse, CreateClusterRequest>({
      query: (request) => {
        return ({
          url: CREATE_CLUSTER_API,
          method: "POST",
          data: request,
        })
      },
    }),
  }),
});

export const { useCreateClusterMutation } = clusterApi;
