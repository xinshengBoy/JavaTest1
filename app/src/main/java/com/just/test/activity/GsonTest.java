package com.just.test.activity;

import java.util.ArrayList;
import java.util.List;

import com.just.test.R;
import com.just.test.bean.Video;
import com.just.test.tools.CommonAdapter;
import com.just.test.tools.ViewHolder;
import com.just.test.widget.MyActionBar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
/**
 * gson解析
 * @author user
 *
 */
public class GsonTest extends Activity {
	private ListView list_gson;
	private List<Video> jsonData = new ArrayList<Video>();
	private String mySource = "[{'createTime':'2016-03-04 13:23:33','docURL':'http://mobile.sseinfo.com/options/course/optioneasy/c/4052755.shtml','docTitle':'小专研读：趣谈希腊字母（六）'},"+
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
	private String source = "{'createTime':'今天','docURL':'www.baidu.com','docTitle':'国足又输了'}";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gsontest);
		//// TODO: 2016/12/21 actionbar
		LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
		Bundle bundle = new Bundle();
		bundle.putBoolean("back", true);
		bundle.putString("leftText", null);
		bundle.putString("title", "GSON解析");
		bundle.putBoolean("rightImage", false);
		bundle.putString("rightText", null);
		MyActionBar.actionbar(this,headerLayout,bundle);

		getData();
		list_gson = (ListView) findViewById(R.id.list_gson);
		JsonObjectAdapter adapter = new JsonObjectAdapter(GsonTest.this,jsonData ,R.layout.item_json);
		list_gson.setAdapter(adapter);
		list_gson.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(jsonData.get(position).getDocUrl()));
				intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
				startActivity(intent);
			}
		});
		//JSON字符串只包含一个JSON对象
		TextView txt_gsonTest2 = (TextView)findViewById(R.id.txt_gsonTest2);
		Gson gson = new Gson();
		Video video = gson.fromJson(source, Video.class);
		txt_gsonTest2.setText("标题："+video.getDocTitle()+"\n"
				+"url:"+video.getDocUrl()+"\n"
				+"时间："+video.getCreateTime());
	}

	private void getData() {
		Gson gson = new Gson();
		jsonData = gson.fromJson(mySource, new TypeToken<List<Video>>(){}.getType());
	}

}

class JsonObjectAdapter extends CommonAdapter<Video>{

	public JsonObjectAdapter(Context context, List<Video> mDatas, int itemLayoutId) {
		super(context, mDatas, itemLayoutId);
	}

	@Override
	public void convert(ViewHolder helper, Video item) {
		TextView txt_jsonContent = helper.getView(R.id.txt_jsonContent);
		TextView txt_jsonTime = helper.getView(R.id.txt_jsonTime);

		txt_jsonContent.setText(item.getDocTitle());
		txt_jsonTime.setText(item.getCreateTime());
	}

}
