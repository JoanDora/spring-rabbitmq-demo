package com.joandora.demo.rpc_3;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

public class Server {
	public static void main(String[] args) throws Exception {
		ConnectionFactory cf = new ConnectionFactory();
		cf.setHost("192.168.144.128");
		
		Connection conn = cf.newConnection();
		Channel ch = conn.createChannel();
		
		ch.queueDeclare("rpc_queue_joan", false, false, false, null);
		//告诉RabbitMQ同一时间给一个消息给消费者  
		ch.basicQos(1);
		
		QueueingConsumer consumer = new QueueingConsumer(ch);
		ch.basicConsume("rpc_queue_joan", false, consumer);
		
		while(true) {
			Delivery delivery = consumer.nextDelivery();
			String msg = new String(delivery.getBody());
			System.out.println("server get request : " + msg);
			
			String correlationId = delivery.getProperties().getCorrelationId();
			BasicProperties responseProperties = new BasicProperties.Builder().correlationId(correlationId).build();
			
			String responseQueue = delivery.getProperties().getReplyTo();
			
			ch.basicPublish("", responseQueue, responseProperties, getResponseMsg(msg).getBytes());
			ch.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
		}
	}
	
	public static String getResponseMsg(String inputMsg) {
		return "server echo ," + inputMsg;
	}
}
