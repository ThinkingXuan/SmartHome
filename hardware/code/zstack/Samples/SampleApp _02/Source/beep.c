#include "Beep.H"

void ControlB(int flag);


void InitBeep(void){
    //P1DIR |= 0x20;    
    //P1SEL &= ~0x20; 
    P1DIR |= 0x02;    
    P1SEL &= ~0x02;
}

void ControlB(int flag){ 
    InitBeep();
    if(flag == BEEP_ON_){   
        BEEP_PIN = BEEP_ON_;     
    }else if(flag == BEEP_OFF_){
       BEEP_PIN = BEEP_OFF_;
    }
  
}