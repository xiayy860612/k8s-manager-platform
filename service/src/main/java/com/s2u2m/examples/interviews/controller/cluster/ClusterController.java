package com.s2u2m.examples.interviews.controller.cluster;

import com.s2u2m.examples.interviews.error.ErrorCode;
import com.s2u2m.examples.interviews.error.PlatformException;
import com.s2u2m.examples.interviews.remote.provider.K8sProvider;
import com.s2u2m.examples.interviews.remote.provider.K8sProviderFactory;
import com.s2u2m.examples.interviews.repository.cluster.Cluster;
import com.s2u2m.examples.interviews.service.ClusterService;
import jakarta.validation.Valid;
import java.util.Objects;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clusters")
@Validated
@AllArgsConstructor
public class ClusterController {

  private final K8sProviderFactory providerFactory;
  private final ClusterService clusterService;

  @PostMapping("")
  public CreateClusterResponse createCluster(@Valid @RequestBody CreateClusterRequest request) {
    K8sProvider provider = providerFactory.getProvider(request.getProvider());
    if (Objects.isNull(provider)) {
      throw new PlatformException(
          HttpStatus.BAD_REQUEST, ErrorCode.PROVIDER_NOT_SUPPORTED,
          "Provider[{0}] is not supported", request.getProvider());
    }

    Cluster cluster = clusterService.createCluster(provider, request.getName(), request.getWorkerCount());
    return CreateClusterResponse.builder()
        .id(cluster.getId()).name(cluster.getName()).build();
  }
}
