package com.ys.administrator.ysinteriortest.ui;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.ys.administrator.ysinteriortest.R;
import com.ys.administrator.ysinteriortest.base.BaseActivity;
import com.ys.administrator.ysinteriortest.ui.event.WifiListEvent;
import com.ys.administrator.ysinteriortest.ui.adapter.LoadAdapter;
import com.ys.administrator.ysinteriortest.ui.adapter.WifiListAdapter;
import com.ys.administrator.ysinteriortest.util.WifiAdmin;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * wifi列表
 * Created by amos on 2017/8/2.
 */

public class WifiListActivity extends BaseActivity{

    @Bind(R.id.rv_wifi)
    RecyclerView rvWifi;

    private List<ScanResult> mWifiLists = new ArrayList<>();
    private WifiListAdapter mWifiListAdapter;

    public static void startActivity(Context context)
    {
        context.startActivity(new Intent(context,WifiListActivity.class));
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

        WifiAdmin wifiAdmin = new WifiAdmin(this);
        wifiAdmin.startScan();

        //获取wifi列表
        List<ScanResult> scanResults = wifiAdmin.getWifiList();
        //去掉重名的wifi
        mWifiLists = noSameName(scanResults);

        mWifiListAdapter = new WifiListAdapter(this, mWifiLists);
        rvWifi.setLayoutManager(new LinearLayoutManager(this));
        rvWifi.setAdapter(mWifiListAdapter);
        
        initEvent();
    }

    private void initEvent() {
        mWifiListAdapter.setOnItemClickListener((view, position) -> {
            EventBus.getDefault().post(new WifiListEvent(mWifiLists.get(position).SSID));
            finish();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_wifi_list;
    }

    /**
     * 去除同名WIFI
     *
     * @param oldSr 需要去除同名的列表
     * @return 返回不包含同命的列表
     */
    public List<ScanResult> noSameName(List<ScanResult> oldSr)
    {
        List<ScanResult> newSr = new ArrayList<ScanResult>();
        for (ScanResult result : oldSr)
        {
            if (!TextUtils.isEmpty(result.SSID) && !containName(newSr, result.SSID))
                newSr.add(result);
        }
        return newSr;
    }

    /**
     * 判断一个扫描结果中，是否包含了某个名称的WIFI
     *
     * @param sr
     * 扫描结果
     * @param name
     * 要查询的名称
     * @return 返回true表示包含了该名称的WIFI，返回false表示不包含
     */
    public boolean containName(List<ScanResult> sr, String name)
    {
        for (ScanResult result : sr)
        {
            if (!TextUtils.isEmpty(result.SSID) && result.SSID.equals(name))
                return true;
        }
        return false;
    }
}
