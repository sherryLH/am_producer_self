package com.self.mq.producer.queue;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.stereotype.Component;
/**
 * 接收消费者应答的类
 * @author 666666
 *
 */
@Component
public class GetResponse implements MessageListener {

	public void onMessage(Message arg0) {
		try{
			String textMsg = ((TextMessage)arg0).getText();
			System.out.println("GetResponse 收到消费者响应报文：" + textMsg);
		}catch(JMSException e){
			e.printStackTrace();
		}
	}

}
