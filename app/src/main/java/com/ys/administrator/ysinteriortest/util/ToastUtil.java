package com.ys.administrator.ysinteriortest.util;

import android.content.Context;
import android.widget.Toast;

/**
 * description:	Toast打印帮助类
 * User: amos
 * Date: 2016/6/14
 * Time: 16:41
 */
public class ToastUtil {
	private static MyToast mToast = null;

	public static void showLongToast(Context context, int id){
		if(null != context){
			if(null == mToast){
				mToast = MyToast.makeText(context, id, Toast.LENGTH_LONG);
			}else{
				mToast.setText(id);
			}	
			mToast.show();
		}
	}
	
	public static void showLongToast(Context context, String content){
		if(null != context){
			if(null == mToast){
				mToast = MyToast.makeText(context,content, Toast.LENGTH_LONG);
			}else{
				mToast.setText(content);
			}	
			mToast.show();
		}
	}

	public static void showShortToast(Context context, int id){
		if(null != context){
			if(null == mToast){
				mToast = MyToast.makeText(context, id, Toast.LENGTH_SHORT);
			}else{
				mToast.setText(id);
			}	
			mToast.show();
		}
	}
	
	public static void showShortToast(Context context, String content){
		if(null != context){
			if(null == mToast){
				mToast = MyToast.makeText(context,content, Toast.LENGTH_SHORT);
			}else{
				mToast.setText(content);
			}	
			mToast.show();
		}
	}

	public static void toastPrompt(Context context, int id, int time){
		if(null != context){
			if(null == mToast){
				mToast = MyToast.makeText(context, id, time);
			}else{
				mToast.setText(id);
			}	
			mToast.show();
		}
	}
	

	
	public static void cancelToast(){
		if(null != mToast){
			mToast.cancel();
		}
	}
}
