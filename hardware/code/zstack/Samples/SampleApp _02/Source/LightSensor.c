#include <ioCC2530.h>
#include "LightSensor.H"

#define DATA_PIN_LIGHT P1_6           //����P1.6��Ϊ�������������


//void DelayMS(uint msec)
//{ 
//    uint i,j;
    
 //   for (i=0; i<msec; i++)
//        for (j=0; j<535; j++);
//}



//ADC
uint ReadLightData( void )
{
 // uint reading = 0;
  
  //P0DIR &= ~0x20;  // ����P0.5Ϊ���뷽ʽ
 //asm("NOP");asm("NOP");
  
  /* Clear ADC interrupt flag */
  //ADCIF = 0;
  //ADCCON3 = 0x84;
  
 // /* Wait for the conversion to finish */
  //while ( !ADCIF );
  
  //asm("NOP");asm("NOP");
  
  /* Read the result */
  //reading = ADCL;
  //reading |= (uint) (ADCH << 8);
  //reading >>= 8;
  
    uint reading = 0;
  
  /* Enable channel */
  ADCCFG |= 0x40;  
  
 
  /* writing to this register starts the extra conversion */
  ADCCON3 = 0x84;// AVDD5 ����  00�� 64 ��ȡ��(7 λENOB)  0100�� AIN4


  /* Wait for the conversion to be done */
  while (!(ADCCON1 & 0x80));
  
  /* Disable channel after done conversion */
  ADCCFG &= (0x40 ^ 0xFF); //��λ�����1010^1111=0101�������ƣ�
  
  /* Read the result */
  reading = ADCL;
  reading |= (uint) (ADCH << 8); 
  
  reading >>= 8;
  
  return (reading);
}


void LightSensorInit(uchar str[5]){

  uint value;
  value = ReadLightData();
  //sprintf(str, "BRI:%03d ", value);  //רҵ��Աһ������һ���͸㶨 ��������Ҳһ��Ŷ
  str[0] = value / 1000 + '0';
  str[1] = value / 100 % 10 + '0';
  str[2] = value / 10 % 10 + '0';
  str[3] = value %10 + '0';  
  str[4] = 0; 
}