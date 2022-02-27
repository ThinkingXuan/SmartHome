#include <ioCC2530.h>
#include "LightSensor.H"

#define DATA_PIN_LIGHT P1_6           //定义P1.6口为传感器的输入端


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
  
  //P0DIR &= ~0x20;  // 设置P0.5为输入方式
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
  ADCCON3 = 0x84;// AVDD5 引脚  00： 64 抽取率(7 位ENOB)  0100： AIN4


  /* Wait for the conversion to be done */
  while (!(ADCCON1 & 0x80));
  
  /* Disable channel after done conversion */
  ADCCFG &= (0x40 ^ 0xFF); //按位异或。如1010^1111=0101（二进制）
  
  /* Read the result */
  reading = ADCL;
  reading |= (uint) (ADCH << 8); 
  
  reading >>= 8;
  
  return (reading);
}


void LightSensorInit(uchar str[5]){

  uint value;
  value = ReadLightData();
  //sprintf(str, "BRI:%03d ", value);  //专业人员一般用这一条就搞定 下面的语句也一样哦
  str[0] = value / 1000 + '0';
  str[1] = value / 100 % 10 + '0';
  str[2] = value / 10 % 10 + '0';
  str[3] = value %10 + '0';  
  str[4] = 0; 
}