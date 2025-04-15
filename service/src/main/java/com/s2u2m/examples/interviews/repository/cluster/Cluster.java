package com.s2u2m.examples.interviews.repository.cluster;

import com.s2u2m.examples.interviews.constant.K8sProviderName;
import com.s2u2m.examples.interviews.repository.AuditEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Table(name = "cluster", uniqueConstraints = {
    @UniqueConstraint(
        name = "uk_provider_cluster",
        columnNames = {"provider", "name"}
    )
})
@Data
public class Cluster extends AuditEntity {

  @Enumerated(value = EnumType.STRING)
  private K8sProviderName provider;
  private String name;
  private String url;
}
