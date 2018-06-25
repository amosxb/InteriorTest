package com.ys.administrator.ysinteriortest.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.administrator.ysinteriortest.model.LoginResult;
import com.administrator.ysinteriortest.model.ServiceBean;
import com.administrator.ysinteriortest.model.TestCommandBean;
import com.administrator.ysinteriortest.model.TestMqttBean;
import com.eaglesoul.eplatform.common.link.PublishType;
import com.eaglesoul.eplatform.common.link.ServerInfoDto;
import com.google.gson.Gson;
import com.ys.administrator.ysinteriortest.Constant;
import com.ys.administrator.ysinteriortest.R;
import com.ys.administrator.ysinteriortest.base.BaseActivity;
import com.ys.administrator.ysinteriortest.iview.IloginView;
import com.ys.administrator.ysinteriortest.presenter.LoginPresenter;
import com.ys.administrator.ysinteriortest.service.MqttClientService;
import com.ys.administrator.ysinteriortest.ui.sharePre.SharePrefUtils;
import com.ys.administrator.ysinteriortest.util.LogXmc;
import com.ys.administrator.ysinteriortest.util.LoginUtil;
import com.ys.administrator.ysinteriortest.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 登录界面
 * Created by amos on 2016/5/23.
 */
public class LoginActivity extends BaseActivity implements IloginView<Object> {

    public static final String LOG_TAG = LoginActivity.class.getSimpleName();

    @Bind(R.id.id_login_actv_username)
    AutoCompleteTextView mLoginUserNameTv;

    @Bind(R.id.id_login_et_password)
    EditText mLoginPasswordEt;

    @Bind(R.id.id_remember_password_rb)
    CheckBox mRememberPasswordRb;

    @Bind(R.id.id_sign_in_btn)
    Button mSignBtn;

    @Bind(R.id.pb_loading)
    ProgressBar pbLoading;

    @Bind(R.id.spn_switch_service)
    Spinner spnSwitchSer;


    private LoginPresenter mLoginPresenter;
    private String[] mSwitchServices = new String[]{};
    private List<ServerInfoDto> mServerInfoDto = new ArrayList<>();
    private Handler handler = new Handler();
    private PublishType mPublishType;


    @Override
    public void initViews(Bundle savedInstanceState) {
        mLoginPresenter = new LoginPresenter(mContext ,this);
        initEvent();

        //获取选中状态
        if(SharePrefUtils.getCheck())
        {
            mRememberPasswordRb.setChecked(true);
            mLoginUserNameTv.setText(SharePrefUtils.getUserName());
            mLoginPasswordEt.setText(SharePrefUtils.getPassword());
        }

        //获取服务器列表
        mLoginPresenter.getService();
    }


    private void initEvent() {
        mRememberPasswordRb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked)
            {
                SharePrefUtils.saveCheck(true);
            }else
                SharePrefUtils.saveCheck(false);
        });

        spnSwitchSer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                LogXmc.i(position + "");
                //取得ServerInfoDto对象
                ServerInfoDto serverInfoDo = mServerInfoDto.get(position);
                //创建publishType对象
                mPublishType = PublishType.createInstance(serverInfoDo);
                //切换服务器
                Constant.netSwitch(mPublishType);
//
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        //发送切换个人中心服务器推送
//                        switchPersonCenterServer(publishType);
//                    }
//                },3000);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @OnClick(R.id.id_sign_in_btn)
    public void onClick(View v){
        switch (v.getId())
        {
            case R.id.id_sign_in_btn :
                //进入主界面
                if(LoginUtil.checkLoginParams(LoginActivity.this,mLoginUserNameTv , mLoginPasswordEt) )
                {
                    String account = LoginUtil.getInputInfo(mLoginUserNameTv);
                    String password = LoginUtil.getInputInfo(mLoginPasswordEt);

                    //加密处理
                    String encryptionStr = LoginUtil.encode(account, password);

                    //点击记住密码
                    if(mRememberPasswordRb.isChecked())
                    {
                        SharePrefUtils.saveUserName(account);
                        SharePrefUtils.savePassword(password);
                    }

                    if(mPublishType != null)
                    {
//                        //延迟3秒发送
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
                                LogXmc.i("aaaaaaaa", "s   LogXmc.i(\"aaaaaaaa\", \"send the switch server : " + mPublishType.getBaseUrl());
                                //发送切换个人中心服务器推送
                                switchPersonCenterServer(mPublishType);
//                            }
//                        },3000);
                    }else {
                        ToastUtil.showShortToast(this, getString(R.string.get_the_air_receiver_address_blank));
                        return;
                    }

                    //访问登录接口
                    mLoginPresenter.getLoginResult(getRequestParam(encryptionStr ,account ,password));
                }
                break;
        }
    }

    @Override
    public void loginResult(Object obj)
    {
        if(obj instanceof LoginResult)
        {
            LoginResult loginResult = (LoginResult) obj;

            if(loginResult.mRetCode.equals("SUCCESS"))
            {
                LogXmc.i("success");
                //保存token信息
                SharePrefUtils.saveToken(loginResult.mToken);
                //进入主界面
                SelectInfoActivity.startActivity(this);
                finish();
            }else
            {
                ToastUtil.showShortToast(this, loginResult.mMessage);
            }

        }else if(obj instanceof ServiceBean)
        {
            ServiceBean serviceBean = (ServiceBean) obj;

            if(serviceBean.getRetCode().equals("SUCCESS")){
                LogXmc.i("success");
                if(serviceBean.getData() != null && serviceBean.getData().size() >0)
                {
                    //临时存储
                    List<String> services = new ArrayList<>();

                    List<ServiceBean.DataBean> dataBeans = serviceBean.getData();
                    for (ServiceBean.DataBean dataBean : dataBeans)
                    {
                        services.add(dataBean.getServer_name());
                    }

                    mServerInfoDto = getServerInfoDto(dataBeans);
                    mSwitchServices = services.toArray(new String[services.size()]);

                    //切换服务器，默认选中第一个服务器
                    ServerInfoDto serverInfoDto = mServerInfoDto.get(0);
                    mPublishType = PublishType.createInstance(serverInfoDto);
                    Constant.netSwitch(mPublishType);
                    //启动推送服务
                    MqttClientService.actionStart(this);
                    //显示服务器列表
                    showServiceList();
                }else
                {
                    ToastUtil.showShortToast(this, getString(R.string.get_the_data_is_empty));
                }
            }else
            {
                ToastUtil.showShortToast(this, getString(R.string.failed_to_get_the_server_list));
            }
        }
    }

    /**
     * 发送切换个人中心服务器推送
     * @param publishType 包含服务器的对象
     * */
    private void switchPersonCenterServer(PublishType publishType){
        //发送切换服务器推送服务
        TestMqttBean testMqttBean = new TestMqttBean();
        testMqttBean.setDevice(Constant.MQTT_SWITCH_SERVER_TOPIC);
        TestCommandBean testCommandBean = new TestCommandBean();
        testCommandBean.setPassword(SharePrefUtils.getPassword());
        testCommandBean.setName(new Gson().toJson(publishType));
        testCommandBean.setCommand(Constant.COMMAND_SWITCH_SERVER);
        testMqttBean.setMessage(new Gson().toJson(testCommandBean));

        LogXmc.i("aaaaaaaa","tuisongle");
        MqttClientService.publishSwitchServer(testMqttBean);
    }

    /**
     * 获取serverInfoDto列表
     * @param dataBeans 接口返回的服务器列表
     * */
    private List<ServerInfoDto> getServerInfoDto(List<ServiceBean.DataBean> dataBeans) {
        List<ServerInfoDto> serverInfos = new ArrayList<>();
        for (ServiceBean.DataBean databean : dataBeans) {
            ServerInfoDto serverInfoDto = new ServerInfoDto();
            serverInfoDto.setDisp_no(databean.getDisp_no());
            serverInfoDto.setExtnet_baseurl(databean.getExtnet_baseurl());
            serverInfoDto.setIntnet_baseurl(databean.getIntnet_baseurl());
            serverInfoDto.setMqtt_host(databean.getMqtt_host());
            serverInfoDto.setMqtt_port(databean.getMqtt_port());
            serverInfoDto.setPkg_tail(databean.getPkg_tail());
            serverInfoDto.setServer_name(databean.getServer_name());
            serverInfoDto.setSupport_extnet(databean.isSupport_extnet());
            serverInfoDto.setSupport_extnet_only(databean.isSupport_extnet_only());
            serverInfoDto.setRedis_host(databean.getRedis_host());
            serverInfoDto.setRedis_port(databean.getRedis_port());
            serverInfos.add(serverInfoDto);
        }
        return serverInfos;
    }

    @Override
    public void loading() {
        pbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void cancelLoading() {
        pbLoading.setVisibility(View.GONE);
    }

    /**
     * 获取登录接口调用参数
     * @param account 账户名
     * @param password 密码
     * @param encMessage 加密字符串
     * */
    protected Map<String, String> getRequestParam(String encMessage ,String account,String password)
    {
        mParam.put("enc_message", encMessage);
        mParam.put("account", account);
        mParam.put("password", password);
        mParam.put("role_id", "90");
        LogXmc.i(new Gson().toJson(mParam));
        return mParam;
    }

    /**
     * 显示服务器列表
     * */
    private void showServiceList()
    {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, mSwitchServices);
        //设置下拉列表的风格
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSwitchSer.setAdapter(arrayAdapter);
        spnSwitchSer.setVisibility(View.VISIBLE);
    }
}
