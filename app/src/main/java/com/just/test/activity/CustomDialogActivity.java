package com.just.test.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.just.test.R;
import com.just.test.tools.CommonAdapter;
import com.just.test.tools.ViewHolder;
import com.just.test.widget.MyActionBar;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义对话框
 */
public class CustomDialogActivity extends Activity implements OnClickListener{

	private Button btn_customDialog1,btn_customDialog2,btn_customDialog3,btn_customDialog4,btn_customDialog5;
	private List<String> mList = new ArrayList<>();
	private int positions;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom_dialog_layout);

		//// TODO: 2016/12/21 actionbar
		LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
		Bundle bundle = new Bundle();
		bundle.putBoolean("back", true);
		bundle.putString("leftText", null);
		bundle.putString("title", "自定义对话框");
		bundle.putBoolean("rightImage", false);
		bundle.putString("rightText", null);
		MyActionBar.actionbar(this,headerLayout,bundle);

		btn_customDialog1 = (Button) findViewById(R.id.btn_customDialog1);
		btn_customDialog2 = (Button) findViewById(R.id.btn_customDialog2);
		btn_customDialog3 = (Button) findViewById(R.id.btn_customDialog3);
		btn_customDialog4 = (Button) findViewById(R.id.btn_customDialog4);
		btn_customDialog5 = (Button) findViewById(R.id.btn_customDialog5);
		btn_customDialog1.setOnClickListener(this);
		btn_customDialog2.setOnClickListener(this);
		btn_customDialog3.setOnClickListener(this);
		btn_customDialog4.setOnClickListener(this);
		btn_customDialog5.setOnClickListener(this);
		initDate();
	}

	@Override
	public void onClick(View view) {
		if (view == btn_customDialog1){
			TwoButtonDialog(CustomDialogActivity.this);
		}else if (view == btn_customDialog2){
			ThreeButtonDialog(CustomDialogActivity.this);
		}else if (view == btn_customDialog3){
			InputCustomDialog(CustomDialogActivity.this);
		}else if (view == btn_customDialog4){
			ProgressDialog(CustomDialogActivity.this);
		}else if (view == btn_customDialog5){
			ListViewCustomDialog(CustomDialogActivity.this);
		}
	}
	//两个按钮对话框
	private void TwoButtonDialog(final Context context){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setIcon(R.drawable.sign_star2);
		builder.setTitle("通知");
		builder.setMessage("这个还不错，要不要来一份呐？");
		builder.setNegativeButton("不要了", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				Toast.makeText(context,"不要了",Toast.LENGTH_SHORT).show();
			}
		});
		builder.setPositiveButton("好的", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				Toast.makeText(context,"好的",Toast.LENGTH_SHORT).show();
			}
		});
		builder.create().show();
	}
	//三个按钮对话框
	private void ThreeButtonDialog(final Context context){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setIcon(R.drawable.sign_star2);
		builder.setTitle("通知");
		builder.setMessage("请对本次服务做出评价");
		builder.setPositiveButton("挺好", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				Toast.makeText(context,"挺好",Toast.LENGTH_SHORT).show();
			}
		});
		builder.setNegativeButton("很差", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				Toast.makeText(context,"很差",Toast.LENGTH_SHORT).show();
			}
		});
		builder.setNeutralButton("一般", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				Toast.makeText(context,"一般",Toast.LENGTH_SHORT).show();
			}
		});
		builder.create().show();
	}
	//输入对话框
	private void InputCustomDialog(final Context context){
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.layout_customdialog_item,null);
		final EditText name = (EditText) view.findViewById(R.id.et_customdialog_name);
		final EditText pass = (EditText) view.findViewById(R.id.et_customdialog_pass);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setIcon(R.drawable.sign_star2);
		builder.setTitle("登录");
		builder.setView(view);
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				Toast.makeText(context,"取消",Toast.LENGTH_SHORT).show();
			}
		});
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				Toast.makeText(context,"确定：用户名："+name.getText().toString()+"\n"+"密码："+pass.getText().toString(),Toast.LENGTH_SHORT).show();
			}
		});
		builder.create().show();
	}
	//进度对话框
	private void ProgressDialog(Context context){
		ProgressDialog dialog = new ProgressDialog(context);
		dialog.setIcon(R.drawable.sign_star2);
		dialog.setTitle("更新进度");
		dialog.setMessage("更新中，请稍候");
		dialog.setCanceledOnTouchOutside(true);//点击其他地方会消失
		dialog.show();
	}

	//输入对话框
	private void ListViewCustomDialog(final Context context){
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.layout_customdialog_item2,null);
		final ListView listview_customDialog = (ListView) view.findViewById(R.id.listview_customDialog);
		ListViewDialogAdapter adapter = new ListViewDialogAdapter(this,mList,R.layout.layout_listview_dialog_item);
		listview_customDialog.setAdapter(adapter);
		listview_customDialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
				positions = position;
			}
		});
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setIcon(R.drawable.sign_star2);
		builder.setTitle("温馨提示");
		builder.setView(view);
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				Toast.makeText(context,"点击了取消",Toast.LENGTH_SHORT).show();
			}
		});
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				Toast.makeText(context,"点击了"+mList.get(positions),Toast.LENGTH_SHORT).show();
			}
		});
		builder.show();
	}

	private void initDate(){
		for (int i=1;i<50;i++){
			mList.add("中国式关系第"+i+"集");
		}
	}

	private class ListViewDialogAdapter extends CommonAdapter<String>{

		public ListViewDialogAdapter(Context context, List<String> mDatas, int itemLayoutId) {
			super(context, mDatas, itemLayoutId);
		}

		@Override
		public void convert(ViewHolder helper, String item) {
			TextView textView = helper.getView(R.id.txt_listview_dialog);
			textView.setText(item);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mList = null;
	}
}
