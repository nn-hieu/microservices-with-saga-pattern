package com.hieunn.orderservice.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hieunn.commonlib.configs.rabbitmqs.BaseRabbitMQConfig;
import com.hieunn.commonlib.configs.rabbitmqs.IdempotentInterceptor;
import com.hieunn.commonlib.enums.events.PaymentEvent;
import com.hieunn.commonlib.enums.events.UserEvent;
import com.hieunn.commonlib.enums.rabbitmqs.Exchange;
import com.hieunn.orderservice.entities.ProcessedSagaEvent;
import com.hieunn.orderservice.entities.SagaEvent;
import com.hieunn.orderservice.enums.RabbitMQQueue;
import com.hieunn.orderservice.repositories.ProcessedSagaEventRepository;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig extends BaseRabbitMQConfig {
    @Value("${spring.rabbitmq.message-ttl}")
    private int ttl;

    // Exchanges
    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange(Exchange.ORDER_EXCHANGE.getValue());
    }

    @Bean
    public DirectExchange userExchange() {
        return new DirectExchange(Exchange.USER_EXCHANGE.getValue());
    }

    @Bean
    public DirectExchange paymentExchange() {
        return new DirectExchange(Exchange.PAYMENT_EXCHANGE.getValue());
    }

    // Queues
    @Bean
    public Queue userDebitFailedQueue() {
        return QueueBuilder.durable(RabbitMQQueue.USER_DEBIT_FAILED_QUEUE.getValue())
                .withArgument("x-message-ttl", ttl)
                .withArgument("x-dead-letter-exchange", Exchange.DEAD_LETTER_EXCHANGE.getValue())
                .build();
    }

    @Bean
    public Queue transactionCreatedSucceededQueue() {
        return QueueBuilder.durable(RabbitMQQueue.TRANSACTION_COMPLETED_QUEUE.getValue())
                .withArgument("x-message-ttl", ttl)
                .withArgument("x-dead-letter-exchange", Exchange.DEAD_LETTER_EXCHANGE.getValue())
                .build();
    }

    // Bindings
    @Bean
    public Binding userDebitFailedBinding(Queue userDebitFailedQueue, DirectExchange userExchange) {
        return BindingBuilder
                .bind(userDebitFailedQueue)
                .to(userExchange)
                .with(UserEvent.USER_DEBIT_FAILED.getValue());
    }

    @Bean
    public Binding transactionCreatedSucceededBinding(Queue transactionCreatedSucceededQueue, DirectExchange paymentExchange) {
        return BindingBuilder
                .bind(transactionCreatedSucceededQueue)
                .to(paymentExchange)
                .with(PaymentEvent.TRANSACTION_COMPLETED.getValue());
    }

    // Other Configs
    @Bean
    public MessageConverter messageConverter() {
        return createMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        return createTemplate(connectionFactory);
    }

    @Bean
    public IdempotentInterceptor<ProcessedSagaEvent> idempotentInterceptor(
            ProcessedSagaEventRepository repository,
            ObjectMapper objectMapper
    ) {
        return new IdempotentInterceptor<>(
                repository,
                objectMapper,
                SagaEvent.class
        );
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            IdempotentInterceptor<ProcessedSagaEvent> idempotentInterceptor
    ) {
        SimpleRabbitListenerContainerFactory containerFactory = createFactory(connectionFactory);
        containerFactory.setAdviceChain(idempotentInterceptor);

        return containerFactory;
    }
}