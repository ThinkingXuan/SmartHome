#ifndef __DataDeal_H__
#define __DataDeal_H__

#define uchar unsigned char
#define uint unsigned int

//蜂鸣器控制
#define BEEP_ON  0    
#define BEEP_OFF 1

//继电器控制
#define RELAY_ON   0     //继电器通电 (继电器连接公共端和闭合端)  
#define RELAY_OFF  1    // 继电器断电 (继电器连接公共端和闭合端)

             // 温湿写入
extern void getDHT11(uchar temp[4]);                //温湿传感启动
extern void getMQ_2(uchar TxBuf[5]);
extern void getLightSensor(uchar buff[9]);
extern void controlBeep(uchar flag);
extern void controlRelay(uchar flag);
extern void ControlDCMotor(uchar pwm,uchar direction);

#endif