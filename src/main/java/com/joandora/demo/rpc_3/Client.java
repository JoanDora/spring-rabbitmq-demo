package com.joandora.demo.rpc_3;

import java.util.UUID;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.AMQP.Queue.DeclareOk;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

public class Client {
	public static void main(String[] args) throws Exception {
		ConnectionFactory cf = new ConnectionFactory();
		cf.setHost("192.168.144.128");
		
		Connection conn = cf.newConnection();
		Channel channel = conn.createChannel();
		
		// 申明一个队列，不带参数的话，这个队列在连接关闭时，启动删除
		DeclareOk ok = channel.queueDeclare();
		// rabbitmq会自动给新生成的队列取名
		String returnQueueName = ok.getQueue();
		
		QueueingConsumer consumer = new QueueingConsumer(channel);
		// 指定消费队列 ，服务端在接受到消息后，会往这个队列里面放入返回消息
		channel.basicConsume(returnQueueName, true, consumer);
		
		String correlationId = UUID.randomUUID().toString();
		//封装一个correlationId相当于是一个标志，其实没什么用。服务端接收后，又传回给客户端。客户端可以对这个值进行比较。
		BasicProperties proeprties = new BasicProperties.Builder().correlationId(correlationId).replyTo(returnQueueName).build();
		
		channel.basicPublish("", "rpc_queue_joan", proeprties, "hello".getBytes());
		
		while(true) {
			Delivery delivery = consumer.nextDelivery();
			if(delivery.getProperties().getCorrelationId().equals(correlationId)) {
				System.out.println("client get response : " +new String(delivery.getBody()));
				break;
			}
		}
		conn.close();
	}
}
