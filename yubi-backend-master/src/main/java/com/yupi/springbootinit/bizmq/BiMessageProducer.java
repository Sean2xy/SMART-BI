package com.yupi.springbootinit.bizmq;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

@Component
public class BiMessageProducer {

    @Resource
    private RabbitTemplate rabbitTemplate;

    // Define the expiration time in milliseconds
    //long expirationTimeMillis = 100; // 1 minute

    /**
     * 发送消息
     * @param message
     */
    public void sendMessage(String message) {

//        MessagePostProcessor messagePostProcessor = message1 -> {
//            message1.getMessageProperties().setExpiration(String.valueOf(expirationTimeMillis));
//            return message1;
//        };

        MessageProperties messageProperties = new MessageProperties();
        //设置过期时间

        messageProperties.setExpiration("1");

        //这个参数是用来做消息的唯一标识
        //发布消息时使用，存储在消息的headers中
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());


        rabbitTemplate.convertAndSend(BiMqConstant.BI_EXCHANGE_NAME, BiMqConstant.BI_ROUTING_KEY, message);


    }

}
