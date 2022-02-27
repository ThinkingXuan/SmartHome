# SmartHome

😁SmartHome 一款基于ZigBee的智能家居的设计于实现😂

简介：

本项目针对传统智能家居系统布线困难、成本高昂和使用不便的问题，分析了CC2530开发板、ZigBee协议栈、网关、腾讯云服务器、MySQL数据库、Android客户端等物联网技术的特点，在此基础上设计了以CC2530为主控制器，传感器为终端采集设备，家居电器为终端控制设备，服务器和数据库作为数据接收、处理、存储设备，Android客户端作为显示设备。使用该系统用户可以通过手机APP实时查看家居的温度、湿度、烟雾浓度和光照强度，并且远程操控风扇、电灯和蜂鸣器，同时用户也可以查看统计数据和并以Excel表格导出，如果出现异常情况，系统会自动启动蜂鸣器报警并用邮件通知用户。通过该系统的使用，提供给用户一个安全、智能、便捷、舒适的居住环境。

技术点：

- 硬件：CC2530，ZigBee协议栈，ESP-01s 智能网关模块，各种常用传感器使用等
- 服务端：SpringMVC, MySQL，Socket等
- Android客户端：RxJava，Okhttp，Retrofit，Gson，MPAndroidChart，WebSocket等

开发软件：

- IAR：硬件部分的代码编写集成环境
- IDEA: 后台服务器的代码编写的集成开发环境
- AndroidStudio：Android客户端的代码编写的集成开发环境
- Tomcat：一个支持HTTP协议的Web容器