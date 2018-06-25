package com.ys.administrator.ysinteriortest.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ys.administrator.ysinteriortest.R;
import com.ys.administrator.ysinteriortest.util.InputMethodUtil;

/** 
* @ClassName: CollectNewDialog 
* @Description:自定义收藏目录中新建文件夹的dialog 
* @author shaobing  
* @date 2016年3月10日 上午11:50:05 
* @version V1.0 
*/
public class NewDialog extends Dialog  implements View.OnClickListener
{
     public NewDialog(Context context, int themeResId) {
		super(context, R.style.CustomDialog);
		// TODO Auto-generated constructor stub
	}

	private Context context;
	private TextView collect_dialog_title;

    private EditText dialog_wifi_password;
	private EditText dialog_wifi_name;

    private Button collect_dialog_ok;

	private Button collect_dialog_cancel;

    private String name;
    private String filename ="";

    private OnDialogButtonClickListerner listerner;

    public NewDialog(Context context, String name)
    {
    		super(context, R.style.CustomDialog);
                this.name = name;
				this.context =context;
    }
    public NewDialog setOnDialogButtonClickListerner(OnDialogButtonClickListerner listerner)
    {
    	this.listerner = listerner;
		return this;
    }
    
    public void setfilename(String filename )
    {
    	this.filename = filename;
    }

        @Override
        protected void onCreate(Bundle savedInstanceState) { 
                super.onCreate(savedInstanceState);
                setContentView(R.layout.collect_new_dialog);
                this.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
				Window dialogWindow = this.getWindow();
				WindowManager.LayoutParams lp = dialogWindow.getAttributes();
				dialogWindow.setGravity(Gravity.TOP);
				lp.y =114; // 新位置X坐标
				dialogWindow.setAttributes(lp);
                collect_dialog_title = (TextView) findViewById(R.id.collect_dialog_title);
                collect_dialog_title.setText(name);

				dialog_wifi_name = (EditText) findViewById(R.id.dialog_wifi_name);
				dialog_wifi_password = (EditText) findViewById(R.id.dialog_wifi_password);
				dialog_wifi_name.setFocusableInTouchMode(true);
				dialog_wifi_name.requestFocus();

				collect_dialog_ok = (Button) findViewById(R.id.collect_dialog_ok);
                collect_dialog_ok.setOnClickListener(this);
				collect_dialog_ok.setEnabled(true);
				collect_dialog_ok.setTextColor(0xFF6C6C6C);
				collect_dialog_cancel = (Button) findViewById(R.id.collect_dialog_cancel);
                collect_dialog_cancel.setOnClickListener(this);
			    collect_dialog_cancel.setTextColor(0xFF6C6C6C);}



	@Override
	public void show()
	{
		super.show();

	}

	public NewDialog setWifiName(String wifiName){
		if(!TextUtils.isEmpty(wifiName))
		{
			dialog_wifi_name.setText(wifiName);
		}
		return this;
	}


	public  EditText getEditView()
	{
		return  dialog_wifi_name;
	}

	@Override
    	public void onClick(View view)
    	{
    		switch (view.getId()) 
    		{
			case R.id.collect_dialog_ok:
			{
				if(listerner != null)
				{
					String name = dialog_wifi_name.getText().toString();
					String password = dialog_wifi_password.getText().toString();
					listerner.onDialogOkClick(name,password);
				}
				InputMethodUtil.hideSoftInput(dialog_wifi_name);
				this.dismiss();
				break;
			}
			case R.id.collect_dialog_cancel:
			{
   				if(listerner != null)
				{
					listerner.onDialogCancelClick();
				}
				InputMethodUtil.hideSoftInput(dialog_wifi_name);

				this.dismiss();
				break;
			}

    		default:
    			break;
    		}
    		
    	}

	/**
	 * @ClassName: OnDialogButtonClickListerner
	 * @Description: 对自定义对话框中的按键进行监听
	 * @author shaobing
	 * @date 2016年3月10日 下午3:15:49
	 * @version V1.0
	 */
	public interface OnDialogButtonClickListerner
	{
		//监听OK按键
		void onDialogOkClick(String name,String password);
		//监听Cancel按键
		void onDialogCancelClick();

	}

}