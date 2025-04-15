package com.s2u2m.examples.interviews.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@Configuration
public class RepositoryConfig {

  @Bean
  public AuditorAware<String> auditorAware() {
    return new EntityAuditAware();
  }
}
