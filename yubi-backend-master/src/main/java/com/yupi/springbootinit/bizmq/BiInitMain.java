package com.yupi.springbootinit.bizmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于创建测试程序用到的交换机和队列（只用在程序启动前执行一次）
 */
public class BiInitMain {

    public static void main(String[] args) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            String EXCHANGE_NAME = BiMqConstant.BI_EXCHANGE_NAME;
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");

            // 声明死信交换机和死信队列并绑定
            String DEAD_EXCHANGE_NAME = BiMqConstant.DEAD_BI_EXCHANGE_NAME;
            channel.exchangeDeclare(DEAD_EXCHANGE_NAME,"direct");

            String deadBiQueueName = BiMqConstant.DEAD_BI_QUEUE_NAME;
            channel.queueDeclare(deadBiQueueName, true, false, false, null);
            channel.queueBind(deadBiQueueName, DEAD_EXCHANGE_NAME,  BiMqConstant.DEAD_BI_ROUTING_KEY);
            //声明消息过期
//            Map<String, Object> args1 = new HashMap<String, Object>();
//            args1.put("x-message-ttl", 5000);

            // 创建队列，随机分配一个队列名称
            Map<String,Object> map = new HashMap<>();

            // BI分析队列绑定死信交换机
            map.put("x-dead-letter-exchange", DEAD_EXCHANGE_NAME);
            map.put("x-dead-letter-routing-key", BiMqConstant.DEAD_BI_ROUTING_KEY);
            map.put("x-message-ttl", 60000);

            String queueName = BiMqConstant.BI_QUEUE_NAME;
            channel.queueDeclare(queueName, true, false, false, map);
            channel.queueBind(queueName, EXCHANGE_NAME,  BiMqConstant.BI_ROUTING_KEY);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
