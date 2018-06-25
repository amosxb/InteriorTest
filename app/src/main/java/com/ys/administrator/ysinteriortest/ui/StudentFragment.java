package com.ys.administrator.ysinteriortest.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.administrator.ysinteriortest.model.bean.SchoolInfoBean;
import com.ys.administrator.ysinteriortest.R;
import com.ys.administrator.ysinteriortest.ui.adapter.StudentRecycleAdapter;
import com.ys.administrator.ysinteriortest.view.OnStartDragListener;
import com.ys.administrator.ysinteriortest.view.SimpleItemTouchHelperCallback;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class StudentFragment extends Fragment implements OnStartDragListener,StudentRecycleAdapter.OnRecyclerViewItemClickListener {



    @Bind(R.id.hint_img)
    ImageView mHintIv;

    @Bind(R.id.id_type_name)
    TextView mTypeNameTv;

    @Bind(R.id.id_type_name_rv)
    RecyclerView mClassesRv;

    private ArrayList<SchoolInfoBean.ClassBean> mClassesModels;
    private ItemTouchHelper mItemTouchHelper;
    private String mSchoolName;

    public static Fragment newInstance(ArrayList<SchoolInfoBean.ClassBean> classBeen, String schoolName)
    {
        StudentFragment fragment = new StudentFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("classModels" , classBeen);
        bundle.putString("schoolName", schoolName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pro_type, null);
        ButterKnife.bind(this,view);

        mClassesModels = (ArrayList<SchoolInfoBean.ClassBean>) getArguments().getSerializable("classModels");
        mSchoolName = getArguments().getString("schoolName","");

        //设置学校名称
        mTypeNameTv.setText(mSchoolName);

        mClassesRv.setHasFixedSize(true);
        mClassesRv.setLayoutManager(new GridLayoutManager(getActivity(),3));
        StudentRecycleAdapter studentRe = new StudentRecycleAdapter(getActivity(),mClassesModels);
        mClassesRv.setAdapter(studentRe);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(studentRe);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mClassesRv);

        studentRe.setOnRecycleViewItemClickListener(this);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);

    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onItemClick(View view, SchoolInfoBean.ClassBean data) {
        DetailsActivity.startActivity(getActivity() , data.getClass_id(), mSchoolName , data.getClass_name(), data.getSchool_id());
    }
}
