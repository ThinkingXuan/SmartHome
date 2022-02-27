package com.nuist.you.smarthome.ui.module.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.nuist.you.smarthome.R;
import com.nuist.you.smarthome.base.BaseActivity;


/**
 * 忘记密码
 */
public class ForgetMeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetme);

//        setToolBar((Toolbar) findViewById(R.id.toolbar));
        showContentView();

    }
}
