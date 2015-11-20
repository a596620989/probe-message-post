package producer;

import java.util.Properties;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.SendResult;

public class ProducerClient {

	public static void main(String[] args) {
		Properties properties = new Properties();
		properties.put(PropertyKeyConst.ProducerId, "PID_1002");
		properties.put(PropertyKeyConst.AccessKey, "0XznofrZlweGBMOW");
		properties.put(PropertyKeyConst.SecretKey, "szjziLTxr9NZAXVOcYGsgBKOY4ENan");
		Producer producer = ONSFactory.createProducer(properties);

		// 在发送消息前，必须调用start方法来启动Producer，只需调用一次即可。
		producer.start();

		for (int i = 0; i < 100; i++) {
			Message msg = new Message(
					// Message Topic
					"eros_test",
					// Message Tag,
					// 可理解为Gmail中的标签，对消息进行再归类，方便Consumer指定过滤条件在ONS服务器过滤
					"3041158L03E8",
					// Message Body
					// 任何二进制形式的数据，ONS不做任何干预，需要Producer与Consumer协商好一致的序列化和反序列化方式
					"[{\"devMac\":\"00:00:00:00:00:00\",\"rssi\": -55,\"timeStamp\":1446010565999, \"leave\": 1},{\"devMac\":\"00:00:00:00:00:00\",\"rssi\": -55,\"timeStamp\":1446010565999, \"leave\": 1},{\"devMac\":\"00:00:00:00:00:00\",\"rssi\": -55,\"timeStamp\":1446010565999, \"leave\": 1},{\"devMac\":\"00:00:00:00:00:00\",\"rssi\": -55,\"timeStamp\":1446010565999, \"leave\": 2}]".getBytes());
			// 设置代表消息的业务关键属性，请尽可能全局唯一。
			// 以方便您在无法正常收到消息情况下，可通过ONS Console查询消息并补发。
			// 注意：不设置也不会影响消息正常收发
			msg.setKey("ORDERID_" + i);

			// 发送消息，只要不抛异常就是成功
			SendResult sendResult = producer.send(msg);
			System.out.println(sendResult);
		}
		// 在应用退出前，销毁Producer对象
		// 注意：如果不销毁也没有问题
		producer.shutdown();
	}
}