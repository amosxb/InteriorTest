package com.ys.administrator.ysinteriortest.ui.adapter;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ys.administrator.ysinteriortest.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * wifi适配器
 * Created by amos on 2017/8/2.
 */

public class WifiListAdapter extends RecyclerView.Adapter<WifiListAdapter.ViewHolder>{


    private Context mContext;
    private List<ScanResult> mLists;
    private LoadAdapter.OnItemClickListener mListener;

    /**
     * 设置onItemClick监听
     *
     * @param mListener listener
     */
    public void setOnItemClickListener(LoadAdapter.OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    /**
     * Description  ：
     * author       ：shaobing
     * created date ： 2016/6/7 18:51
     */
    public WifiListAdapter(Context mContext, List<ScanResult> mLists) {
        this.mLists = mLists;
        this.mContext = mContext;
    }

    /**
     * 更新数据
     * update data
     *
     * @param mLists datas
     */
    public void setData(List<ScanResult> mLists) {
        this.mLists = mLists;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_name)
        TextView tvName;

        @Bind(R.id.tv_level)
        TextView tvLevel;

        @Bind(R.id.llyt_main)
        LinearLayout llytMain;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public WifiListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_wifi_list, viewGroup, false);
        WifiListAdapter.ViewHolder vh = new WifiListAdapter.ViewHolder(view);
        return vh;
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(final WifiListAdapter.ViewHolder viewHolder, int position) {
        ScanResult scanResult = mLists.get(position);
        viewHolder.tvName.setText(scanResult.SSID);
        viewHolder.tvLevel.setText(String.valueOf(scanResult.level));

        if (mListener != null) {
            if (!viewHolder.llytMain.hasOnClickListeners()) {
                viewHolder.llytMain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = viewHolder.getPosition();
                        mListener.onItemClick(v, pos);

                    }
                });
            }
        }
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        if (mLists == null) {
            return 0;
        }
        return mLists.size();
    }

    /**
     * description: 设置item点击监听
     * User: shaobing
     * Date: 2016/6/12
     * Time: 17:08
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

}
