package com.joandora.spring.rpc.service;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServerSetUp {
	public static void main(String[] args) throws InterruptedException {
		AbstractApplicationContext cxt = new ClassPathXmlApplicationContext("classpath:com/joandora/spring/rpc/service/server.xml");
		cxt.registerShutdownHook();
		
		while(true) {
			Thread.sleep(1000);
		}
	}
}
