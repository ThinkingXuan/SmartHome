package com.nuist.you.smarthome.ui.module.view;

public interface IRegisterView {

    void showProgressDialog();
    void closeProgressDialog();

    //flag 1 成功  2 警告  3 错误
    void showToast(String content, int flag);

    String getInputPhone();

    String getInputPassword();
    String getInputEmail();

    String getInputCode();

    void resetCodeSecond(long second);

    void changeBtBackground(boolean flag);

    void exitActivity();

}
