package com.nuist.you.smarthome.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.nuist.you.smarthome.R;
import com.nuist.you.smarthome.bean.viewbean.HomeAvageBean;
import com.nuist.you.smarthome.listener.ItemClickListener;
import com.nuist.you.smarthome.ui.chartdetail.ChartDetailActivity;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by youxuan on 2020/4/2.
 */

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.MainListViewHolder> {


    private Context mContext;
    private List<HomeAvageBean> dataList;
    private int roundCorner;
    private ItemClickListener mListener;


    public HomeRecyclerAdapter(Context context) {
        mContext = context;
        roundCorner = mContext.getResources().getDimensionPixelOffset(R.dimen.dp_5);
    }

    public void setItemOnClickListener(ItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public MainListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_homelist, parent, false);
        MainListViewHolder viewHolder = new MainListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MainListViewHolder holder, final int position) {
        if (dataList.size() > 0) {
            holder.mTvRecyDataTitle.setText(dataList.get(position).getAvageTitle());
            String data = dataList.get(position).getAvageData();
            Logger.d(data);

            holder.mTvRecyData.setText(data);
            Float progress;
            if (!"0".equals(data)){
                 progress =Float.valueOf(data.substring(0,data.length()-1));
            }else {
                progress = 0f;
            }
            holder.mProgressRecyItem.setProgress(progress.intValue());
            // holder.mImageView.setImageResource(datas.get(position).getTopDrawable());
            // holder.mImageView.setRoundCorner(roundCorner);
            holder.mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.OnItemOnClickListener(v, position);
                }
            });
            //holder.mRelativeLayout.setOnClickListener(v -> Toast.makeText(mContext, "position: " + position, Toast.LENGTH_LONG).show());

//            holder.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    Intent voteIntent = new Intent(mContext, VoteActivity.class);
////                    voteIntent.putExtra("url", datas.get(position).getUrl());
////                    mContext.startActivity(4);
//                    Toast.makeText(mContext,"position: "+position,Toast.LENGTH_LONG).show();
//                }
//            });
        }

    }

    @Override
    public int getItemCount() {
        return null == dataList ? 0 : dataList.size();
    }


    public void addList(List<HomeAvageBean> datas) {
        dataList = datas;
    }

    class MainListViewHolder extends RecyclerView.ViewHolder {


        private CardView mCardView;
        private TextView mTvRecyDataTitle;
        private ImageView mTvRecyDataUpdate;
        private ImageView mTvRecyIconData;
        private TextView mTvRecyData;
        private ProgressBar mProgressRecyItem;


        public MainListViewHolder(View itemView) {
            super(itemView);
            mCardView = itemView.findViewById(R.id.cardview_home_item);
            mTvRecyDataTitle = itemView.findViewById(R.id.tv_recy_data_title);
            mTvRecyDataUpdate = itemView.findViewById(R.id.tv_recy_data_update);
            mTvRecyIconData = itemView.findViewById(R.id.tv_recy_icon_data);
            mTvRecyData = itemView.findViewById(R.id.tv_recy_data);
            mProgressRecyItem = itemView.findViewById(R.id.progress_recy_item);
        }
    }
}
