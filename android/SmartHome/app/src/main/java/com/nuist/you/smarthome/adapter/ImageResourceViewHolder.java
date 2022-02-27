package com.nuist.you.smarthome.adapter;

import android.view.View;
import android.widget.TextView;

import com.nuist.you.smarthome.R;
import com.nuist.you.smarthome.bean.viewbean.SensorDataItem;
import com.nuist.you.smarthome.util.DataUtil;
import com.nuist.you.smarthome.view.CornerImageView;
import com.zhpan.bannerview.holder.ViewHolder;

public class ImageResourceViewHolder implements ViewHolder<SensorDataItem> {

    private int roundCorner;


    public ImageResourceViewHolder(int roundCorner) {
        this.roundCorner = roundCorner;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_slide_mode;
    }


    @Override
    public void onBind(View itemView, SensorDataItem data, int position, int size) {
        CornerImageView imageView = itemView.findViewById(R.id.banner_image);
//        CircleProgress progress = itemView.findViewById(R.id.circle_progress_bar);
//        progress.setCurrentPosition(position);
        TextView textView = itemView.findViewById(R.id.tv_temp_item);
        TextView mTvDataTypeItem = itemView.findViewById(R.id.tv_data_type_item);
        TextView mTvTipsItem = itemView.findViewById(R.id.tv_tips_item);
        imageView.setImageResource(data.getTopDrawable());
        imageView.setRoundCorner(roundCorner);

        mTvDataTypeItem.setText(data.getDataType());

        if (position == 0) {

            if (null != data.getData()) {
                textView.setText(data.getData() + "℃");
            } else {
                textView.setText("0℃");
            }
//            progress.setHint("温度");
//            progress.setUnit("℃");
//            if (null != data.getData()) {
//                progress.setValue(Float.valueOf(data.getData()));
//            } else {
//                progress.setValue(0f);
//            }

//            progress.setMaxValue(100);
        } else if (position == 1) {
            if (null != data.getData()) {
                textView.setText(data.getData() + "%");
            } else {
                textView.setText("0%");
            }

//            progress.setHint("湿度");
//            progress.setUnit("%");
//            progress.setMaxValue(100);
//            if (null != data.getData()) {
//                progress.setValue(Float.valueOf(data.getData()));
//            } else {
//                progress.setValue(0f);
//            }
        } else if (position == 2) {
//            progress.setHint("烟雾浓度");
//            progress.setUnit("ppm");
//            progress.setMaxValue(100);
//            if (null != data.getData()) {
//                progress.setValue((float) DataUtil.MQ2Convert(data.getData()));
//            } else {
//                progress.setValue(0f);
//            }
            if (null != data.getData()) {
                textView.setText((int) (DataUtil.MQ2Convert(data.getData())) + "P");
            } else {
                textView.setText("0P");
            }
        } else {
//            progress.setHint("光照强度");
//            progress.setUnit("lx");
//
//            progress.setMaxValue(100);
//            if (null != data.getData()) {
//                progress.setValue((float) DataUtil.BeamConvert(data.getData()));
//            } else {
//                progress.setValue(0f);
//            }
            if (null != data.getData()) {
                textView.setText((int) (DataUtil.BeamConvert(data.getData())) + "L");
            } else {
                textView.setText("0L");
            }

        }

        mTvTipsItem.setText(data.getTips());

    }


}
