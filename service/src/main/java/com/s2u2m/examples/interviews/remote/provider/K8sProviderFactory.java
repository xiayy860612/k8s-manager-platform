package com.s2u2m.examples.interviews.remote.provider;

import com.s2u2m.examples.interviews.constant.K8sProviderName;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class K8sProviderFactory {
  private final Map<K8sProviderName, K8sProvider> providers;

  public K8sProviderFactory(LocalK3dK8sProvider localK3dK8sProvider) {
    providers = Map.of(
        K8sProviderName.K3D, localK3dK8sProvider
    );
  }

  public K8sProvider getProvider(K8sProviderName provider) {
    return providers.get(provider);
  }
}
