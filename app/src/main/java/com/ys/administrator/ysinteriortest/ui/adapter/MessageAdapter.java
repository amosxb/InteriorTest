package com.ys.administrator.ysinteriortest.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.administrator.ysinteriortest.model.bean.StudentInfoBean;
import com.ys.administrator.ysinteriortest.R;
import com.ys.administrator.ysinteriortest.util.StringUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * description: 口语界面适配
 * User: shaobing
 * Date: 2016-06-07
 * Time: 18:35
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {



    //自定义的ViewHolder，持有每个Item的的所有界面元素

    private Context mContext;
    private List<StudentInfoBean.DataBean> mLists;
    private OnItemClickListener mListener;
    private boolean isEdit;

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

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
    public MessageAdapter(Context mContext, List<StudentInfoBean.DataBean> mLists) {
        this.mLists = mLists;
        this.mContext = mContext;
    }

    /**
     * 更新数据
     * update data
     *
     * @param mLists datas
     */
    public void setData(List<StudentInfoBean.DataBean> mLists) {
        this.mLists = mLists;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_password)
        TextView tvPassword;
        @Bind(R.id.tv_stuName)
        TextView tvStuName;
        @Bind(R.id.tv_stuID)
        TextView tvStuID;
        @Bind(R.id.tv_isLogin)
        TextView tvIsLogin;
        @Bind(R.id.tv_macAdd)
        TextView tvMacAdd;
        @Bind(R.id.tv_ipAdd)
        TextView tvIpAdd;
        @Bind(R.id.tv_padVersion)
        TextView tvPadVersion;
        @Bind(R.id.tv_battery)
        TextView tvBattery;
        @Bind(R.id.ck)
        CheckBox ck;
        @Bind(R.id.llyt_set)
        LinearLayout llytSet;
        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_stu_msg, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {

        if (StringUtils.isEmpty(mLists.get(position).getMacAddress())) {
            viewHolder.llytSet.setBackgroundColor(Color.WHITE);
        }else{
            viewHolder.llytSet.setBackgroundColor(Color.GREEN);
        }
        viewHolder.tvStuName.setText(mLists.get(position).getStu_name());
        viewHolder.tvStuID.setText(mLists.get(position).getStu_id());

        //是否登录
        if(mLists.get(position).getIs_login())
        {
            viewHolder.tvIsLogin.setText(mContext.getString(R.string.yes));
        }else
        {
            viewHolder.tvIsLogin.setText(mContext.getString(R.string.no));
        }
        viewHolder.tvPadVersion.setText(mLists.get(position).getPadVersion());
        viewHolder.tvBattery.setText(mLists.get(position).getResidualBattery());
        viewHolder.tvMacAdd.setText(mLists.get(position).getMacAddress());
        viewHolder.tvIpAdd.setText(mLists.get(position).getIpAddress());
        viewHolder.tvPassword.setText(mLists.get(position).getPassword());
        if (mLists.get(position).isSelect()) {
            viewHolder.ck.setChecked(true);
        } else {
            viewHolder.ck.setChecked(false);
        }
        if (isEdit) {
            viewHolder.ck.setVisibility(View.VISIBLE);
        } else {
            viewHolder.ck.setVisibility(View.GONE);
        }
        if (mListener != null) {
            if (!viewHolder.ck.hasOnClickListeners()) {
                viewHolder.ck.setOnClickListener(new View.OnClickListener() {
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
