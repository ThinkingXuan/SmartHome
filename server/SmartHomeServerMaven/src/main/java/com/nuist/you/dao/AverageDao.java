package com.nuist.you.dao;

import com.nuist.you.bean.daobean.AverageDataBean;

import java.util.Date;
import java.util.List;

public interface AverageDao {


    boolean insertOneAverageData(String table, AverageDataBean data);


    //删除指定时间后的数据
    boolean deleteDataByTime(String table, String date);

    //获取最新的时间
    Date selectLastestTime(String table);

    /**
     * 获取指定时间、时间段的数据
     *
     * @param table          表名
     * @param time           要查询时间，时间段
     * @param Date_Format_DB 数据库时间格式
     * @return
     */
    AverageDataBean selectAvgData(String table, String time, String Date_Format_DB);

    /**
     * 获取所有平均数据，
     * @param tableName 表名
     * @return
     */

    List<AverageDataBean> selectAllAvgData(String tableName);


}
