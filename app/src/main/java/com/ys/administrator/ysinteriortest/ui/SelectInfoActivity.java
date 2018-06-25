package com.ys.administrator.ysinteriortest.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.administrator.ysinteriortest.model.bean.SchoolInfoBean;
import com.ys.administrator.ysinteriortest.R;
import com.ys.administrator.ysinteriortest.base.BaseActivity;
import com.ys.administrator.ysinteriortest.iview.IschoolInfoView;
import com.ys.administrator.ysinteriortest.presenter.SelectInfoPresenter;
import com.ys.administrator.ysinteriortest.ui.sharePre.SharePrefUtils;
import com.ys.administrator.ysinteriortest.util.ToastUtil;
import com.ys.administrator.ysinteriortest.view.MyScollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 学校列表
 * Created by amos on 2016/5/23.
 */
public class SelectInfoActivity extends BaseActivity implements View.OnClickListener, IschoolInfoView {


    public static final String LOG_TAG = SelectInfoActivity.class.getSimpleName();

    @Bind(R.id.id_tools_sv)
    MyScollView mToolsSv;

    @Bind(R.id.id_show_school_ll)
    LinearLayout mShowSchoolLl;

    @Bind(R.id.id_show_student_vp)
    ViewPager mShowStudentVp;

    @Bind(R.id.pb_loading)
    ProgressBar mPbLoading;

    private List<SchoolInfoBean.DataBean> mSchoolInfobean = new ArrayList<>();
    private TextView mShowScroolNameTv[];
    private View mViews[];
    private int mCurrentItem;
    private int scrllViewWidth = 0;
    private int scrollViewMiddle = 0;
    private SelectInfoPresenter mSelectInfoPresenter;

    public static void startActivity(Context context){
        context.startActivity(new Intent(context, SelectInfoActivity.class));
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mSelectInfoPresenter = new SelectInfoPresenter(mContext , this);

        initEvent();
        showToolsView();
    }

    private void initEvent()
    {
        mShowStudentVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position)
            {
                if (mShowStudentVp.getCurrentItem() != position)
                    mShowStudentVp.setCurrentItem(position);
                if (mCurrentItem != position)
                {
                    changeTextColor(position);
                    changeTextLocation(position);
                }
                mCurrentItem = position;
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_info;
    }

    @Override
    public void onClick(View v) {
        changeTextColor(v.getId());
        mShowStudentVp.setCurrentItem(v.getId());
    }

    private void showToolsView() {
        mParam.clear();

        mParam.put("account", SharePrefUtils.getUserName());
        mParam.put("token", SharePrefUtils.getToken());

        //访问接口
        mSelectInfoPresenter.getSchoolInfo(mParam);
    }

    @Override
    public void loginResult(SchoolInfoBean schoolInfoBean) {
        if(schoolInfoBean.getRetCode().equals("SUCCESS"))
        {
            if(schoolInfoBean.getData() != null && schoolInfoBean.getData().size() > 0)
            {
                List<SchoolInfoBean.DataBean> dataBeans = schoolInfoBean.getData();
                mSchoolInfobean.clear();
                mSchoolInfobean.addAll(dataBeans);

                //显示学校名称
                showSchoolName();
                mShowStudentVp.setAdapter(new ClassesAdapter(getSupportFragmentManager()));
            }else
            {
                ToastUtil.showShortToast(this, getString(R.string.no_data));
            }
        }else
        {
            ToastUtil.showShortToast(this, getString(R.string.failed_to_get_the_school_list));
        }
    }

    @Override
    public void loading() {
        mPbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void cancelLoading() {
        mPbLoading.setVisibility(View.GONE);
    }

    /**
     * 改变textView的颜色
     *
     * @param id 要改变view的Id
     */
    private void changeTextColor(int id) {
        for (int i = 0; i < mShowScroolNameTv.length; i++) {
            if (i != id) {
                mShowScroolNameTv[i].setBackgroundResource(android.R.color.transparent);
                mShowScroolNameTv[i].setTextColor(0xff000000);
            }
        }
        mShowScroolNameTv[id].setBackgroundResource(R.color.show_school_name_select_bg);
        mShowScroolNameTv[id].setTextColor(0xffff5d5e);
    }

    /**
     * 改变栏目位置
     *
     * @param clickPosition
     */
    private void changeTextLocation(int clickPosition) {
        final int x = (mViews[clickPosition].getTop() - getScrollViewMiddle() + (getViewheight(mViews[clickPosition]) / 2));//移动到栏目中间
        mToolsSv.post(() -> mToolsSv.smoothScrollTo(0, x));
    }

    /**
     * 显示学校的名字
     * */
    private void showSchoolName() {
        mShowScroolNameTv = new TextView[mSchoolInfobean.size()];
        mViews = new View[mSchoolInfobean.size()];

        for (int i = 0; i < mSchoolInfobean.size(); i++)
        {
            View view = LayoutInflater.from(this).inflate(R.layout.item_b_top_nav_layout, null);
            view.setId(i);
            view.setOnClickListener(SelectInfoActivity.this);
            TextView schoolNameTv = (TextView) view.findViewById(R.id.id_school_name_tv);
            schoolNameTv.setText(mSchoolInfobean.get(i).getSchool_name());
            mShowSchoolLl.addView(view);

            mShowScroolNameTv[i] = schoolNameTv;
            mViews[i] = view;
        }
        changeTextColor(0);
    }

    /**
     * 返回scrollview的中间位置
     *
     * @return
     */
    private int getScrollViewMiddle() {
        if (scrollViewMiddle == 0)
            scrollViewMiddle = getScrollViewheight() / 2;
        return scrollViewMiddle;
    }

    /**
     * @param view
     * @return 返回view的高度
     */
    private int getViewheight(View view) {
        return view.getBottom() - view.getTop();
    }

    /**
     * @return  返回ScrollView的高度
     */
    private int getScrollViewheight() {
        return mToolsSv.getBottom() - mToolsSv.getTop();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    /**
     * ViewPager 加载选项卡
     */
    private class ClassesAdapter extends FragmentPagerAdapter {
        public ClassesAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            ArrayList<SchoolInfoBean.ClassBean> classBean = (ArrayList<SchoolInfoBean.ClassBean>) mSchoolInfobean.get(i).getLstClass();
            return StudentFragment.newInstance(classBean , mSchoolInfobean.get(i).getSchool_name());
        }

        @Override
        public int getCount() {
            return mSchoolInfobean.size();
        }
    }
}
