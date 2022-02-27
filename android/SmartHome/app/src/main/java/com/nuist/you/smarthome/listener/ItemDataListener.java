package com.nuist.you.smarthome.listener;

import android.view.View;

import com.nuist.you.smarthome.bean.AverageDataBean;
import com.nuist.you.smarthome.bean.MessageAllData;

import java.util.List;
import java.util.Map;

/**
 * created by youxuan
 * on 2020/4/18
 */
public interface ItemDataListener {
    /**
     * 用户点击底部ReyclerView然后，调整ChartDetailActivity的数据回调
     * @param objectBean 数据集
     * @param position ViewPager的位置
     */
    void OnItemDataListenerr(MessageAllData.ObjectBean objectBean, int position);
}
