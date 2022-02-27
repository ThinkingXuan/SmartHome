#include "DCMotor.h"

void ControlDC(uchar PWM,uchar direction);

unsigned char pwm_period = 100;     //定义PWM的周期
unsigned char pwm_duy;              //定义PWM的占空比
unsigned char t_count = 0;          //间隔定时次数累计


/*=================定时器1初始化函数=====================
功能：初始化定时器1
      使用内部16MHz晶振，定时周期1ms，
      使用模模式，开启通道0的输出比较模式，
      分频系数8，打开相应的定时中断
========================================================*/

void Init_Timer1()
{
  /*=内部16MHz晶振8分频定时0.05ms的最大计数值为*/
  T1CC0L = 0x64;    //设置最大计数值的低8位
  T1CC0H = 0x00;    //设置最大计数值的高8位
  //000000011001000 FFFF    
  //                 65,535
  T1CCTL0 |= 0x04;  //开启通道0的输出比较模式
  T1IE = 1;         //使能定时器1中断
  T1OVFIM = 1;      //使能定时器1溢出中断
  EA = 1;           //使能总中断         0.0
  T1CTL = 0x06;     //分频系数是8,模模式 0.0005ms
}

/*================定时器1中断服务函数====================
功能：1ms间隔定时中断服务函数
      对间隔定时次数进行累加，即t_count++
      t_count < pwm_duy, 输出高电平
      pwm_duy < t_count < pwm_period, 输出低电平
      t_count = pwm_period，t_count清0，输出高电平
      每完成一个PWM改变一个占空比，实现呼吸灯
========================================================*/
#pragma vector = T1_VECTOR
__interrupt void Timer1_Sevice()
{
  T1STAT &= ~0x01;        //清除定时器1通道0中断标志
  t_count++;              //对间隔定时次数进行累加
  if(t_count < pwm_duy)            //高电平周期到
  {
    DCMotor_PIN1 = 1;                     
  }
  else if(t_count < pwm_period)   //低电平周期到
  {
    DCMotor_PIN1 = 0;                     
  }
  else                  //准备开始下一个PWM输出
  {
    DCMotor_PIN1 = 1;
    t_count = 0;        //间隔定时累加清0
   // pwm_duy--;
   // if(pwm_duy == 1)
    //{
     // pwm_duy = 90;
   // }
  }
}



void InitDC(void){

    P1DIR |= 0x0c;    
    P1SEL &= ~0x0c;
}


void ControlDC(uchar PWM,uchar direction){
    InitDC();
    DCMotor_PIN2 = direction;
    pwm_duy = PWM;
    //Init_Timer1();

}