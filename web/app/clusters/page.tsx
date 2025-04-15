"use client";

import { Button, Modal } from "antd";
import styles from "./styles.module.css";
import Link from "next/link";

export default function ClusterPage() {
  return (
    <div className={styles.page}>
      <div>
        <Button type="primary" size="middle">
          <Link href="/clusters/create">Create Cluster</Link>
        </Button>
      </div>
      <div>List</div>
    </div>
  );
}
