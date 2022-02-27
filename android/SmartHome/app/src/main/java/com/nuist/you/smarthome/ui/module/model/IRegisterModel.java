package com.nuist.you.smarthome.ui.module.model;


import com.nuist.you.smarthome.bean.BaseCode;
import com.nuist.you.smarthome.bean.Users;
import com.nuist.you.smarthome.listener.RequestImpl;

public interface IRegisterModel {

    //上传用户数据，无需带返回值。接受到的内容通过接口回调
    void upUser(Users user, RequestImpl listener);

    //下载用户数据
    BaseCode downUser(Users user);


}
