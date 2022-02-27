package com.nuist.you.dao;

import com.nuist.you.bean.SensorDataBean;
import com.nuist.you.bean.daobean.AverageDataBean;

import java.util.List;

public interface SensorDao {

    /***
     * 插入传感器数据
     * @param sensor
     * @return true/false
     */
    boolean insertSensorData(SensorDataBean sensor);

    List<SensorDataBean> selectAllSensorData();

    /**
     * 计算数据数量
     * @return
     */
    long selectCountSensorData();

    /**
     *
     * 统计全部平均值
     * @return
     */

    List<AverageDataBean> countSensorData(String time_format);


    /**
     *
     * 统计指定大于指定时间的全部平均值
     * @return
     */

    List<AverageDataBean> countSensorData(String time_format, String time);



}
