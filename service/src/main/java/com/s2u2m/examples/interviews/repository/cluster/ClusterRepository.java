package com.s2u2m.examples.interviews.repository.cluster;

import com.s2u2m.examples.interviews.constant.K8sProviderName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClusterRepository extends JpaRepository<Cluster, Long> {
  boolean existsByProviderAndName(K8sProviderName provider, String name);
}
