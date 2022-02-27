package com.nuist.you.smarthome.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.nuist.you.smarthome.R;
import com.nuist.you.smarthome.adapter.HomeRecyclerAdapter;
import com.nuist.you.smarthome.base.BaseString;
import com.nuist.you.smarthome.bean.AverageDataBean;
import com.nuist.you.smarthome.bean.MessageAllData;
import com.nuist.you.smarthome.bean.MessageBean;
import com.nuist.you.smarthome.bean.MessageData;
import com.nuist.you.smarthome.bean.MessageEvent;
import com.nuist.you.smarthome.bean.SensorData;
import com.nuist.you.smarthome.bean.SensorDataBean;
import com.nuist.you.smarthome.bean.viewbean.HomeAvageBean;
import com.nuist.you.smarthome.bean.viewbean.SensorDataItem;
import com.nuist.you.smarthome.http.ConnectListener;
import com.nuist.you.smarthome.http.MyWebSocket;
import com.nuist.you.smarthome.http.two.WsManager;
import com.nuist.you.smarthome.listener.ItemClickListener;
import com.nuist.you.smarthome.listener.ItemDataListener;
import com.nuist.you.smarthome.util.DataUtil;
import com.nuist.you.smarthome.util.GsonUtil;
import com.nuist.you.smarthome.util.ResUtils;
import com.nuist.you.smarthome.adapter.ImageResourceViewHolder;
import com.nuist.you.smarthome.util.SharedPreUtil;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.annotation.APageStyle;
import com.zhpan.bannerview.constants.PageStyle;
import com.zhpan.indicator.IndicatorView;
import com.zhpan.indicator.enums.IndicatorSlideMode;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.java_websocket.handshake.ServerHandshake;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getSimpleName();
    private HomeViewModel homeViewModel;
    private String socket_address = "";
    private BannerViewPager mViewPager;
    private IndicatorView mIndicatorView;
    private HomeRecyclerAdapter mRecyclerAdapter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshLayout;
    private List<SensorDataItem> mList = new ArrayList<>();
    private List<HomeAvageBean> mAvageBeans = new ArrayList<>();
    private MessageData messageData;
    private Map<String, AverageDataBean> mapData;

    private MessageAllData.ObjectBean mObjectBean;
    private ItemDataListener mDataListener;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        initDataList(4);
        initView(root);
        String inAddress  = SharedPreUtil.getString("userIpAddress","");

        Log.d(TAG, "onCreateView: "+inAddress);
        WsManager.getInstance().init("ws://"+inAddress+":8888");
        setAdatper();
        return root;
    }


    public void setDataListener(ItemDataListener dataListener) {
        mDataListener = dataListener;
    }

    private void initView(View view) {
        setHasOptionsMenu(true);               //开启Toolbr自定义菜单

        mRecyclerView = view.findViewById(R.id.recyclerview_home);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRefreshLayout = view.findViewById(R.id.swipe_refresh_home);
        mRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        mRefreshLayout.setRefreshing(true);

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //发送请求平均数据
                requestAverageData();
            }
        });


        mViewPager = view.findViewById(R.id.banner_view);
        mIndicatorView = view.findViewById(R.id.indicator_view);
        mIndicatorView.setVisibility(View.VISIBLE);
        mViewPager
                .setAutoPlay(false)
                .setIndicatorSlideMode(IndicatorSlideMode.NORMAL)
                .setIndicatorVisibility(View.GONE)
                .setIndicatorView(mIndicatorView)
                .setHolderCreator(() -> new ImageResourceViewHolder(getResources().getDimensionPixelOffset(R.dimen.dp_5)))
                .setIndicatorSliderColor(ResUtils.getColor(getActivity(), R.color.subscribe_bg_press), ResUtils.getColor(getActivity(), R.color.colorWhite))
                .setIndicatorSliderRadius(getResources().getDimensionPixelOffset(R.dimen.dp_2), getResources().getDimensionPixelOffset(R.dimen.dp_3))
                .setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        switch (position) {
                            case 0:
                                mAvageBeans = DataUtil.getHomeAvageBean(mapData, BaseString.DATA_TEMP);
                                break;
                            case 1:
                                mAvageBeans = DataUtil.getHomeAvageBean(mapData, BaseString.DATA_HUM);
                                break;
                            case 2:
                                mAvageBeans = DataUtil.getHomeAvageBean(mapData, BaseString.DATA_MQ2);
                                break;
                            case 3:
                                mAvageBeans = DataUtil.getHomeAvageBean(mapData, BaseString.DATA_LIGHT);
                                break;
                        }
                        //更新数据
                        addDataToRecyclerView();
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                })
                .setInterval(5000);
        setupBanner(PageStyle.MULTI_PAGE_SCALE);
    }

    @Override
    public void onStart() {
        super.onStart();
        //注册
        EventBus.getDefault().register(this);
    }

    //接收传感器json数据
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        SensorData sensorData = GsonUtil.getObject(event.message, SensorData.class);

        SensorDataBean sensorDataBean = sensorData.getObject();

        if (sensorData.getCode() == 1) {          //收到推送传感器数据
            mList.get(0).setData(sensorDataBean.getTemperature());
            mList.get(1).setData(sensorDataBean.getHumidity());
            mList.get(2).setData(sensorDataBean.getMq2());
            mList.get(3).setData(sensorDataBean.getBeam());

            mList.get(0).setDataType("温度");
            mList.get(1).setDataType("湿度");
            mList.get(2).setDataType("烟雾浓度");
            mList.get(3).setDataType("光照强度");


            mList.get(0).setTips("请多穿衣服，保暖");
            mList.get(1).setTips("湿度较高，请让阳光进来");
            mList.get(2).setTips("有危险，不利于身体健康");
            mList.get(3).setTips("卧室太暗，请开灯");

            int currentItem = mViewPager.getCurrentItem();
            mViewPager.create(mList);
            mViewPager.setCurrentItem(currentItem);
        } else if (sensorData.getCode() == BaseString.CODE_AVG_DATA) {  //收到请求的平均统计的数据

            messageData = GsonUtil.getObject(event.message, MessageData.class);
            MessageData.ObjectBean object = messageData.getObject();
            mapData = object.getMap();
            int currentPage = mViewPager.getCurrentItem();
            if (currentPage == 0) {
                mAvageBeans = DataUtil.getHomeAvageBean(mapData, BaseString.DATA_TEMP);
            } else if (currentPage == 1) {
                mAvageBeans = DataUtil.getHomeAvageBean(mapData, BaseString.DATA_HUM);
            } else if (currentPage == 2) {
                mAvageBeans = DataUtil.getHomeAvageBean(mapData, BaseString.DATA_MQ2);
            } else if (currentPage == 3){
                mAvageBeans = DataUtil.getHomeAvageBean(mapData, BaseString.DATA_LIGHT);
            }else {
                mAvageBeans = DataUtil.getHomeAvageBean(mapData, BaseString.DATA_TEMP);
            }
            addDataToRecyclerView();
        } else if (sensorData.getCode() == BaseString.CODE_DRAW_CHART) {
            MessageAllData messageAllData = GsonUtil.getObject(event.message, MessageAllData.class);
            mObjectBean = messageAllData.getObject();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        //反注册
        EventBus.getDefault().unregister(this);
    }

    private void setupBanner(@APageStyle int pageStyle) {
        mViewPager
                .setPageMargin(getResources().getDimensionPixelOffset(R.dimen.dp_10))
                .setRevealWidth(getResources().getDimensionPixelOffset(R.dimen.dp_10))
                .setPageStyle(pageStyle)
                .create(mList);
    }

    private void initDataList(int j) {
        mList.clear();
        for (int i = 1; i <= j; i++) {
            Integer drawable = getResources().getIdentifier("home" + i, "drawable", getActivity().getPackageName());
            SensorDataItem sensorDataItem = new SensorDataItem();
            sensorDataItem.setTopDrawable(drawable);
            mList.add(sensorDataItem);
        }
//        requestAverageData();
//
//        MessageBean messageBean = new MessageBean();
//        messageBean.setCode(BaseString.CODE_DRAW_CHART);
//        messageBean.setMessage(BaseString.MESSAGE_DRAW_CHART);
//        WsManager.getInstance().sendMessage(GsonUtil.getJsonStr(messageBean));

    }

    private void setAdatper() {
        mRecyclerAdapter = new HomeRecyclerAdapter(getContext());
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerAdapter.setItemOnClickListener(new ItemClickListener() {
            @Override
            public void OnItemOnClickListener(View v, int position) {

                //回调给MainActivity
                mDataListener.OnItemDataListenerr(mObjectBean, mViewPager.getCurrentItem());
            }
        });
    }

    private void requestAverageData() {
        MessageBean messageBean = new MessageBean();
        messageBean.setCode(BaseString.CODE_AVG_DATA);
        messageBean.setMessage(BaseString.MESSAGE_AVG_DATA);
        WsManager.getInstance().sendMessage(GsonUtil.getJsonStr(messageBean));
    }

    private void addDataToRecyclerView() {
        mRecyclerAdapter.addList(mAvageBeans);
        mRecyclerAdapter.notifyDataSetChanged();
        mRefreshLayout.setRefreshing(false);
    }

    //添加菜单
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
    }

    //菜单点击
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // case R.id.menu_add:
            //ddItem();
            //  break;

        }
        return super.onOptionsItemSelected(item);
    }


    private void addItem() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add, null);
        builder.setView(view);

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //获取地址
                EditText eturl = view.findViewById(R.id.et_url);
                socket_address = "ws://" + eturl.getText().toString() + ":8888";
                if (null == socket_address || socket_address.length() == 0) {
                    Toast.makeText(getActivity(), "输入错误，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }

                //连接socket
                MyWebSocket.getInstance().initSocket(socket_address);
                initSocketConnect();

            }

        });
        builder.show();

//        view.findViewById(R.id.et_name).setFocusable(true);
//        view.findViewById(R.id.et_name).setFocusableInTouchMode(true);
//        view.findViewById(R.id.et_name).requestFocus();
    }

    private void initSocketConnect() {
        MyWebSocket.getInstance().socketConnect(new ConnectListener() {
            @Override
            public void onOpen(ServerHandshake handshakedata, String url) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "连接成功", Toast.LENGTH_SHORT).show();
                    }
                });
                Log.d(TAG, "服务器地址: " + url);

            }

            @Override
            public void onMessage(String message) {
                Log.d(TAG, "onMessage: " + message);
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                Log.d(TAG, "onClose: 关闭");
            }

            @Override
            public void onError(Exception ex) {
                Log.d(TAG, "onError: 错误");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "连接失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
