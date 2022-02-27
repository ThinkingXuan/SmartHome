#ifndef __Relay_H__
#define __Relay_H__

#include <ioCC2530.h>

#define uchar unsigned char
#define uint unsigned int

//#define RELAY_PIN  P1_4       // 暂时使用,可更换
#define RELAY_PIN  P0_6       // 暂时使用,可更换

#define RELAY_ON_   0     //继电器通电 (继电器连接公共端和闭合端)  
#define RELAY_OFF_  1    // 继电器断电 (继电器连接公共端和闭合端)

extern void ControlR(int flag);

#endif