package com.hieunn.deadletterservice.configs;

import com.hieunn.commonlib.configs.rabbitmqs.BaseRabbitMQConfig;
import com.hieunn.commonlib.enums.rabbitmqs.Exchange;
import com.hieunn.deadletterservice.enums.RabbitMQQueue;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig extends BaseRabbitMQConfig {
    // Exchanges
    @Bean
    public FanoutExchange dlx() {
        return new FanoutExchange(Exchange.DEAD_LETTER_EXCHANGE.getValue());
    }

    // Queues
    @Bean
    public Queue dlq() {
        return new Queue(RabbitMQQueue.DLQ.getValue(), true);
    }

    // Bindings
    @Bean
    public Binding deadLetterBinding(
            Queue dlq,
            FanoutExchange dlx
    ) {
        return BindingBuilder.bind(dlq).to(dlx);
    }

    @Bean
    public MessageConverter messageConverter() {
        return createMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        return createTemplate(connectionFactory);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory containerFactory = createFactory(connectionFactory);
        containerFactory.setAcknowledgeMode(AcknowledgeMode.AUTO);

        return containerFactory;
    }
}
