package com.nuist.you.smarthome.ui.module.model.impl;

import android.util.Log;


import com.nuist.you.smarthome.bean.BaseCode;
import com.nuist.you.smarthome.bean.Users;
import com.nuist.you.smarthome.http.ApiManage;
import com.nuist.you.smarthome.listener.RequestImpl;
import com.nuist.you.smarthome.ui.module.model.IRegisterModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class RegisterModel implements IRegisterModel {
    private static final String TAG = "RegisterModel";

    @Override
    public void upUser(Users user, final RequestImpl listener) {
        if (null != user) {
            ApiManage.getInstance().getChatService().register(user)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseCode>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            Log.d(TAG, "onSubscribe: ");
                        }

                        @Override
                        public void onNext(BaseCode value) {
                            Log.d(TAG, "onNext: " + value);
                            if (value != null) {
                                listener.loadSuccess(value);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG, "onError: ");
                            listener.loadFailed();
                        }

                        @Override
                        public void onComplete() {
                            Log.d(TAG, "onComplete: ");
                        }
                    });


        }

    }

    @Override
    public BaseCode downUser(Users user) {
        return null;
    }
}
