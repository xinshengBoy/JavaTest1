package com.just.test.activity;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.bean.Sentence;
import com.just.test.tools.VerticalScrollTextView;
import com.just.test.widget.MyActionBar;

import java.util.ArrayList;
import java.util.List;

/**
 * textview相关，如换行，下划线，滚动等等
 * @author user
 *
 */
public class AboutTextView extends Activity {
	private VerticalScrollTextView txt_verticalScrollTextview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_textview_layout);

		//// TODO: 2016/12/21 actionbar
		LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
		Bundle bundle = new Bundle();
		bundle.putBoolean("back", true);
		bundle.putString("leftText", null);
		bundle.putString("title", "textview相关");
		bundle.putBoolean("rightImage", false);
		bundle.putString("rightText", null);
		MyActionBar.actionbar(this,headerLayout,bundle);

		//下划线示例1
		TextView txt_textview_underline1 = (TextView) findViewById(R.id.txt_textview_underline1);
		txt_textview_underline1.setText(Html.fromHtml("<u>"+"下划线示例1"+"</u>"));
		//下划线示例2
		TextView txt_textview_underline2 = (TextView) findViewById(R.id.txt_textview_underline2);
		txt_textview_underline2.setText("下划线示例2");
		txt_textview_underline2.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		txt_textview_underline2.getPaint().setAntiAlias(true);//抗锯齿
		//中划线、斜线
		TextView txt_textview_centureline = (TextView) findViewById(R.id.txt_textview_centureline);
		txt_textview_centureline.setText("中划线示例");
		txt_textview_centureline.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
//		txt_textview_centureline.getPaint().setFlags(0);//取消设置
		//粗体
		TextView txt_textview_fake = (TextView)findViewById(R.id.txt_textview_fake);
		txt_textview_fake.setText("粗体文字测试");
		txt_textview_fake.getPaint().setFakeBoldText(true);
		//斜体
		TextView txt_textview_italic = (TextView) findViewById(R.id.txt_textview_italic);
		txt_textview_italic.setText("斜体字体测试");
		//滚动
		TextView txt_textview_runline = (TextView) findViewById(R.id.txt_textview_runline);
		txt_textview_runline.setText("textview相关，如换行，下划线，滚动等textview相关，如换行，下划线，滚动等textview相关，如换行，下划线，滚动等textview相关，如换行，下划线，滚动等");
		//自下而上滚动1
		txt_verticalScrollTextview = (VerticalScrollTextView)findViewById(R.id.txt_verticalScrollTextview);
		initDate();
		//自上而下滾動2
		TextView txt_textview_downToUp =(TextView)  findViewById(R.id.txt_textview_downToUp);
		txt_textview_downToUp.setText("我\n公\n牛\n与\n用\n三\n大\n主\n力\n换\n魔兽");
		txt_textview_downToUp.setMovementMethod(ScrollingMovementMethod.getInstance());
		txt_textview_downToUp.setVisibility(View.GONE);
	}

	private void initDate() {
		List<Sentence> lst = new ArrayList<>();
		for(int i=0;i<30;i++){
			if(i%2 == 0){
				Sentence sen=new Sentence(i,i+"、金球奖三甲揭晓 C罗梅西哈维入围 ");
				lst.add(i, sen);
			}else{
				Sentence sen=new Sentence(i,i+"、公牛欲用三大主力换魔兽？？？？");
				lst.add(i, sen);
			}
		}
		//给View传递数据
		txt_verticalScrollTextview.setList(lst);
		//更新View
		txt_verticalScrollTextview.updateUI();
	}
}
