package com.ys.administrator.ysinteriortest.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.util.Log;

import com.administrator.ysinteriortest.model.TestCommandBean;
import com.administrator.ysinteriortest.model.TestMqttBean;
import com.google.gson.Gson;
import com.ys.administrator.ysinteriortest.Constant;
import com.ys.administrator.ysinteriortest.R;
import com.ys.administrator.ysinteriortest.util.LogXmc;
import com.ys.administrator.ysinteriortest.util.TestUtile;
import com.ys.administrator.ysinteriortest.util.ToastUtil;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
* @ClassName: MqttClientSetvice
* @Description: MQTT推送服务
* @author shaobing 2362017447@qq.com
* @date 2016年6月2日 下午3:15:45
* @version V1.0
*/
public class MqttClientService extends Service implements MqttCallback {
	private static final String TAG = "MqttClientService";
	private String MQTT_HOST = Constant.MQTT_HOST; //服务器URL
	private int MQTT_PORT = Constant.MQTT_PORT; //服务器URL
	private String DEFAULT_USER = Constant.MQTT_USER; //用户名
	private String DEFAULT_PASSWORD = Constant.MQTT_PASSWORD; //密码
	private static final String MQTT_URL_FORMAT = "tcp://%s:%d"; // 推送URL格式组装
	private static String TOPIC = "t/device/"; //默认的主题
	private static String clientId = "";	//客户端的clientID
	boolean isCleanSession = true;							//是否持久发送
    private static final String MQTT_SERVICE = "MqttClientService";
    private static final String ACTION_START = MQTT_SERVICE + ".START"; // 启动
    private static final String ACTION_STOP = MQTT_SERVICE + ".STOP"; // 停止
    private static final String ACTION_SUBSCRIBE = MQTT_SERVICE + ".SUBSCRIBE"; // 订阅
    private static final String ACTION_UNSUBSCRIBE = MQTT_SERVICE + ".UNSUBSCRIBE"; // 取消订阅
    private static final String ACTION_KEEPALIVE = MQTT_SERVICE + ".KEEPALIVE"; // 保持心跳闹钟使用
    private static final String ACTION_RECONNECT = MQTT_SERVICE + ".RECONNECT"; // 重新连接
	private static final int MQTT_CONNECTION_CHECK_INTERVAL = 60 * 1000; //检查连接状态，毫秒
	private static final String MQTT_THREAD_NAME = MQTT_SERVICE; // Handler Thread ID
	private static MqttClient mClient;		//MQTT客户端
	private ConnectivityManager mConnectivityManager;//网络改变接收器
	private Handler mMainHandler;  //设置心跳包子线程
	private boolean mCheckConnection = false;		//是否进行mqtt连接检查

    /**
     * 启动推送服务
     * @param ctx context to start the service with
     * @return void
     */
    public static void actionStart(Context ctx) {
		LogXmc.i(TAG, "actionStart");
        Intent i = new Intent(ctx, MqttClientService.class);
        i.setAction(ACTION_START);
        ctx.startService(i);
    }

    /**
     * 停止推送服务
     * @param ctx context to start the service with
     * @return void
     */
    public static void actionStop(Context ctx) {
        Intent i = new Intent(ctx, MqttClientService.class);
        i.setAction(ACTION_STOP);
        ctx.startService(i);
    }




    /**
     * 释放service资源
     * @param ctx
     */
    public static void actionrelease(Context ctx)
    {
        Intent i = new Intent(ctx, MqttClientService.class);
        ctx.stopService(i);
    }

    /**
     * 发送心跳包，在断网的过程中需要发送心跳包进行重新连接
     * @param ctx context to start the service with
     * @return void
     */
    public static void actionKeepalive(Context ctx) {
        Intent i = new Intent(ctx, MqttClientService.class);
        i.setAction(ACTION_KEEPALIVE);
        ctx.startService(i);
    }

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	@Override
	public void onCreate() {
		super.onCreate();
//		//实例化网络改变接收器
		clientId ="mqtt" + TestUtile.getAndroidOsSystemProperties("ro.serialno");
		mConnectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

		HandlerThread thread = new HandlerThread(MQTT_THREAD_NAME);
		thread.start();
		//实例化心跳包监听UI子线程
		mMainHandler = new Handler(thread.getLooper());

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (intent != null) {
			if (intent.getAction().equals(ACTION_START)) {
				start();
			} else if (intent.getAction().equals(ACTION_STOP)) {
				stop();
			}
		}
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		stopCheckConnection();
		stop();
		mMainHandler = null;
		mClient = null;
		mConnectivityManager = null;
		LogXmc.i(TAG, "onDestroy");
	}

	/**
	 * 尝试启动推送服务器，并注册网络改变接收器
	 */
//	private synchronized void start() {
//			LogUtil.i(TAG, "start");
//			stopCheckConnection();
//			startCheckConnection();
//			if(isNetworkAvailable())
//			{
//				connect();
//			}
//			else
//			{
//				Log.i(TAG, "网络断开");
//			}
//			registerReceiver(mConnectivityReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
//	}
	private synchronized void start() {
		mMainHandler.post(new Thread(){
			@Override
			public void run() {
				LogXmc.i(TAG, "start");
				stopCheckConnection();
				startCheckConnection();
				if(isNetworkAvailable())
				{
					connect();
				}
				else
				{
					Log.i(TAG, "网络断开");
				}
				registerReceiver(mConnectivityReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
			}
		});
	}

	/**
	 * 停止推送服务
	 */
	private void stop() {
			stopCheckConnection();

			if (mClient != null) {
				try {
					if (mClient.isConnected()) {
						mClient.disconnect();
					}
				} catch (MqttException e) {
				}
				mClient = null;
			unregisterReceiver(mConnectivityReceiver);
		}
	}

	/**
	 * 发送推送消息
	 * @param tmBean 推送消息bean
	 * */
	public static void publish(TestMqttBean tmBean){
		MqttMessage mqttMessage = new MqttMessage();
		mqttMessage.setPayload(tmBean.getMessage().getBytes());
		//是否在服务器中保存消息
		mqttMessage.setRetained(false);
		//消息质量
		mqttMessage.setQos(2);

		String topic = TOPIC + tmBean.getDevice();
		try {
//			String topic= "t/device/aaaa";
			mClient.publish(topic, mqttMessage);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送切换服务器推送
	 * */
	public static synchronized void publishSwitchServer(TestMqttBean testMqttBean){
		MqttMessage mqttMessage = new MqttMessage();
		mqttMessage.setPayload(testMqttBean.getMessage().getBytes());
		//是否在服务器中保存消息
		mqttMessage.setRetained(false);
		//消息质量
		mqttMessage.setQos(2);

		String topic = testMqttBean.getDevice();
		try {
			mClient.publish(topic, mqttMessage);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭测试功能
	 * @param command 指令
	 * */
	public static synchronized void publishCloseTest(String command, String device){
		MqttMessage message = new MqttMessage();
		message.setPayload(command.getBytes());
		//是否在服务器中保存消息
		message.setRetained(false);
		//消息质量
		message.setQos(2);

		String topic = TOPIC + device;
		try {
//			String topic= "t/device/aaaa";
			mClient.publish(topic, message);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 1.设置MQTT客户端对象
	 * 2.连接推送者
	 * 3.订阅相关主题信息
	 * @return true 代表连接成功  false 代表连接失败
	 */
	public  boolean connect()
	{
		//开启心跳包检测
		stopCheckConnection();
		startCheckConnection();
		try {
			if(mClient == null)//创建MQTT客户端对象
			{
				String url = String.format(MQTT_URL_FORMAT, MQTT_HOST, MQTT_PORT);
				mClient = new MqttClient(url, clientId, new MemoryPersistence());
			}
			boolean isSuccess=false;
			if(!mClient.isConnected())	//连接推送者
			{
					MqttConnectOptions options= new MqttConnectOptions();
					options.setCleanSession(isCleanSession);//mqtt receive offline message
					options.setKeepAliveInterval(5000);
					options.setPassword(DEFAULT_PASSWORD.toCharArray());
					options.setUserName(DEFAULT_USER);
					//连接broker
					mClient.connect(options);//CLIENT ID CAN NOT BE SAME
					isSuccess=true;
					if(!isSuccess)
					{
						String message="连接失败,请检查client id是否重复了 或者activeMQ是否启动";
						//System.out.println(message);
						LogXmc.i(TAG, message);
						return false;
					}
					else
					{
						LogXmc.i("aaaaaaaaaaaaaaaa", TOPIC);
						//订阅相关的主题信息
//						mClient.subscribe(new String[]{"t/device/aaaa"});
						mClient.subscribe(new String[]{TOPIC+TestUtile.getLocalMacAddress(this).replace(":", "")});
						//设置添加接口回调
						mClient.setCallback(this);
						String message="连接成功";
						//System.out.println(message);
						LogXmc.i(TAG, message);
					}
			}
		}
		catch (MqttException e)
		{
            LogXmc.e(TAG,"mClient.connect1 :"+ e.toString());
			return false;
		}
		catch (Exception e) {
            LogXmc.e(TAG, "mClient.connect2 :"+e.toString());
			return false;
		}
		return true;
	}

	/**
	 * 重新连接如果他是必须的
	 */
	private synchronized void reconnectIfNecessary() {
	{
			if (!isConnected())
			{
				connect();
			} else {
                LogXmc.i(TAG, "已经连接");
			}
		}
	}

	/**
	 * 取消检查连接状态
	 */
	private void stopCheckConnection() {
		mCheckConnection = false;
		if (mMainHandler != null) {
			try {
				mMainHandler.removeCallbacksAndMessages(null);
			} catch (Exception e) {
                LogXmc.i(TAG,"removeCallbacksAndMessages Error:" +e.toString());
			}
		}
	}
	/**
	 * 启动检查连接状态
	 */
	private void startCheckConnection() {
		mCheckConnection = true;
		if (mMainHandler == null) {
			return;
		}
		mMainHandler.postDelayed(new Thread() {
			@Override
			public void run() {
				try {
                    LogXmc.i(TAG, "心跳包");
					if (isNetworkAvailable()) {
						reconnectIfNecessary();
					}
					if (mCheckConnection) {
						mMainHandler.postDelayed(this, MQTT_CONNECTION_CHECK_INTERVAL);
					}
				} catch (Exception e) {
                    LogXmc.i(TAG, "postDelayed MQTT_CONNECTION_CHECK_INTERVAL Error:"+ e.getMessage());
				}
			}
		}, MQTT_CONNECTION_CHECK_INTERVAL);
	}

	/**
	 * 判断推送服务是否连接
	 *
	 * @return 如果是连接的则返回true反之false
	 */
	private boolean isConnected() {
		if(mClient == null) {
			return false;
		}
		else {
			if(mClient.isConnected()) {
				return true;
			}
			else {
				return false;
			}
		}
	}
	/**
	 * 通过ConnectivityManager查询网络连接状态
	 *
	 * @return 如果网络状态正常则返回true反之flase
	 */
	private boolean isNetworkAvailable() {
		NetworkInfo info = mConnectivityManager.getActiveNetworkInfo();
		return (info == null) ? false : info.isConnected() && info.isAvailable();
	}


	/**
	 * 网络状态发生变化接收器
	 */
	private final BroadcastReceiver mConnectivityReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (isNetworkAvailable())
			{
                LogXmc.i(TAG, "网络连接发生了变化--网络连接");
				reconnectIfNecessary();
			}
			else
			{
                LogXmc.i(TAG, "网络连接发生了变化--网络断开");
			}
		}
	};
	@Override
	public void connectionLost(Throwable throwable) {
		Log.i(TAG, "connectionLost:"+throwable.toString());
		if(isNetworkAvailable())
		{
			connect();
		}
	}

	@Override
	public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        LogXmc.i(TAG,"收到的消息 ："+mqttMessage.toString());
			try {
//				MqttHandleUtile.handlerNotification(this, PersonCenterApplication.getInstance().getActivity(), mqttMessage.toString());
			} catch (Exception e) {
				// TODO: handle exception
                LogXmc.i(TAG,"handlerNotification :  "+getClass()+e.fillInStackTrace().toString());
			}
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
		LogXmc.i(iMqttDeliveryToken.isComplete());

		String s = iMqttDeliveryToken.isComplete() ? getString(R.string.send_successfully): getString(R.string.send_error);
		ToastUtil.showShortToast(getApplicationContext(), s);
	}
}
