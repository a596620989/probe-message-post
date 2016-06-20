## 第三方探针绑定接口

此接口用于第三方向树熊绑定探针, 探针只有绑定到第三方后, 树熊才会将探针的实时数据通过实时数据接口转发给第三方. 此接口需要第三方主动调用. 方法名为"treebear.device.probe.post.router.set",具体调用方式参见树熊open平台.

## 应用级别输入参数
|参数|说明|
|---|----|
|token|树熊提供给第三方的token|
|probeSn|探针序列号, 如3041158L01FA|
|captcha|探针校验码, 树熊通过校验码来判断第三方拥有探针所有权|

## 应用级别返回结果
|参数|说明|
|---|---|
|callResult|true/false|