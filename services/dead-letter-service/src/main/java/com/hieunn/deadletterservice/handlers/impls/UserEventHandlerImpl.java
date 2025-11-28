package com.hieunn.deadletterservice.handlers.impls;

import com.hieunn.commonlib.enums.constants.ServiceName;
import com.hieunn.commonlib.enums.events.UserEvent;
import com.hieunn.commonlib.enums.rabbitmqs.Exchange;
import com.hieunn.deadletterservice.entities.SagaEvent;
import com.hieunn.deadletterservice.handlers.UserEventHandler;
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
public class UserEventHandlerImpl implements UserEventHandler {
    private final EurekaUtil eurekaUtil;
    private final RabbitTemplate rabbitTemplate;
    private final SagaEventRepository sagaEventRepository;

    @Override
    @Transactional
    public void handleUserEvent(SagaEvent sagaEvent) {
        UserEvent userEvent = UserEvent.fromValue(sagaEvent.getEventName());

        try {
            switch (userEvent) {
                case USER_DEBIT_FAILED:
                    if (eurekaUtil.areServicesUpAny(ServiceName.ORDER_SERVICE, ServiceName.PAYMENT_SERVICE)) {
                        rabbitTemplate.convertAndSend(
                                Exchange.USER_EXCHANGE.getValue(),
                                sagaEvent.getEventName(),
                                sagaEvent
                        );

                        sagaEventRepository.delete(sagaEvent);
                    }
                    break;
                case USER_DEBIT_SUCCEEDED:
                    if (eurekaUtil.areServicesUp(ServiceName.PAYMENT_SERVICE)) {
                        rabbitTemplate.convertAndSend(
                                Exchange.USER_EXCHANGE.getValue(),
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
