package com.nuist.you.smarthome.ui.module.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.mob.wrappers.UMSSDKWrapper;
import com.nuist.you.smarthome.MainActivity;
import com.nuist.you.smarthome.R;
import com.nuist.you.smarthome.base.BaseActivity;
import com.nuist.you.smarthome.bean.Users;
import com.nuist.you.smarthome.ui.module.presenter.LoginPresenter;
import com.nuist.you.smarthome.ui.module.view.ILoginView;
import com.nuist.you.smarthome.util.DialogUtil;
import com.nuist.you.smarthome.util.SharedPreUtil;
import com.nuist.you.smarthome.util.ToastUtil;
import com.nuist.you.smarthome.view.ClearEditText;


/**
 * 登陆
 */
public class LoginActivity extends BaseActivity implements ILoginView, View.OnClickListener {
    public static final String TAG = "LoginActivity";
    private TextView mTvForgetPassword;
    private TextView mTvGoRegister;

    private Toolbar mToolbar;
    private ClearEditText mEtInputPhoneLogin;
    private ClearEditText mEtInputPasswordLogin;
    private Button mBtLogin;
    private LoginPresenter mLoginPresenter;

    private boolean isPhone = false;
    private boolean isPassword = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        mLoginPresenter = new LoginPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                mLoginPresenter.SubmitLogin();
                break;
            case R.id.tv_forget_password:
                Intent forgetIntent = new Intent(LoginActivity.this, ForgetMeActivity.class);
                startActivity(forgetIntent);
                break;
            case R.id.tv_go_register:
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
                break;
        }
    }

    @Override
    public void showProgressDialog() {
        DialogUtil.showProgressDialog(this, getString(R.string.text_login_dialog));
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
        return mEtInputPhoneLogin.getText().toString().trim();
    }

    @Override
    public String getInputPassword() {
        return mEtInputPasswordLogin.getText().toString().trim();
    }

    @Override
    public void changeBtBackground(boolean flag) {
        if (flag) {
            mBtLogin.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            mBtLogin.setClickable(true);
            mBtLogin.setActivated(true);
            mBtLogin.setPressed(true);

        } else {
            mBtLogin.setBackgroundColor(getResources().getColor(R.color.colorbt));
            mBtLogin.setClickable(false);
            mBtLogin.setActivated(false);
            mBtLogin.setPressed(false);
        }
    }

    @Override
    public void goMainActivty() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public Users isAlreadyLogin() {

        Users users = null;
        String userPhone = SharedPreUtil.getString("userPhone", "");
        String userPassword = SharedPreUtil.getString("userPassword", "");
        String userIpAddress = SharedPreUtil.getString("userIpAddress", "");

        if (!"".equals(userPhone) && !"".equals(userPassword) && !"".equals(userIpAddress)) {
            users = new Users();
            users.setUserPhone(userPhone);
            users.setUserPassword(userPassword);
            users.setIpAddress(userIpAddress);
        }
        return users;
    }

    private void initView() {
        mTvForgetPassword = findViewById(R.id.tv_forget_password);
        mTvGoRegister = findViewById(R.id.tv_go_register);
        mToolbar = findViewById(R.id.toolbar);
        mEtInputPhoneLogin = findViewById(R.id.et_input_phone_login);
        mEtInputPasswordLogin = findViewById(R.id.et_input_password_login);
        mBtLogin = findViewById(R.id.bt_login);

        mBtLogin.setOnClickListener(this);
        mTvGoRegister.setOnClickListener(this);
        mTvForgetPassword.setOnClickListener(this);
//        setToolBar(mToolbar);

        changeBtBackground(false);

        mEtInputPhoneLogin.addTextChangedListener(new TextWatcher() {
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
                Log.d(TAG, "afterTextChanged: " + isPhone + "  " + isPassword);
                if (isPhone && isPassword) {
                    changeBtBackground(true);
                } else {
                    changeBtBackground(false);
                }
            }
        });
        mEtInputPasswordLogin.addTextChangedListener(new TextWatcher() {
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
                Log.d(TAG, "afterTextChanged: " + isPhone + " " + isPassword);
                if (isPhone && isPassword) {
                    changeBtBackground(true);
                } else {
                    changeBtBackground(false);
                }
            }
        });
        showContentView();

    }

}
