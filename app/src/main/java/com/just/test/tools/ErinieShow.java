package com.just.test.tools;

import com.just.test.R;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ErinieShow extends RelativeLayout {
	Context context;
	RelativeLayout rubblerBG;
	RubblerShow rubblerShow;
	Button getReward;

	int rubblerBGId = 10001;
	int getRewardId = 10002;

	int level;

	public ErinieShow(Context context, int level) {
		super(context);
		this.context = context;
		this.level = level;
		getElement();
		setElementLP();
		setElementStyle();
		setElement();
	}

	private void getElement() {
		rubblerBG = new RelativeLayout(context);
		rubblerShow = new RubblerShow(context,handler);
		getReward = new Button(context);

		rubblerBG.setId(rubblerBGId);
		getReward.setId(getRewardId);
		rubblerBG.addView(rubblerShow);
		addView(rubblerBG);
		addView(getReward);
	}

	private void setElementLP() {
		int[] resolution = PhoneUtil.getResolution(context);
		RelativeLayout.LayoutParams rubblerBG_LP = new RelativeLayout.LayoutParams(
				resolution[0], PhoneUtil.getFitHeight(context, 125));

		rubblerBG.setLayoutParams(rubblerBG_LP);
		rubblerShow.setLayoutParams(rubblerBG_LP);

		RelativeLayout.LayoutParams getReward_LP = new LayoutParams(-2, -2);
		getReward_LP.addRule(RelativeLayout.CENTER_HORIZONTAL);
		getReward_LP.addRule(RelativeLayout.BELOW,rubblerBGId);
		getReward.setLayoutParams(getReward_LP);

	}

	private void setElementStyle() {
		switch (level) {
			case 0:
				rubblerBG.setBackgroundResource(R.drawable.rewardlevel0);

				break;
			case 1:
				rubblerBG.setBackgroundResource(R.drawable.rewardlevel1);

				break;
			case 2:
				rubblerBG.setBackgroundResource(R.drawable.rewardlevel2);
				break;
			default:
				rubblerBG.setBackgroundResource(R.drawable.rewardlevel3);
				break;
		}
//		getReward.setBackgroundResource(R.drawable.get_award);
	}

	private void setElement() {
		rubblerShow.beginRubbler(Color.parseColor("#d3d3d3"), 30, 10);


		getReward.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(level==0){
					Toast.makeText(context, "很遗憾，您未中奖，请再接再厉！", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(context, "恭喜你，你中的是"+level+"等奖", Toast.LENGTH_SHORT).show();
				}
			}
		});
		//不可点击
		getReward.setClickable(false);

		getReward.setText("查看");
	}

	Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(msg.what==200){
				getReward.setClickable(true);
			}else{

			}

		}

	};

}
