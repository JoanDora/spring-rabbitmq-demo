/**
 * Copyright (c) 2006-2015 Hzins Ltd. All Rights Reserved. 
 *  
 * This code is the confidential and proprietary information of   
 * Hzins. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Hzins,http://www.hzins.com.
 *  
 */   
package com.joandora.spring.rpc.client; 

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * <p>
 * 
 * </p>
 * @author	hz15111811 
 * @date	2015年11月14日 下午2:30:54
 * @version      
 */
public class ClientSetUp {
    public static void main(String[] args) throws InterruptedException {
	AbstractApplicationContext cxt = new ClassPathXmlApplicationContext("classpath:com/joandora/spring/rpc/client/client.xml");
	cxt.registerShutdownHook();
	AmqpTemplate  amqpTemplate = cxt.getBean(AmqpTemplate.class);
	amqpTemplate.convertAndSend("joandora");
	while(true) {
		Thread.sleep(1000);
	}
}
}
 