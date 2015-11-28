package com.joandora.spring.rpc.pure_java;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;

public class Test {
	public static void main(String[] args) {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory("192.168.56.128");
		connectionFactory.setUsername("guest");
		connectionFactory.setPassword("guest");

		Connection connection = connectionFactory.createConnection();
	}
}
