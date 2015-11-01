# 概述
此文档为树熊和第三方厂商的探针数据对接方案, 树熊会将第三方所购买的探针设备的实时数据推送到第三方厂商的服务器.

![image](https://github.com/a596620989/treebear-message-api/blob/master/img/probe-message-post.jpg) 


### 规约
1. 树熊服务器在5秒内(之后会根据数据包的大小动态调整)收不到响应会断掉连接, 考虑到我们的业务场景, 允许一定的数据丢失, 所以失败后树熊不会发起重试(不排除后期加上重试机制的可能).
2. 由于重试机制依赖于第三方的响应, 因此树熊并不能保证无重复的数据包.
3. 数据统一使用utf-8编码

### Get Started
* 向树熊索要token.
* 如果需要对消息进行加密, 向树熊索要aes密钥(目前还不支持)
* 向树熊提供一个回调url, 用于树熊推送消息给第三方

### 数据包格式
|参数|说明|
|---|----|
|version|协议版本号,当前1.0|
|method|方法, 如treebear.probedata.post|
|timestamp| 为后续抵抗重放攻击提供可能|
|nonce| 随机数, 防重放攻击|
|signature|加密/校验流程如下：<ul><li>1. 将token、timestamp、nonce三个参数进行字典序排序</li><li>2. 将三个参数字符串拼接成一个字符串进行sha1加密</li><li>3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于树熊</li></ul>|
|probeMac|探针mac|
|probeSn|探针序列号, 如3041158L01FA|
|probeData|实际的payload,包含该探针下探测到的设备mac,格式参见附件|

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

1. 访问http://wifitest2.witown.com:10808/api/debug.htm
2. 选择要调试的方法, 按要求填入url, toke等参数
3. 之后树熊会向第三方提供的url中发送post请求
	

## 附录
#### probeData

```
	[
		{devMac},
		{devMac},
		...,
		{devMac}
	]
```

#### devMac(设备mac)


``` 
	{
		"devMac":"00:00:00:00:00:00", //设备mac
		"rssi": -55 //信号强度
		"timeStamp":1446010565999, //探测这条消息时的时间戳
		...更多列
	}
```

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
    

## TODO
1. svr_probe2
2. probe.log 分离
3. post重试可以依赖ons自身的重试机制
4. 开发一套第三方的调试界面 -> done