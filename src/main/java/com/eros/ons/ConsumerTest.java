package com.eros.ons;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.Header;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.PropertyValueConst;

public class ConsumerTest {
	public static void main(String[] args) {
		Properties properties = new Properties();
		properties.put(PropertyKeyConst.ConsumerId, "CID_1002");
		properties.put(PropertyKeyConst.AccessKey, "0XznofrZlweGBMOW");
		properties.put(PropertyKeyConst.SecretKey, "szjziLTxr9NZAXVOcYGsgBKOY4ENan");
		/**
         * 设置消费端线程数固定为20
         */
//        properties.put(PropertyKeyConst.ConsumeThreadNums,20);
		properties.put(PropertyKeyConst.MessageModel,
				 PropertyValueConst.CLUSTERING);
		Consumer consumer = ONSFactory.createConsumer(properties);
		consumer.subscribe("eros_test", "*", new MessageListener() {
			public Action consume(Message message, ConsumeContext context) {
				//TODO: 能否在consume里面使用线程池?
				//TODO: 如何保证不同用户的隔离, 即A用户超时不会影响B
//				System.out.println(Thread.currentThread().getId());
				System.out.println("Receive: " + message.getKey());
				CloseableHttpClient httpclient = HttpClients.createDefault();
				String probeSn = message.getTag();
//				http://hc.apache.org/httpcomponents-client-ga/tutorial/html/fundamentals.html#d5e49
				HttpPost httpPost = new HttpPost(getRouterAddress(probeSn));
				List<NameValuePair> nvps = buildPostFormEntity(message); 
				
				CloseableHttpResponse response2 = null;

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
					response2 = httpclient.execute(httpPost);
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				try {
//					System.out.println(response2.getStatusLine());
					HttpEntity entity2 = response2.getEntity();
//					System.out.println("response:" + EntityUtils.toString(entity2));
					entity2.getContent();
					// do something useful with the response body
					// and ensure it is fully consumed
					EntityUtils.consume(entity2);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {
						response2.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				//现阶段不进行重传. TODO: 后期加上重传
				return Action.CommitMessage;
			}
		});
		consumer.start();
	}
	
	private static List<NameValuePair> buildPostFormEntity(Message message){
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		String timestamp = String.valueOf(System.currentTimeMillis());
		Random random = new Random();
		String nonce = String.valueOf(random.nextInt(1000));
		String token = getToken(message.getTag());
		
		nvps.add(new BasicNameValuePair("command", "RAWDATA"));
		nvps.add(new BasicNameValuePair("timestamp",timestamp));
		nvps.add(new BasicNameValuePair("nonce", nonce));
		nvps.add(new BasicNameValuePair("signature", sign(timestamp,nonce,token)));
		nvps.add(new BasicNameValuePair("probeData", new String(message.getBody())));
		nvps.add(new BasicNameValuePair("chinese", "中文"));
		
		return nvps;
	}
	
	private static String getRouterAddress(String sn){
		if("3041158L01FA".equalsIgnoreCase(sn)){
			return "http://127.0.0.1:18080/witown-cheat-sheet/api/postdata.htm"; 
		}
		System.err.println("err occur" + sn);
		return "";
	}
	
	/**
	 * sn -> router mapping -> appid -> token
	 * @param sn
	 * @return
	 */
	private static String getToken(String sn){
		if("3041158L01FA".equals(sn)){
			return "gjA4fd0";
		}
		return "";
	}
	
	private static String sign(String timestamp, String nonce, String token){
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
