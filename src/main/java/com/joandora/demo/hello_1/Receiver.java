package com.joandora.demo.hello_1;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class Receiver {
	public static void main(String[] args) throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException, TimeoutException {
		// 1、创建connection工厂
		ConnectionFactory cf = new ConnectionFactory();
		cf.setHost("localhost");
		// 2、获取connection
		Connection conn = cf.newConnection();
		
		System.out.println(conn.isOpen());
		// 3、创建channel
		Channel channel = conn.createChannel();
		// 4、创建queue   queue名称、是否持久化消息、是否connection独占、是否自动删除、构造器其他参数
		channel.queueDeclare("helloQueue", false, false, false, null);
		// 5、创建一个消费者  内部是阻塞队列
		QueueingConsumer consumer = new QueueingConsumer(channel);
		// 6、channel绑定消费者     消费队列名称、是否自动ack、消费者-这里有消息的时候将回调consumer
		channel.basicConsume("helloQueue", false, consumer);
		// 7、循环读取消息
		while(true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String msg = new String(delivery.getBody());
			System.out.println(msg);
			// 手动确认收到消息
			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
		}
	}
}
