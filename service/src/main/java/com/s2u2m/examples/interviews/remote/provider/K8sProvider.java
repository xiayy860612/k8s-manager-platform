package com.s2u2m.examples.interviews.remote.provider;

import com.s2u2m.examples.interviews.constant.K8sProviderName;
import com.s2u2m.examples.interviews.repository.cluster.Cluster;

public interface K8sProvider {

  K8sProviderName getProviderName();
  Cluster createCluster(String name, Integer workerCount);
}
