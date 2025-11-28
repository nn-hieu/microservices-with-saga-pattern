package com.hieunn.deadletterservice.handlers.impls;

import com.hieunn.commonlib.enums.constants.ServiceName;
import com.hieunn.commonlib.enums.events.OrderEvent;
import com.hieunn.commonlib.enums.rabbitmqs.Exchange;
import com.hieunn.deadletterservice.entities.SagaEvent;
import com.hieunn.deadletterservice.handlers.OrderEventHandler;
import com.hieunn.deadletterservice.repositories.SagaEventRepository;
import com.hieunn.deadletterservice.utils.EurekaUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventHandlerImpl implements OrderEventHandler {
    private final EurekaUtil eurekaUtil;
    private final RabbitTemplate rabbitTemplate;
    private final SagaEventRepository sagaEventRepository;

    @Override
    @Transactional
    public void handleOrderEvent(SagaEvent sagaEvent) {
        OrderEvent orderEvent = OrderEvent.fromValue(sagaEvent.getEventName());

        try {
            switch (orderEvent) {
                case ORDER_CREATED_SUCCEEDED:
                    if (eurekaUtil.areServicesUpAny(ServiceName.USER_SERVICE, ServiceName.PAYMENT_SERVICE)) {
                        rabbitTemplate.convertAndSend(
                                Exchange.ORDER_EXCHANGE.getValue(),
                                sagaEvent.getEventName(),
                                sagaEvent
                        );

                        sagaEventRepository.delete(sagaEvent);
                    }
                    break;
                case null, default:
                    break;
            }
        } catch (AmqpException e) {
            sagaEvent.setRetryCount(sagaEvent.getRetryCount() + 1);
            sagaEvent.setLastAttemptAt(LocalDateTime.now());

            sagaEventRepository.save(sagaEvent);
        }
    }
}
