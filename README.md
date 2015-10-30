#TODO:
	svr_probe2
	probe.log 分离
	

树熊服务器在5秒内(之后会根据数据包的大小动态调整)收不到响应会断掉连接, 考虑到我们的业务场景, 允许一定的数据丢失, 所以失败后树熊不会发起重试(不排除后期加上重试机制的可能).
由于重试机制依赖于第三方的响应, 因此树熊并不能保证无重复的数据包.

### Get Started
* 向树熊索要token.
* 如果需要对消息进行加密, 向树熊索要aes密钥(目前还不支持)
* 向树熊提供一个回调url, 用于树熊推送消息给第三方

### 数据包格式
|参数|说明|
|---|----|
|command|命令类型|
|timestamp| 为后续抵抗重放攻击提供可能|
|nonce| 随机数, 防重放攻击|
|signature|加密/校验流程如下：<ul><li>1. 将token、timestamp、nonce三个参数进行字典序排序</li><li>2. 将三个参数字符串拼接成一个字符串进行sha1加密</li><li>3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信</li></ul>|
|probeData|格式参见附件|

### 回复的格式
|参数|说明|
|---|---|
|errcode|错误码|
|errmsg|错误详情|

例:
```
{"errcode":0,"errmsg":"ok"}
```

|errcode|说明|
|-------|------|
|0|请求成功|
|-1|失败|



### 第三方如何测试?
	TODO:开发一套第三方的调试界面
	调试时的token咋办?
	
	https://mp.weixin.qq.com/debug/cgi-bin/apiinfo?t=index&type=%E6%B6%88%E6%81%AF%E6%8E%A5%E5%8F%A3%E8%B0%83%E8%AF%95&form=%E4%BA%8B%E4%BB%B6%E6%B6%88%E6%81%AF
	提供url, 开发者填写URL，调试时将把消息推送到该URL上
	command:RAWDATA
	probeData: 
	
	TODO:可以考虑使用SDK的方式封装到listener中去
	@RequestMapping("api/postData"){
		switch(request.getParameter("command")){
			case:TreebearCommand.RAWDATA
				//request.getParam("jsonData")
				//store to db
				//response to treebear
			case:TreebearCommand.DATAFORMAT1
				//...
			case:TreebearCommand.REALABLEDATA
				//...
				//响应树熊以便树熊重试
	}
	
	

### 附录
* probeData

```
	[
		{probeMac},
		{probeMac},
		...,
		{probeMac}
	]
```

* probeMac


``` 
	{
		"probeMac":"00:00:00:00:00:00",
		"devMac":"00:00:00:00:00:00",
		"probeSN":"3041158L01FA",
		"timeStamp":1446010565999,
		...更多列
	}
```


api和安全相关的代码应该分开. 比如用interceptor

#### 安全机制
1. 伪造:签名
2. 重放攻击
    
    对付重放攻击，一般是除了时间戳，再加一个nonce参数，这样时间戳允许十五分钟偏差，那么服务端只要记录十五分钟内用过的nonce就行了，这样就可以屏蔽重放，但如果请求量比较大，十五分钟也可能产生海量数据。
3. 消息加密: 对称加密


#### 签名示例代码
	String[] tempArr = new String[]{token, timestamp, nonce};
	Arrays.sort(tempArr);
	StringBuffer tempStrSB = new StringBuffer();
    for(String str : tempArr){
    	tempStrSB.append(str);
    }
    String tempStr = tempStrSB.toString();
    tempStr = DigestUtils.shaHex(tempStr);
    if(tempStr.equals(signature)){
        responseText(echostr);
    }
    
![image](https://github.com/a596620989/treebear-message-api/img/probe-message-post.jpg) 
