package com.eros.ons;

import java.util.Properties;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.PropertyValueConst;

public class ConsumerTest2 {
    public static void main(String[] args) {
        Properties properties = new Properties();
		properties.put(PropertyKeyConst.ConsumerId, "CID_1002");
		properties.put(PropertyKeyConst.AccessKey, "0XznofrZlweGBMOW");
		properties.put(PropertyKeyConst.SecretKey, "szjziLTxr9NZAXVOcYGsgBKOY4ENan");
        properties.put(PropertyKeyConst.MessageModel,
				 PropertyValueConst.CLUSTERING);
        Consumer consumer = ONSFactory.createConsumer(properties);
        consumer.subscribe("eros_test", "*", new MessageListener() {
            public Action consume(Message message, ConsumeContext context) {
				System.out.println("Receive: " + message.getKey());
                return Action.CommitMessage;
            }
        });
        consumer.start();
        System.out.println("Consumer Started");
    }
}    
