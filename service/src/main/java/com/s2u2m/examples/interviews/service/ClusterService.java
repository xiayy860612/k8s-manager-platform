package com.s2u2m.examples.interviews.service;

import com.s2u2m.examples.interviews.error.ErrorCode;
import com.s2u2m.examples.interviews.error.PlatformException;
import com.s2u2m.examples.interviews.remote.provider.K8sProvider;
import com.s2u2m.examples.interviews.repository.cluster.Cluster;
import com.s2u2m.examples.interviews.repository.cluster.ClusterRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ClusterService {

  private final ClusterRepository clusterRepository;

  public ClusterService(ClusterRepository clusterRepository) {
    this.clusterRepository = clusterRepository;
  }

  public Cluster createCluster(K8sProvider provider, String name, Integer workerCount) {
    boolean isExisted = clusterRepository
        .existsByProviderAndName(provider.getProviderName(), name);
    if (isExisted) {
      throw new PlatformException(
          HttpStatus.BAD_REQUEST, ErrorCode.CLUSTER_ALREADY_EXISTED,
          "Cluster[{0}] is already existed in Provider[{1}]",
          name, provider.getProviderName());
    }

    Cluster cluster = provider.createCluster(name, workerCount);
    cluster = clusterRepository.save(cluster);
    return cluster;
  }
}
