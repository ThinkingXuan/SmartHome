#ifndef __DCMotor_H__
#define __DCMotor_H__

#include <ioCC2530.h>

#define uchar unsigned char
#define uint unsigned int

//直流电机驱动控制管脚
//PIN1用于PWM调制，PIN2控制正反转
#define DCMotor_PIN1  P1_2       // IA1
#define DCMotor_PIN2  P1_3       // IB1
  

extern void ControlDC(uchar pwm,uchar direction);

#endif