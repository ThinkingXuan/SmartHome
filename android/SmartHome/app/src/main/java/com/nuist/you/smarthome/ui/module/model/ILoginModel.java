package com.nuist.you.smarthome.ui.module.model;


import com.nuist.you.smarthome.bean.Users;
import com.nuist.you.smarthome.listener.RequestImpl;

public interface ILoginModel {

    void login(Users user, RequestImpl listener);
}
