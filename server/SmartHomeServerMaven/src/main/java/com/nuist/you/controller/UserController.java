package com.nuist.you.controller;


import com.nuist.you.bean.BaseCode;
import com.nuist.you.bean.SensorData;
import com.nuist.you.bean.SensorDataBean;
import com.nuist.you.bean.Users;
import com.nuist.you.bean.daobean.DeviceData;
import com.nuist.you.services.IUserServices;
import com.nuist.you.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.logging.Logger;


/**
 * 管理用户操作的Controller
 * 用于 注册，登陆，修改密码
 */
@Controller
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    IUserServices userServices;

    @RequestMapping(path = "/index")
    public String inter() {
        return "/updateUser";
    }

    /**
     * 注册
     *
     * @param users
     * @return
     */
    @RequestMapping(path = "/register", method = RequestMethod.POST)
    @ResponseBody
    public BaseCode register(@RequestBody Users users) {
        System.out.println("\n开始注册：");
        System.out.println("接收参数：" + users.toString());
        return userServices.saveUser(users);
    }


    /**
     * 登陆
     *
     * @param users
     * @return
     */
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    @ResponseBody
    public BaseCode login(@RequestBody Users users) {
        System.out.println("\n开始登陆：");
        System.out.println("接收参数：" + users.toString());
        return userServices.userLogin(users);
    }


    /**
     * 更改用户信息
     *
     * @param users
     * @return
     */

    @RequestMapping(path = "/updateUser", method = RequestMethod.POST)
    @ResponseBody
    public BaseCode updateUser(@RequestBody Users users) {
        System.out.println("\n开始更新用户信息：");
        System.out.println("接收参数：" + users.toString());
        return userServices.updateUser(users);
    }


    @RequestMapping(path = "/getxls", method = RequestMethod.POST)
    @ResponseBody
    public List<SensorDataBean> getxls(@RequestBody Users users) {
        LogUtil.info("\n开始下载数据：");
        System.out.println("\n开始下载数据：");
        System.out.println("接收参数：" + users.toString());
        return userServices.getAllSensorDate(users);
    }

    @RequestMapping(path = "/getConntrolxls", method = RequestMethod.POST)
    @ResponseBody
    public List<DeviceData> getControlxls(@RequestBody Users users) {
        LogUtil.info("\n开始下载数据：");
        System.out.println("\n开始下载数据：");
        System.out.println("接收参数：" + users.toString());
        return userServices.getAllControlData(users);
    }


}
