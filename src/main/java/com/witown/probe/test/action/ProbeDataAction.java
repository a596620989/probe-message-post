package com.witown.probe.test.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.witown.probe.web.service.ProbeService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

@Controller("probeDataAction")
@Scope("prototype")
public class ProbeDataAction   {
	
	@Autowired
	private ProbeService probeService;
	
//	@Autowired
//	private OpenThirdInfoDAO openThirdInfoDAO;

	protected static Logger            logger           = LoggerFactory.getLogger("WebLogger"); 

	static int i = 0;

	@RequestMapping("/api/postdataslow.htm")
	public String postdataSlow(HttpServletRequest request, HttpServletResponse response) {
		logger.info("我不会写代码, 我睡了5秒" + i);

		logger.info("appid:" + probeService.aas().getAppid());
		i++;
		try {
			Thread.sleep(1000 * i);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping("/api/debug.htm")
	public String debug(HttpServletRequest request, HttpServletResponse response) {
		logger.info("enter debug");
		return "debug";
	}
	
	@RequestMapping("/api/do_debug.htm")
	public String doDebug(HttpServletRequest request) {
		
		String method = request.getParameter("method");
		String nonce = request.getParameter("nonce");	
//		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String probeData = request.getParameter("probeData");	
		String probeSn = request.getParameter("probeSn");	
//		String probeMac = request.getParameter("probeMac");	
		String url = request.getParameter("url");
		String token = request.getParameter("token");
		
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
//		http://hc.apache.org/httpcomponents-client-ga/tutorial/html/fundamentals.html#d5e49
		HttpPost httpPost = new HttpPost(url);
		
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("method", method));
		nvps.add(new BasicNameValuePair("timestamp",timestamp));
		nvps.add(new BasicNameValuePair("nonce", nonce));
		nvps.add(new BasicNameValuePair("signature", sign(timestamp,nonce,token)));
		nvps.add(new BasicNameValuePair("probeData", probeData));
		nvps.add(new BasicNameValuePair("probeSn", probeSn));
		
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
			logger.info(e.getMessage());
			e.printStackTrace();
		}

		try {
			logger.info(response.getStatusLine().toString());
			HttpEntity entity = response.getEntity();
			logger.info("response:" + EntityUtils.toString(entity));
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
		
		return "success";
	}
	
	
	/**
	 * 自己模拟的一个第三方实现, 接受postdata请求.
	 * [{ProbeMessage},...,{ProbeMessage}]
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/api/postdata.htm")
	public String postdata(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		String token = "gjA4fd0";
		
		request.setCharacterEncoding("UTF-8");
		String method = request.getParameter("method");
		String nonce = request.getParameter("nonce");	
		String signature = request.getParameter("signature");	
		String timestamp = request.getParameter("timestamp");
		
		if(signature== null || !signature.equals(sign(timestamp, nonce, token))){
			System.err.println("invalidate sign");
			return null;
		}
		
		String chinese = request.getParameter("chinese");
		logger.info(chinese);
		if (TreebearCommand.PROBEDATA_POST.equals(method)) {
			String probeData = request.getParameter("probeData");
			net.sf.json.JSONArray probeArray = (JSONArray) JSONSerializer.toJSON(probeData);
			logger.info("probeArray size:" + probeArray.size());
			for (Object jo : probeArray) {
				JSONObject json = (JSONObject) jo;
				logger.info("probeMac: {}", json.get("probeMac"));
				logger.info("devMac: {}", json.get("devMac"));
				logger.info("probeSN: {}", json.get("probeSN"));
				logger.info("timeStamp: {}", json.get("timeStamp")); 
			}
			try {
				// 响应树熊以便树熊重试
				response.getWriter().println("{\"errcode\":0,\"errmsg\":\"ok\"}");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
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
