package com.s2u2m.examples.interviews.controller.cluster;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.s2u2m.examples.interviews.constant.K8sProviderName;
import com.s2u2m.examples.interviews.error.ErrorCode;
import com.s2u2m.examples.interviews.error.PlatformException;
import com.s2u2m.examples.interviews.remote.provider.K8sProvider;
import com.s2u2m.examples.interviews.remote.provider.K8sProviderFactory;
import com.s2u2m.examples.interviews.repository.cluster.Cluster;
import com.s2u2m.examples.interviews.service.ClusterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ClusterController.class)
class ClusterControllerTest {

  private ObjectMapper objectMapper = new ObjectMapper();

  @Autowired
  private MockMvc mvc;

  @MockitoBean
  private K8sProviderFactory providerFactory;
  @MockitoBean
  private ClusterService clusterService;

  @Test
  void shouldReturnCreatedCluster_WhenClusterNameNotExisted() throws Exception {
    K8sProvider provider = mock(K8sProvider.class);
    when(providerFactory.getProvider(any())).thenReturn(provider);

    String name = "test";
    Cluster cluster = new Cluster();
    cluster.setName(name);
    when(clusterService.createCluster(any(), any(), any())).thenReturn(cluster);

    CreateClusterRequest request = CreateClusterRequest.builder()
        .provider(K8sProviderName.K3D)
        .name(name)
        .build();
    mvc.perform(post("/api/clusters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value(name));
  }

  @Test
  void shouldBadRequest_WhenProviderNotSupported() throws Exception {
    when(providerFactory.getProvider(any())).thenReturn(null);

    CreateClusterRequest request = CreateClusterRequest.builder()
        .provider(K8sProviderName.K3D)
        .name("test")
        .build();
    mvc.perform(post("/api/clusters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(request)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldBadRequest_WhenClusterExisted() throws Exception {
    K8sProvider provider = mock(K8sProvider.class);
    when(providerFactory.getProvider(any())).thenReturn(provider);

    String name = "test";
    PlatformException exception = new PlatformException(
        HttpStatus.BAD_REQUEST,
        ErrorCode.CLUSTER_ALREADY_EXISTED, "");
    when(clusterService.createCluster(any(), any(), any())).thenThrow(exception);

    CreateClusterRequest request = CreateClusterRequest.builder()
        .provider(K8sProviderName.K3D)
        .name(name)
        .build();
    mvc.perform(post("/api/clusters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(request)))
        .andExpect(status().isBadRequest());
  }
}