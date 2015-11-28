package com.joandora.demo.hello_1;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Sender {
	public static void main(String[] args) throws IOException, TimeoutException {
		// 1、创建connection工厂
		ConnectionFactory cf = new ConnectionFactory();
		cf.setHost("localhost");
		// 2、获取连接
		/**
		 * 如果factory.setAutomaticRecoveryEnabled(true);
		 * 那那么就说名这个connection连接失败时，默认5秒后会自动重新连接
		 * 这个connection就是AutorecoveringConnection 否则默认是AMQConnection
		 * 
		 * 当然，这里的5秒是可以配置的： // attempt recovery every 10 seconds
		 * factory.setNetworkRecoveryInterval(10000);
		 * 
		 * 如果提供了一个list的地址，则重新连接时会随机选择一个 Address[] addresses = {new
		 * Address("192.168.1.4"), new Address("192.168.1.5")};
		 * factory.newConnection(addresses);
		 */
		// 这里会默认读取host和端口组成 Address数组
		Connection conn = cf.newConnection();

		System.out.println(conn.isOpen());
		// 3、创建channel
		Channel channel = conn.createChannel();
		// 4、创建queue 队列名字、 是否持久化消息
		// 、是否把这个queue限制只有这个connection能使用、是否使用完自动删除、构造器其他参数
		channel.queueDeclare("helloQueue", false, false, false, null);

		String message = "您好";
		// 5、发布消息 exchange名称、queue名称、mandatory、消息
		/**
		 * mandatory和immediate是AMQP协议中basic.pulish方法中的两个标志位，
		 * 它们都有当消息传递过程中不可达目的地时将消息返回给生产者的功能。具体区别在于： 1. mandatory标志位
		 * 当mandatory标志位设置为true时
		 * ，如果exchange根据自身类型和消息routeKey无法找到一个符合条件的queue，那么会调用basic
		 * .return方法将消息返还给生产者；当mandatory设为false时，出现上述情形broker会直接将消息扔掉。 2.
		 * immediate标志位
		 * 当immediate标志位设置为true时，如果exchange在将消息route到queue(s)时发现对应的queue上没有消费者
		 * ，那么这条消息不会放入队列中
		 * 。当与消息routeKey关联的所有queue(一个或多个)都没有消费者时，该消息会通过basic.return方法返还给生产者。
		 * 
		 * 概括来说，mandatory标志告诉服务器至少将该消息route到一个队列中，否则将消息返还给生产者；
		 * immediate标志告诉服务器如果该消息关联的queue上有消费者
		 * ，则马上将消息投递给它，如果所有queue都没有消费者，直接把消息返还给生产者，不用将消息入队列等待消费者了。
		 */
		channel.basicPublish("", "helloQueue", false, null, message.getBytes());
        //6、连接关闭
		channel.close();
		conn.close();
	}
}
