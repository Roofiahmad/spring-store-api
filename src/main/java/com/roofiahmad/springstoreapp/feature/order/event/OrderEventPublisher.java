package com.roofiahmad.springstoreapp.feature.order.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderEventPublisher {
    private final RedisTemplate<String, OrderEvent> orderEventRedisTemplate;
    private static final String REDIS_CHANNEL = "order-notification";

    public void publishOrderPaidEvent(OrderEvent event) {
        log.info("📢 Firing Redis Pub/Sub event for Order #{}", event.orderNumber());

        try {
            orderEventRedisTemplate.convertAndSend(REDIS_CHANNEL, event);

        } catch (Exception e) {
            log.error("Failed to push event to Redis for order #{}: {}", event.orderNumber(), e.getMessage());
        }
    }
}
