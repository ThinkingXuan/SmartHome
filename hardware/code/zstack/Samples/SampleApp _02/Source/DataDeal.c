/**
*���ݴ���ģ��,�û���ȡ���д�������ֵ
*/

#include "DHT11.H"
#include "MQ_2.H"
#include "LightSensor.H"
#include "Beep.H"
#include "Relay.H"
#include "DCMotor.H"

#define uchar unsigned char

void getDHT11(uchar temp[4]);                //��ʪ��������
void getMQ_2(uchar TxBuf[5]);
void getLightSensor(uchar buff[9]);
void controlBeep(uchar flag);
void controlRelay(uchar flag);
void ControlDCMotor(uchar pwm,uchar direction);

//��ȡ��ʪ��  �������飬ǰ�����ַ�Ϊ�¶ȣ��������ַ�Ϊʪ��
void getDHT11(uchar tempH[4]){
   unsigned char temp[2],humi[2];
   char r_val;
    //��ȡ��ʪ��
   r_val = dht11_value(temp , humi , DHT11_STRING);
   if(r_val == 0)
   {
          tempH[0] = temp[0];
          tempH[1] = temp[1];
          tempH[2] = humi[0];
          tempH[3] = humi[1];
     }
}

void getMQ_2(uchar TxBuf[5]){
    MQInit(TxBuf);
}

void getLightSensor(uchar buff[5]){
    LightSensorInit(buff);
}

void controlBeep(uchar flag){
    ControlB(flag);
}
void controlRelay(uchar flag){
    ControlR(flag);
}

void ControlDCMotor(uchar pwm,uchar direction){
    //InitDC();
    ControlDC(pwm,direction);
}