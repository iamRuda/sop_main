package com.example.springdatabasicdemo.amqp;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    @Bean
    public Queue truckQueue() {
        return new Queue("truckQueue", true); // durable = true
    }

    @Bean
    public Queue checkedTruckQueue() {
        return new Queue("checkedTruckQueue", true); // durable = true
    }

    @Bean
    public Queue inspectedQueue() {
        return new Queue("inspectedQueue", true); // durable = true
    }

    @Bean
    public Queue checkedInspectedQueue() {
        return new Queue("checkedInspectedQueue", true); // durable = true
    }

    @Bean
    public Queue inspectionQueue() {
        return new Queue("inspectionQueue", true); // durable = true
    }

    @Bean
    public Queue checkedInspectionQueue() {
        return new Queue("checkedInspectionQueue", true); // durable = true
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
