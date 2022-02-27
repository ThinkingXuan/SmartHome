package com.nuist.you.services.impl;


import com.nuist.you.bean.BaseCode;
import com.nuist.you.bean.SensorDataBean;
import com.nuist.you.bean.Users;
import com.nuist.you.bean.daobean.DeviceData;
import com.nuist.you.mapper.UserDao;
import com.nuist.you.services.IUserServices;
import com.nuist.you.util.BaseUtil;
import com.nuist.you.util.DBCPUtil;

import com.nuist.you.util.LogUtil;
import com.nuist.you.util.SocketBase;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements IUserServices {

    @Autowired
    UserDao userDao;

    QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());

    public BaseCode saveUser(Users users) {
        BaseCode baseCode = new BaseCode();
        //1 手机号是否被注册过
        List<Users> oldUsers = userDao.selectUser(users);
        if (null != oldUsers && oldUsers.size() != 0) {
            baseCode.setCode(1);
            baseCode.setMessage("该手机已经被注册");
            return baseCode;
        }

        //2 赋值 uuid register_time
        users.setUserId(BaseUtil.getUUID32());
        users.setLoginTime(new java.util.Date());
        users.setUserEmail(users.getUserEmail());
        users.setIpAddress("140.143.127.174");

        //3 插入数据
        int code = userDao.insertUser(users);
        if (code == 1) {
            baseCode.setCode(0);
            baseCode.setMessage("注册成功");
            return baseCode;
        } else {
            baseCode.setCode(1);
            baseCode.setMessage("注册失败");
        }
        return baseCode;
    }

    public BaseCode userLogin(Users users) {
        BaseCode baseCode = new BaseCode();
        //1.根据登陆的手机号查询
        Users tableUser = userDao.selectUserByPhone(users.getUserPhone());
        //不存在
        if (tableUser == null) {
            baseCode.setCode(1);
            baseCode.setMessage("该手机号没有注册");
            return baseCode;
        }

        //密码错误
        if (!users.getUserPassword().equals(tableUser.getUserPassword())) {
            baseCode.setCode(1);
            baseCode.setMessage("密码错误");
            return baseCode;
        }

        SocketBase.userPhone = users.getUserPhone();

        //成功，赋值uuid
        baseCode.setCode(0);
        baseCode.setMessage(tableUser.getIpAddress());
        return baseCode;
    }


    public BaseCode updateUser(Users users) {
        BaseCode baseCode = new BaseCode();
        //1.根据登陆的手机号查询
        Users tableUser = userDao.selectUserByPhone(users.getUserPhone());
        //不存在
        if (tableUser == null) {
            baseCode.setCode(1);
            baseCode.setMessage("该手机号没有注册");
            return baseCode;
        }

        //2.存在更新信息
        int code = userDao.updateUser(users);
        if (code == 1) {
            baseCode.setCode(0);
            baseCode.setMessage("修改成功");
            return baseCode;
        } else {
            baseCode.setCode(1);
            baseCode.setMessage("修改失败");
        }
        return baseCode;
    }

    @Override
    public List<SensorDataBean> getAllSensorDate(Users users) {

        String start_time = users.getUserId();
        String end_time = users.getUserName();
        try {
            String sql = " select * from sensor_data where time >='" + start_time + "' and time <= '" + end_time + "' order by time desc ";

            List<SensorDataBean> dataList = qr.query(sql, new BeanListHandler<SensorDataBean>(SensorDataBean.class));

            if (null != dataList && dataList.size() > 0) {
                return dataList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<DeviceData> getAllControlData(Users users) {
        String start_time = users.getUserId();
        String end_time = users.getUserName();
        try {
            String sql = " select * from device_data where controlTime >='" + start_time + "' and controlTime <= '" + end_time + "'  order by controlTime desc";

            LogUtil.info(sql);
            List<DeviceData> dataList = qr.query(sql, new BeanListHandler<DeviceData>(DeviceData.class));

            if (null != dataList && dataList.size() > 0) {
                return dataList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
