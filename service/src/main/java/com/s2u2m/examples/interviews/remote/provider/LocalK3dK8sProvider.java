package com.s2u2m.examples.interviews.remote.provider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.s2u2m.examples.interviews.constant.K8sProviderName;
import com.s2u2m.examples.interviews.error.ErrorCode;
import com.s2u2m.examples.interviews.error.PlatformException;
import com.s2u2m.examples.interviews.repository.cluster.Cluster;
import com.s2u2m.examples.interviews.utils.ProcessUtils;
import java.io.IOException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class LocalK3dK8sProvider implements K8sProvider {

  public static final String URL_JSON_PATH = "/0/serverLoadBalancer/runtimeLabels/k3d.cluster.url";
  private final ObjectMapper objectMapper;

  public LocalK3dK8sProvider(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public K8sProviderName getProviderName() {
    return K8sProviderName.K3D;
  }

  @Override
  public Cluster createCluster(String name, Integer workerCount) {
    String result;
    try {
      ProcessUtils.runCommand(
          List.of("k3d", "cluster", "create", name, "--agents", workerCount.toString()));
      result = ProcessUtils.runCommand(
          List.of("k3d", "cluster", "list", name, "--token", "-o", "json"));
    } catch (IOException | InterruptedException e) {
      throw new PlatformException(
          HttpStatus.SERVICE_UNAVAILABLE, ErrorCode.PROVIDER_SERVICE_NOT_AVAILABLE,
          "Provide[{0}] is not available", getProviderName());
    }

    String url;
    try {
      JsonNode tree = objectMapper.readTree(result);
      url = tree.at(URL_JSON_PATH).asText();
    } catch (JsonProcessingException e) {
      throw new PlatformException(
          HttpStatus.SERVICE_UNAVAILABLE, ErrorCode.PROVIDER_SERVICE_NOT_AVAILABLE,
          "Provide[{0}] raise some error[]{1}", getProviderName(), e.getMessage());
    }

    Cluster cluster = new Cluster();
    cluster.setName(name);
    cluster.setProvider(getProviderName());
    cluster.setUrl(url);
    return cluster;
  }
}
