package com.joandora.demo.pubsub_2;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class Receiver {
	private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] argv)
                  throws Exception {
        // 1、创建connection工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        // 2、创建connection
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        // 3、创建fanout类型的exchange
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        // 4、创建一个queue 并绑定到上述fanout类型的exchange上
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        // 5、创建消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        // 6、channel绑定消费者 -zidongack
        channel.basicConsume(queueName, true, consumer);

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());

            System.out.println(" [x] Received '" + message + "'");
        }
    }
}
