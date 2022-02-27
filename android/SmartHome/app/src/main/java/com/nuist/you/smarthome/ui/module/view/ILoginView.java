package com.nuist.you.smarthome.ui.module.view;

import com.nuist.you.smarthome.bean.Users;

public interface ILoginView {
    void showProgressDialog();
    void closeProgressDialog();

    //flag 1 成功  2 警告  3 错误
    void showToast(String content, int flag);

    String getInputPhone();

    String getInputPassword();

    void changeBtBackground(boolean flag);

    void goMainActivty();

    Users isAlreadyLogin();
}
