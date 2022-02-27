package com.nuist.you.smarthome.ui.module.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.nuist.you.smarthome.R;
import com.nuist.you.smarthome.base.BaseActivity;
import com.nuist.you.smarthome.ui.module.presenter.RegisterPresenter;
import com.nuist.you.smarthome.ui.module.view.IRegisterView;
import com.nuist.you.smarthome.util.DialogUtil;
import com.nuist.you.smarthome.util.ToastUtil;
import com.nuist.you.smarthome.view.ClearEditText;

import cn.smssdk.SMSSDK;


/**
 * 注册
 */
public class RegisterActivity extends BaseActivity implements IRegisterView, View.OnClickListener {

    public static final String TAG = "RegisterActivity";
    private Toolbar mBaseToolbar;
    private ClearEditText mEtInputPhoneRegister;
    private TextView mTvGetCodeRegister;
    private ClearEditText mEtInputPasswordRegister;
    private ClearEditText mEtInputCodeRegister;
    private ClearEditText mEtInputEmailRegister;


    private Button mBtRegister;

    private boolean isPhone = false;
    private boolean isPassword = false;
    private boolean isCode = false;

    private RegisterPresenter mPresenter = new RegisterPresenter(this);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        initView();

        // 注册一个事件回调，用于处理SMSSDK接口请求的结果
        SMSSDK.registerEventHandler(mPresenter.getEventHandler());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_register:
                mPresenter.SubmitRegister();
                break;
            case R.id.tv_get_code_register:
                mPresenter.getPhoneCode();
                break;
        }
    }

    @Override
    public void showProgressDialog() {
        DialogUtil.showProgressDialog(this, getString(R.string.text_register_dialog));
    }

    @Override
    public void closeProgressDialog() {
        DialogUtil.closeProgressDialog();

    }

    //flag 1 成功  2 警告  3 错误
    @Override
    public void showToast(String content, int flag) {
        switch (flag) {
            case 1:
                ToastUtil.Success(this, content);
                break;
            case 2:
                ToastUtil.Warning(this, content);
                break;
            case 3:
                ToastUtil.Error(this, content);
                break;
        }
    }

    @Override
    public String getInputPhone() {
        return mEtInputPhoneRegister.getText().toString().trim();
    }

    @Override
    public String getInputPassword() {
        return mEtInputPasswordRegister.getText().toString().trim();
    }

    @Override
    public String getInputEmail() {
        return mEtInputEmailRegister.getText().toString().trim();
    }

    @Override
    public String getInputCode() {
        return mEtInputCodeRegister.getText().toString().trim();
    }

    //重置发送验证码的text
    @Override
    public void resetCodeSecond(long second) {
        if (second > 0) {
            mTvGetCodeRegister.setText(second + "秒后重新获取");
            mTvGetCodeRegister.setClickable(false);
            mTvGetCodeRegister.setPressed(false);
        }
        if (second <= 0) {
            mTvGetCodeRegister.setText("发送验证码");
            mTvGetCodeRegister.setClickable(true);
            mTvGetCodeRegister.setPressed(true);
        }

    }

    //改变提交按钮的背景和焦点
    @Override
    public void changeBtBackground(boolean flag) {
        if (flag) {
            mBtRegister.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            mBtRegister.setClickable(true);
            mBtRegister.setActivated(true);
            mBtRegister.setPressed(true);

        } else {
            mBtRegister.setBackgroundColor(getResources().getColor(R.color.colorbt));
            mBtRegister.setClickable(false);
            mBtRegister.setActivated(false);
            mBtRegister.setPressed(false);
        }
    }

    @Override
    public void exitActivity() {
        finish();
    }

    private void initView() {
        mBaseToolbar = findViewById(R.id.toolbar);
        mEtInputPhoneRegister = findViewById(R.id.et_input_phone_register);
        mTvGetCodeRegister = findViewById(R.id.tv_get_code_register);
        mEtInputPasswordRegister = findViewById(R.id.et_input_password_register);
        mEtInputCodeRegister = findViewById(R.id.et_input_code_register);
        mEtInputEmailRegister = (ClearEditText) findViewById(R.id.et_input_email_register);
        mBtRegister = findViewById(R.id.bt_register);
        mBtRegister.setOnClickListener(this);
        mTvGetCodeRegister.setOnClickListener(this);

//        setToolBar(mBaseToolbar);
        changeBtBackground(false);

        mEtInputPhoneRegister.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 8) {
                    isPhone = true;
                } else {
                    isPhone = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "afterTextChanged: " + isPhone + " " + isPassword + "" + isCode);
                if (isPhone && isPassword && isCode) {
                    changeBtBackground(true);
                } else {
                    changeBtBackground(false);
                }
            }
        });
        mEtInputPasswordRegister.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 1) {
                    isPassword = true;
                } else {
                    isPassword = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "afterTextChanged: " + isPhone + " " + isPassword + "" + isCode);
                if (isPhone && isPassword && isCode) {
                    changeBtBackground(true);
                } else {
                    changeBtBackground(false);
                }
            }
        });
        mEtInputCodeRegister.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 3) {
                    isCode = true;
                } else {
                    isCode = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "afterTextChanged: " + isPhone + " " + isPassword + "" + isCode);
                if (isPhone && isPassword && isCode) {
                    changeBtBackground(true);
                } else {
                    changeBtBackground(false);
                }
            }
        });

        showContentView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(mPresenter.getEventHandler());
    }
}
