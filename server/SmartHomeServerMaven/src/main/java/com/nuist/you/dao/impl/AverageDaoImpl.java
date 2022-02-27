package com.nuist.you.dao.impl;

import com.nuist.you.bean.daobean.AverageDataBean;
import com.nuist.you.dao.AverageDao;
import com.nuist.you.util.DBCPUtil;
import com.nuist.you.util.LogUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class AverageDaoImpl implements AverageDao {

    private QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());

    @Override
    public boolean insertOneAverageData(String table, AverageDataBean data) {

        String sql = "insert into " + table + "(average_id,average_temp,average_hum,average_mq2,average_beam,average_time) values(?,?,?,?,?,?)";
        try {
            Object[] params = new Object[]{data.getAvg_id(), data.getAvg_temp(), data.getAvg_hum(), data.getAvg_mq2(), data.getAvg_beam(), data.getAvg_time()};
            int flag = qr.update(sql, params);
            return flag > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteDataByTime(String table, String date) {
        String sql = "delete from " + table + " where average_time = ?";
        try {
            Object[] params = new Object[]{date};
            int flag = qr.update(sql, params);
            return flag > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Date selectLastestTime(String table) {

        Date date;
        try {
            String sql = "SELECT max(average_time) as 'time'  from " + table;
            date = qr.query(sql, new ScalarHandler<Date>("time"));
            return date;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AverageDataBean selectAvgData(String table, String time, String Date_Format_DB) {


        try {
            String sql = "select average_id as avg_id,average_temp as avg_temp,average_hum as avg_hum,average_mq2 as avg_mq2,average_beam as avg_beam,average_time as avg_time from " + table + " where " +
                    "DATE_FORMAT('" + time + "','" + Date_Format_DB + "') = DATE_FORMAT(average_time,'" + Date_Format_DB + "')";
            LogUtil.info(sql);
            List<AverageDataBean> sensors = qr.query(sql, new BeanListHandler<AverageDataBean>(AverageDataBean.class));

            if (null != sensors && sensors.size() > 0) {
                return sensors.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<AverageDataBean> selectAllAvgData(String tableName) {


        try {
            String sql = "select average_id as avg_id,average_temp as avg_temp,average_hum as avg_hum,average_mq2 as avg_mq2,average_beam as avg_beam,average_time as avg_time from " + tableName + " order by average_time asc";
            LogUtil.info(sql);
            List<AverageDataBean> sensors = qr.query(sql, new BeanListHandler<AverageDataBean>(AverageDataBean.class));

            if (null != sensors && sensors.size() > 0) {
                return sensors;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
