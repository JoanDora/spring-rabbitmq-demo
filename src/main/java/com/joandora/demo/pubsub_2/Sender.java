package com.joandora.demo.pubsub_2;

import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Sender {
	private static final String EXCHANGE_NAME = "logs";

	public static void main(String[] argv) throws java.io.IOException, TimeoutException {
		// 1、创建conenction工厂
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		// 2、创建connection
		Connection connection = factory.newConnection();
		// 3、创建通道
		Channel channel = connection.createChannel();
        // 4、声明exchange，也可以直接声明queue，则exchange是默认的
		// excahenge名字、exchange类型fanout-任何发送到此exchange的消息都将发送到与之绑定的queue
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

		String message = "fsadfdsaf";
        // fanout类型的exchange允许没有routingkey
		channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
		System.out.println(" [x] Sent '" + message + "'");

		channel.close();
		connection.close();
	}
}
