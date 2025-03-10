package com.example.rabbitmqdemo.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Value("${rabbitmq.queue.name}")
    private String queue;
    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    @Value("${rabbitmq.queue.json.name}")
    private String jsonQueue;
    @Value("${rabbitmq.routing.json.key}")
    private String jsonRoutingKey;

    @Bean
    public Queue getQueue() {
        return new Queue(queue);
    }

    @Bean
    public Queue getJsonQueue() {
        return new Queue(jsonQueue);
    }

    @Bean
    public TopicExchange getExchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public Binding getBinding() {
        return BindingBuilder
                .bind(getQueue())
                .to(getExchange())
                .with(routingKey);
    }

    @Bean
    public Binding getJsonBinding() {
        return BindingBuilder
                .bind(getJsonQueue())
                .to(getExchange())
                .with(jsonRoutingKey);
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
