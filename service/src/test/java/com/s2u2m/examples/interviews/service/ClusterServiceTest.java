package com.s2u2m.examples.interviews.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.s2u2m.examples.interviews.error.ErrorCode;
import com.s2u2m.examples.interviews.error.PlatformException;
import com.s2u2m.examples.interviews.remote.provider.K8sProvider;
import com.s2u2m.examples.interviews.repository.cluster.Cluster;
import com.s2u2m.examples.interviews.repository.cluster.ClusterRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class ClusterServiceTest {

  @InjectMocks
  private ClusterService clusterService;

  @Mock
  private ClusterRepository clusterRepository;

  @Test
  void shouldGetCluster_WhenCreateSuccess() {
    when(clusterRepository.existsByProviderAndName(any(), anyString())).thenReturn(false);

    K8sProvider provider = Mockito.mock(K8sProvider.class);
    String name = "test";
    Cluster cluster = new Cluster();
    cluster.setName(name);
    when(provider.createCluster(anyString(), any())).thenReturn(cluster);

    when(clusterRepository.save(any())).thenReturn(cluster);

    Cluster result = clusterService.createCluster(provider, name, 0);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(name, result.getName());
  }

  @Test
  void shouldBadRequest_WhenClusterExisted() {
    when(clusterRepository.existsByProviderAndName(any(), anyString()))
        .thenReturn(true);

    K8sProvider provider = Mockito.mock(K8sProvider.class);
    String name = "test";

    PlatformException exception = Assertions.assertThrows(PlatformException.class, () -> {
      clusterService.createCluster(provider, name, 0);
    });
    Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    Assertions.assertEquals(ErrorCode.CLUSTER_ALREADY_EXISTED, exception.getCode());
  }
}