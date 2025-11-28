package com.hieunn.orderservice.publishers.impls;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hieunn.commonlib.dtos.events.OrderCreatedEvent;
import com.hieunn.commonlib.enums.events.OrderEvent;
import com.hieunn.commonlib.dtos.orders.OrderDTO;
import com.hieunn.commonlib.publishers.AbstractSagaEventPublisher;
import com.hieunn.orderservice.entities.SagaEvent;
import com.hieunn.orderservice.publishers.OrderEventPublisher;
import com.hieunn.orderservice.repositories.SagaEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventPublisherImpl implements OrderEventPublisher {
    private final SagaEventRepository sagaEventRepository;
    private final AbstractSagaEventPublisher sagaEventPublisher;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    @Async
    public void publishOrderCreatedSucceededEvent(OrderDTO.Response order) {
        try {
            OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent();
            orderCreatedEvent.setOrderId(order.getId());
            orderCreatedEvent.setUserId(order.getUserId());
            orderCreatedEvent.setTotalAmount(order.getTotalAmount());

            SagaEvent sagaEvent = new SagaEvent();
            sagaEvent.setEventName(OrderEvent.ORDER_CREATED_SUCCEEDED.getValue());
            sagaEvent.setSagaId(UUID.randomUUID().toString());
            sagaEvent.setPayload(objectMapper.writeValueAsString(orderCreatedEvent));

            sagaEventRepository.save(sagaEvent);

            log.info("Publish order created succeeded event with payload: {}", sagaEvent.getPayload());

            sagaEventPublisher.publishEvent(sagaEvent);
        } catch (JsonProcessingException e) {
            log.error("Json exception: {}", e.getMessage());

            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Json exception",
                    e
            );
        }
    }
}
