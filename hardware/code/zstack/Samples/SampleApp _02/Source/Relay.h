#ifndef __Relay_H__
#define __Relay_H__

#include <ioCC2530.h>

#define uchar unsigned char
#define uint unsigned int

//#define RELAY_PIN  P1_4       // ��ʱʹ��,�ɸ���
#define RELAY_PIN  P0_6       // ��ʱʹ��,�ɸ���

#define RELAY_ON_   0     //�̵���ͨ�� (�̵������ӹ����˺ͱպ϶�)  
#define RELAY_OFF_  1    // �̵����ϵ� (�̵������ӹ����˺ͱպ϶�)

extern void ControlR(int flag);

#endif