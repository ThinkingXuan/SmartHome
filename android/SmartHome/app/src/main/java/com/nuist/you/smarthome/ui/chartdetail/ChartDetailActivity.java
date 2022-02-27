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
    private String[] title = {"温度统计", "湿度统计", "烟雾浓度统计", "光照强度统计"};


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
        //获取数据
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
            label = "温度";
        } else if (currentPosition == 1) {
            label = "湿度";
        } else if (currentPosition == 2) {
            label = "烟雾浓度";
        } else if (currentPosition == 3) {
            label = "光照强度";
        } else {
            label = "温度";
        }
        initEntryData(mListMap, currentPosition);
    }

    //初始化EntryData
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

//        mLineChart.setNoDataText("没有数据");//没有数据时显示的文字
//        mLineChart.setNoDataTextColor(Color.WHITE);//没有数据时显示文字的颜色
//        mLineChart.setDrawGridBackground(false);//chart 绘图区后面的背景矩形将绘制
//        mLineChart.setDrawBorders(false);//是否禁止绘制图表边框的线
//        mLineChart.setBorderColor(Color.WHITE); //设置 chart 边框线的颜色。
//        mLineChart.setBorderWidth(3f); //设置 chart 边界线的宽度，单位 dp。
////        mLineChart.setTouchEnabled(true);     //能否点击
////        mLineChart.setDragEnabled(false);   //能否拖拽
////        mLineChart.setScaleEnabled(false);  //能否缩放
        mLineChart.animateX(1000);//绘制动画 从左到右

////        mLineChart.setDoubleTapToZoomEnabled(false);//设置是否可以通过双击屏幕放大图表。默认是true
////        mLineChart.setHighlightPerDragEnabled(false);//能否拖拽高亮线(数据点与坐标的提示线)，默认是true
////        mLineChart.setDragDecelerationEnabled(false);//拖拽滚动时，手放开是否会持续滚动，默认是true（false是拖到哪是哪，true拖拽之后还会有缓冲）
//
////        MyMarkerView mv = new MyMarkerView(mActivity,
////                R.layout.custom_marker_view);
////        mv.setChartView(mLineChart); // For bounds control
////        mLineChart.setMarker(mv);        //设置 marker ,点击后显示的功能 ，布局可以自定义
//
        XAxis xAxis = mLineChart.getXAxis();       //获取x轴线
//        xAxis.setDrawAxisLine(true);//是否绘制轴线
//        xAxis.setDrawGridLines(false);//设置x轴上每个点对应的线
//        xAxis.setDrawLabels(true);//绘制标签  指x轴上的对应数值

//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置
//        xAxis.setTextSize(12f);//设置文字大小
//        xAxis.setAxisMinimum(0f);//设置x轴的最小值 //`
////        xAxis.setAxisMaximum(31f);//设置最大值 //
//        xAxis.setLabelCount(5);  //设置X轴的显示个数
//        xAxis.setAvoidFirstLastClipping(false);//图表将避免第一个和最后一个标签条目被减掉在图表或屏幕的边缘
//
        xAxis.setTextColor(getResources().getColor(R.color.subscribe_bg_press));
        xAxis.setAxisLineColor(Color.WHITE);//设置x轴线颜色
        xAxis.setDrawGridLines(false);
//        xAxis.setAxisLineWidth(0.5f);//设置x轴线宽度
        YAxis leftAxis = mLineChart.getAxisLeft();
        YAxis rightAxis = mLineChart.getAxisRight();

//        leftAxis.enableGridDashedLine(10f, 10f, 0f);  //设置Y轴网格线条的虚线，参1 实线长度，参2 虚线长度 ，参3 周期
//        leftAxis.setGranularity(20f); // 网格线条间距
//        axisRight.setEnabled(false);   //设置是否使用 Y轴右边的
//        leftAxis.setEnabled(true);     //设置是否使用 Y轴左边的
//        leftAxis.setGridColor(Color.parseColor("#7189a9"));  //网格线条的颜色
        leftAxis.setDrawLabels(true);        //是否显示Y轴刻度
//        leftAxis.setStartAtZero(true);        //设置Y轴数值 从零开始
//        leftAxis.setDrawGridLines(true);      //是否使用 Y轴网格线条
//        leftAxis.setTextSize(12f);            //设置Y轴刻度字体
        leftAxis.setTextColor(Color.WHITE);   //设置字体颜色
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setDrawGridLines(false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setEnabled(false);

//        leftAxis.setAxisLineColor(Color.WHITE); //设置Y轴颜色
//        leftAxis.setAxisLineWidth(0.5f);
//        leftAxis.setDrawAxisLine(true);//是否绘制轴线
//        leftAxis.setMinWidth(0f);
//        leftAxis.setMaxWidth(200f);
//        leftAxis.setDrawGridLines(false);//设置x轴上每个点对应的线
        Legend l = mLineChart.getLegend();//图例
        l.setTextColor(Color.WHITE);
//        l.setEnabled(false);   //是否使用 图例

        leftAxis.setTextColor(getResources().getColor(R.color.subscribe_bg_press));   //设置字体颜色
        leftAxis.setAxisLineColor(Color.WHITE);

        rightAxis.setTextColor(getResources().getColor(R.color.subscribe_bg_press));   //设置字体颜色
        rightAxis.setAxisLineColor(Color.WHITE);

//
        //设置X轴坐标位置
//        XAxis xAxis = mLineChart.getXAxis();
        YAxis yAxis = mLineChart.getAxisLeft();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        //放缩
        if (type.equals(BaseString.TIME_STR_HOUR)) {
            if (hourEntryList.size() > 10) {
                //倍率可以根据实际情况而定
                mLineChart.setScaleMinima(8.0f, 1.0f);
            } else {
                mLineChart.setScaleMinima(1.5f, 1.0f);
            }

            xAxis.setLabelRotationAngle(-30);     //x轴label选择
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
        // 设置x轴的LimitLine
        LimitLine yLimitLine = new LimitLine(20f, "警戒线");
        yLimitLine.setLineColor(Color.RED);
        yLimitLine.setTextColor(Color.RED);
        // 获得左侧侧坐标轴
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
                        return datestr.substring(5, datestr.length() - 6) + "点";
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
                        return datestr.substring(5, datestr.length() - 12) + "月";
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
                        return "    " + datestr.substring(0, 4) + "年";
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
//     * 设计DataLine的数据集
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
////            values2.add("包"+i);
//        }
//
//        LineDataSet d1 = new LineDataSet(values1, "温度");
////        d1.setLineWidth(2.5f);
////        d1.setCircleRadius(4.5f);
////        d1.setHighLightColor(Color.rgb(244, 117, 117));
////        d1.setDrawValues(false);
////        d1.setDrawValues(true);
//
//        d1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
//        d1.setColor(Color.WHITE);
//        d1.setCircleColor(Color.parseColor("#AAFFFFFF"));
//        d1.setHighLightColor(Color.WHITE);//设置点击交点后显示交高亮线的颜色
//        d1.setHighlightEnabled(true);//是否使用点击高亮线
//        d1.setDrawCircles(true);
//        d1.setValueTextColor(Color.WHITE);
//        d1.setLineWidth(1f);//设置线宽
//        d1.setCircleRadius(2f);//设置焦点圆心的大小
//        d1.setHighlightLineWidth(0.5f);//设置点击交点后显示高亮线宽
//        d1.enableDashedHighlightLine(10f, 5f, 0f);//点击后的高亮线的显示样式
//        d1.setValueTextSize(12f);//设置显示值的文字大小
//        d1.setDrawFilled(true);//设置使用 范围背景填充
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

    //菜单点击
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
            Toast.makeText(getApplicationContext(), "保存成功",
                    Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(), "保存失败", Toast.LENGTH_SHORT)
                    .show();
    }

}