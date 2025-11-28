package com.hieunn.deadletterservice.handlers.impls;

import com.hieunn.commonlib.enums.constants.ServiceName;
import com.hieunn.commonlib.enums.events.PaymentEvent;
import com.hieunn.commonlib.enums.rabbitmqs.Exchange;
import com.hieunn.deadletterservice.entities.SagaEvent;
import com.hieunn.deadletterservice.handlers.PaymentEventHandler;
import com.hieunn.deadletterservice.repositories.SagaEventRepository;
import com.hieunn.deadletterservice.utils.EurekaUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PaymentEventHandlerImpl implements PaymentEventHandler {
    private final EurekaUtil eurekaUtil;
    private final RabbitTemplate rabbitTemplate;
    private final SagaEventRepository sagaEventRepository;

    @Override
    public void handlePaymentEvent(SagaEvent sagaEvent) {
        PaymentEvent paymentEvent = PaymentEvent.fromValue(sagaEvent.getEventName());

        try {
            switch (paymentEvent) {
                case TRANSACTION_COMPLETED:
                    if (eurekaUtil.areServicesUp(ServiceName.ORDER_SERVICE)) {
                        rabbitTemplate.convertAndSend(
                                Exchange.PAYMENT_EXCHANGE.getValue(),
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
