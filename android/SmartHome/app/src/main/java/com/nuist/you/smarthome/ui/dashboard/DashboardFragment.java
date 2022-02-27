package com.nuist.you.smarthome.ui.dashboard;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.mob.wrappers.UMSSDKWrapper;
import com.nuist.you.smarthome.R;
import com.nuist.you.smarthome.bean.BaseCode;
import com.nuist.you.smarthome.bean.ControlDeviceBean;
import com.nuist.you.smarthome.bean.DeviceData;
import com.nuist.you.smarthome.bean.SensorData;
import com.nuist.you.smarthome.bean.SensorDataBean;
import com.nuist.you.smarthome.bean.Users;
import com.nuist.you.smarthome.http.ApiManage;
import com.nuist.you.smarthome.util.DateUtil;
import com.nuist.you.smarthome.util.DialogUtil;
import com.nuist.you.smarthome.util.OpenFileUtil;
import com.nuist.you.smarthome.util.PieChartUtils;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.read.biff.BiffException;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WriteException;
import me.zhouzhuo.zzexcelcreator.ColourUtil;
import me.zhouzhuo.zzexcelcreator.ZzExcelCreator;
import me.zhouzhuo.zzexcelcreator.ZzFormatCreator;

public class DashboardFragment extends Fragment implements OnDateSetListener, View.OnClickListener {

    private LinearLayout mLinerSensorStartTimePick;
    private TextView mTvPickStartTime;
    private TextView mTvSensorStartTimePick;
    private LinearLayout mLinerSensorEndTimePick;
    private TextView mTvSensorEndTimePick;
    private LinearLayout mLinerControlStartTimePick;
    private TextView mTvControlStartTimePick;
    private LinearLayout mLinerControlEndTimePick;
    private TextView mTvControlEndTimePick;
    private DashboardViewModel dashboardViewModel;
    private String xlsName = "";

    private TimePickerDialog mDialogYearMonthDay;
    private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

    private Button mButton;
    private Button mButton2;
    private static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SmartHome/";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        initView(root);
        return root;
    }

    private void initView(View root) {
        mButton = root.findViewById(R.id.bt_get_all_sensor);
        mButton2 = root.findViewById(R.id.bt_get_all_control);
        mLinerSensorStartTimePick = (LinearLayout) root.findViewById(R.id.liner_sensor_start_time_pick);
        mTvPickStartTime = (TextView) root.findViewById(R.id.tv_pick_start_time);
        mTvSensorStartTimePick = (TextView) root.findViewById(R.id.tv_sensor_start_time_pick);
        mLinerSensorEndTimePick = (LinearLayout) root.findViewById(R.id.liner_sensor_end_time_pick);
        mTvSensorEndTimePick = (TextView) root.findViewById(R.id.tv_sensor_end_time_pick);
        mLinerControlStartTimePick = (LinearLayout) root.findViewById(R.id.liner_control_start_time_pick);
        mTvControlStartTimePick = (TextView) root.findViewById(R.id.tv_control_start_time_pick);
        mLinerControlEndTimePick = (LinearLayout) root.findViewById(R.id.liner_control_end_time_pick);
        mTvControlEndTimePick = (TextView) root.findViewById(R.id.tv_control_end_time_pick);
        mLinerSensorStartTimePick.setOnClickListener(this);
        mLinerSensorEndTimePick.setOnClickListener(this);
        mLinerControlStartTimePick.setOnClickListener(this);
        mLinerControlEndTimePick.setOnClickListener(this);

        getXlsName();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Users users = new Users();  //为了方便，使用User记录 开始和结束时间
                users.setUserId(mTvSensorStartTimePick.getText().toString().trim());
                users.setUserName(mTvSensorEndTimePick.getText().toString().trim());
                xlsName = "SmartHome" + getXlsName();
                ApiManage.getInstance().getChatService().getAllSensorData(users)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<SensorDataBean>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                DialogUtil.showProgressDialog(getContext(), "正在下载中，请稍后");
                            }

                            @Override
                            public void onNext(List<SensorDataBean> value) {
                                Logger.d(value.size());
                                //生成Excel表格
                                if (null != value && value.size() > 0) {
                                    createExcel(value);
                                } else {
                                    DialogUtil.closeProgressDialog();
                                    Toast.makeText(getContext(), "没有数据", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                DialogUtil.closeProgressDialog();
                                Toast.makeText(getContext(), "没有数据", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onComplete() {
//                                DialogUtil.closeProgressDialog();
                            }
                        });
            }
        });


        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Users users = new Users();  //为了方便，使用User记录 开始和结束时间
                users.setUserId(mTvControlStartTimePick.getText().toString().trim());
                users.setUserName(mTvControlEndTimePick.getText().toString().trim());
                xlsName = "SmartHome" + getXlsName();
                ApiManage.getInstance().getChatService().getConntrolxlsData(users)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<DeviceData>>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                                DialogUtil.showProgressDialog(getContext(), "正在下载中，请稍后");
                            }

                            @Override
                            public void onNext(List<DeviceData> value) {
                                Logger.d(value.size());
                                //生成Excel表格
                                if (null != value && value.size() > 0) {
                                    createExcel2(value);
                                } else {
                                    DialogUtil.closeProgressDialog();
                                    Toast.makeText(getContext(), "没有数据", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                DialogUtil.closeProgressDialog();
                                Toast.makeText(getContext(), "没有数据", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onComplete() {
//
                            }
                        });
            }
        });
    }

    private long getXlsName() {
        Date date = new Date();
        return date.getTime();
    }


    private void createExcel2(List<DeviceData> dataBeans) {

        new AsyncTask<String, Void, Integer>() {

            @Override
            protected Integer doInBackground(String... params) {
                try {

                    List<String> title = Arrays.asList("用户名", "设备类型", "控制方式", "控制动作", "控制原因", "控制时间");

                    ZzExcelCreator zzExcelCreator = createExc();
                    createTitle(zzExcelCreator, title);
                    createContent2(zzExcelCreator, dataBeans);
                    zzExcelCreator.close();
                    return 1;
                } catch (Exception e) {
                    e.printStackTrace();
                    return 0;
                }
            }

            @Override
            protected void onPostExecute(Integer aVoid) {
                super.onPostExecute(aVoid);
                DialogUtil.closeProgressDialog();

                if (aVoid == 1) {
                    Toast.makeText(getActivity(), "表格创建成功！请到" + PATH + "路径下查看~", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "表格创建失败！", Toast.LENGTH_SHORT).show();
                }

                Logger.d(PATH + xlsName + ".xls");
                OpenFileUtil.openFile(getContext(), PATH + xlsName + ".xls");

            }
        }.execute("fileName", "sheetName");

    }

    private void createExcel(List<SensorDataBean> dataBeans) {
        new AsyncTask<String, Void, Integer>() {

            @Override
            protected Integer doInBackground(String... params) {
                try {


                    List<String> title = Arrays.asList("温度", "湿度", "烟雾浓度", "光照强度", "采集时间");

                    ZzExcelCreator zzExcelCreator = createExc();
                    createTitle(zzExcelCreator, title);
                    createContent(zzExcelCreator, dataBeans);

                    zzExcelCreator.close();
                    return 1;
                } catch (Exception e) {
                    e.printStackTrace();
                    return 0;
                }
            }

            @Override
            protected void onPostExecute(Integer aVoid) {
                super.onPostExecute(aVoid);
                DialogUtil.closeProgressDialog();

                if (aVoid == 1) {
                    Toast.makeText(getActivity(), "表格创建成功！请到" + PATH + "路径下查看~", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "表格创建失败！", Toast.LENGTH_SHORT).show();
                }

                Logger.d(PATH + xlsName + ".xls");
                OpenFileUtil.openFile(getContext(), PATH + xlsName + ".xls");

            }
        }.execute("fileName", "sheetName");

    }

    public ZzExcelCreator createExc() {
        ZzExcelCreator zzExcelCreator = null;
        try {
            zzExcelCreator = ZzExcelCreator
                    .getInstance()
                    .createExcel(PATH, xlsName)
                    .createSheet("Sheet1");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return zzExcelCreator;
    }

    //填充内容
    public void createContent2(ZzExcelCreator zzExcelCreator, List<DeviceData> dataBeans) {
        int col = 0;
        int row = 1;
        try {
            for (DeviceData s : dataBeans) {

                zzExcelCreator
                        .fillContent(col++, row, s.getUserId(), getContentCellFormat())
                        .fillContent(col++, row, s.getDeviceType(), getContentCellFormat())
                        .fillContent(col++, row, s.getControlMode(), getContentCellFormat())
                        .fillContent(col++, row, s.getControlAction(), getContentCellFormat())
                        .fillContent(col++, row, s.getControlReason(), getContentCellFormat())
                        .fillContent(col++, row, DateUtil.timeStamp2Date(s.getControlTime(), "yyyy-MM-dd HH:mm:ss"), getContentCellFormat());
                row++;
                col = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //填充内容
    public void createContent(ZzExcelCreator zzExcelCreator, List<SensorDataBean> dataBeans) {
        int col = 0;
        int row = 1;
        try {
            for (SensorDataBean s : dataBeans) {

                zzExcelCreator
                        .fillContent(col++, row, s.getTemperature(), getContentCellFormat())
                        .fillContent(col++, row, s.getHumidity(), getContentCellFormat())
                        .fillContent(col++, row, s.getMq2(), getContentCellFormat())
                        .fillContent(col++, row, s.getBeam(), getContentCellFormat())
                        .fillContent(col++, row, DateUtil.timeStamp2Date(s.getTime(), "yyyy-MM-dd HH:mm:ss"), getContentCellFormat());
                row++;
                col = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //创建标题
    public void createTitle(ZzExcelCreator zzExcelCreator, List<String> title) {
        int i = 0;
        try {
            for (String s : title) {
                zzExcelCreator
                        .fillContent(i++, 0, s, getTitleCellFormat());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public WritableCellFormat getTitleCellFormat() throws WriteException {

        WritableCellFormat cellFormat = ZzFormatCreator
                .getInstance()
                .createCellFont(WritableFont.ARIAL)
                .setAlignment(Alignment.CENTRE, VerticalAlignment.CENTRE)
                .setFontSize(15)
                .setFontBold(true)
                .setUnderline(true)
                .setItalic(true)
                .setWrapContent(true, 100)
                .setFontColor(ColourUtil.getCustomColor3("#0187fb"))
                .getCellFormat();

        return cellFormat;
    }

    public WritableCellFormat getContentCellFormat() {

        WritableCellFormat cellFormat = ZzFormatCreator
                .getInstance()
                .getCellFormat();

        return cellFormat;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.liner_sensor_start_time_pick:
                buildTimePicker("开始时间");
                mDialogYearMonthDay.show(getFragmentManager(), "sensor_start");
                break;
            case R.id.liner_sensor_end_time_pick:
                buildTimePicker("结束时间");
                mDialogYearMonthDay.show(getFragmentManager(), "sensor_end");
                break;
            case R.id.liner_control_start_time_pick:
                buildTimePicker("开始时间");
                mDialogYearMonthDay.show(getFragmentManager(), "control_start");
                break;
            case R.id.liner_control_end_time_pick:
                buildTimePicker("结束时间");
                mDialogYearMonthDay.show(getFragmentManager(), "control_end");

                break;
        }
    }

    void buildTimePicker(String title) {
        mDialogYearMonthDay = new TimePickerDialog.Builder()
                .setTitleStringId(title)
                .setThemeColor(R.color.colorPrimaryDark)
                .setType(Type.YEAR_MONTH_DAY)
                .setCallBack(this)
                .build();
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {

        long nowcurrentTime = System.currentTimeMillis();

        assert timePickerView.getTag() != null;
        switch (timePickerView.getTag()) {

            case "sensor_start":

                if (millseconds > nowcurrentTime) {
                    Toast.makeText(getActivity(), "时间选择错误", Toast.LENGTH_SHORT).show();
                } else {
                    mTvSensorStartTimePick.setText(getDateToString(millseconds));
                }
                break;
            case "sensor_end":
                    mTvSensorEndTimePick.setText(getDateToString(millseconds));

                break;
            case "control_start":
                if (millseconds > nowcurrentTime) {
                    Toast.makeText(getActivity(), "时间选择错误", Toast.LENGTH_SHORT).show();
                } else {
                    mTvControlStartTimePick.setText(getDateToString(millseconds));
                }
                break;
            case "control_end":
                mTvControlEndTimePick.setText(getDateToString(millseconds));
                break;
        }

    }

    public String getDateToString(long time) {
        Date d = new Date(time);
        return sf.format(d);
    }
}
