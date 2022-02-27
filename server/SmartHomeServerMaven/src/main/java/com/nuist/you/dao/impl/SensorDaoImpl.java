package com.nuist.you.dao.impl;

import com.nuist.you.bean.SensorDataBean;
import com.nuist.you.bean.daobean.AverageDataBean;
import com.nuist.you.dao.SensorDao;
import com.nuist.you.util.DBCPUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class SensorDaoImpl implements SensorDao {

    private QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());

    @Override
    public boolean insertSensorData(SensorDataBean sensor) {

        try {
            Object[] params = new Object[]{sensor.getId(), sensor.getTime(), sensor.getTemperature(), sensor.getHumidity(), sensor.getMq2(), sensor.getBeam()};
            int flag = qr.update("insert into sensor_data(id,time,temperature,humidity,mq2,beam) values(?,?,?,?,?,?)", params);
            return flag > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<SensorDataBean> selectAllSensorData() {
        try {
            List<SensorDataBean> sensors = qr.query(" select * from sensor_data ", new BeanListHandler<SensorDataBean>(SensorDataBean.class));
            if (null != sensors && sensors.size() > 0) {
                return sensors;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public long selectCountSensorData() {
        long count = 0;
        try {
            String sql = "SELECT count(id) as 'id'  FROM sensor_data";
            count = qr.query(sql, new ScalarHandler<Long>("id"));

            return count;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<AverageDataBean> countSensorData(String time_format) {
        String sql = "select avg(temperature) as avg_temp ,avg(humidity) as avg_hum,avg(mq2) as avg_mq2,avg(beam) as avg_beam ,time as avg_time from sensor_data group by date_format(time, '" + time_format + "');";

        try {
            List<AverageDataBean> sensors = qr.query(sql, new BeanListHandler<AverageDataBean>(AverageDataBean.class));
            if (null != sensors && sensors.size() > 0) {
                return sensors;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public List<AverageDataBean> countSensorData(String time_format, String time) {
        String sql = "select avg(temperature) as avg_temp ,avg(humidity) as avg_hum,avg(mq2) as avg_mq2,avg(beam) as avg_beam ,time as avg_time from sensor_data where time >= '" + time + "' group by date_format(time, '" + time_format + "');";
        try {
            List<AverageDataBean> sensors = qr.query(sql, new BeanListHandler<AverageDataBean>(AverageDataBean.class));
            if (null != sensors && sensors.size() > 0) {
                return sensors;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;

    }

}
