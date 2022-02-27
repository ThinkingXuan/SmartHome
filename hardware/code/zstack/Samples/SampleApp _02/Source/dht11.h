/****************************************************
*       
*       DHT11��ʪ�ȴ�������������
*       �������ߣ� jafy
*       ��дʱ�䣺 2013/10/12
*       DHT11��ⷶΧ��20-90%RH  0-50��
*       ע�����DHT11����ʪ�Ȳɼ�С������Ϊ0
*
*****************************************************/
#ifndef __DHT11_H__
#define __DHT11_H__

#include <ioCC2530.h>

#define DHT11_PIN P0_7 //��������


//��������Ϊ�����
#define DHT11_PIN_OUT() { P0DIR |= 1<<7; asm("NOP"); } 

//��������Ϊ���룻
#define DHT11_PIN_IN()  { P0DIR &= ~(1<<7); asm("NOP"); }

//���������ߣ�
#define DHT11_PIN_H() { DHT11_PIN = 1; asm("NOP"); }

//���������ߣ�
#define DHT11_PIN_L() { DHT11_PIN = 0; asm("NOP"); }




/****************************************************
*       
*       ��������  dht11_delay_us
*       �������ܣ�us��ʱ����
*       ����������
*                 param1: ��ʱ����us
*
*****************************************************/
void dht11_delay_us(unsigned int n);



/****************************************************
*       
*       ������    : dht11_read_byte
*       ��������  : ��ȡDHT11������һ���ֽ�
*       ��������ֵ: �ɹ����ض�ȡֵ��ʧ�ܷ���100
*
*****************************************************/
unsigned char dht11_read_byte(void);




/************************************************************************
*       
*       ������  : dht11_value
*       ��������: ��ȡ��ʪ����ֵ
*       ��������:
*                 param1: �¶�ֵ
*                 param2: ʪ��ֵ
*                 param3: ����(DHT11_STRING or DHT11_UINT8)
*       ����ֵ:   ��ȡ�ɹ�����0��ʧ�ܷ���-1
*       �÷�����:
*                 ��һ��
*                 unsigned char temp , humi;
*                 char  r_val;
*                 r_val = dht11_value(&temp , &humi , DHT11_UINT8);
*                   
*                 ������
*                 unsigned char temp[2],humi[2];
*                 char r_val;
*                 r_val = dht11_value(temp , humi , DHT11_STRING);
*
*************************************************************************/
#define DHT11_STRING 1
#define DHT11_UINT8  2
extern char dht11_value(unsigned char *temp , unsigned char *humi , unsigned char flag);






#endif


