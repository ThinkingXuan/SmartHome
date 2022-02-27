#include "DCMotor.h"

void ControlDC(uchar PWM,uchar direction);

unsigned char pwm_period = 100;     //����PWM������
unsigned char pwm_duy;              //����PWM��ռ�ձ�
unsigned char t_count = 0;          //�����ʱ�����ۼ�


/*=================��ʱ��1��ʼ������=====================
���ܣ���ʼ����ʱ��1
      ʹ���ڲ�16MHz���񣬶�ʱ����1ms��
      ʹ��ģģʽ������ͨ��0������Ƚ�ģʽ��
      ��Ƶϵ��8������Ӧ�Ķ�ʱ�ж�
========================================================*/

void Init_Timer1()
{
  /*=�ڲ�16MHz����8��Ƶ��ʱ0.05ms��������ֵΪ*/
  T1CC0L = 0x64;    //����������ֵ�ĵ�8λ
  T1CC0H = 0x00;    //����������ֵ�ĸ�8λ
  //000000011001000 FFFF    
  //                 65,535
  T1CCTL0 |= 0x04;  //����ͨ��0������Ƚ�ģʽ
  T1IE = 1;         //ʹ�ܶ�ʱ��1�ж�
  T1OVFIM = 1;      //ʹ�ܶ�ʱ��1����ж�
  EA = 1;           //ʹ�����ж�         0.0
  T1CTL = 0x06;     //��Ƶϵ����8,ģģʽ 0.0005ms
}

/*================��ʱ��1�жϷ�����====================
���ܣ�1ms�����ʱ�жϷ�����
      �Լ����ʱ���������ۼӣ���t_count++
      t_count < pwm_duy, ����ߵ�ƽ
      pwm_duy < t_count < pwm_period, ����͵�ƽ
      t_count = pwm_period��t_count��0������ߵ�ƽ
      ÿ���һ��PWM�ı�һ��ռ�ձȣ�ʵ�ֺ�����
========================================================*/
#pragma vector = T1_VECTOR
__interrupt void Timer1_Sevice()
{
  T1STAT &= ~0x01;        //�����ʱ��1ͨ��0�жϱ�־
  t_count++;              //�Լ����ʱ���������ۼ�
  if(t_count < pwm_duy)            //�ߵ�ƽ���ڵ�
  {
    DCMotor_PIN1 = 1;                     
  }
  else if(t_count < pwm_period)   //�͵�ƽ���ڵ�
  {
    DCMotor_PIN1 = 0;                     
  }
  else                  //׼����ʼ��һ��PWM���
  {
    DCMotor_PIN1 = 1;
    t_count = 0;        //�����ʱ�ۼ���0
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