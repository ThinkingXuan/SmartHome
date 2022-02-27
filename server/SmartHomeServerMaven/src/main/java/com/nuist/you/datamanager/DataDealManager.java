package com.nuist.you.datamanager;

import com.nuist.you.bean.*;
import com.nuist.you.bean.daobean.*;
import com.nuist.you.dao.AverageDao;
import com.nuist.you.dao.DeviceDao;
import com.nuist.you.dao.SensorDao;
import com.nuist.you.dao.User2Dao;
import com.nuist.you.dao.impl.AverageDaoImpl;
import com.nuist.you.dao.impl.DeviceDaoImpl;
import com.nuist.you.dao.impl.SensorDaoImpl;
import com.nuist.you.dao.impl.User2DaoImpl;
import com.nuist.you.espsocket.ESPSocket;
import com.nuist.you.espsocket.ESPSocketManager;
import com.nuist.you.listener.DataReceiveListener;
import com.nuist.you.mywebsocket.ChatServer;
import com.nuist.you.mywebsocket.WebSocketManager;
import com.nuist.you.mywebsocket.WebSocketPool;
import com.nuist.you.util.*;
import com.nuist.you.util.gsonutil.GsonUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * author: youxuan
 */

//集中处理Android端和ESP8266发送来的数据
public class DataDealManager {


    private SensorDao sensorDao = new SensorDaoImpl();
    private DeviceDao deviceDao = new DeviceDaoImpl();
    private AverageDao averageDao = new AverageDaoImpl();
    private User2Dao user2Dao = new User2DaoImpl();

    boolean isBeap = false;

    public void deal() {

        ESPSocket chatSocket = ESPSocketManager.getInstance().getSocket();
        ChatServer chatServer = WebSocketManager.getInstance().getSocket();

        DataReceiveListener dataReceiveListener = new DataReceiveListener() {
            @Override
            public void onReceiveAndroid(String data) {
                LogUtil.info("Android监听器：" + data);
                receiveAndroidData(data);
            }

            @Override
            public void onReceiveESP8266(String data) {
                LogUtil.info("ESP8266监听器：" + data);
                receiveESP8266Data(data);
            }

        };
        chatServer.setReceiveListener(dataReceiveListener);

        //防止ESP未接入是出现异常
        if (chatSocket != null && chatSocket.isAlive()) {
            chatSocket.setReceiveListener(dataReceiveListener);
        }


    }

    //来自ESP数据处理
    private void receiveESP8266Data(String data) {

        //判断消息头
        String code = data.substring(0, 1);
        switch (code) {

            case SocketBase.FROM_ESP_SENSOR_CODE:
                //数据转换
                String contentjson = "";
                SensorData sensorData = null;
                if (null != data && "" != data) {
                    //转换Esp来的数据
                    sensorData = GsonUtil.ConvertSensor(SocketBase.SUCCESS, SocketBase.CODE_SUCCESS, data.substring(2, data.length()));
                    if (null !=sensorData){
                        //报警功能处理
                        AlarmDeal(sensorData.getObject());
                    }

                }


                if (null !=sensorData && sensorData.getCode() != SocketBase.CODE_FAILURE) {
                    //把数据转换为Json字符串
                    contentjson = GsonUtil.getJsonStr(sensorData);
                }

                if (null !=sensorData && !"".equals(contentjson) && sensorData.getCode() != SocketBase.CODE_FAILURE) {
                    //发送给所有客户端
                    WebSocketPool.sendMessage(contentjson);
                }


                if (null !=sensorData && sensorData.getCode() != SocketBase.CODE_FAILURE) {
                    //存储数据库
                    SensorDataBean sensor = sensorData.getObject();
                    sensor.setId(BaseUtil.getUUID32());
                    sensor.setTime(new Date());
                    sensorDao.insertSensorData(sensor);
                }


                break;

            case SocketBase.FROM_ESP_CONTROL_CODE:
                break;
        }


    }

    //报警功能处理
    void AlarmDeal(SensorDataBean sensorDataBean) {

        if (sensorDataBean.getMq2() > 100) {
            if (!isBeap) {    //没有打开

                ESPSocketManager.getInstance().publishAll(SocketBase.BEEP_OPEN);   //打开蜂鸣器
                insertControlToDB("00000000", SocketBase.BEEP_DEVICE, SocketBase.CONTROL_ACTION_OPEN, SocketBase.CONTROL_MODE_AUTO, SocketBase.CONTROL_REASON_ABNORMAL);

                //发送邮件
                List<Users> users = user2Dao.selectAllUser();
                if (null != users && users.size() > 0) {
                    for (int i = 0; i < users.size(); i++) {
                        String email = users.get(i).getUserEmail();
                        String subject = "家居环境异常";
                        String content = "家居烟雾浓度出现异常，蜂鸣器已自动打开，请及时关注，避免出现风险!!!";
                        try {
                            MailUtil.SendMail(email, subject, content);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                isBeap = true;
            }
        } else {

            if (isBeap) {          //已经打开
                ESPSocketManager.getInstance().publishAll(SocketBase.BEEP_CLOSE);   //打开蜂鸣器
                insertControlToDB("00000000", SocketBase.BEEP_DEVICE, SocketBase.CONTROL_ACTION_CLOSE, SocketBase.CONTROL_MODE_AUTO, SocketBase.CONTROL_REASON_ABNORMAL);
                isBeap = false;
            }
        }
    }

    //获取Message和Code
    private void receiveAndroidData(String data) {

        if (!"".equals(data) && null != data) {

            MessageBean sensorData = GsonUtil.getMessageBean(data);
            switch (sensorData.getCode()) {

                case SocketBase.CODE_CONTROAL:      //控制
                    receiveAndroidControlData(data);
                    break;
                case SocketBase.CODE_AVG_DATA:
                    receiveAndroidAvgData();       //请求主要的平均数据统计
                    break;
                case SocketBase.CODE_DRAW_CHART:
                    receiveAndroidDrawChart();

            }

        }
    }

    private void receiveAndroidDrawChart() {

        MessageAllData messageData = new MessageAllData();
        messageData.setCode(SocketBase.CODE_DRAW_CHART);
        messageData.setMessage(SocketBase.MESSAGE_DRAW_CHART);

        MessageAllData.ObjectBean objectBean = new MessageAllData.ObjectBean();

        Map<String, List<AverageDataBean>> maps = new HashMap<>();
        List<AverageDataBean> average;

        //获取所有小时统计数据
        average = averageDao.selectAllAvgData(SocketBase.TABLE_AVERAGE_HOUR);
        maps.put(SocketBase.TIME_STR_HOUR, average);

        //获取所有天时统计数据
        average = averageDao.selectAllAvgData(SocketBase.TABLE_AVERAGE_DAY);
        maps.put(SocketBase.TIME_STR_DAY, average);

        //获取所有月统计数据
        average = averageDao.selectAllAvgData(SocketBase.TABLE_AVERAGE_MONTH);
        maps.put(SocketBase.TIME_STR_MONTH, average);

        //获取所有年统计数据
        average = averageDao.selectAllAvgData(SocketBase.TABLE_AVERAGE_YEAR);
        maps.put(SocketBase.TIME_STR_YEAR, average);

        objectBean.setMaps(maps);
        messageData.setObject(objectBean);

        String json = GsonUtil.getJsonStr(messageData);

        LogUtil.info(json);
        //发送客户端
        WebSocketPool.sendMessage(json);


    }

    private void receiveAndroidAvgData() {
        MessageData messageData = new MessageData();
        messageData.setCode(SocketBase.CODE_AVG_DATA);
        messageData.setMessage(SocketBase.MESSAGE_AVG_DATA);

        MessageData.ObjectBean objectBean = new MessageData.ObjectBean();
        Map<String, AverageDataBean> map = new HashMap<>();
        AverageDataBean average;

        //小时
        average = averageDao.selectAvgData(SocketBase.TABLE_AVERAGE_HOUR, Dateutil.getNowHourDate(), SocketBase.TIME_FORMAT_HOUR);
        map.put(SocketBase.TIME_STR_HOUR, average);

        //今天
        average = averageDao.selectAvgData(SocketBase.TABLE_AVERAGE_DAY, Dateutil.getTodayDate(), SocketBase.TIME_FORMAT_DAY);
        map.put(SocketBase.TIME_STR_TODAY, average);

        //昨天
        average = averageDao.selectAvgData(SocketBase.TABLE_AVERAGE_DAY, Dateutil.getYesterDayDate(), SocketBase.TIME_FORMAT_DAY);
        map.put(SocketBase.TIME_STR_YESTERDAY, average);

        //本月 (不知道为什么 用Dateutil.getMonthDate()查不到)
        average = averageDao.selectAvgData(SocketBase.TABLE_AVERAGE_MONTH, Dateutil.getTodayDate(), SocketBase.TIME_FORMAT_MONTH);
        map.put(SocketBase.TIME_STR_MONTH, average);

        //本年
        average = averageDao.selectAvgData(SocketBase.TABLE_AVERAGE_YEAR, Dateutil.getTodayDate(), SocketBase.TIME_FORMAT_YEAR);
        map.put(SocketBase.TIME_STR_YEAR, average);

        objectBean.setMap(map);
        messageData.setObject(objectBean);

        //转换JSON
        String json = GsonUtil.getJsonStr(messageData);

        LogUtil.info(json);
        //发送客户端
        WebSocketPool.sendMessage(json);


    }

    //获取具体的控制命令发送给ESP
    private void receiveAndroidControlData(String data) {
        ControlBean controlBean = GsonUtil.getControlBean(data);
        ControlDeviceBean controlDeviceBean = controlBean.getObject();
        String str;
        if (null != controlDeviceBean) {
            str = controlDeviceBean.getBeep();
            if (!"".equals(str) && null != str) {
                LogUtil.info("Beep:" + str);
                switch (str) {
                    case SocketBase.OPEN:
                        ESPSocketManager.getInstance().publishAll(SocketBase.BEEP_OPEN);
                        insertControlToDB(controlDeviceBean.getUser_id(), SocketBase.BEEP_DEVICE, SocketBase.CONTROL_ACTION_OPEN, SocketBase.CONTROL_MODE_MANUAL, SocketBase.CONTROL_REASON_ROUTINE);
                        break;
                    case SocketBase.CLOSE:
                        ESPSocketManager.getInstance().publishAll(SocketBase.BEEP_CLOSE);
                        insertControlToDB(controlDeviceBean.getUser_id(), SocketBase.BEEP_DEVICE, SocketBase.CONTROL_ACTION_CLOSE, SocketBase.CONTROL_MODE_MANUAL, SocketBase.CONTROL_REASON_ROUTINE);

                        break;
                }
                return;
            }

            str = controlDeviceBean.getRelay();
            if (!"".equals(str) && null != str) {
                LogUtil.info("Relay:" + str);
                switch (str) {
                    case SocketBase.OPEN:
                        ESPSocketManager.getInstance().publishAll(SocketBase.RELAY_OPEN);
                        insertControlToDB(controlDeviceBean.getUser_id(), SocketBase.LIGHT_DEVICE, SocketBase.CONTROL_ACTION_OPEN, SocketBase.CONTROL_MODE_MANUAL, SocketBase.CONTROL_REASON_ROUTINE);
                        break;
                    case SocketBase.CLOSE:
                        ESPSocketManager.getInstance().publishAll(SocketBase.RELAY_CLOSE);
                        insertControlToDB(controlDeviceBean.getUser_id(), SocketBase.LIGHT_DEVICE, SocketBase.CONTROL_ACTION_CLOSE, SocketBase.CONTROL_MODE_MANUAL, SocketBase.CONTROL_REASON_ROUTINE);

                        break;
                }
                return;
            }

            str = controlDeviceBean.getDc_motor();
            if (!"".equals(str) && null != str) {
                LogUtil.info("Dc_motor:" + str);
                switch (str) {
                    case SocketBase.OPEN:
                        ESPSocketManager.getInstance().publishAll(SocketBase.DC_OPEN);
                        insertControlToDB(controlDeviceBean.getUser_id(), SocketBase.FAN_DEVICE, SocketBase.CONTROL_ACTION_OPEN, SocketBase.CONTROL_MODE_MANUAL, SocketBase.CONTROL_REASON_ROUTINE);

                        break;
                    case SocketBase.CLOSE:
                        ESPSocketManager.getInstance().publishAll(SocketBase.DC_CLOSE);
                        insertControlToDB(controlDeviceBean.getUser_id(), SocketBase.FAN_DEVICE, SocketBase.CONTROL_ACTION_CLOSE, SocketBase.CONTROL_MODE_MANUAL, SocketBase.CONTROL_REASON_ROUTINE);

                        break;
                }
            }
        }


    }

    private void insertControlToDB(String user_id, String deviceType, String controlAction, String controlMode, String controlReason) {
        DeviceData deviceData = new DeviceData();
        deviceData.setId(BaseUtil.getUUID32());
        deviceData.setUserId(user_id);
        deviceData.setControlTime(new Date());
        deviceData.setDeviceType(deviceType);
        deviceData.setControlAction(controlAction);
        deviceData.setControlMode(controlMode);
        deviceData.setControlReason(controlReason);

        deviceDao.insertControlData(deviceData);
    }
}
