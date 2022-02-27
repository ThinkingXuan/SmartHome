package com.nuist.you.smarthome.ui.module.presenter;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;


import com.nuist.you.smarthome.bean.BaseCode;
import com.nuist.you.smarthome.bean.Users;
import com.nuist.you.smarthome.listener.RequestImpl;
import com.nuist.you.smarthome.ui.module.model.IRegisterModel;
import com.nuist.you.smarthome.ui.module.model.impl.RegisterModel;
import com.nuist.you.smarthome.ui.module.view.IRegisterView;
import com.nuist.you.smarthome.util.rx.RxCountDown;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class RegisterPresenter {

    public static final String TAG = "RegisterPresenter";
    private IRegisterView mRegisterView;
    private IRegisterModel mRegisterModel;
    private boolean isSucessCheckCode = false;
    private boolean isSendCheckCode = false;

    EventHandler eventHandler = new EventHandler() {
        public void afterEvent(int event, int result, Object data) {
            // afterEvent会在子线程被调用，因此如果后续有UI相关操作，需要将数据发送到UI线程
            Message msg = new Message();
            msg.arg1 = event;
            msg.arg2 = result;
            msg.obj = data;
            new Handler(Looper.getMainLooper(), new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    int event = msg.arg1;
                    int result = msg.arg2;
                    Object data = msg.obj;
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            Log.d(TAG, "handleMessage: 发送验证码成功");
                            //  处理成功得到验证码的结果
                            // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                        } else {
                            //  处理错误的结果
                            ((Throwable) data).printStackTrace();
                        }
                    } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            Log.d(TAG, "handleMessage: 验证码输入正确");
                            isSucessCheckCode = true;
                            //调用getCode方法，作为提交用户信息的中转
                            getCode(isSucessCheckCode);

                        } else {
                            isSucessCheckCode = false;
                            getCode(isSucessCheckCode);
                            Log.d(TAG, "handleMessage: 验证码输入错误");
                            //  处理错误的结果
                            ((Throwable) data).printStackTrace();
                        }
                    }
                    // TODO 其他接口的返回结果也类似，根据event判断当前数据属于哪个接口
                    return false;
                }
            }).sendMessage(msg);
        }
    };

    public RegisterPresenter(IRegisterView registerView) {
        mRegisterView = registerView;
        mRegisterModel = new RegisterModel();
    }

    public void SubmitRegister() {
        //验证用户输入的正确性
        checkUserInput();

//        submitServer();
    }


    //请求网络
    private void submitServer() {
        mRegisterView.showProgressDialog();

        Users users = new Users();
        users.setUserPhone(mRegisterView.getInputPhone());
        users.setUserPassword(mRegisterView.getInputPassword());
        users.setUserEmail(mRegisterView.getInputEmail());
        mRegisterModel.upUser(users, new RequestImpl() {
            @Override
            public void loadSuccess(Object object) {
                mRegisterView.closeProgressDialog();

                BaseCode code = (BaseCode) object;
                submitServerSuccess(code);
            }

            @Override
            public void loadFailed() {
                mRegisterView.closeProgressDialog();
                mRegisterView.showToast("网络连接失败", 3);
            }
        });


    }

    //请求网络成功
    private void submitServerSuccess(BaseCode code) {
        int iCode = code.getCode();
        String message = code.getMessage();
        switch (iCode) {
            case 0:
                mRegisterView.showToast("注册成功", 1);
                mRegisterView.exitActivity();

                break;
            case 1:
                mRegisterView.showToast(message, 3);
                break;
            default:
                mRegisterView.showToast("请检测网络连接", 3);

        }
    }

    private void checkCode() {
        // 提交验证码，其中的code表示验证码，如“1357”
        SMSSDK.submitVerificationCode("86", mRegisterView.getInputPhone(), mRegisterView.getInputCode());
    }

    private void getCode(boolean isSucess) {
        if (isSucess) {
            //提交服务器()
            submitServer();
        } else {
            mRegisterView.showToast("验证码输入错误", 3);
            return;
        }
    }


    public EventHandler getEventHandler() {
        return eventHandler;
    }

    private void checkUserInput() {
        String phone = mRegisterView.getInputPhone();
        String password = mRegisterView.getInputPassword();
        String code = mRegisterView.getInputCode();
        String email = mRegisterView.getInputEmail();


        if (!checkEmail(phone)) {
            mRegisterView.showToast("手机号输入错误", 3);
            return;
        }

        if (null == password || "".equals(password)) {
            mRegisterView.showToast("请输入密码", 3);
            return;
        }
        if (password.length() < 6) {
            mRegisterView.showToast("密码不得少于6位", 2);
            return;
        }
        if (null == code || "".equals(code)) {
            mRegisterView.showToast("请输入验证码", 3);
            return;
        }
        if (null == email || "".equals(email)) {
            mRegisterView.showToast("邮箱输入错误", 3);
        }

        //验证是否发送验证码的正确
        if (isSendCheckCode) {
            //验证正确性
            checkCode();
        } else {
            mRegisterView.showToast("请发送验证码", 2);
        }

    }

    //获取验证码
    public void getPhoneCode() {

        String phone = mRegisterView.getInputPhone();
        // 检查手机号输入的正确性
        if (!checkEmail(phone)) {
            mRegisterView.showToast("手机号输入错误", 3);
            return;
        }


        final String phone2 = mRegisterView.getInputPhone();
        //更新UI，60秒不结束无法获取
        RxCountDown.countdown(60)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mRegisterView.showToast("验证码发送成功", 1);
                        isSendCheckCode = true;
                        //通过第三方得到验证码
                        // 在尝试读取通信录时以弹窗提示用户（可选功能）
                        SMSSDK.setAskPermisionOnReadContact(true);
                        // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
                        SMSSDK.getVerificationCode("86", phone2);
                    }

                    @Override
                    public void onNext(Integer value) {
                        mRegisterView.resetCodeSecond(value);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        mRegisterView.resetCodeSecond(-1);
                    }
                });


    }

    private boolean checkEmail(String phone) {
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(phone);
        return m.matches();
    }

}
