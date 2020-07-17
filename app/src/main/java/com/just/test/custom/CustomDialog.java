package com.just.test.custom;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.just.test.R;

public class CustomDialog extends Dialog{

	private Context mContext;
	private TextView txt_mycustomdialog_message;
	private Button btn_mycustomdialog_cancle,btn_mycustomdialog_ok;

	public CustomDialog(Context context) {
		super(context, R.style.mycustomdialog);
		this.mContext = context;

		View layout = LayoutInflater.from(mContext).inflate(R.layout.layout_my_customdialog, null);
		txt_mycustomdialog_message = (TextView)layout.findViewById(R.id.txt_mycustomdialog_message);
		btn_mycustomdialog_cancle = (Button)layout.findViewById(R.id.btn_mycustomdialog_cancle);
		btn_mycustomdialog_ok = (Button)layout.findViewById(R.id.btn_mycustomdialog_ok);
		setContentView(layout);
	}


	public void setMessage(String message){
		txt_mycustomdialog_message.setText(message);
	}

	//确定按钮监听
	public void setPositiveButton(String positiveText,View.OnClickListener listener){
		btn_mycustomdialog_ok.setText(positiveText);
		btn_mycustomdialog_ok.setOnClickListener(listener);
	}

	//取消按钮监听
	public void setNegativeButton(String negativeText,View.OnClickListener listener){
		btn_mycustomdialog_cancle.setText(negativeText);
		btn_mycustomdialog_cancle.setOnClickListener(listener);
	}

}
