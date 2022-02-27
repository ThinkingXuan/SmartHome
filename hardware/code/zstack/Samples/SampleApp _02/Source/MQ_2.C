#include <ioCC2530.h>
#include "MQ_2.H"

uint GasData;
uint ReadGasData( void );
void MQInit(uchar TxBuf[5]);


void DelayMS(uint msec)
{ 
    uint i,j;
    
    for (i=0; i<msec; i++)
        for (j=0; j<535; j++);
}

//读取MQ-2 AD转换后的数字量
uint ReadGasData( void )
{
  uint reading = 0;
  
  /* Enable channel */
  ADCCFG |= 0x40;  
  
 
  /* writing to this register starts the extra conversion */
  ADCCON3 = 0x85;       // AVDD5 引脚  00： 64 抽取率(7 位ENOB)  0101： AIN5


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


void MQInit(uchar TxBuf[5]){
    
    GasData = ReadGasData();  //读取烟雾传感器引脚上的ad转换值，并没有换算成能表示烟雾浓度的值
    //演示如何使用2530芯片的AD功能，更具体在组网中给出
    
    //读取到的数值转换成字符串，供串口函数输出
    TxBuf[0] = GasData / 100 + '0';
    TxBuf[1] = GasData / 10%10 + '0';
    TxBuf[2] = GasData % 10 + '0';
    //TxBuf[3] = 0;
    //TxBuf[4] = 0;
}