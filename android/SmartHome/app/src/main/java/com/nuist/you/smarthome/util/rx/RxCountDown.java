package com.nuist.you.smarthome.util.rx;


import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class RxCountDown {
    public static Observable<Integer> countdown(int time) {
        if (time <= 0) {
            time = 0;
        }
        final int countTime = time;

        return Observable.interval(0,1, TimeUnit.SECONDS)
                .map(new Function<Long, Integer>() {
                    @Override
                    public Integer apply(Long aLong) throws Exception {
                        return countTime - aLong.intValue();
                    }
                })
                .take(countTime + 1);
    }
}
