package com.roofiahmad.springstoreapp.infra.event.redis;

import com.roofiahmad.springstoreapp.feature.order.event.OrderEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, OrderEvent> orderEventRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, OrderEvent> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new JacksonJsonRedisSerializer<>(OrderEvent.class));
        return template;
    }
}
