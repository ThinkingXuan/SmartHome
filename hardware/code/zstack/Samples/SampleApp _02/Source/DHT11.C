#include "dht11.h"

//32MHZ us延时函数；
#pragma optimize=none
char dht11_value(unsigned char *temp , unsigned char *humi , unsigned char flag);
void dht11_delay_us(unsigned int n)
{
    n>>=1;
    while(n--)
    {
          asm("NOP");
          asm("NOP");
          asm("NOP");
          asm("NOP");
          asm("NOP");
          asm("NOP");
          asm("NOP");
          asm("NOP");
          asm("NOP");
          asm("NOP");
          asm("NOP");
          asm("NOP");
          asm("NOP");
          asm("NOP");
          asm("NOP");
    }
}


unsigned char dht11_read_byte(void)
{
    unsigned char r_val = 0; 
    unsigned char t_count = 0;          //计时器，防止超时；
    unsigned char i;
    
    for(i = 0 ; i < 8 ; i++)
    {
        t_count = 0;
      
        //低电平50us后开始一个数据位读取；
        while( !DHT11_PIN )
        {
              asm("NOP");
              t_count++;
              if(t_count > 250) //超时；
                  return 100;
        } 
        t_count = 0;
                             
        dht11_delay_us(32);  //32us
        
        //高电平26~28us表示'0',70us表示'1'
        if( DHT11_PIN == 1 )
        {      
            r_val <<= 1;
            r_val |= 1;
        }
        else
        {
            r_val <<= 1;
            continue;
        }
               
        //等待DHT11数据输出结束；      
        while( DHT11_PIN == 1)
        {
            asm("NOP");
            t_count++;
            if(t_count>250)
            {
                return 100;
            }
        }            
    }
    return r_val;  
}


char dht11_value(unsigned char *temp , unsigned char *humi , unsigned char flag)
{
     unsigned char t_count = 0; //计时器；
     unsigned char h_i = 0 , h_f = 0;
     unsigned char t_i = 0 , t_f = 0;
     unsigned char check_sum = 0;
     
     DHT11_PIN_OUT();
     DHT11_PIN_L();  //输出低电平；
     
     //低电平持续时间必须大于18ms;
     dht11_delay_us(20000); //20ms;
     
     DHT11_PIN_H();  //主机结束信号,高电平；
     
     //主机等待20us~40us，读取DHT11响应输出；
     dht11_delay_us(30);
     
     DHT11_PIN_IN();
     if(DHT11_PIN == 0) //正确的响应输出；
     {        
            while( !DHT11_PIN )
            {
                  asm("NOP");
                  t_count++;
            
                  if(t_count > 250) //超时；
                    return -1;
            }   
            
            t_count = 0;
            
            dht11_delay_us(50); //DHT11给出响应输出后会拉高总线80us;
            while( DHT11_PIN ); //等待接收；
            {
                  asm("NOP");
                  t_count++;
            
                  if(t_count > 250) //超时；
                     return -1;
            }  
            
            h_i = dht11_read_byte(); //湿度整数部分；
            h_f = dht11_read_byte(); //湿度小数部分；
            t_i = dht11_read_byte(); //温度整数部分；
            t_f = dht11_read_byte(); //温度小数部分；
            check_sum = dht11_read_byte(); //校验和；  
              
            //校验和正确或者温湿度整数部分获取正确即表示获取成功！
            if(check_sum == ( h_i + h_f + t_i + t_f ) || (h_i != 100 && t_i != 100) )
            {            
                  if(flag == DHT11_STRING)
                  {
                      temp[0] = t_i/10+0x30;
                      temp[1] = t_i%10+0x30;
                      humi[0] = h_i/10+0x30;
                      humi[1] = h_i%10+0x30;
                  }
                  else 
                  {
                      *temp = t_i;
                      *humi = h_i;
                  }                                                       
            }
            else
            {          
                if(flag == DHT11_STRING)
                {
                    temp[0] = '0';
                    temp[1] = '0';
                    humi[0] = '0';
                    humi[1] = '0';
                }
                else 
                {
                    *temp = 0;
                    *humi = 0;
                }     
    
                return -1;
            }
     }
     else
     {
           if(flag == DHT11_STRING)
            {
                temp[0] = '0';
                temp[1] = '0';
                humi[0] = '0';
                humi[1] = '0';
            }
            else 
            {
                *temp = 0;
                *humi = 0;
            }     

            return -1; 
     }     
     return 0;
}




