#ifndef __DataDeal_H__
#define __DataDeal_H__

#define uchar unsigned char
#define uint unsigned int

//����������
#define BEEP_ON  0    
#define BEEP_OFF 1

//�̵�������
#define RELAY_ON   0     //�̵���ͨ�� (�̵������ӹ����˺ͱպ϶�)  
#define RELAY_OFF  1    // �̵����ϵ� (�̵������ӹ����˺ͱպ϶�)

             // ��ʪд��
extern void getDHT11(uchar temp[4]);                //��ʪ��������
extern void getMQ_2(uchar TxBuf[5]);
extern void getLightSensor(uchar buff[9]);
extern void controlBeep(uchar flag);
extern void controlRelay(uchar flag);
extern void ControlDCMotor(uchar pwm,uchar direction);

#endif