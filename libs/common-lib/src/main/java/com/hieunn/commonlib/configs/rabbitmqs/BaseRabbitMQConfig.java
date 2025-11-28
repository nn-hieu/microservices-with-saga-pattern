package com.hieunn.commonlib.configs.rabbitmqs;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;

@RequiredArgsConstructor
public class BaseRabbitMQConfig {
    protected MessageConverter createMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    protected AmqpTemplate createTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(createMessageConverter());
        return rabbitTemplate;
    }

    protected SimpleRabbitListenerContainerFactory createFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        factory.setPrefetchCount(1);
        factory.setDefaultRequeueRejected(false);
        factory.setMessageConverter(createMessageConverter());

        return factory;
    }
}
