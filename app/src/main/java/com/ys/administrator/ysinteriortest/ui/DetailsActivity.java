package com.ys.administrator.ysinteriortest.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.administrator.ysinteriortest.model.TestCommandBean;
import com.administrator.ysinteriortest.model.TestMqttBean;
import com.administrator.ysinteriortest.model.bean.StudentAddInfoBean;
import com.administrator.ysinteriortest.model.bean.StudentInfoBean;
import com.google.gson.Gson;
import com.lzp.floatingactionbuttonplus.FabTagLayout;
import com.lzp.floatingactionbuttonplus.FloatingActionButtonPlus;
import com.ys.administrator.ysinteriortest.Constant;
import com.ys.administrator.ysinteriortest.R;
import com.ys.administrator.ysinteriortest.base.BaseActivity;
import com.ys.administrator.ysinteriortest.iview.IstudentInfoView;
import com.ys.administrator.ysinteriortest.presenter.StudentInfoPresenter;
import com.ys.administrator.ysinteriortest.service.MqttClientService;
import com.ys.administrator.ysinteriortest.ui.adapter.MessageAdapter;
import com.ys.administrator.ysinteriortest.ui.event.DownloadApkEvent;
import com.ys.administrator.ysinteriortest.ui.event.LoadApkEvent;
import com.ys.administrator.ysinteriortest.ui.event.WifiListEvent;
import com.ys.administrator.ysinteriortest.ui.sharePre.SharePrefUtils;
import com.ys.administrator.ysinteriortest.util.LogXmc;
import com.ys.administrator.ysinteriortest.util.StringUtils;
import com.ys.administrator.ysinteriortest.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 基础操作类
 * Created by amos on 2017/7/25.
 */

public class DetailsActivity extends BaseActivity implements IstudentInfoView{

    public static final String LOG_TAG = DetailsActivity.class.getSimpleName();

    //编辑
    @Bind(R.id.tv_operator)
    TextView tvOperator;

    //全选
    @Bind(R.id.tv_ckAll)
    TextView tvCkAll;

    //学校名称
    @Bind(R.id.tv_school)
    TextView tvSchool;

    //班级
    @Bind(R.id.tv_class)
    TextView tvClass;

    //学生人数
    @Bind(R.id.tv_stuNum)
    TextView tvStuNum;

    //清除平板数据
    @Bind(R.id.tv_clear_pad)
    TextView btnClearPad;

    //一键登录
    @Bind(R.id.tv_one_click_login)
    TextView btnOneClickLogin;

    //apk下载
    @Bind(R.id.tv_download)
    TextView btnDownload;

    //刷新
    @Bind(R.id.tv_refresh)
    TextView btnRefresh;

    @Bind(R.id.rv_msg)
    RecyclerView rvMsg;


    @Bind(R.id.pb_loading)
    ProgressBar pbLoading;

    @Bind(R.id.float_btn_plus)
    FloatingActionButtonPlus floatingActionButtonPlus;

    private String mClassId;
    private String mSchool_id;
    private StudentInfoPresenter mStudentInfoPresenter;
    private MessageAdapter mMessageAdapter;
    private List<StudentInfoBean.DataBean> mStudentInfoBeans = new ArrayList<>();
    //是否是自动刷新
    private boolean isAutoRefresh;
    //定时器
    private final Timer timer = new Timer();
    //task
    private TimerTask task;
    //是否清楚服务器数据
    private boolean isClearServerData = false;
    //apk名字
    private String mApkName;
    //apk版本号
    private String mVersion;

    private String mFilePath;
    //存储学生信息
    List<StudentInfoBean.DataBean> list = new ArrayList<>();
    //单个登录或者多个学生id登录
    private List<String> loginStuds = new ArrayList<>();

    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            // 要做的事情
            isAutoRefresh = true;
            getStudentInfo();
            super.handleMessage(msg);
        }
    };

    public static void startActivity(Context context, String classId, String schoolName, String className ,String school_id)
    {
        Intent intent = new Intent(context , DetailsActivity.class);
        intent.putExtra("class_id" ,classId);
        intent.putExtra("school_name",schoolName);
        intent.putExtra("class_name",className);
        intent.putExtra("school_id", school_id);
        context.startActivity(intent);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mStudentInfoPresenter = new StudentInfoPresenter(mContext ,this);
        //注册eventbus
        EventBus.getDefault().register(this);

        initData();
        initEvent();
        //启动自动刷新线程
        startAutoRefresh();
    }

    private void initData() {
        mClassId = getIntent().getStringExtra("class_id");
        mSchool_id = getIntent().getStringExtra("school_id");
        LogXmc.i(mClassId + "......." + SharePrefUtils.getUserName() + "......." + SharePrefUtils.getToken());

        //设置学校、班级、人数、老师、老师设备信息
        tvSchool.setText(String.format(getString(R.string.school), getIntent().getStringExtra("school_name")));
        tvClass.setText(String.format(getString(R.string.class_and_grade), getIntent().getStringExtra("class_name")));
        tvCkAll.setText(getString(R.string.check_all));
        tvCkAll.setVisibility(View.VISIBLE);

        //实例化适配器
        mMessageAdapter = new MessageAdapter(this, mStudentInfoBeans);
        mMessageAdapter.setEdit(true);
        rvMsg.setAdapter(mMessageAdapter);
        rvMsg.setLayoutManager(new LinearLayoutManager(this));

        isAutoRefresh = false;
        //获取学生列表接口
        getStudentInfo();
    }

    @Override
    public void loginResult(Object obj) {
        if(obj instanceof StudentInfoBean)
        {
            StudentInfoBean studentInfoBean = (StudentInfoBean) obj;
            //是否是清除服务器数据
            if(isClearServerData)
            {
                if(studentInfoBean.getRetCode().equals("SUCCESS"))
                {
                    ToastUtil.showShortToast(this, getString(R.string.clear_server_data_success));
                    btnClearPad.setClickable(false);//设置不可点击
                    btnClearPad.setEnabled(false);
                    btnClearPad.setTextColor(getResources().getColor(R.color.gray));//设置成灰色
                    clearCheckState();//清出所有选中的按钮
                    loginStuds.clear();//清除选择的学生id
                }else
                {
                    ToastUtil.showShortToast(this, getString(R.string.clear_server_data_error));
                }
            }else
            {
                if(studentInfoBean.getRetCode().equals("SUCCESS"))
                {
                    if(studentInfoBean.getData() != null && studentInfoBean.getData().size() > 0)
                    {
                        List<StudentInfoBean.DataBean> dataBeans = studentInfoBean.getData();
                        //赋值获取的数据
                        for (int i = 0; i < dataBeans.size(); i++)
                        {
                            if (mStudentInfoBeans.size() > i)
                            {
                                //mac地址不为空
                                if(!TextUtils.isEmpty(dataBeans.get(i).getMacAddress()))
                                {
                                    mStudentInfoBeans.get(i).setIs_login(true);
                                }else {
                                    mStudentInfoBeans.get(i).setIs_login(false);
                                }

                                mStudentInfoBeans.get(i).setStu_name(dataBeans.get(i).getStu_name());
                                mStudentInfoBeans.get(i).setStu_id(dataBeans.get(i).getStu_id());
                                mStudentInfoBeans.get(i).setMacAddress(dataBeans.get(i).getMacAddress());
                                mStudentInfoBeans.get(i).setIpAddress(dataBeans.get(i).getIpAddress());
                                mStudentInfoBeans.get(i).setPadVersion(dataBeans.get(i).getPadVersion());
                                mStudentInfoBeans.get(i).setResidualBattery(dataBeans.get(i).getResidualBattery());
                                mStudentInfoBeans.get(i).setPassword(dataBeans.get(i).getPassword());
                                mStudentInfoBeans.get(i).setSsid(dataBeans.get(i).getSsid());
                            } else
                            {
                                StudentInfoBean.DataBean dataBean = new StudentInfoBean.DataBean();

                                //mac地址不为空
                                if(!TextUtils.isEmpty(dataBeans.get(i).getMacAddress()))
                                {
                                    dataBean.setIs_login(true);
                                }else
                                {
                                    dataBean.setIs_login(false);
                                }

                                dataBean.setStu_name(dataBeans.get(i).getStu_name());
                                dataBean.setStu_id(dataBeans.get(i).getStu_id());
                                dataBean.setMacAddress(dataBeans.get(i).getMacAddress());
                                dataBean.setIpAddress(dataBeans.get(i).getIpAddress());
                                dataBean.setPadVersion(dataBeans.get(i).getPadVersion());
                                dataBean.setResidualBattery(dataBeans.get(i).getResidualBattery());
                                dataBean.setPassword(dataBeans.get(i).getPassword());
                                dataBean.setSsid(dataBeans.get(i).getSsid());
                                mStudentInfoBeans.add(dataBean);
                            }
                        }
                        //更新数据
                        mMessageAdapter.notifyDataSetChanged();
                        //更新班级人数
                        tvStuNum.setText(String.format(getString(R.string.number_of_people), mStudentInfoBeans.size()));
                    }else
                    {
                        ToastUtil.showShortToast(this, getString(R.string.no_student_data));
                    }
                }else
                {
                    ToastUtil.showShortToast(this, getString(R.string.failed_to_get_student_list));
                }
            }
        }else if(obj instanceof StudentAddInfoBean)
        {
            StudentAddInfoBean studentAddInfoBean = (StudentAddInfoBean) obj;

            if(studentAddInfoBean.getRetCode().equals("SUCCESS"))
            {
                btnClearPad.setClickable(true);//设置可点击
                btnClearPad.setEnabled(true);
                btnClearPad.setTextColor(Color.BLACK);//设置成黑色
                ToastUtil.showShortToast(this,getString(R.string.login_successfully));
            }else
            {
                ToastUtil.showShortToast(this, studentAddInfoBean.getMessage());
            }
        }
    }

    @OnClick({R.id.tv_ckAll,R.id.tv_operator,R.id.tv_clear_pad,R.id.tv_one_click_login,R.id.tv_refresh,R.id.tv_download,R.id.tv_close_test})
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tv_ckAll://全选
                setCheckBoxState();
                break;
            case R.id.tv_operator://编辑
                setShowCheckAll();
                break;
            case R.id.tv_clear_pad://清除平板数据
                clearPanelData();
                break;
            case R.id.tv_one_click_login://一键登录
                oneClickLogin();
                break;
            case R.id.tv_refresh://刷新
                isAutoRefresh = false;
                //获取学生列表接口
                getStudentInfo();
                break;
            case R.id.tv_download://apk下载
                downloadApk();
                break;
            case R.id.tv_close_test://关闭测试功能
                ToastUtil.showShortToast(this,"关闭测试功能");
                closeTestOperation();
                break;
        }
    }

    /**
     * 关闭测试功能发送指令
     * */
    private void closeTestOperation() {
        list.clear();
        for (int i = 0; i < mStudentInfoBeans.size(); i++) {
            if (mStudentInfoBeans.get(i).isSelect()) {
                list.add(mStudentInfoBeans.get(i));
            }
        }
        if (list.size() == 0) {
            ToastUtil.showShortToast(DetailsActivity.this, getString(R.string.select_the_test_machine_be_turned_off));
            return;
        }
        for (int i = 0; i< list.size();i++){

            if (TextUtils.isEmpty(list.get(i).getMacAddress()))
            {
                continue;
            }
            MqttClientService.publishCloseTest(Constant.COMMAND_CLOSE_TEST, list.get(i).getMacAddress());
        }
    }

    /**
     * apk下载
     * */
    private void downloadApk() {
        list.clear();
        for (int i = 0; i < mStudentInfoBeans.size(); i++) {
            if (mStudentInfoBeans.get(i).isSelect()) {
                list.add(mStudentInfoBeans.get(i));
            }
        }
        if (list.size() == 0) {
            ToastUtil.showShortToast(DetailsActivity.this, getString(R.string.please_select_the_device_download));
            return;
        }

        if (TextUtils.isEmpty(mApkName) && TextUtils.isEmpty(mVersion)) {
            ToastUtil.showShortToast(DetailsActivity.this, getString(R.string.not_yet_selected_apk_to_download));
            return;
        }

        for (int i = 0; i < list.size(); i++)
        {
            LogXmc.i("apkUrl", StringUtils.getDownloadApkUrl(mApkName, mVersion ,mFilePath));
            if (TextUtils.isEmpty(list.get(i).getMacAddress()))
            {
                continue;
            }
            sendCommand(mApkName, StringUtils.getDownloadApkUrl(mApkName, mVersion ,mFilePath), list.get(i).getMacAddress(), Constant.APK_LOAD);
        }
    }


    /**
     * 调用获取学生列表接口
     * */
    private void getStudentInfo(){
        mParam.clear();

        isClearServerData = false;
        //访问接口参数集
        mParam.put("class_id", mClassId);
        mParam.put("account", SharePrefUtils.getUserName());
        mParam.put("token", SharePrefUtils.getToken());
        //访问接口
        mStudentInfoPresenter.getStudentInfo(mParam);
    }

    /**
     * 一键登录调用接口
     * */
    private void oneClickLogin() {
        mParam.clear();

        //参数集合
        mParam.put("account", SharePrefUtils.getUserName());
        mParam.put("token" , SharePrefUtils.getToken());
        mParam.put("class_id", mClassId);
        //调用接口
        mStudentInfoPresenter.getStudentPanelInfo(mParam);
    }

    /**
     * 单个登录或者多个登录
     */
    private void loginRequest() {
        for (int i = 0; i < mStudentInfoBeans.size(); i++)
        {
            if (mStudentInfoBeans.get(i).isSelect())
            {
                loginStuds.add(mStudentInfoBeans.get(i).getStu_id());
            }
        }

        if (loginStuds==null || loginStuds.size() == 0)
        {
            ToastUtil.showShortToast(DetailsActivity.this, getString(R.string.done_anything_yet));
            return;
        }
        selectLoginRequest(loginStuds);
    }

    /**
     * 单个登录
     * @param s 需要登录的学生id集合
     * */
    private void selectLoginRequest(List<String> s)
    {
        mParam.clear();

        //参数集合
        mParam.put("account", SharePrefUtils.getUserName());
        mParam.put("token" , SharePrefUtils.getToken());
        mParam.put("class_id", mClassId);
        mParam.put("id_list", StringUtils.getStudentId(s));
        //调用接口
        mStudentInfoPresenter.getStudentPanelInfo(mParam);
    }

    /**
     * 设置清除所有的选择操作
     */
    private void clearCheckState() {
        for (int i = 0; i < mStudentInfoBeans.size(); i++) {
            mStudentInfoBeans.get(i).setSelect(false);
        }
        mMessageAdapter.setData(mStudentInfoBeans);
        mMessageAdapter.notifyDataSetChanged();
    }

    @Override
    public synchronized void loading() {
        if(!isAutoRefresh)
        {
            pbLoading.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public synchronized void cancelLoading() {
        if(!isAutoRefresh)
        {
            pbLoading.setVisibility(View.GONE);
        }
    }

    private void initEvent() {
        floatingActionButtonPlus.setOnItemClickListener((tagView, position) -> {
            switch (position)
            {
                case 0://切换WIFI
                    WifiListActivity.startActivity(mContext);
//                        switchWifi();
                    break;
                case 1://选择apk进行下载
                    LoadApkActivity.startActivity(mContext);
                    EventBus.getDefault().postSticky(new LoadApkEvent(mSchool_id));
                    break;
                case 2://清楚该班服务器数据
                    clearServer(false);
                    break;
                case 3://清除服务器所有数据
                    clearServer(true);
                    break;
                case 4://单个登录或者多个登录
                    loginRequest();
                    break;
            }
        });

        mMessageAdapter.setOnItemClickListener((view, position) -> {
            if (mStudentInfoBeans.get(position).isSelect())
            {
                mStudentInfoBeans.get(position).setSelect(false);
            } else
            {
                mStudentInfoBeans.get(position).setSelect(true);
            }

            LogXmc.i(LOG_TAG, "选中的参数    :" + position);
            //刷新操作
            mMessageAdapter.notifyDataSetChanged();
        });
    }

    /**
     * 清除服务器数据
     * */
    private void clearServer(boolean isClear) {
        mParam.clear();

        isClearServerData = true;
        mParam.put("account", SharePrefUtils.getUserName());
        mParam.put("token", SharePrefUtils.getToken());
        mParam.put("class_id", mClassId);

        if(isClear)
        {
            mParam.put("force_clear_all", "1");
        }
        mStudentInfoPresenter.clearStudentInfo(mParam);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_show_message;
    }

    /**
     * 切换WiFi
     * @param wifiName wifi名
     */
    private void switchWifi(final String wifiName) {
        list.clear();
        for (int i = 0; i < mStudentInfoBeans.size(); i++) {
            if (mStudentInfoBeans.get(i).isSelect()) {
                list.add(mStudentInfoBeans.get(i));
            }
        }
        if (list.size() == 0) {
            ToastUtil.showShortToast(DetailsActivity.this, getString(R.string.please_select_switch_WiFi_devices));
            return;
        }

        View  v = View.inflate(this, R.layout.item_dialog, null);
        final EditText etWifiPass = (EditText) v.findViewById(R.id.et_wifi_pass);

        new AlertDialog.Builder(this).setTitle(getString(R.string.switch_the_wifi)).setMessage(getString(R.string.the_currently_selected_wifi) + wifiName).setView(v)
                .setNegativeButton(getString(R.string.cancel), (dialog, which) -> dialog.dismiss())
                .setPositiveButton(getString(R.string.confirm), (dialog, which) -> {

                    String wifiPass = etWifiPass.getText().toString().trim();

                    ToastUtil.showShortToast(DetailsActivity.this, getString(R.string.switch_is_WiFi) + wifiName + "    " + wifiPass);

                    for (int i = 0; i < list.size(); i++) {
                        if (TextUtils.isEmpty(list.get(i).getMacAddress())) {
                            continue;
                        }
                        sendCommand(wifiName , wifiPass,  list.get(i).getMacAddress(), Constant.COMMAND_WIFI);
                    }

                }).show();
    }

    /**
     * 发送推送的公共方法
     * @param macAddress mac地址
     * @param command 推送命令
     * */
    private void sendCommand(String name , String password ,String macAddress, String command) {
        TestMqttBean testMqttBean = new TestMqttBean();
        testMqttBean.setDevice(macAddress.replace(":",""));

        TestCommandBean testCommandBean = new TestCommandBean();
        testCommandBean.setCommand(command);
        testCommandBean.setName(name);
        testCommandBean.setPassword(password);

        testMqttBean.setMessage(new Gson().toJson(testCommandBean));
        MqttClientService.publish(testMqttBean);
    }

    /**
     * 消息传递
     * */
    @Subscribe
    public void onMainEvent(Object obj){
        if(obj instanceof DownloadApkEvent)
        {
            DownloadApkEvent downloadApkItem = (DownloadApkEvent) obj;
            mApkName = downloadApkItem.getApp_name();
            mVersion = downloadApkItem.getVersion();
            mFilePath = downloadApkItem.getFile_path();
            LogXmc.i("apk_list", "mApkName :" + mApkName + "mVersion : " + mVersion);
        }else if(obj instanceof WifiListEvent)
        {
            WifiListEvent wifiListItem = (WifiListEvent) obj;
            switchWifi(wifiListItem.getSsid());
        }
    }


    /**
     * 清除平板数据
     */
    private void clearPanelData() {
        list.clear();
        for (int i = 0; i < mStudentInfoBeans.size(); i++) {
            if (mStudentInfoBeans.get(i).isSelect()) {
                list.add(mStudentInfoBeans.get(i));
            }
        }

        if (list != null && list.size() == 0) {
            ToastUtil.showShortToast(DetailsActivity.this, "请选择需要关机的测试机");
            return;
        }

        for (int i = 0; i < list.size(); i++) {

            if (TextUtils.isEmpty(list.get(i).getMacAddress())) {
                continue;
            }
            //发送推送
            sendCommand(SharePrefUtils.getUserName(), SharePrefUtils.getPassword(), list.get(i).getMacAddress(), Constant.COMMAND_SHUTDOWN);
        }
    }

    /**
     * 启动自动刷新线程
     * */
    private void startAutoRefresh() {
        timer.schedule(task = new TimerTask() {
            @Override
            public  void run() {
                LogXmc.i("automatic refresh ...");
                handler.sendEmptyMessage(1);
            }
        },3000,3000);
    }

    /**
     * 结束自动刷新线程
     */
    private void stopAutoRefresh() {
        isAutoRefresh = false;
        timer.cancel();
    }

    /**
     * 全选操作
     */
    private void setCheckBoxState() {
        if (tvCkAll.getText().toString().equals(getString(R.string.check_all)))
        {
            for (int i = 0; i < mStudentInfoBeans.size(); i++)
            {
                mStudentInfoBeans.get(i).setSelect(true);
            }
            tvCkAll.setText(getString(R.string.check_no_all));
        } else
        {
            for (int i = 0; i < mStudentInfoBeans.size(); i++)
            {
                mStudentInfoBeans.get(i).setSelect(false);
            }
            tvCkAll.setText(getString(R.string.check_all));
        }
        mMessageAdapter.setData(mStudentInfoBeans);
        mMessageAdapter.notifyDataSetChanged();
    }


    /**
     * 设置是否全选
     * */
    private void setShowCheckAll(){
        if (mMessageAdapter.isEdit())
        {
            mMessageAdapter.setEdit(false);
            mMessageAdapter.notifyDataSetChanged();
            tvCkAll.setText(getString(R.string.check_all));
            tvCkAll.setVisibility(View.INVISIBLE);

            for (int i = 0; i < mStudentInfoBeans.size(); i++) {
                mStudentInfoBeans.get(i).setSelect(false);
            }
            mMessageAdapter.notifyDataSetChanged();
        } else
        {
            mMessageAdapter.setEdit(true);
            mMessageAdapter.notifyDataSetChanged();
            tvCkAll.setText(getString(R.string.check_all));
            tvCkAll.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopAutoRefresh();
        EventBus.getDefault().unregister(this);
    }
}
