package com.s2u2m.examples.interviews.repository;

import com.s2u2m.examples.interviews.utils.AuthUtils;
import java.util.Optional;
import org.springframework.data.domain.AuditorAware;

public class EntityAuditAware implements AuditorAware<String> {
  @Override
  public Optional<String> getCurrentAuditor() {
    return Optional.of(AuthUtils.getUsername());
  }
}
