package com.ys.administrator.ysinteriortest.network;

import android.util.Log;

import com.eaglesoul.eplatform.common.link.PublishType;
import com.ys.administrator.ysinteriortest.App;
import com.ys.administrator.ysinteriortest.Constant;
import com.ys.administrator.ysinteriortest.network.api.DownloadApkService;
import com.ys.administrator.ysinteriortest.network.api.LoginService;
import com.ys.administrator.ysinteriortest.network.api.SelectInfoService;
import com.ys.administrator.ysinteriortest.network.api.StudentInfoService;
import com.ys.administrator.ysinteriortest.util.LogXmc;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit帮助类
 * Created by amos on 2017/7/24.
 */
public class RetrofitHelper
{

    private static final String TAG = RetrofitHelper.class.getSimpleName();

    private static OkHttpClient mOkHttpClient;

    static
    {
        initOkHttpClient();
    }



    /**
     * 获取登录界面api
     * */
    public static LoginService getLoginApi()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .baseUrl(Constant.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(LoginService.class);
    }

    /**
     * 获取服务器列表api
     * */
    public static LoginService getServiceApi()
    {

        Retrofit retrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .baseUrl(Constant.BASE_SERVICE_LIST_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(LoginService.class);
    }

    /**
     * 获取学校列表api
     * */
    public static SelectInfoService getSchoolInfoApi()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(SelectInfoService.class);
    }

    /**
     * 获取学生列表api
     * */
    public  static StudentInfoService getStudentInfo()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(StudentInfoService.class);
    }

    /**
     * 获取下载apk列表apk
     * */
    public static DownloadApkService getApkList()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(DownloadApkService.class);
    }



    /**
     * 初始化OKHttpClient
     * 设置缓存
     * 设置超时时间
     * 设置打印日志
     */
    private static void initOkHttpClient()
    {

//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (mOkHttpClient == null)
        {
            synchronized (RetrofitHelper.class)
            {
                if (mOkHttpClient == null)
                {
                    //设置Http缓存
                    Cache cache = new Cache(new File(App.getInstance()
                            .getCacheDir(), "HttpCache"), 1024 * 1024 * 100);

                    mOkHttpClient = new OkHttpClient.Builder()
                            .cache(cache)
                            .retryOnConnectionFailure(true)//断线重连
                            .addInterceptor(new LogInterceptor())
//                            .addNetworkInterceptor(new LogInterceptor())
                            .retryOnConnectionFailure(true)
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .writeTimeout(20, TimeUnit.SECONDS)
                            .readTimeout(20, TimeUnit.SECONDS)
//                            .addInterceptor(new UserAgentInterceptor())
                            .build();
                }
            }
        }
    }

    /**
     * OkHttp3 的监听
     */
    private static class LogInterceptor implements Interceptor {

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException
        {
            Request request = chain.request();
            Log.e(TAG, "okhttp3:" + request.toString());//输出请求前整个url
            okhttp3.Response response = chain.proceed(chain.request());

            okhttp3.MediaType mediaType = response.body().contentType();
            String content = response.body().string();
            LogXmc.i("response body:" + content);//输出返回信息
            return response.newBuilder()
                    .body(okhttp3.ResponseBody.create(mediaType, content))
                    .build();
        }
    }
}
