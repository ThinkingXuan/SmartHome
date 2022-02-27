package com.nuist.you.smarthome.ui.device;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.nuist.you.smarthome.R;
import com.nuist.you.smarthome.base.BaseString;
import com.nuist.you.smarthome.bean.ControlBean;
import com.nuist.you.smarthome.bean.ControlDeviceBean;
import com.nuist.you.smarthome.http.two.WsManager;
import com.nuist.you.smarthome.util.GsonUtil;

public class DeviceFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    private static final String TAG = DeviceFragment.class.getSimpleName();
    private DeviceViewModel mDeviceViewModel;
    private Switch mSwitchLampDevices;
    private Switch mSwitchBeapDevices;
    private Switch mSwitchFanDevices;

    private ImageView mIvLightIcon;
    private ImageView mIvBeamIcon;
    private ImageView mIvFanIcon;
    private ImageView mIvTvIcon;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mDeviceViewModel =
                ViewModelProviders.of(this).get(DeviceViewModel.class);
        View root = inflater.inflate(R.layout.fragment_devices, container, false);
        initView(root);
        return root;
    }

    private void initView(View view) {
        mSwitchLampDevices = (Switch) view.findViewById(R.id.switch_lamp_devices);
        mSwitchBeapDevices = (Switch) view.findViewById(R.id.switch_beap_devices);
        mSwitchFanDevices = (Switch) view.findViewById(R.id.switch_fan_devices);
        mSwitchBeapDevices.setOnCheckedChangeListener(this);
        mSwitchLampDevices.setOnCheckedChangeListener(this);
        mSwitchFanDevices.setOnCheckedChangeListener(this);

        mIvLightIcon = (ImageView) view.findViewById(R.id.iv_light_icon);
        mIvBeamIcon = (ImageView) view.findViewById(R.id.iv_beam_icon);
        mIvFanIcon = (ImageView) view.findViewById(R.id.iv_fan_icon);
        mIvTvIcon = (ImageView) view.findViewById(R.id.iv_tv_icon);
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.switch_beap_devices:
                if (isChecked) {
                    SendControlMessage(BaseString.DEVICE_BEEP, BaseString.OPEN);
                    mIvBeamIcon.setBackgroundResource(R.drawable.ic_device_beep_open);
                } else {
                    SendControlMessage(BaseString.DEVICE_BEEP, BaseString.CLOSE);
                    mIvBeamIcon.setBackgroundResource(R.drawable.ic_device_beep_close);
                }
                break;
            case R.id.switch_fan_devices:
                if (isChecked) {
                    SendControlMessage(BaseString.DEVICE_FAN, BaseString.OPEN);
                    mIvFanIcon.setBackgroundResource(R.drawable.ic_device_fan_open);
                } else {
                    SendControlMessage(BaseString.DEVICE_FAN, BaseString.CLOSE);
                    mIvFanIcon.setBackgroundResource(R.drawable.ic_device_fan_close);
                }
                break;
            case R.id.switch_lamp_devices:
                if (isChecked) {
                    SendControlMessage(BaseString.DEVICE_LAMP, BaseString.OPEN);
                    mIvLightIcon.setBackgroundResource(R.drawable.ic_deivce_lamp_open);
                } else {
                    SendControlMessage(BaseString.DEVICE_LAMP, BaseString.CLOSE);
                    mIvLightIcon.setBackgroundResource(R.drawable.ic_deivce_lamp_close);
                }
                break;
        }
    }

    //向服务器发送设备控制命令
    void SendControlMessage(String type, String state) {
        ControlBean controlBean = new ControlBean();
        controlBean.setMessage(BaseString.MESSAGE_CONTROAL);
        controlBean.setCode(BaseString.CODE_CONTROAL);
        ControlDeviceBean controlDeviceBean = new ControlDeviceBean();
        controlDeviceBean.setUser_id("youxuan");

        switch (type) {
            case BaseString.DEVICE_BEEP:
                controlDeviceBean.setBeep(state);
                break;
            case BaseString.DEVICE_FAN:
                controlDeviceBean.setDc_motor(state);
                break;
            case BaseString.DEVICE_LAMP:
                controlDeviceBean.setRelay(state);
                break;
        }
        controlBean.setObject(controlDeviceBean);
        WsManager.getInstance().sendMessage(GsonUtil.getJsonStr(controlBean));
    }
}
