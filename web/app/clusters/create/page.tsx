"use client";

import "@ant-design/v5-patch-for-react-19";
import { Button, Form, Input, InputNumber, message, Select } from "antd";
import styles from "./styles.module.css";
import { K8sProviderName, useCreateClusterMutation } from "@/api/cluster";
import { useRouter } from "next/navigation";

interface FormProps {
  provider: string;
  name: string;
  workerCount: number;
}

export default function CreateClusterPage() {
  const [messageApi, contextHolder] = message.useMessage();
  const router = useRouter();

  const [createCluster, { isLoading, isError, data }] =
    useCreateClusterMutation();

  if (isLoading) {
    return <div>Loading...</div>;
  }

  const providers = Object.entries(K8sProviderName).map(([_, value]) => ({
    label: value,
    value: value,
  }));

  const handleSubmit = async (values: FormProps) => {
    try {
      await createCluster({ ...values }).unwrap();
      message.success("Create Success");
      router.back();
    } catch (error) {}
  };

  return (
    <div className={styles.page}>
      {contextHolder}
      <Form onFinish={handleSubmit}>
        <Form.Item<FormProps>
          label="K8s Provider"
          name="provider"
          rules={[{ required: true, message: "Please select Provider" }]}
        >
          <Select style={{ width: 200 }} options={providers} />
        </Form.Item>

        <Form.Item<FormProps>
          label="Cluster Name"
          name="name"
          rules={[{ required: true, message: "Please input cluster name" }]}
        >
          <Input style={{ width: 200 }} placeholder="Enter cluster name" />
        </Form.Item>

        <Form.Item<FormProps> label="Worker Count" name="workerCount">
          <InputNumber style={{ width: 200 }}
            min="0" max="3"
            parser={(value) => value?.replace(/[^0-9]/g, "") || 0}
            onKeyDown={(e) => {
              if (['.', '-', 'e'].includes(e.key)) {
                e.preventDefault();
              }
            }}
            onChange={(value) => {
              return Math.round(Number(value) ?? 0);
            }}
          />
        </Form.Item>

        <Form.Item>
          <Button type="primary" htmlType="submit">
            Create Cluster
          </Button>
        </Form.Item>
      </Form>
    </div>
  );
}
