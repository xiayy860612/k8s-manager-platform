import apiClient from "@/api/axios-client";
import { BaseQueryFn } from "@reduxjs/toolkit/query";
import { AxiosError, AxiosRequestConfig } from "axios";

const baseUrl = process.env.NEXT_PUBLIC_API_HOST || "http://localhost:3000";

type RequestType = {
  url: string;
  method?: AxiosRequestConfig["method"];
  data?: AxiosRequestConfig["data"];
  params?: AxiosRequestConfig["params"];
  headers?: AxiosRequestConfig["headers"];
};

const baseQuery =
  (): BaseQueryFn<RequestType, unknown, unknown> =>
  async ({ url, method, data, params, headers }) => {
    try {
      const result = await apiClient.request({
        url: baseUrl + url,
        method,
        data,
        params,
        headers,
      });
      return { data: result.data };
    } catch (axiosError) {
      const err = axiosError as AxiosError;
      return {
        error: {
          status: err.response?.status,
          data: err.response?.data || err.message,
        },
      };
    }
  };

export default baseQuery;
