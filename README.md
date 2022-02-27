# SmartHome

😁SmartHome 一款基于ZigBee的智能家居的设计于实现😂

简介：

本项目针对传统智能家居系统布线困难、成本高昂和使用不便的问题，分析了CC2530开发板、ZigBee协议栈、网关、腾讯云服务器、MySQL数据库、Android客户端等物联网技术的特点，在此基础上设计了以CC2530为主控制器，传感器为终端采集设备，家居电器为终端控制设备，服务器和数据库作为数据接收、处理、存储设备，Android客户端作为显示设备。使用该系统用户可以通过手机APP实时查看家居的温度、湿度、烟雾浓度和光照强度，并且远程操控风扇、电灯和蜂鸣器，同时用户也可以查看统计数据和并以Excel表格导出，如果出现异常情况，系统会自动启动蜂鸣器报警并用邮件通知用户。通过该系统的使用，提供给用户一个安全、智能、便捷、舒适的居住环境。

技术点：

- 硬件：CC2530，ZigBee协议栈，各种常用传感器使用等
- 网关：ESP-01s 构建智能网关，将硬件接入互联网
- 家电控制技术：常用家电和驱动电路
- 服务端：SpringMVC, MySQL，Socket等
- Android客户端：RxJava，Okhttp，Retrofit，Gson，MPAndroidChart，WebSocket等

开发软件：

- IAR：硬件部分的代码编写集成环境
- IDEA: 后台服务器的代码编写的集成开发环境
- AndroidStudio：Android客户端的代码编写的集成开发环境
- Tomcat：一个支持HTTP协议的Web容器

硬件图示：

| 硬件图片                                                     |   名称   | 功能           |
| ------------------------------------------------------------ | :------: | -------------- |
| <center><img src="https://cdn.jsdelivr.net/gh/ThinkingXuan/HexoStaticImage/img/image-20220227145925353.png" width="100" center=true></center> |  CC2530  | 单片机控制器   |
| <center><img src="https://cdn.jsdelivr.net/gh/ThinkingXuan/HexoStaticImage/img/image-20220227151216878.png" width="100" center=true></center> | ESP-01s  | 组成智能网关   |
| <center><img src="https://cdn.jsdelivr.net/gh/ThinkingXuan/HexoStaticImage/img/image-20220227151616885.png" width="100" center=true></center> |  DHT111  | 温湿度传感器   |
| <center><img src="https://cdn.jsdelivr.net/gh/ThinkingXuan/HexoStaticImage/img/image-20220227151628449.png" width="100" center=true></center> |   MQ-2   | 烟雾传感器     |
| <center><img src="https://cdn.jsdelivr.net/gh/ThinkingXuan/HexoStaticImage/img/image-20220227151634335.png" width="100" center=true></center> | 光敏电阻 | 测量光照       |
| <center><img src="https://cdn.jsdelivr.net/gh/ThinkingXuan/HexoStaticImage/img/image-20220227151642418.png" width="100" center=true></center> |  蜂鸣器  | 报警           |
| <center><img src="https://cdn.jsdelivr.net/gh/ThinkingXuan/HexoStaticImage/img/image-20220227151647023.png" width="100" center=true></center> |  继电器  | 控制小灯的通断 |
| <center><img src="https://cdn.jsdelivr.net/gh/ThinkingXuan/HexoStaticImage/img/image-20220227151651053.png" width="100" center=true></center> |   小灯   | 照明           |
| <center><img src="https://cdn.jsdelivr.net/gh/ThinkingXuan/HexoStaticImage/img/image-20220227151655968.png" width="100" center=true></center> | 电机驱动 | 驱动直流电机   |
| <center><img src="https://cdn.jsdelivr.net/gh/ThinkingXuan/HexoStaticImage/img/image-20220227151659965.png" width="100" center=true></center> | 直流电机 | 电机使风扇旋转 |

系统总体功能图：

![image-20220227152347205](https://cdn.jsdelivr.net/gh/ThinkingXuan/HexoStaticImage/img/image-20220227152347205.png)

电路连接设计

| 电路图                                                       | 说明                     |
| ------------------------------------------------------------ | ------------------------ |
| <center><img src="https://cdn.jsdelivr.net/gh/ThinkingXuan/HexoStaticImage/img/image-20220227152721721.png" width="400" center=true></center> | ZigBee最小电路           |
| <center><img src="https://cdn.jsdelivr.net/gh/ThinkingXuan/HexoStaticImage/img/image-20220227152846570.png" width="400" center=true></center> | DHT11温湿度传感器        |
| <center><img src="https://cdn.jsdelivr.net/gh/ThinkingXuan/HexoStaticImage/img/image-20220227152854766.png" width="400" center=true></center> | MQ-2烟雾传感器           |
| <center><img src="https://cdn.jsdelivr.net/gh/ThinkingXuan/HexoStaticImage/img/image-20220227152902574.png" width="400" center=true></center> | 光敏电阻传感器           |
| <center><img src="https://cdn.jsdelivr.net/gh/ThinkingXuan/HexoStaticImage/img/image-20220227152910936.png" width="400" center=true></center> | 有源蜂鸣器               |
| <center><img src="https://cdn.jsdelivr.net/gh/ThinkingXuan/HexoStaticImage/img/image-20220227152919704.png" width="400" center=true></center> | L9110s驱动电路和直流电机 |



Android界面

- 数据实时显示模块

  <center><img src="https://cdn.jsdelivr.net/gh/ThinkingXuan/HexoStaticImage/img/image-20220227153628787.png"></center>

- 家居控制模块

    <center><img src="https://cdn.jsdelivr.net/gh/ThinkingXuan/HexoStaticImage/img/image-20220227153834294.png"></center>

- 数据统计模块

    <center><img src="https://cdn.jsdelivr.net/gh/ThinkingXuan/HexoStaticImage/img/image-20220227153937703.png"></center>

- 报表导出模块

    <center><img src="https://cdn.jsdelivr.net/gh/ThinkingXuan/HexoStaticImage/img/image-20220227154010972.png"></center>
    
    <center><img src="https://cdn.jsdelivr.net/gh/ThinkingXuan/HexoStaticImage/img/image-20220227154048902.png"></center>
