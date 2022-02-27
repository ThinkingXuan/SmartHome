package com.nuist.you.smarthome.base;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.nuist.you.smarthome.R;
import com.nuist.you.smarthome.listener.PerfectClickListener;
import com.nuist.you.smarthome.util.StatusBarUtil;


//BaseActivity
public class BaseActivity extends AppCompatActivity {
    public static final String TAG = "BaseActivity";

    //布局
    private LinearLayout mProgressBarLinear;
    private View refresh;
    private View mBaseView;
    private View mContentView;
    private AnimationDrawable mAnimationDrawable;


    protected <T extends View> T getView(int id){

        return (T)findViewById(id);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //开启沉浸状态栏
        StatusBarUtil.StatusBarLightMode(this);
    }


    @Override
    public void setContentView(int layoutResID) {
        mBaseView = LayoutInflater.from(this).inflate(R.layout.activity_base,null,false);
        mContentView = LayoutInflater.from(this).inflate(layoutResID,null,false);

        //content
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mBaseView.setLayoutParams(params);
        RelativeLayout mContainter =  mBaseView.findViewById(R.id.container);

        mContainter.addView(mContentView);
        getWindow().setContentView(mBaseView);


        mProgressBarLinear = getView(R.id.ll_progress_bar);
        refresh = getView(R.id.ll_error_refresh);
        ImageView img = getView(R.id.img_progress);

        //加载动画
        mAnimationDrawable = (AnimationDrawable) img.getDrawable();
        //默认进入页面就开启动画
        if (!mAnimationDrawable.isRunning()){
            mAnimationDrawable.start();
        }

        refresh.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                showLoading();
                onRefresh();
            }
        });

        mContentView.setVisibility(View.GONE);
    }

    protected void setToolBar(Toolbar toolBar) {
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (actionBar != null) {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    protected void showLoading(){
        if (mProgressBarLinear.getVisibility()!= View.VISIBLE){
            mProgressBarLinear.setVisibility(View.VISIBLE);
        }
        //开始动画
        if (mAnimationDrawable.isRunning()){
            mAnimationDrawable.start();
        }

        if (mContentView.getVisibility()!= View.GONE){
            mContentView.setVisibility(View.GONE);
        }

        if (refresh.getVisibility()!= View.GONE){
            refresh.setVisibility(View.GONE);
        }
    }

    protected void showContentView(){
        if (mProgressBarLinear.getVisibility()!= View.GONE){
            mProgressBarLinear.setVisibility(View.GONE);
        }

        // 停止动画
        if (mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }
        if (refresh.getVisibility() != View.GONE) {
            refresh.setVisibility(View.GONE);
        }
        if (mContentView.getVisibility() != View.VISIBLE) {
            mContentView.setVisibility(View.VISIBLE);
        }
    }

    protected void showError(){
        if (mProgressBarLinear.getVisibility()!= View.GONE){
            mProgressBarLinear.setVisibility(View.GONE);
        }
        //停止动画
        if (mAnimationDrawable.isRunning()){
            mAnimationDrawable.stop();
        }

        if (refresh.getVisibility()!= View.VISIBLE){
            refresh.setVisibility(View.VISIBLE);
        }

        if (mContentView.getVisibility()!= View.GONE){
            mContentView.setVisibility(View.GONE);
        }
    }

    /**
     * 失败后点击刷新
     */

    protected void onRefresh(){

    }

}
