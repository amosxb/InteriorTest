package com.ys.administrator.ysinteriortest.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ys.administrator.ysinteriortest.R;


/**
 *  custom toast view display in center of srceen
 */
public class MyToast extends Toast{
	private static MyToast mToast;
	//private ImageView image;
	private TextView mTextView;

	private MyToast(Context context) {
		super(context);
		View layout = LayoutInflater.from(context).inflate(R.layout.toast_custom_view, null);
		//image = (ImageView) layout.findViewById(R.id.toast_image);  
        //image.setImageResource(R.drawable.head);  
		mTextView = (TextView) layout.findViewById(R.id.toast_text);
		//setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		setView(layout);
	}

	private MyToast(Context context, String str) {
		super(context);
		View layout = LayoutInflater.from(context).inflate(R.layout.toast_custom_view, null);
		mTextView = (TextView) layout.findViewById(R.id.toast_text);  
		//setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		setText(str);
		setView(layout);
	}
	
	private MyToast(Context context, String str, int duration) {
		super(context);
		View layout = LayoutInflater.from(context).inflate(R.layout.toast_custom_view, null);
		mTextView = (TextView) layout.findViewById(R.id.toast_text);  
		//setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		setText(str);
		setDuration(duration);
		setView(layout);
	}
	
	private MyToast(Context context, int resId) {
		super(context);
		View layout = LayoutInflater.from(context).inflate(R.layout.toast_custom_view, null);
		mTextView = (TextView) layout.findViewById(R.id.toast_text);  
		//setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		setText(resId);
		setView(layout);
	}
	
	private MyToast(Context context, int resId, int duration) {
		super(context);
		View layout = LayoutInflater.from(context).inflate(R.layout.toast_custom_view, null);
		mTextView = (TextView) layout.findViewById(R.id.toast_text);  
		//setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		setText(resId);
		setDuration(duration);
		setView(layout);
	}
	
	public static MyToast makeText(Context ctx, String str) {
		if(null == mToast) {
			mToast = new MyToast(ctx, str);
		}else {
			mToast.setText(str);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		return mToast;
	}
	
	public static MyToast makeText(Context ctx, String str, int duration) {
		if(null == mToast) {
			mToast = new MyToast(ctx, str, duration);
		}else {
			mToast.setText(str);
			mToast.setDuration(duration);
		}
		return mToast;
	}
	
	public static MyToast makeText(Context ctx, int resId) {
		if(null == mToast) {
			mToast = new MyToast(ctx, resId);
		}else {
			mToast.setText(resId);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		return mToast;
	}
	
	public static MyToast makeText(Context ctx, int resId, int duration) {
		if(null == mToast) {
			mToast = new MyToast(ctx, resId, duration);
		}else {
			mToast.setText(resId);
			mToast.setDuration(duration);
		}
		return mToast;
	}

	public static void showToast(Context ctx, String str) {
		makeText(ctx, str);
		mToast.show();
	}

	public static void showToast(Context ctx, String str, int duration) {
		makeText(ctx, str, duration);
		mToast.show();
	}

	public static void showToast(Context ctx, int resId) {
		makeText(ctx, resId);
		mToast.show();
	}

	public static void showToast(Context ctx, int resId, int duration) {
		makeText(ctx, resId, duration);
		mToast.show();
	}

	public void setText(String str) {
		mTextView.setText(str);
	}
	
	public void setText(int resId) {
		mTextView.setText(resId);
	}
}
