#ifndef __Beep_H__
#define __Beep_H__

#include <ioCC2530.h>

#define uchar unsigned char
#define uint unsigned int

#define BEEP_PIN P1_1           // ������

#define BEEP_ON_  0        //�͵�ƽ����
#define BEEP_OFF_ 1

extern void ControlB(int flag);

#endif
