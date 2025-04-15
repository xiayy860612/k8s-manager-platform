import { http, HttpResponse } from "msw";
import { CREATE_CLUSTER_API, CreateClusterRequest, CreateClusterResponse } from ".";

const createSuccess = () => HttpResponse.json<CreateClusterResponse>(
  {
    id: 1,
    name: "test-cluster",
  }
);

const clusterHandler = http.post<
  never,
  CreateClusterRequest,
  CreateClusterResponse | ErrorResponse
>(CREATE_CLUSTER_API, async () => {
  return createSuccess();
});

export default clusterHandler;
