package com.shopping.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    //配置direct类型交换机
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("directExchange");
    }

    //配置fanout类型交换机
    @Bean
    public FanoutExchange FanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }

    //配置topic类型交换机
    @Bean
    public TopicExchange TopicExchange() {
        return new TopicExchange("topicExchange");
    }

    //配置一个队列
    @Bean
    public Queue directQueue() {
        return new Queue("directQueue");
    }

    //配置队列和交换机绑定
    @Bean
    public Binding directBinding(Queue directQueue, DirectExchange directExchange) {
        //完成绑定，directQueue：绑定的队列，directExchange：绑定的交换机，directRoutingKey：routingKey
        return BindingBuilder.bind(directQueue).to(directExchange).with("directRoutingKey");
    }

}
