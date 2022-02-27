/****************************************************
*       
*       DHT11温湿度传感器驱动程序
*       程序作者： jafy
*       编写时间： 2013/10/12
*       DHT11检测范围：20-90%RH  0-50℃
*       注意事项：DHT11对温湿度采集小数部分为0
*
*****************************************************/
#ifndef __DHT11_H__
#define __DHT11_H__

#include <ioCC2530.h>

#define DHT11_PIN P0_7 //数据引脚


//引脚配置为输出；
#define DHT11_PIN_OUT() { P0DIR |= 1<<7; asm("NOP"); } 

//引脚配置为输入；
#define DHT11_PIN_IN()  { P0DIR &= ~(1<<7); asm("NOP"); }

//拉高数据线；
#define DHT11_PIN_H() { DHT11_PIN = 1; asm("NOP"); }

//拉低数据线；
#define DHT11_PIN_L() { DHT11_PIN = 0; asm("NOP"); }




/****************************************************
*       
*       函数名：  dht11_delay_us
*       函数功能：us延时函数
*       函数参数：
*                 param1: 延时多少us
*
*****************************************************/
void dht11_delay_us(unsigned int n);



/****************************************************
*       
*       函数名    : dht11_read_byte
*       函数功能  : 读取DHT11传感器一个字节
*       函数返回值: 成功返回读取值，失败返回100
*
*****************************************************/
unsigned char dht11_read_byte(void);




/************************************************************************
*       
*       函数名  : dht11_value
*       函数功能: 获取温湿度数值
*       函数参数:
*                 param1: 温度值
*                 param2: 湿度值
*                 param3: 类型(DHT11_STRING or DHT11_UINT8)
*       返回值:   读取成功返回0，失败返回-1
*       用法举例:
*                 例一：
*                 unsigned char temp , humi;
*                 char  r_val;
*                 r_val = dht11_value(&temp , &humi , DHT11_UINT8);
*                   
*                 例二：
*                 unsigned char temp[2],humi[2];
*                 char r_val;
*                 r_val = dht11_value(temp , humi , DHT11_STRING);
*
*************************************************************************/
#define DHT11_STRING 1
#define DHT11_UINT8  2
extern char dht11_value(unsigned char *temp , unsigned char *humi , unsigned char flag);






#endif


