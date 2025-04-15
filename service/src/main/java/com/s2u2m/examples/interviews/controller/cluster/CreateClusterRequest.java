package com.s2u2m.examples.interviews.controller.cluster;

import com.s2u2m.examples.interviews.constant.K8sProviderName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateClusterRequest {
  @NotNull
  private K8sProviderName provider;
  @NotBlank
  private String name;
  @PositiveOrZero
  private Integer workerCount;
}
