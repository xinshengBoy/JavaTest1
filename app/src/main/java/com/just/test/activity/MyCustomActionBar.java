package com.just.test.activity;

import java.util.ArrayList;
import java.util.List;

import com.just.test.R;
import com.just.test.custom.CustomDialog;
import com.just.test.custom.CustonDeleteListView;
import com.just.test.custom.CustonDeleteListView.OnDeleteListener;
import com.just.test.custom.TitleView;
import com.just.test.tools.CommonAdapter;
import com.just.test.tools.ViewHolder;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyCustomActionBar extends Activity {

	private TitleView view_titleBar;
	private CustonDeleteListView cuostom_deletelist;
	private List<Bundle> mList = new ArrayList<>();
	private CustomDeleteAdapter adapter;
	private ImageView iv_mycustomdialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_mycustionactionbar);

		//标题头
		view_titleBar = (TitleView)findViewById(R.id.view_titleBar);
		//设置标题头
		view_titleBar.setTitleText("清纯美女写真");
		//左边按钮
		view_titleBar.setLeftButtonOnClicker(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MyCustomActionBar.this.finish();
			}
		});
		//右侧按钮
		view_titleBar.setRightButtonOnClicker(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(MyCustomActionBar.this, "哇，美女耶", Toast.LENGTH_LONG).show();
			}
		});
		//自定义对话框
		iv_mycustomdialog = (ImageView) findViewById(R.id.iv_mycustomdialog);
		iv_mycustomdialog.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final CustomDialog dialog = new CustomDialog(MyCustomActionBar.this);
				dialog.getWindow().setLayout(800, 500);
				dialog.setMessage("点击查看更多高清美女图片");
				dialog.setNegativeButton("取消", new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
				dialog.setPositiveButton("前往", new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.dismiss();
						Toast.makeText(MyCustomActionBar.this, "果然好多美女", Toast.LENGTH_SHORT).show();
					}
				});
				dialog.show();
			}
		});



		getDate();
		cuostom_deletelist = (CustonDeleteListView)findViewById(R.id.cuostom_deletelist);
		cuostom_deletelist.setOnDeleteLister(new OnDeleteListener() {

			@Override
			public void onDelete(int index) {
				mList.remove(index);
				adapter.notifyDataSetChanged();
			}
		});
		adapter = new CustomDeleteAdapter(MyCustomActionBar.this,mList,R.layout.item_json);
		cuostom_deletelist.setAdapter(adapter);


	}

	private void getDate() {
		for (int i = 1; i <= 30; i++) {
			Bundle bundle = new Bundle();
			bundle.putString("count", "第"+i+"集");
			bundle.putString("title", "天龙八部第"+i+"式");
			mList.add(bundle);
		}
	}
	class CustomDeleteAdapter extends CommonAdapter<Bundle>{

		public CustomDeleteAdapter(Context context, List<Bundle> mDatas,
								   int itemLayoutId) {
			super(context, mDatas, itemLayoutId);
		}

		@Override
		public void convert(ViewHolder helper, Bundle item) {
			TextView txt_jsonContent = helper.getView(R.id.txt_jsonContent);
			TextView txt_jsonTime = helper.getView(R.id.txt_jsonTime);

			txt_jsonContent.setText(item.getString("title"));
			txt_jsonTime.setText(item.getString("count"));
		}

	}

	@Override
	public void onBackPressed() {
		if (cuostom_deletelist.isDeleteBtnShow()) {
			cuostom_deletelist.hideDelete();
			return;
		}
		super.onBackPressed();
	}
}
