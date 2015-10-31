package com.witown.probe.test.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
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

	static int i = 0;

	@RequestMapping("/api/postdataslow.htm")
	public String postdataSlow(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("我不会写代码, 我睡了5秒" + i);

		System.out.println("appid:" + probeService.aas().getAppid());
		i++;
		try {
			Thread.sleep(1000 * i);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * [{ProbeMessage},...,{ProbeMessage}]
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/api/postdata.htm")
	public String postdata(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

		String token = "gjA4fd0";
		
		request.setCharacterEncoding("UTF-8");
		String command = request.getParameter("command");
		String nonce = request.getParameter("nonce");	
		String signature = request.getParameter("signature");	
		String timestamp = request.getParameter("timestamp");
		
		if(signature== null || !signature.equals(sign(timestamp, nonce, token))){
			System.err.println("invalidate sign");
			return null;
		}
		
		String chinese = request.getParameter("chinese");
		System.out.println(chinese);
		if (TreebearCommand.PROBEDATA_POST.equals(command)) {
			String probeData = request.getParameter("probeData");
			net.sf.json.JSONArray nameArray = (JSONArray) JSONSerializer.toJSON(probeData);
			System.out.println(nameArray.size());
			for (Object jo : nameArray) {
				JSONObject json = (JSONObject) jo;
				System.out.println(json.get("probeMac"));
				System.out.println(json.get("devMac"));
				System.out.println(json.get("probeSN"));
				System.out.println(json.get("timeStamp")); 
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
