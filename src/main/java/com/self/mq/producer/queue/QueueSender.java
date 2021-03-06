package com.self.mq.producer.queue;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;
/**
 * 队列消息生产者，发送消息到队列
 * @author lixiao
 *
 */
@Component("queueSender")
public class QueueSender {

		@Autowired
		@Qualifier("jmsQueueTemplate")
		private JmsTemplate jmsTemplate;
		
		@Autowired
		private GetResponse getResponse;
		
		public void send(String queueName,final String message){
			jmsTemplate.send(queueName, new MessageCreator(){
				public Message createMessage(Session session) throws JMSException{
					Message msg = session.createTextMessage(message);
					
					//配置消费者应答相关内容
					Destination tempDest = session.createTemporaryQueue();
					MessageConsumer responseConsumer = session.createConsumer(tempDest);
					responseConsumer.setMessageListener(getResponse);
					msg.setJMSReplyTo(tempDest);
					//消费者应答的id，用来对发送是消息和应答的消息进行匹配
					String uid = System.currentTimeMillis()+"";
					msg.setJMSCorrelationID(uid);
					return msg;
				}
			});
		}
}