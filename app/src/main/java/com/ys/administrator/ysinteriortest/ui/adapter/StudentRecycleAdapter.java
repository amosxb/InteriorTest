package com.ys.administrator.ysinteriortest.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.administrator.ysinteriortest.model.bean.SchoolInfoBean;
import com.ys.administrator.ysinteriortest.R;
import com.ys.administrator.ysinteriortest.util.LogXmc;
import com.ys.administrator.ysinteriortest.view.ItemTouchHelperAdapter;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by　amos 2016/5/26.
 * RecyclerView的适配器
 */
public class StudentRecycleAdapter extends RecyclerView.Adapter<StudentRecycleAdapter.MyViewHolder> implements View.OnClickListener,ItemTouchHelperAdapter {

    public static final String LOG_TAG =  StudentRecycleAdapter.class.getSimpleName();
    private LayoutInflater mLayoutInflater;
    private List<SchoolInfoBean.ClassBean> mClassBeans;
    private Context mContext;

    public StudentRecycleAdapter(Context context ,List<SchoolInfoBean.ClassBean> classesModels) {
        mLayoutInflater = LayoutInflater.from(context);
        this.mClassBeans = classesModels;
        this.mContext = context;
    }

    /**
     * RecyclerView item点击事件监听接口
     * */
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, SchoolInfoBean.ClassBean data);
    }

    private OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener;


    public void setOnRecycleViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener){
        this.mOnRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.classesNameTv.setText(mClassBeans.get(position).getClass_name());
        LogXmc.i(LOG_TAG,"className = " + mClassBeans.get(position).getClass_name());
        holder.itemView.setTag(mClassBeans.get(position));
        holder.classesIv.setBackgroundResource(R.mipmap.slab);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_list_pro_type,null);
        view.setOnClickListener(this);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public int getItemCount() {
        return mClassBeans.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.id_classes_iv)
        ImageView classesIv;

        @Bind(R.id.id_classes_name_tv)
        TextView classesNameTv;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);//注解绑定
        }
    }
    @Override
    public void onClick(View v) {
        mOnRecyclerViewItemClickListener.onItemClick(v ,(SchoolInfoBean.ClassBean)v.getTag());
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mClassBeans, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        mClassBeans.remove(position);
        notifyItemRemoved(position);
    }
}
