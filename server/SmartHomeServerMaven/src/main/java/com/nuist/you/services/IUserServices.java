package com.nuist.you.services;


import com.nuist.you.bean.BaseCode;
import com.nuist.you.bean.ControlBean;
import com.nuist.you.bean.SensorDataBean;
import com.nuist.you.bean.Users;
import com.nuist.you.bean.daobean.DeviceData;

import java.util.List;

public interface IUserServices {
    /**
     * 注册保存用户信息
     * @param users
     * @return
     */
    BaseCode saveUser(Users users);

    /**
     * 注册验证
     * @param users
     * @return
     */
    BaseCode userLogin(Users users);

    /**
     * 更改用户信息
     * @param users
     * @return
     */
    BaseCode updateUser(Users users);

    List<SensorDataBean> getAllSensorDate(Users users);


    List<DeviceData> getAllControlData(Users users);


}
