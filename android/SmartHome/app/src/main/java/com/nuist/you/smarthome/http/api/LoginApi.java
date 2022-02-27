package com.nuist.you.smarthome.http.api;


import com.nuist.you.smarthome.bean.BaseCode;
import com.nuist.you.smarthome.bean.DeviceData;
import com.nuist.you.smarthome.bean.SensorData;
import com.nuist.you.smarthome.bean.SensorDataBean;
import com.nuist.you.smarthome.bean.Users;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginApi {

    @POST("user/register")
    Observable<BaseCode> register(@Body Users users);

    @POST("user/login")
    Observable<BaseCode> login(@Body Users users);

    @POST("user/updateUser")
    Observable<BaseCode> rest(@Body Users users);

    @POST("user/getxls")
    Observable<List<SensorDataBean>> getAllSensorData(@Body Users users);

    @POST("user/getConntrolxls")
    Observable<List<DeviceData>> getConntrolxlsData(@Body Users users);



}
