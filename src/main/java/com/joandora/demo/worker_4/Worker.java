package com.joandora.demo.worker_4;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class Worker {
	public static void main(String[] args) throws Exception{
		ConnectionFactory cf = new ConnectionFactory();
		cf.setHost("localhost");
		Connection conn = cf.newConnection();
		
		Channel ch = conn.createChannel();
		ch.basicQos(1);
		
		ch.queueDeclare("workQueue", false, false, false, null);
		
		QueueingConsumer consumer = new QueueingConsumer(ch);
		
		ch.basicConsume("workQueue", false, consumer);
		
		while(true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			
			System.out.println("worker : " + new String(delivery.getBody()));
			
			Thread.sleep(4000);
			
			ch.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
		}
	}
}
