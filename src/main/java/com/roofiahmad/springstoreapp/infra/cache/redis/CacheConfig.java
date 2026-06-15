package com.roofiahmad.springstoreapp.infra.cache.redis;

import org.springframework.boot.cache.autoconfigure.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;

@Configuration
public class CacheConfig {

    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        RedisCacheConfiguration baseJsonConfig = RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()));

        return (builder) -> builder
                .withCacheConfiguration("category-list",
                        baseJsonConfig.entryTtl(Duration.ofDays(1)))
                .withCacheConfiguration("product-list",
                        baseJsonConfig.entryTtl(Duration.ofMinutes(10)))
                .withCacheConfiguration("product-details",
                        baseJsonConfig.entryTtl(Duration.ofHours(1)))
                .withCacheConfiguration("product-reviews",
                        baseJsonConfig.entryTtl(Duration.ofHours(1)));
    }
}
