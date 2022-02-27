package com.nuist.you.smarthome;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nuist.you.smarthome.adapter.FragmentAdapter;
import com.nuist.you.smarthome.bean.AverageDataBean;
import com.nuist.you.smarthome.bean.MessageAllData;
import com.nuist.you.smarthome.http.two.WsManager;
import com.nuist.you.smarthome.listener.ItemDataListener;
import com.nuist.you.smarthome.ui.chartdetail.ChartDetailActivity;
import com.nuist.you.smarthome.ui.dashboard.DashboardFragment;
import com.nuist.you.smarthome.ui.home.HomeFragment;
import com.nuist.you.smarthome.ui.my.MyFragment;
import com.nuist.you.smarthome.ui.device.DeviceFragment;
import com.orhanobut.logger.Logger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private BottomNavigationView navView;
    private ViewPager viewPager;
    private MenuItem menuItem;
    private List<Fragment> fragments;
    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                }
            }
        }
    }

    private void initView() {
        navView = findViewById(R.id.nav_view);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        navView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.navigation_device:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.navigation_dashboard:
                                viewPager.setCurrentItem(2);
                                break;
                            case R.id.navigation_my:
                                viewPager.setCurrentItem(3);
                                break;
                        }
                        setTitle(item.getTitle());
                        return false;
                    }
                });


        viewPager.setOffscreenPageLimit(4);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    navView.getMenu().getItem(0).setChecked(false);
                }
                menuItem = navView.getMenu().getItem(position);
                menuItem.setChecked(true);
                setTitle(menuItem.getTitle());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

//        //禁止ViewPager滑动
//        viewPager.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return false;
//            }
//        });
        setupViewPager(viewPager);
    }


    private void setupViewPager(ViewPager viewPager) {
        initFragment();
        FragmentAdapter adapter = new FragmentAdapter(fragments, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        setTitle(R.string.title_home);
    }

    private void initFragment() {
        if (fragments != null) {
            fragments.clear();
        } else {
            fragments = new ArrayList<>();
        }
        HomeFragment homeFragment = new HomeFragment();
        fragments.add(homeFragment);
        fragments.add(new DeviceFragment());
        fragments.add(new DashboardFragment());
        fragments.add(new MyFragment());

        //从HomeFragment的信息回调
//        HomeFragment homeFragment;
//        FragmentManager fm = getSupportFragmentManager();
//        homeFragment = (HomeFragment) fm.findFragmentById(R.id.navigation_home);
        homeFragment.setDataListener(new ItemDataListener() {
            @Override
            public void OnItemDataListenerr(MessageAllData.ObjectBean objectBean, int position) {

                Intent intent = new Intent(MainActivity.this, ChartDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", objectBean);
                bundle.putInt("position", position);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onBackPressed() {

        if ((System.currentTimeMillis() - mExitTime) > 3000) {
            Toast.makeText(this, R.string.exit_app, Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            moveTaskToBack(true);//就是在位于Activity栈底activity中Hook其返回键行为，保证用户点击返回键后不再退出app
        }
    }

    @Override
    protected void onDestroy() {
        Logger.d("onDestroy");
        WsManager.getInstance().disconnect();
        super.onDestroy();
    }

}
