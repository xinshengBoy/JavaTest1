package com.just.test.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.just.test.R;
import com.just.test.tools.CommonAdapter;
import com.just.test.tools.JSONDateKey;
import com.just.test.tools.ViewHolder;
import com.just.test.widget.MyActionBar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * json数组解析
 * jsonArraylist解析，及listview的头部悬停效果，
 * 注：listview的悬停为了达到最佳效果，最好给listview加两个标题头，第二个标题头是最主要的，还有最好第二个标题头的背景颜色与listview里面的背景颜色一致，这样看起来效果会更好
 * @author user
 *
 */
public class JsonTest extends Activity {

	private ListView list_json;
	private String source = "[{'createTime':'2016-03-04 13:23:33','docURL':'http://mobile.sseinfo.com/options/course/optioneasy/c/4052755.shtml','docTitle':'小专研读：趣谈希腊字母（六）'},"+
			"{'createTime':'2016-03-04 13:22:35','docURL':'http://mobile.sseinfo.com/options/course/optioneasy/c/4052754.shtml','docTitle':'小新起步：白话股票期权之大火篇'},"+
			"{'createTime':'2016-03-04 13:19:18','docURL':'http://mobile.sseinfo.com/options/course/optioneasy/c/4052753.shtml','docTitle':'期权不倒问0304'},"+
			"{'createTime':'2016-03-03 13:18:03','docURL':'http://mobile.sseinfo.com/options/course/optioneasy/c/4052752.shtml','docTitle':'小专研读：趣谈希腊字母（五）'},"+
			"{'createTime':'2016-03-03 13:16:46','docURL':'http://mobile.sseinfo.com/options/course/optioneasy/c/4052745.shtml','docTitle':'小新起步：白话股票期权之地铁篇'},"+
			"{'createTime':'2016-03-03 13:05:51','docURL':'http://mobile.sseinfo.com/options/course/optioneasy/c/4052744.shtml','docTitle':'期权不倒问0303'},"+
			"{'createTime':'2016-03-02 08:34:50','docURL':'http://mobile.sseinfo.com/options/course/optioneasy/c/4052703.shtml','docTitle':'小专研读：趣谈希腊字母（四）'},"+
			"{'createTime':'2016-03-02 08:33:07','docURL':'http://mobile.sseinfo.com/options/course/optioneasy/c/4052702.shtml','docTitle':'小新起步：期权的合约设计'},"+
			"{'createTime':'2016-03-02 08:25:32','docURL':'http://mobile.sseinfo.com/options/course/optioneasy/c/4052701.shtml','docTitle':'期权不倒”问“0302'},"+
			"{'createTime':'2016-03-01 12:14:24','docURL':'http://mobile.sseinfo.com/options/course/optioneasy/c/4052339.shtml','docTitle':'小专研读：趣谈希腊字母（三）'},"+
			"{'createTime':'2016-03-01 12:13:19','docURL':'http://mobile.sseinfo.com/options/course/optioneasy/c/4052338.shtml','docTitle':'小新起步：期权的合约要素'},"+
			"{'createTime':'2016-03-01 12:11:43','docURL':'http://mobile.sseinfo.com/options/course/optioneasy/c/4052337.shtml','docTitle':'期权不倒”问“0301'},"+
			"{'createTime':'2016-02-29 13:53:42','docURL':'http://mobile.sseinfo.com/options/course/optioneasy/c/4053516.shtml','docTitle':'小专研读：长江金工刘胜利-二元期权早知道'},"+
			"{'createTime':'2016-02-29 13:46:57','docURL':'http://mobile.sseinfo.com/options/course/optioneasy/c/4053515.shtml','docTitle':'小新起步：长江金工刘胜利-VIX指数编制'},"+
			"{'createTime':'2016-02-29 11:43:29','docURL':'http://mobile.sseinfo.com/options/course/optioneasy/c/4052335.shtml','docTitle':'期权不倒问0229'},"+
			"{'createTime':'2016-02-26 15:33:02','docURL':'http://mobile.sseinfo.com/options/course/optioneasy/c/4048923.shtml','docTitle':'小专研读：趣谈希腊字母（二）'},"+
			"{'createTime':'2016-02-26 15:31:19','docURL':'http://mobile.sseinfo.com/options/course/optioneasy/c/4048922.shtml','docTitle':'小新起步：期权策略的精确制导'},"+
			"{'createTime':'2016-02-26 15:29:23','docURL':'http://mobile.sseinfo.com/options/course/optioneasy/c/4048921.shtml','docTitle':'期权不倒”问“0226'},"+
			"{'createTime':'2016-02-25 15:28:20','docURL':'http://mobile.sseinfo.com/options/course/optioneasy/c/4048920.shtml','docTitle':'小专研读：趣谈希腊字母（一）'},"+
			"{'createTime':'2016-02-25 15:26:10','docURL':'http://mobile.sseinfo.com/options/course/optioneasy/c/4048919.shtml','docTitle':'小新起步：期权策略的多样性'}]";
	private ArrayList<Bundle> jsonData;
	private LinearLayout invis;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jsontest);
		//// TODO: 2016/12/21 actionbar
		LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
		Bundle bundle = new Bundle();
		bundle.putBoolean("back", true);
		bundle.putString("leftText", null);
		bundle.putString("title", "json数组解析");
		bundle.putBoolean("rightImage", false);
		bundle.putString("rightText", null);
		MyActionBar.actionbar(this,headerLayout,bundle);

		//listview加头部
		getData();
		invis = (LinearLayout) findViewById(R.id.invis);
		list_json = (ListView) findViewById(R.id.list_json);
		View header = View.inflate(this, R.layout.stick_header, null);
		list_json.addHeaderView(header);//头部内容
		//ListView条目中的悬浮部分 添加到头部
		list_json.addHeaderView(View.inflate(this, R.layout.json_action, null));
		JsonAdapter adapter = new JsonAdapter(getApplicationContext(),jsonData ,R.layout.item_json);
		list_json.setAdapter(adapter);
		list_json.setOverScrollMode(View.OVER_SCROLL_NEVER);
		list_json.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(jsonData.get(position).getString(JSONDateKey.DOCURL)));
				intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
				startActivity(intent);
			}
		});
		//listview加尾部
//		View fonterView = getLayoutInflater().inflate(R.layout.json_headview, null);
//		fonterView.setEnabled(false);
//		list_json.addFooterView(fonterView);

		list_json.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
								 int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				if (firstVisibleItem >= 1) {
					invis.setVisibility(View.VISIBLE);
				} else {
					invis.setVisibility(View.GONE);
				}
			}
		});
	}

	private void getData() {

		JSONArray jsonArray;
		try {
			jsonData = new ArrayList<Bundle>();
			jsonArray = new JSONArray(source);
			for (int i = 0; i < jsonArray.length(); i++) {
				Bundle bundle = new Bundle();
				JSONObject object = jsonArray.getJSONObject(i);
				bundle.putString(JSONDateKey.DOCTITLE, object.getString(JSONDateKey.DOCTITLE));
				bundle.putString(JSONDateKey.CREATETIME, object.getString(JSONDateKey.CREATETIME));
				bundle.putString(JSONDateKey.DOCURL, object.getString(JSONDateKey.DOCURL));
				jsonData.add(bundle);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}

class JsonAdapter extends CommonAdapter<Bundle>{

	public JsonAdapter(Context context, List<Bundle> mDatas, int itemLayoutId) {
		super(context, mDatas, itemLayoutId);
	}

	@Override
	public void convert(ViewHolder helper, Bundle item) {
		TextView txt_jsonContent = helper.getView(R.id.txt_jsonContent);
		TextView txt_jsonTime = helper.getView(R.id.txt_jsonTime);

		txt_jsonContent.setText(item.getString(JSONDateKey.DOCTITLE));
		txt_jsonTime.setText(item.getString(JSONDateKey.CREATETIME));
	}

}
