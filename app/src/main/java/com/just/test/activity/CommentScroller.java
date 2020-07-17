package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.custom.AdvTextSwitcher;
import com.just.test.custom.AdvancedSwitcher;
import com.just.test.widget.MyActionBar;

/**
 * 评论滚动显示
 * 参考文章：http://p.codekk.com/detail/Android/SumiMakito/AdvancedTextSwitcher
 */
public class CommentScroller extends Activity {

	private AdvTextSwitcher txt_advText1,txt_advText2,txt_advText3;
	private String [] adv1 = new String[]{"这是第一条评论","评论2看着还不错","太渣渣了吧","你还能更逗一点么"};
	private String [] adv2 = new String[]{"我就是评论之王","看不惯你咬我呀","看你能怎么滴","我是不是很厉害"};
	private String [] adv3 = new String[]{"不要崇拜我哟，我只是一个神话","欧文就是那么的骚","可怜的大猩猩呀"};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_comment_scroller);

		//// TODO: 2016/12/21 actionbar
		LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
		Bundle bundle = new Bundle();
		bundle.putBoolean("back", true);
		bundle.putString("leftText", null);
		bundle.putString("title", "评论滚动显示");
		bundle.putBoolean("rightImage", false);
		bundle.putString("rightText", null);
		MyActionBar.actionbar(this,headerLayout,bundle);

		initView();
	}

	private void initView() {
		txt_advText1 = (AdvTextSwitcher)findViewById(R.id.txt_advText1);
		txt_advText2 = (AdvTextSwitcher)findViewById(R.id.txt_advText2);
		txt_advText3 = (AdvTextSwitcher)findViewById(R.id.txt_advText3);

		txt_advText1.setTexts(adv1);
		txt_advText1.next();
		txt_advText1.previous();
		new AdvancedSwitcher().attach(txt_advText1).setDuration(3000).start();

		txt_advText2.setTexts(adv2);
		txt_advText2.next();
		txt_advText2.previous();
		new AdvancedSwitcher().attach(txt_advText2).setDuration(2000).start();

		txt_advText3.setTexts(adv3);
		txt_advText3.next();
		txt_advText3.previous();
		new AdvancedSwitcher().attach(txt_advText3).setDuration(2000).start();


	}
}
