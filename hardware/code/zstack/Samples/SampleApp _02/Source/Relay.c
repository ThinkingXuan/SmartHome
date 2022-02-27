#include "Relay.h"

void InitRelay(void){
  
   // P1DIR |= 0x10;    
    //P1SEL &= ~0x10;
    P0DIR |= 0x40;    
    P0SEL &= ~0x40;
    
}


void ControlR(int flag){ 
    InitRelay();
    if(flag == RELAY_ON_){
        RELAY_PIN = RELAY_ON_;
    }else if(flag == RELAY_OFF_){
        RELAY_PIN = RELAY_OFF_;
    }
}