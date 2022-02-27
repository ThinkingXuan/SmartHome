package com.nuist.you.dao;

import com.nuist.you.bean.daobean.DeviceData;

import java.util.List;

public interface DeviceDao {

    boolean insertControlData(DeviceData deviceData);

    List<DeviceData> selectAllDeviceData();
}
