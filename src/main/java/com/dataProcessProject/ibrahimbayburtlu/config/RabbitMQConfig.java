package com.dataProcessProject.ibrahimbayburtlu.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.name}")
    private String mySQLDataQueue;

    @Value("${rabbitmq.queue.name.mongo}")
    private String mongoDataQueue;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    @Bean
    public Queue mySQLDataQueue() {
        return new Queue(mySQLDataQueue);
    }

    @Bean
    public Queue mongoDataQueue() {
        return new Queue(mongoDataQueue);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public Binding mySQLQueueBinding(Queue mySQLDataQueue, TopicExchange exchange) {
        return BindingBuilder.bind(mySQLDataQueue).to(exchange).with(routingKey);
    }

    @Bean
    public Binding mongoDataQueueBinding(Queue mongoDataQueue, TopicExchange exchange) {
        return BindingBuilder.bind(mongoDataQueue).to(exchange).with(routingKey);
    }
}
