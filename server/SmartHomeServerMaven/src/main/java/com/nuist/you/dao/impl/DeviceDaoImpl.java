package com.nuist.you.dao.impl;

import com.nuist.you.bean.daobean.DeviceData;
import com.nuist.you.dao.DeviceDao;
import com.nuist.you.util.DBCPUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class DeviceDaoImpl implements DeviceDao {

    private QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());

    @Override
    public boolean insertControlData(DeviceData deviceData) {
        try {
            Object[] params = new Object[]{deviceData.getId(), deviceData.getUserId(), deviceData.getControlTime(), deviceData.getDeviceType(), deviceData.getControlAction(), deviceData.getControlMode(), deviceData.getControlReason()};
            int flag = qr.update("insert into device_data(id,userId,controlTime,deviceType,controlAction,controlMode,controlReason) values(?,?,?,?,?,?,?)", params);
            return flag > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<DeviceData> selectAllDeviceData() {
        try {
            List<DeviceData> dataList = qr.query(" select * from device_data ", new BeanListHandler<DeviceData>(DeviceData.class));
            if (null != dataList && dataList.size() > 0) {
                return dataList;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
