package com.nuist.you.statistics;

import com.nuist.you.bean.daobean.AverageDataBean;
import com.nuist.you.dao.AverageDao;
import com.nuist.you.dao.SensorDao;
import com.nuist.you.dao.impl.AverageDaoImpl;
import com.nuist.you.dao.impl.SensorDaoImpl;
import com.nuist.you.util.*;

import java.util.Date;
import java.util.List;

public class DataStatisticsListener extends Thread {

    private Timer timer = new Timer();
    private AverageDao averageDao = new AverageDaoImpl();
    private SensorDao sensorDao = new SensorDaoImpl();

    @Override
    public void run() {

        try {
            timer.start();
            //1000毫秒后开启，然后 5分钟毫米执行一次         1*60*5*1000 5分钟
            timer.scheduleAtFixedRate(1000, 1 * 60 * 5 * 1000, () -> {
                startAverageHourCount();
            });

            //2000毫秒后开启，然后 10分钟执行一次
            timer.scheduleAtFixedRate(2000, 1 * 60 * 10 * 1000, () -> {
                startAverageDayCount();
            });

            //3000毫秒后开启，然后 30分钟执行一次
            timer.scheduleAtFixedRate(3000, 1 * 60 * 30 * 1000, () -> {
                startAverageWeekCount();
            });

            //4000毫秒后开启，然后 30分钟执行一次
            timer.scheduleAtFixedRate(4000, 1 * 60 * 30 * 1000, () -> {
                startAverageMonthCount();
            });

            //5000毫秒后开启，然后 60分钟执行一次
            timer.scheduleAtFixedRate(5000, 1 * 60 * 60* 1000, () -> {
                startAverageYearCount();
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //数据统计主方法
    private void startAverageHourCount() {

        LogUtil.info("传感器小时数据统计监听器开启");

        //查询最后的时间
        Date date = averageDao.selectLastestTime(SocketBase.TABLE_AVERAGE_HOUR);
        if (null == date) {          //无数据时

            //获取统计数据
            List<AverageDataBean> averageDatumBeans = sensorDao.countSensorData(SocketBase.TIME_FORMAT_HOUR);
            //插入统计数据表中
            for (AverageDataBean avg : averageDatumBeans) {
                avg.setAvg_id(BaseUtil.getUUID32());      //填入UUID
                averageDao.insertOneAverageData(SocketBase.TABLE_AVERAGE_HOUR, avg);
            }

        } else {

            //删除最后一条数据(因为之前是按照时间统计的，定时器开启的时间并不和真正的时间相同，所以最新的数据统计不完全)
            averageDao.deleteDataByTime(SocketBase.TABLE_AVERAGE_HOUR, date.toString());

            //格式化时间
            String dataString = Dateutil.DateStringFormat(date, SocketBase.TIME_FORMAT_STR_HOUR);

            //获取指定时间后的数据
            List<AverageDataBean> averageDatumBeans = sensorDao.countSensorData(SocketBase.TIME_FORMAT_HOUR, dataString);

            for (AverageDataBean avg : averageDatumBeans) {
                avg.setAvg_id(BaseUtil.getUUID32());      //填入UUID
                LogUtil.info("更新小时统计：" + avg.toString());
                averageDao.insertOneAverageData(SocketBase.TABLE_AVERAGE_HOUR, avg);
            }

        }

    }


    private void startAverageYearCount() {
        startAverageCount(SocketBase.TABLE_AVERAGE_YEAR, SocketBase.TIME_FORMAT_YEAR, SocketBase.TIME_FORMAT_STR_YEAR, "年");

    }

    private void startAverageMonthCount() {
        startAverageCount(SocketBase.TABLE_AVERAGE_MONTH, SocketBase.TIME_FORMAT_MONTH, SocketBase.TIME_FORMAT_STR_MONTH, "月");

    }

    private void startAverageWeekCount() {
        //startAverageCount(SocketBase.TABLE_AVERAGE_WEEK, SocketBase.TIME_FORMAT, SocketBase.TIME_FORMAT_STR_DAY, "天");

    }


    private void startAverageDayCount() {
        startAverageCount(SocketBase.TABLE_AVERAGE_DAY, SocketBase.TIME_FORMAT_DAY, SocketBase.TIME_FORMAT_STR_DAY, "天");
    }


    private void startAverageCount(String tableName, String selectDBTimeFormat, String TimeFormat, String type) {

        LogUtil.info("传感器" + type + "数据统计监听器开启");
        //查询最后的时间
        Date date = averageDao.selectLastestTime(tableName);
        if (null == date) {          //无数据时

            //获取统计数据
            List<AverageDataBean> averageDatumBeans = sensorDao.countSensorData(selectDBTimeFormat);
            //插入统计数据表中
            for (AverageDataBean avg : averageDatumBeans) {
                avg.setAvg_id(BaseUtil.getUUID32());      //填入UUID
                averageDao.insertOneAverageData(tableName, avg);
            }

        } else {

            //删除最后一条数据(因为之前是按照时间统计的，定时器开启的时间并不和真正的时间相同，所以最新的数据统计不完全)
            averageDao.deleteDataByTime(tableName, date.toString());

            //格式化时间
            String dataString = Dateutil.DateStringFormat(date, TimeFormat);

            //获取指定时间后的数据
            List<AverageDataBean> averageDatumBeans = sensorDao.countSensorData(selectDBTimeFormat, dataString);

            for (AverageDataBean avg : averageDatumBeans) {
                avg.setAvg_id(BaseUtil.getUUID32());      //填入UUID
                LogUtil.info("更新" + type + "统计：" + avg.toString());
                averageDao.insertOneAverageData(tableName, avg);
            }

        }
    }
}
