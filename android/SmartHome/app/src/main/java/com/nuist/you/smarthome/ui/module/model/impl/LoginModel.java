package com.nuist.you.smarthome.ui.module.model.impl;



import com.nuist.you.smarthome.bean.BaseCode;
import com.nuist.you.smarthome.bean.Users;
import com.nuist.you.smarthome.http.ApiManage;
import com.nuist.you.smarthome.listener.RequestImpl;
import com.nuist.you.smarthome.ui.module.model.ILoginModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginModel implements ILoginModel {
    @Override
    public void login(Users user, final RequestImpl listener) {
        if (null != user) {
            ApiManage.getInstance().getChatService().login(user)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BaseCode>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseCode value) {
                            if (null != value) {
                                listener.loadSuccess(value);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            listener.loadFailed();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }
}
