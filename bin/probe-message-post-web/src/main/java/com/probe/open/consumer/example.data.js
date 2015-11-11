[
	{
		"probeMac":"00:00:00:00:00:00",
		"devMac":"00:00:00:00:00:00",
		"probeSN":"3041158L01FA",
		"timeStamp":1446010561239
	}
	{
		"probeMac":"00:00:00:00:00:00",
		"devMac":"AA:00:00:00:00:00",
		"probeSN":"3041158L01FA",
		"timeStamp":1446010565878
	}
	{
		"probeMac":"00:00:00:00:00:00",
		"devMac":"BB:00:00:00:00:00",
		"probeSN":"3041158L01FA",
		"timeStamp":1446010511199
	}
]


[{"probeMac":"00:00:00:00:00:00","devMac":"00:00:00:00:00:00","probeSN":"3041158L01FA","timeStamp":1446010561239},{"probeMac":"00:00:00:00:00:00","devMac":"AA:00:00:00:00:00","probeSN":"3041158L01FA","timeStamp":1446010565878},{"probeMac":"00:00:00:00:00:00","devMac":"BB:00:00:00:00:00","probeSN":"3041158L01FA","timeStamp":1446010511199}]

"[{\"probeMac\":\"00:00:00:00:00:00\",\"devMac\":\"00:00:00:00:00:00\",\"probeSN\":\"3041158L01FA\",\"timeStamp\":1446010561239},{\"probeMac\":\"00:00:00:00:00:00\",\"devMac\":\"AA:00:00:00:00:00\",\"probeSN\":\"3041158L01FA\",\"timeStamp\":1446010565878},{\"probeMac\":\"00:00:00:00:00:00\",\"devMac\":\"BB:00:00:00:00:00\",\"probeSN\":\"3041158L01FA\",\"timeStamp\":1446010511199}]"


package com.witown.open.probe;

import java.util.Properties;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.PropertyValueConst;


/**
 * 一个topic可以被多个消费者实例消费, 对应于我们的情况会是一台服务器一个消费者实例
 * @author eros
 *
 */
public class ConsumerClient2 {
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
