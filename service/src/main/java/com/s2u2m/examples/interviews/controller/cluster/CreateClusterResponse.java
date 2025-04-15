package com.s2u2m.examples.interviews.controller.cluster;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateClusterResponse {
  private Long id;
  private String name;
}
