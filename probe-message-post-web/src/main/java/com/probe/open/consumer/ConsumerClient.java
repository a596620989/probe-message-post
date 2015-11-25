package com.probe.open.consumer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.PropertyValueConst;
import com.probe.open.service.PostRouterService;
import com.witown.portal.domain.Router;
import com.witown.portal.service.RemoteRouterService;

/**
 * 一个topic可以被多个消费者实例消费, 对应于我们的情况会是一台服务器一个消费者实例
 * @author eros
 *
 */
@Component
public class ConsumerClient {
	
	
	@Autowired
	private PostRouterService postRouterService;
	
	@Autowired
	private RemoteRouterService remoteRouterService;
	
	@Value("${probe.message.ons.consumerid}")
	private String consumerId;
	@Value("${probe.message.ons.accesskey}")
	private String accessKey;
	@Value("${probe.message.ons.secretkey}")
	private String secretKey;
	@Value("${probe.message.ons.topic}")
	private String topic;
	
	
	protected static Logger            logger           = LoggerFactory.getLogger("ConsumerLogger"); 
	
	/**
	 * 当容器启动时自动执行
	 */
	public void startup(){
		
		logger.info("begin to start consumer >>>");

		Properties properties = new Properties();
		properties.put(PropertyKeyConst.ConsumerId, consumerId);
		properties.put(PropertyKeyConst.AccessKey, accessKey);
		properties.put(PropertyKeyConst.SecretKey, secretKey);
		
		/**
         * 设置消费端线程数, 阿里云默认值为20
         */
        properties.put(PropertyKeyConst.ConsumeThreadNums,20);
		properties.put(PropertyKeyConst.MessageModel,
				 PropertyValueConst.CLUSTERING);
		Consumer consumer = ONSFactory.createConsumer(properties);
		consumer.subscribe(topic, "*", new MessageListener() {
			public Action consume(Message message, ConsumeContext context) {
				messageHandler(message);
				//现阶段不进行重传. TODO: 后期加上重传
				logger.info("message commited");
				return Action.CommitMessage;
			}
		});
		consumer.start();
		logger.info("begin to start consumer end <<<");
	}
	
	private void messageHandler(Message message) {
		//TODO: 如何保证不同用户的隔离, 即A用户超时不会影响B
		logger.debug("Receive: " + message.getKey());
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String probeSn = message.getTag();
		logger.debug("probeSn:" + probeSn);
//		http://hc.apache.org/httpcomponents-client-ga/tutorial/html/fundamentals.html#d5e49
		String routerAddress = postRouterService.getAddress(probeSn);
		if(routerAddress == null){
			logger.warn("routerAddress is null");
			return;
		}
		logger.info("routerAddress :" + routerAddress);
		HttpPost httpPost = new HttpPost(routerAddress);
		List<NameValuePair> nvps = buildPostFormEntity(message); 
		
		CloseableHttpResponse response = null;

		//超过一定时间自动关闭
		int timeout = 5;
		RequestConfig config = RequestConfig.custom()
				.setConnectTimeout(timeout * 1000)
				.setConnectionRequestTimeout(timeout * 1000)
				.setSocketTimeout(timeout * 1000)
				.build();
		httpPost.setConfig(config);
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
			response = httpclient.execute(httpPost);
		} catch (Exception e){
			logger.error(e.getMessage());
			e.printStackTrace();
		}

		try {
			logger.info(response.getStatusLine().toString());
			HttpEntity entity = response.getEntity();
			logger.debug("response:" + EntityUtils.toString(entity));
			// do something useful with the response body
			// and ensure it is fully consumed
			EntityUtils.consume(entity);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private List<NameValuePair> buildPostFormEntity(Message message){
		logger.info("building post request start");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		String timestamp = String.valueOf(System.currentTimeMillis());
		Random random = new Random();
		String nonce = String.valueOf(random.nextInt(1000));
		String token = postRouterService.getToken(message.getTag());
		
		nvps.add(new BasicNameValuePair("version", "1.0"));
		nvps.add(new BasicNameValuePair("method", "treebear.probedata.post"));
		nvps.add(new BasicNameValuePair("timestamp",timestamp));
		nvps.add(new BasicNameValuePair("nonce", nonce));
		nvps.add(new BasicNameValuePair("signature", sign(timestamp,nonce,token)));
		nvps.add(new BasicNameValuePair("probeData", new String(message.getBody())));
		String probeSn = message.getTag();
		nvps.add(new BasicNameValuePair("probeSn", probeSn));
		Router router = remoteRouterService.getCachedRouterBySeq(probeSn);
		String mac = null;
		if (router != null){
			mac = router.getMac();
		}
		nvps.add(new BasicNameValuePair("probeMac", mac));
		
		
		logger.info("building post request end");
		
		return nvps;
	}
	
	
	private String sign(String timestamp, String nonce, String token){
		String[] tempArr = new String[]{token, timestamp, nonce};
		Arrays.sort(tempArr);
		StringBuffer tempStrSB = new StringBuffer();
	    for(String str : tempArr){
	    	tempStrSB.append(str);
	    }
	    String tempStr = tempStrSB.toString();
	    tempStr = DigestUtils.sha1Hex(tempStr);
	    
		return tempStr;
	}
}
