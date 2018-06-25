package com.ys.administrator.ysinteriortest.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.administrator.ysinteriortest.model.bean.ApkInfoBean;
import com.ys.administrator.ysinteriortest.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * description: 口语界面适配
 * User: amos
 * Date: 2016-06-07
 * Time: 18:35
 */
public class LoadAdapter extends RecyclerView.Adapter<LoadAdapter.ViewHolder> {

    private Context mContext;
    private List<ApkInfoBean.DataBean> mLists;
    private OnItemClickListener mListener;

    /**
     * 设置onItemClick监听
     *
     * @param mListener listener
     */
    public void setOnItemClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    /**
     * Description  ：
     * author       ：shaobing
     * created date ： 2016/6/7 18:51
     */
    public LoadAdapter(Context mContext, List<ApkInfoBean.DataBean> mLists) {
        this.mLists = mLists;
        this.mContext = mContext;
    }

    /**
     * 更新数据
     * update data
     *
     * @param mLists datas
     */
    public void setData(List<ApkInfoBean.DataBean> mLists) {
        this.mLists = mLists;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_name)
        TextView tvName;

        @Bind(R.id.tv_apk_version)
        TextView tvApkVersion;

        @Bind(R.id.llyt_main)
        LinearLayout llytMain;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_load, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        viewHolder.tvName.setText(mLists.get(position).getApp_name());
        viewHolder.tvApkVersion.setText(mLists.get(position).getVersion());

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
