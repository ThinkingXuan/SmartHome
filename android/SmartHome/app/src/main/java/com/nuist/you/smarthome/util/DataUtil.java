package com.nuist.you.smarthome.util;

import com.nuist.you.smarthome.base.BaseString;
import com.nuist.you.smarthome.bean.AverageDataBean;
import com.nuist.you.smarthome.bean.viewbean.HomeAvageBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * created by youxuan
 * on 2020/4/16
 */
public class DataUtil {

    /**
     * @param mapData 服务器返回的Map集合
     * @param type    需要解析的数据类型 比如 temp,hum,mq2等
     * @return
     */
    public static List<HomeAvageBean> getHomeAvageBean(Map<String, AverageDataBean> mapData, String type) {
        List<HomeAvageBean> mList = new ArrayList<>();
        HomeAvageBean homeAvageBean;
        switch (type) {
            case BaseString.DATA_TEMP:
                homeAvageBean = getHomeAvage(mapData, BaseString.DATA_TITLE_HOUR, BaseString.TIME_STR_HOUR, BaseString.DATA_TEMP);
                mList.add(homeAvageBean);

                homeAvageBean = getHomeAvage(mapData, BaseString.DATA_TITLE_TODAY, BaseString.TIME_STR_TODAY, BaseString.DATA_TEMP);
                mList.add(homeAvageBean);

                homeAvageBean = getHomeAvage(mapData, BaseString.DATA_TITLE_YESTERDAY, BaseString.TIME_STR_YESTERDAY, BaseString.DATA_TEMP);
                mList.add(homeAvageBean);

                homeAvageBean = getHomeAvage(mapData, BaseString.DATA_TITLE_MONTH, BaseString.TIME_STR_MONTH, BaseString.DATA_TEMP);
                mList.add(homeAvageBean);

                homeAvageBean = getHomeAvage(mapData, BaseString.DATA_TITLE_YEAR, BaseString.TIME_STR_YEAR, BaseString.DATA_TEMP);
                mList.add(homeAvageBean);
                break;
            case BaseString.DATA_HUM:
                homeAvageBean = getHomeAvage(mapData, BaseString.DATA_TITLE_HOUR, BaseString.TIME_STR_HOUR, BaseString.DATA_HUM);
                mList.add(homeAvageBean);

                homeAvageBean = getHomeAvage(mapData, BaseString.DATA_TITLE_TODAY, BaseString.TIME_STR_TODAY, BaseString.DATA_HUM);
                mList.add(homeAvageBean);

                homeAvageBean = getHomeAvage(mapData, BaseString.DATA_TITLE_YESTERDAY, BaseString.TIME_STR_YESTERDAY, BaseString.DATA_HUM);
                mList.add(homeAvageBean);

                homeAvageBean = getHomeAvage(mapData, BaseString.DATA_TITLE_MONTH, BaseString.TIME_STR_MONTH, BaseString.DATA_HUM);
                mList.add(homeAvageBean);

                homeAvageBean = getHomeAvage(mapData, BaseString.DATA_TITLE_YEAR, BaseString.TIME_STR_YEAR, BaseString.DATA_HUM);
                mList.add(homeAvageBean);
                break;
            case BaseString.DATA_MQ2:
                homeAvageBean = getHomeAvage(mapData, BaseString.DATA_TITLE_HOUR, BaseString.TIME_STR_HOUR, BaseString.DATA_MQ2);
                mList.add(homeAvageBean);

                homeAvageBean = getHomeAvage(mapData, BaseString.DATA_TITLE_TODAY, BaseString.TIME_STR_TODAY, BaseString.DATA_MQ2);
                mList.add(homeAvageBean);

                homeAvageBean = getHomeAvage(mapData, BaseString.DATA_TITLE_YESTERDAY, BaseString.TIME_STR_YESTERDAY, BaseString.DATA_MQ2);
                mList.add(homeAvageBean);

                homeAvageBean = getHomeAvage(mapData, BaseString.DATA_TITLE_MONTH, BaseString.TIME_STR_MONTH, BaseString.DATA_MQ2);
                mList.add(homeAvageBean);

                homeAvageBean = getHomeAvage(mapData, BaseString.DATA_TITLE_YEAR, BaseString.TIME_STR_YEAR, BaseString.DATA_MQ2);
                mList.add(homeAvageBean);
                break;
            case BaseString.DATA_LIGHT:
                homeAvageBean = getHomeAvage(mapData, BaseString.DATA_TITLE_HOUR, BaseString.TIME_STR_HOUR, BaseString.DATA_LIGHT);
                mList.add(homeAvageBean);

                homeAvageBean = getHomeAvage(mapData, BaseString.DATA_TITLE_TODAY, BaseString.TIME_STR_TODAY, BaseString.DATA_LIGHT);
                mList.add(homeAvageBean);

                homeAvageBean = getHomeAvage(mapData, BaseString.DATA_TITLE_YESTERDAY, BaseString.TIME_STR_YESTERDAY, BaseString.DATA_LIGHT);
                mList.add(homeAvageBean);

                homeAvageBean = getHomeAvage(mapData, BaseString.DATA_TITLE_MONTH, BaseString.TIME_STR_MONTH, BaseString.DATA_LIGHT);
                mList.add(homeAvageBean);

                homeAvageBean = getHomeAvage(mapData, BaseString.DATA_TITLE_YEAR, BaseString.TIME_STR_YEAR, BaseString.DATA_LIGHT);
                mList.add(homeAvageBean);
                break;
        }
        return mList;
    }

    private static HomeAvageBean getHomeAvage(Map<String, AverageDataBean> mapData, String title, String dateType, String dataType) {
        HomeAvageBean homeAvageBean = new HomeAvageBean();
        homeAvageBean.setAvageTitle(title);

        if (null != mapData && null != mapData.get(dateType)) {
            switch (dataType) {
                case BaseString.DATA_TEMP:
                    homeAvageBean.setAvageData(formatFloat(mapData.get(dateType).getAvg_temp()) + "℃");
                    break;
                case BaseString.DATA_HUM:
                    homeAvageBean.setAvageData(formatFloat(mapData.get(dateType).getAvg_hum()) + "%");
                    break;
                case BaseString.DATA_MQ2:
                    homeAvageBean.setAvageData(formatFloat(DataUtil.MQ2Convert(mapData.get(dateType).getAvg_mq2())) + "P");
                    break;
                case BaseString.DATA_LIGHT:
                    homeAvageBean.setAvageData(formatFloat(DataUtil.BeamConvert(mapData.get(dateType).getAvg_beam())) + "L");
                    break;
            }
        } else {
            homeAvageBean.setAvageData("0");
        }
        return homeAvageBean;
    }

    //保留1位小数
    private static float formatFloat(float f) {
        return (float) (Math.round(f * 10)) / 10;
    }


    //烟雾浓度转换
    public static double MQ2Convert(String intValue) {
        Double value = Double.valueOf(intValue);
        Double v0 = value * 5 / 127;        //ADC 128分辨率
        double v1 = 11.5428 * 35.904 * v0 / (25.5 - 5.1 * v0);
        return Math.pow(v1, 0.6549);
    }

    //烟雾浓度转换
    public static float MQ2Convert(float floatValue) {
        float v0 = floatValue * 5 / 127;        //ADC 128分辨率
        float v1 = (float) (11.5428 * 35.904 * v0 / (25.5 - 5.1 * v0));
        return (float) Math.pow(v1, 0.6549);
    }

    //光照强度转换
    public static double BeamConvert(String intValue) {
        Double value = Double.valueOf(intValue);
        return 127 - value;
    }

    //光照强度转换
    public static float BeamConvert(float floatValue) {
        return 127 - floatValue;
    }

}
