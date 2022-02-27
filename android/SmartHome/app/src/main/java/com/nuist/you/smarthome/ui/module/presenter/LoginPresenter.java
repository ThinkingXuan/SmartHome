package com.nuist.you.smarthome.ui.module.presenter;


import android.util.Log;

import com.mob.wrappers.UMSSDKWrapper;
import com.nuist.you.smarthome.MyApplication;
import com.nuist.you.smarthome.bean.BaseCode;
import com.nuist.you.smarthome.bean.Users;
import com.nuist.you.smarthome.listener.RequestImpl;
import com.nuist.you.smarthome.ui.module.model.ILoginModel;
import com.nuist.you.smarthome.ui.module.model.impl.LoginModel;
import com.nuist.you.smarthome.ui.module.view.ILoginView;
import com.nuist.you.smarthome.util.SharedPreUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginPresenter {

    public static final String TAG = "LoginPresenter";
    private ILoginView mILoginView;
    private ILoginModel mILoginModel;

    public LoginPresenter(ILoginView ILoginView) {
        mILoginView = ILoginView;
        mILoginModel = new LoginModel();

        //是否已经登录
        Users users = mILoginView.isAlreadyLogin();

        if (null != users) {
            mILoginModel.login(users, new RequestImpl() {
                @Override
                public void loadSuccess(Object object) {
                    mILoginView.closeProgressDialog();

                    BaseCode code = (BaseCode) object;
                    int iCode = code.getCode();
                    if (iCode == 0) {
                        mILoginView.showToast("登陆成功", 1);
                        //写入
                        writeUserInfo(users, code.getMessage());
                        mILoginView.goMainActivty();

                    } else if (iCode == 1) {
                        mILoginView.showToast(code.getMessage(), 3);
                    }
                }


                @Override
                public void loadFailed() {
                    mILoginView.closeProgressDialog();

                    mILoginView.showToast("请检查网络", 3);
                }
            });
        }
    }

    public void SubmitLogin() {
        //验证用户输入的正确性
        checkUserInput();
    }

    private void submitServer() {
        mILoginView.showProgressDialog();
        //获取最新的用户数据
        Users users = new Users();
        users.setUserPhone(mILoginView.getInputPhone());
        users.setUserPassword(mILoginView.getInputPassword());
        mILoginModel.login(users, new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                mILoginView.closeProgressDialog();

                BaseCode code = (BaseCode) object;
                int iCode = code.getCode();
                if (iCode == 0) {
                    mILoginView.showToast("登陆成功", 1);
                    //写入
                    writeUserInfo(users, code.getMessage());
                    mILoginView.goMainActivty();

                } else if (iCode == 1) {
                    mILoginView.showToast(code.getMessage(), 3);
                }
            }


            @Override
            public void loadFailed() {
                mILoginView.closeProgressDialog();

                mILoginView.showToast("请检查网络", 3);
            }
        });

    }


    private void checkUserInput() {
        String phone = mILoginView.getInputPhone();
        String password = mILoginView.getInputPassword();


        if (!checkEmail(phone)) {
            mILoginView.showToast("手机号输入错误", 3);
            return;
        }
        if (null == password || "".equals(password)) {
            mILoginView.showToast("请输入密码", 3);
            return;
        }
        if (password.length() < 6) {
            mILoginView.showToast("密码不得少于6位", 2);
            return;
        }

        //提交服务器()
        submitServer();
    }

    private boolean checkEmail(String phone) {
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    private void writeUserInfo(Users users, String ipAddress) {
        SharedPreUtil.setString(MyApplication.getInstance().getApplicationContext(), "userPhone", users.getUserPhone());
        SharedPreUtil.setString(MyApplication.getInstance().getApplicationContext(), "userPassword", users.getUserPassword());
        SharedPreUtil.setString(MyApplication.getInstance().getApplicationContext(), "userIpAddress", ipAddress);
    }
}
