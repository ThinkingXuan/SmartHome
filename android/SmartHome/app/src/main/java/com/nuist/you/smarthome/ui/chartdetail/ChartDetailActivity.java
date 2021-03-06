package com.nuist.you.smarthome.ui.chartdetail;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.nuist.you.smarthome.MainActivity;
import com.nuist.you.smarthome.R;
import com.nuist.you.smarthome.base.BaseString;
import com.nuist.you.smarthome.bean.AverageDataBean;
import com.nuist.you.smarthome.bean.MessageAllData;
import com.nuist.you.smarthome.bean.MessageBean;
import com.nuist.you.smarthome.bean.MessageEvent;
import com.nuist.you.smarthome.bean.MessageEventClick;
import com.nuist.you.smarthome.bean.SensorData;
import com.nuist.you.smarthome.http.two.WsManager;
import com.nuist.you.smarthome.listener.ItemDataListener;
import com.nuist.you.smarthome.ui.home.HomeFragment;
import com.nuist.you.smarthome.util.DataUtil;
import com.nuist.you.smarthome.util.DateUtil;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * created by youxuan
 * on 2020/4/17
 */
public class ChartDetailActivity extends AppCompatActivity {

    public static final String TAG = ChartDetailActivity.class.getSimpleName();

    private LineChart mLineChart;
    private LineChart mDayLineChart;
    private LineChart mMonthChart;
    private LineChart mYearChart;
    private int currentPosition;
    private List<AverageDataBean> averageHourData;
    private List<AverageDataBean> averageDayData;
    private List<AverageDataBean> averageMonthData;
    private List<AverageDataBean> averageYearData;
    private List<Entry> hourEntryList;
    private List<Entry> dayEntryList;
    private List<Entry> monthEntryList;
    private List<Entry> yearEntryList;
    private String label;
    private String[] title = {"????????????", "????????????", "??????????????????", "??????????????????"};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chardetail);
        initView();
        initData();
    }

    private void initView() {
        mLineChart = (LineChart) findViewById(R.id.hour_lineChart);
        mDayLineChart = (LineChart) findViewById(R.id.day_lineChart);
        mMonthChart = (LineChart) findViewById(R.id.month_chart);
        mYearChart = (LineChart) findViewById(R.id.year_chart);
    }

    private void initData() {
        //????????????
        Bundle bundle = getIntent().getExtras();
        MessageAllData.ObjectBean objectBean = (MessageAllData.ObjectBean) bundle.getSerializable("data");
        Map<String, List<AverageDataBean>> mListMap = new HashMap<>();
        if (null != objectBean) {
            mListMap = objectBean.getMaps();
        }
        currentPosition = bundle.getInt("position");

        if (currentPosition < 4) {
            setTitle(title[currentPosition]);
        } else {
            setTitle(title[0]);
        }

        if (currentPosition == 0) {
            label = "??????";
        } else if (currentPosition == 1) {
            label = "??????";
        } else if (currentPosition == 2) {
            label = "????????????";
        } else if (currentPosition == 3) {
            label = "????????????";
        } else {
            label = "??????";
        }
        initEntryData(mListMap, currentPosition);
    }

    //?????????EntryData
    private void initEntryData(Map<String, List<AverageDataBean>> mListMap, int position) {
        averageHourData = mListMap.get(BaseString.TIME_STR_HOUR);
        averageDayData = mListMap.get(BaseString.TIME_STR_DAY);
        averageMonthData = mListMap.get(BaseString.TIME_STR_MONTH);
        averageYearData = mListMap.get(BaseString.TIME_STR_YEAR);
        hourEntryList = new ArrayList<>();
        dayEntryList = new ArrayList<>();
        monthEntryList = new ArrayList<>();
        yearEntryList = new ArrayList<>();
        initLineChart();
    }

    private void initLineChart() {
        mLineChart.setData(generateDataLine(averageHourData, BaseString.TIME_STR_HOUR));
        mDayLineChart.setData(generateDataLine(averageDayData, BaseString.TIME_STR_DAY));
        mMonthChart.setData(generateDataLine(averageMonthData, BaseString.TIME_STR_MONTH));
        mYearChart.setData(generateDataLine(averageYearData, BaseString.TIME_STR_YEAR));

        setLineCharStyle(mLineChart, BaseString.TIME_STR_HOUR);
        setLineCharStyle(mDayLineChart, BaseString.TIME_STR_DAY);
        setLineCharStyle(mMonthChart, BaseString.TIME_STR_MONTH);
        setLineCharStyle(mYearChart, BaseString.TIME_STR_YEAR);
    }

    private void setLineCharStyle(LineChart mLineChart, String type) {

        mLineChart.getDescription().setEnabled(true);
        mLineChart.getDescription().setText("");

//        mLineChart.setNoDataText("????????????");//??????????????????????????????
//        mLineChart.setNoDataTextColor(Color.WHITE);//????????????????????????????????????
//        mLineChart.setDrawGridBackground(false);//chart ???????????????????????????????????????
//        mLineChart.setDrawBorders(false);//????????????????????????????????????
//        mLineChart.setBorderColor(Color.WHITE); //?????? chart ?????????????????????
//        mLineChart.setBorderWidth(3f); //?????? chart ??????????????????????????? dp???
////        mLineChart.setTouchEnabled(true);     //????????????
////        mLineChart.setDragEnabled(false);   //????????????
////        mLineChart.setScaleEnabled(false);  //????????????
        mLineChart.animateX(1000);//???????????? ????????????

////        mLineChart.setDoubleTapToZoomEnabled(false);//????????????????????????????????????????????????????????????true
////        mLineChart.setHighlightPerDragEnabled(false);//?????????????????????(??????????????????????????????)????????????true
////        mLineChart.setDragDecelerationEnabled(false);//????????????????????????????????????????????????????????????true???false?????????????????????true??????????????????????????????
//
////        MyMarkerView mv = new MyMarkerView(mActivity,
////                R.layout.custom_marker_view);
////        mv.setChartView(mLineChart); // For bounds control
////        mLineChart.setMarker(mv);        //?????? marker ,???????????????????????? ????????????????????????
//
        XAxis xAxis = mLineChart.getXAxis();       //??????x??????
//        xAxis.setDrawAxisLine(true);//??????????????????
//        xAxis.setDrawGridLines(false);//??????x???????????????????????????
//        xAxis.setDrawLabels(true);//????????????  ???x?????????????????????

//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//??????x??????????????????
//        xAxis.setTextSize(12f);//??????????????????
//        xAxis.setAxisMinimum(0f);//??????x??????????????? //`
////        xAxis.setAxisMaximum(31f);//??????????????? //
//        xAxis.setLabelCount(5);  //??????X??????????????????
//        xAxis.setAvoidFirstLastClipping(false);//???????????????????????????????????????????????????????????????????????????????????????
//
        xAxis.setTextColor(getResources().getColor(R.color.subscribe_bg_press));
        xAxis.setAxisLineColor(Color.WHITE);//??????x????????????
        xAxis.setDrawGridLines(false);
//        xAxis.setAxisLineWidth(0.5f);//??????x????????????
        YAxis leftAxis = mLineChart.getAxisLeft();
        YAxis rightAxis = mLineChart.getAxisRight();

//        leftAxis.enableGridDashedLine(10f, 10f, 0f);  //??????Y??????????????????????????????1 ??????????????????2 ???????????? ??????3 ??????
//        leftAxis.setGranularity(20f); // ??????????????????
//        axisRight.setEnabled(false);   //?????????????????? Y????????????
//        leftAxis.setEnabled(true);     //?????????????????? Y????????????
//        leftAxis.setGridColor(Color.parseColor("#7189a9"));  //?????????????????????
        leftAxis.setDrawLabels(true);        //????????????Y?????????
//        leftAxis.setStartAtZero(true);        //??????Y????????? ????????????
//        leftAxis.setDrawGridLines(true);      //???????????? Y???????????????
//        leftAxis.setTextSize(12f);            //??????Y???????????????
        leftAxis.setTextColor(Color.WHITE);   //??????????????????
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setDrawGridLines(false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setEnabled(false);

//        leftAxis.setAxisLineColor(Color.WHITE); //??????Y?????????
//        leftAxis.setAxisLineWidth(0.5f);
//        leftAxis.setDrawAxisLine(true);//??????????????????
//        leftAxis.setMinWidth(0f);
//        leftAxis.setMaxWidth(200f);
//        leftAxis.setDrawGridLines(false);//??????x???????????????????????????
        Legend l = mLineChart.getLegend();//??????
        l.setTextColor(Color.WHITE);
//        l.setEnabled(false);   //???????????? ??????

        leftAxis.setTextColor(getResources().getColor(R.color.subscribe_bg_press));   //??????????????????
        leftAxis.setAxisLineColor(Color.WHITE);

        rightAxis.setTextColor(getResources().getColor(R.color.subscribe_bg_press));   //??????????????????
        rightAxis.setAxisLineColor(Color.WHITE);

//
        //??????X???????????????
//        XAxis xAxis = mLineChart.getXAxis();
        YAxis yAxis = mLineChart.getAxisLeft();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        //??????
        if (type.equals(BaseString.TIME_STR_HOUR)) {
            if (hourEntryList.size() > 10) {
                //????????????????????????????????????
                mLineChart.setScaleMinima(8.0f, 1.0f);
            } else {
                mLineChart.setScaleMinima(1.5f, 1.0f);
            }

            xAxis.setLabelRotationAngle(-30);     //x???label??????
            mLineChart.moveViewToX(hourEntryList.size() - 1);

            setXAxisValueFromatter(xAxis, BaseString.TIME_STR_HOUR);
            setXAxisValueFromatter(mDayLineChart.getXAxis(), BaseString.TIME_STR_DAY);
            setXAxisValueFromatter(mMonthChart.getXAxis(), BaseString.TIME_STR_MONTH);
            setXAxisValueFromatter(mYearChart.getXAxis(), BaseString.TIME_STR_YEAR);

        }
//        xAxis.setAxisMinimum(5);

        setYAxisValueFromatter(yAxis);

////        xAxis.setValueFormatter((value, axis) -> mValues.get((int)  value).getData()+"");
//
////        xAxis.setLabelsToSkip(4);
////        xAxis.resetLabelsToSkip();
//
        // ??????x??????LimitLine
        LimitLine yLimitLine = new LimitLine(20f, "?????????");
        yLimitLine.setLineColor(Color.RED);
        yLimitLine.setTextColor(Color.RED);
        // ????????????????????????
        leftAxis = mLineChart.getAxisLeft();
        leftAxis.addLimitLine(yLimitLine);
    }

    private void setYAxisValueFromatter(YAxis yAxis) {
        switch (currentPosition) {
            case 0:
            case 1:
                yAxis.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        return value + "%";
                    }
                });
                break;
            case 2:
                yAxis.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        return value + "P";
                    }
                });
                break;
            case 3:
                yAxis.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        return value + "L";
                    }
                });
                break;
            default:
                yAxis.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        return value + "%";
                    }
                });
                break;
        }
    }

    private void setXAxisValueFromatter(XAxis xAxis, String type) {

        switch (type) {
            case BaseString.TIME_STR_HOUR:
                xAxis.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getAxisLabel(float value, AxisBase axis) {
                        String datestr = hourEntryList.get((int) value).getData().toString();
                        return datestr.substring(5, datestr.length() - 6) + "???";
                    }
                });
                break;
            case BaseString.TIME_STR_DAY:
                xAxis.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getAxisLabel(float value, AxisBase axis) {
                        String datestr = dayEntryList.get((int) value).getData().toString();
                        return datestr.substring(5, datestr.length() - 9);
                    }
                });
                break;
            case BaseString.TIME_STR_MONTH:
                xAxis.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getAxisLabel(float value, AxisBase axis) {
                        String datestr = monthEntryList.get((int) value).getData().toString();
                        return datestr.substring(5, datestr.length() - 12) + "???";
                    }
                });
                break;
            case BaseString.TIME_STR_YEAR:
                xAxis.setLabelCount(4);
                xAxis.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getAxisLabel(float value, AxisBase axis) {
                        String datestr = yearEntryList.get((int) value).getData().toString();
                        Log.d(TAG, "getAxisLabel: " + datestr);
                        return "    " + datestr.substring(0, 4) + "???";
                    }
                });
                break;

        }
    }


    private LineData generateDataLine(List<AverageDataBean> mlist, String type) {

        assert mlist != null;
        for (int i = 0; i < mlist.size(); i++) {
            Entry entry;
            if (currentPosition == 0) {
                addTimeDataEntryList(i, mlist.get(i).getAvg_temp(), DateUtil.formateGMTTime(mlist.get(i).getAvg_time().toString()), type);

            } else if (currentPosition == 1) {
                addTimeDataEntryList(i, mlist.get(i).getAvg_hum(), DateUtil.formateGMTTime(mlist.get(i).getAvg_time().toString()), type);

            } else if (currentPosition == 2) {
                addTimeDataEntryList(i, DataUtil.MQ2Convert(mlist.get(i).getAvg_mq2()), DateUtil.formateGMTTime(mlist.get(i).getAvg_time().toString()), type);

            } else if (currentPosition == 3) {
                addTimeDataEntryList(i, DataUtil.BeamConvert(mlist.get(i).getAvg_beam()), DateUtil.formateGMTTime(mlist.get(i).getAvg_time().toString()), type);

            } else {
                addTimeDataEntryList(i, mlist.get(i).getAvg_temp(), DateUtil.formateGMTTime(mlist.get(i).getAvg_time().toString()), type);
            }
        }
        LineDataSet dataSet;

        if (type.equals(BaseString.TIME_STR_HOUR)) {
            dataSet = new LineDataSet(hourEntryList, label);
        } else if (type.equals(BaseString.TIME_STR_DAY)) {
            dataSet = new LineDataSet(dayEntryList, label);
        } else if (type.equals(BaseString.TIME_STR_MONTH)) {
            dataSet = new LineDataSet(monthEntryList, label);
        } else if (type.equals(BaseString.TIME_STR_YEAR)) {
            dataSet = new LineDataSet(yearEntryList, label);
        } else {
            dataSet = new LineDataSet(hourEntryList, label);
        }
        dataSet.setLineWidth(2.5f);
        dataSet.setCircleRadius(4.5f);
        dataSet.setHighLightColor(Color.rgb(244, 117, 117));

        dataSet.setDrawValues(false);
        dataSet.setDrawValues(true);
        dataSet.setValueTextColor(Color.WHITE);

        ArrayList<ILineDataSet> sets = new ArrayList<>();
        sets.add(dataSet);
        return new LineData(sets);
    }

    void addTimeDataEntryList(int i, float data, String time, String type) {
        Entry entry;
        switch (type) {
            case BaseString.TIME_STR_HOUR:
                entry = new Entry(i, data, time);
                hourEntryList.add(entry);
                break;
            case BaseString.TIME_STR_DAY:
                entry = new Entry(i, data, time);
                dayEntryList.add(entry);
                break;
            case BaseString.TIME_STR_MONTH:
                entry = new Entry(i, data, time);
                monthEntryList.add(entry);
                break;
            case BaseString.TIME_STR_YEAR:
                entry = new Entry(i, data, time);
                yearEntryList.add(entry);
                break;
        }
    }
//    /**
//     * ??????DataLine????????????
//     *
//     * @return Line data
//     */
//    private LineData generateDataLine() {
//
//        values1 = new ArrayList<>();
//        List<String> values2 = new ArrayList<>();
//
//
//        for (int i = 0; i < 100; i++) {
//            values1.add(new Entry(i, (int) (Math.random() * 65) + 40, "2010 " + i));
////            values2.add("???"+i);
//        }
//
//        LineDataSet d1 = new LineDataSet(values1, "??????");
////        d1.setLineWidth(2.5f);
////        d1.setCircleRadius(4.5f);
////        d1.setHighLightColor(Color.rgb(244, 117, 117));
////        d1.setDrawValues(false);
////        d1.setDrawValues(true);
//
//        d1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
//        d1.setColor(Color.WHITE);
//        d1.setCircleColor(Color.parseColor("#AAFFFFFF"));
//        d1.setHighLightColor(Color.WHITE);//????????????????????????????????????????????????
//        d1.setHighlightEnabled(true);//???????????????????????????
//        d1.setDrawCircles(true);
//        d1.setValueTextColor(Color.WHITE);
//        d1.setLineWidth(1f);//????????????
//        d1.setCircleRadius(2f);//???????????????????????????
//        d1.setHighlightLineWidth(0.5f);//???????????????????????????????????????
//        d1.enableDashedHighlightLine(10f, 5f, 0f);//????????????????????????????????????
//        d1.setValueTextSize(12f);//??????????????????????????????
//        d1.setDrawFilled(true);//???????????? ??????????????????
//
//
//        ArrayList<ILineDataSet> sets = new ArrayList<>();
//        sets.add(d1);
//        return new LineData(sets);
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chart_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //????????????
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_chart_save:
                save(mLineChart, "hour");
                save(mDayLineChart, "day");
                save(mMonthChart, "month");
                save(mYearChart, "year");
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void save(LineChart chart, String name) {
        if (chart.saveToGallery(name + "_" + System.currentTimeMillis(), 70))
            Toast.makeText(getApplicationContext(), "????????????",
                    Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(), "????????????", Toast.LENGTH_SHORT)
                    .show();
    }

}