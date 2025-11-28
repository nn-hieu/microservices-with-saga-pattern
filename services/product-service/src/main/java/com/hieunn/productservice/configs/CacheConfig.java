package com.hieunn.productservice.configs;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Setter
@Getter
@Configuration(proxyBeanMethods = false)
@EnableCaching
@ConfigurationProperties(prefix = "cache")
public class CacheConfig {
    @Setter
    @Getter
    private class CacheSpec {
        private Integer ttl;
        private Integer maxSize;
    }

    private CacheSpec defaultSpec;
    private Map<String, CacheSpec> specs;

    public CacheConfig() {
        specs = new HashMap<>();
        defaultSpec = new CacheSpec();
    }

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager manager = new CaffeineCacheManager();

        manager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(defaultSpec.getTtl(), TimeUnit.MINUTES)
                .maximumSize(defaultSpec.getMaxSize())
        );

        specs.forEach((name, spec) -> {
            CacheSpec finalSpec = mergeWithDefault(spec);

            manager.registerCustomCache(
                    name,
                    Caffeine.newBuilder()
                            .expireAfterWrite(finalSpec.getTtl(), TimeUnit.MINUTES)
                            .maximumSize(finalSpec.getMaxSize())
                            .build()
            );
        });

        return manager;
    }

    private CacheSpec mergeWithDefault(CacheSpec spec) {
        CacheSpec merged = new CacheSpec();
        merged.setTtl(spec.getTtl() != null ? spec.getTtl() : defaultSpec.getTtl());
        merged.setMaxSize(spec.getMaxSize() != null ? spec.getMaxSize() : defaultSpec.getMaxSize());
        return merged;
    }
}
