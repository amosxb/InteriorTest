package com.ys.administrator.ysinteriortest.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ys.administrator.ysinteriortest.R;

/** 
* @ClassName: CollectNewDialog 
* @Description:自定义收藏目录中新建文件夹的dialog 
* @author amos
* @date 2016年3月10日 上午11:50:05 
* @version V1.0 
*/
public class OkDialog extends Dialog  implements View.OnClickListener
{
     public OkDialog(Context context, int themeResId) {
		super(context, R.style.CustomDialog);
		// TODO Auto-generated constructor stub
	}


	private TextView collect_dialog_title;
    private ImageView collect_dialog_ok;
	private ImageView collect_dialog_cancel;



        private String name;
        private OnDialogClickListerner listerner;
        EditText etName;

        public OkDialog(Context context, String name) {
    		super(context, R.style.CustomDialog);
                this.name = name;
        }
        
        public void setOnDialogButtonClickListerner( OnDialogClickListerner listerner)
        {
            this.listerner = listerner;
        }
        
        @Override
        protected void onCreate(Bundle savedInstanceState) { 
                super.onCreate(savedInstanceState);
                setContentView(R.layout.collect_ok_dialog);
                this.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失

				Window dialogWindow = this.getWindow();
				WindowManager.LayoutParams lp = dialogWindow.getAttributes();
				dialogWindow.setGravity(Gravity.TOP);
				lp.y =288; // 新位置X坐标
				dialogWindow.setAttributes(lp);
                collect_dialog_title = (TextView) findViewById(R.id.collect_dialog_title);
                collect_dialog_title.setText(name);
                                
                collect_dialog_ok = (ImageView) findViewById(R.id.collect_dialog_ok);
                collect_dialog_ok.setOnClickListener(this);
                
                collect_dialog_cancel = (ImageView) findViewById(R.id.collect_dialog_cancel);
                collect_dialog_cancel.setOnClickListener(this);

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
    					listerner.onDialogOkClick(null);
    				}
    				this.dismiss();
    				break;
    			}
    			case R.id.collect_dialog_cancel:
    			{
       				if(listerner != null)
    				{
    					listerner.onDialogCancelClick();
    				}
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
	public interface OnDialogClickListerner
	{
		//监听OK按键
		void onDialogOkClick(String message);
		//监听Cancel按键
		void onDialogCancelClick();

	}
    	
        
//        private View.OnClickListener clickListener = new View.OnClickListener() {
//                
//                @Override
//                public void onClick(View v) {
//                        customDialogListener.back(String.valueOf(etName.getText()));
//                    MyCustomDialog.this.dismiss();
//                }
//        };

}