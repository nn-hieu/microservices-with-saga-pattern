package com.hieunn.userservice.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hieunn.commonlib.configs.rabbitmqs.BaseRabbitMQConfig;
import com.hieunn.commonlib.configs.rabbitmqs.IdempotentInterceptor;
import com.hieunn.commonlib.enums.events.OrderEvent;
import com.hieunn.commonlib.enums.rabbitmqs.Exchange;
import com.hieunn.userservice.entities.ProcessedSagaEvent;
import com.hieunn.userservice.entities.SagaEvent;
import com.hieunn.userservice.enums.RabbitMQQueue;
import com.hieunn.userservice.repositories.ProcessedSagaEventRepository;
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
    public DirectExchange userExchange() {
        return new DirectExchange(Exchange.USER_EXCHANGE.getValue());
    }

    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange(Exchange.ORDER_EXCHANGE.getValue());
    }

    // Queues
    @Bean
    public Queue orderCreatedSucceededQueue() {
        return QueueBuilder.durable(RabbitMQQueue.ORDER_CREATED_SUCCEEDED_QUEUE.getValue())
                .withArgument("x-message-ttl", ttl)
                .withArgument("x-dead-letter-exchange", Exchange.DEAD_LETTER_EXCHANGE.getValue())
                .build();
    }

    // Bindings
    @Bean
    public Binding orderCreatedSucceededQueueBinding(Queue orderCreatedSucceededQueue, DirectExchange orderExchange) {
        return BindingBuilder
                .bind(orderCreatedSucceededQueue)
                .to(orderExchange)
                .with(OrderEvent.ORDER_CREATED_SUCCEEDED.getValue());
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
