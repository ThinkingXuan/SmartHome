package com.nuist.you.dao.impl;

import com.nuist.you.bean.SensorDataBean;
import com.nuist.you.bean.Users;
import com.nuist.you.bean.daobean.DeviceData;
import com.nuist.you.dao.User2Dao;
import com.nuist.you.util.DBCPUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

public class User2DaoImpl implements User2Dao {

    private QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());

    @Override
    public Users selectUsersByPhone(String phone) {

        try {
            String sql = " select * from users where userPhone = " + phone + " ";
            List<Users> dataList = qr.query(sql, new BeanListHandler<Users>(Users.class));
            if (null != dataList && dataList.size() > 0) {
                return dataList.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Users> selectAllUser() {
        try {
            String sql = " select * from users ";
            List<Users> dataList = qr.query(sql, new BeanListHandler<Users>(Users.class));
            if (null != dataList && dataList.size() > 0) {
                return dataList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
