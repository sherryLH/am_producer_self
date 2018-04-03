package com.self.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.self.mq.producer.queue.QueueSender;
import com.self.mq.producer.topic.TopicSender;

@Controller
@RequestMapping("/activemq")
public class ActivemqController {
	@Resource
	QueueSender queueSender;
	@Resource
	TopicSender topicSender;
	
	/**
	 * 发送消息到队列
	 * Queue队列：仅有一个订阅者会收到消息，消息一旦被处理，就不会存在队列中
	 * @param message
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queueSender")
	public String queueSender(@RequestParam("message")String message){
		System.out.println("收到客户端queue请求："+message);
		queueSender.send("test1.queue", message);//FIXME  ?test1
		return "success";
	}
	
	/**
	 * 发送topic消息到主题
	 * Topic主题：放入一个消息，所有订阅者都会收到（一对多）
	 * @param message
	 * @return
	 */
	@ResponseBody
	@RequestMapping("topicSender")
	public String topicSender(@RequestParam("message")String message){
		System.out.println("收到客户端topic请求："+message);
		topicSender.send("test.topic", message);
		return "success";
	}
}
