package com.joandora.demo.worker_4;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Sender {
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory cf = new ConnectionFactory();
		cf.setHost("localhost");
		Connection conn = cf.newConnection();
		
		Channel channel = conn.createChannel();
		
		channel.queueDeclare("workQueue", false, false, false, null);
		
		for(int i = 0; i < 10; i ++) {
			String message = "您好" + i;
			channel.basicPublish("", "workQueue", false, null, message.getBytes());
		}
		
		channel.close();
		conn.close();
	}
}
