package com.just.test.tools;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.just.test.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 *这个类是在Erik Wallentinsen的demo基础上写的，Erik写的部分基本实现了顶部的下拉刷新部分。
 *llb的扩展工作：
 *●修改了其中关于最后刷新时间显示的bug
 *●修改了释放刷新放弃情况下顶部view显示的bug
 *●调整了下拉刷新用到的接口实现和变量
 *●添加了底部上拉刷新的功能
 *●添加了listview快速滚动条图标的修改函数ChangeScrollBarIcon(int id)
 *
 *要想使用PullToRefreshListView这个类，跟普通ListView的使用不同的地方：
 *●在xml布局文件里面的<ListView/>标签改成<包名.PullToRefreshListView/>
 *●在要使用的类里面实现OnRefreshListener接口，调用listView.setOnRefreshListener(this)设置监听
 *●要想调用ChangeScrollBarIcon(int id)来改变快速滚动条的图标，必须在<包名.PullToRefreshListView/>
 * 的布局文件里面设置android:fastScrollEnabled="true"
 *
 * 原下拉刷新的demo网址：https://github.com/erikwt/PullToRefresh-ListView
 * 原作者Erik Wallentinsen <dev+ptr@erikw.eu>    version 1.3.0
 * @author llb 2013-11-30
 */
public class PullToRefreshListView extends ListView implements 
		AdapterView.OnItemClickListener,
		AdapterView.OnItemLongClickListener,OnScrollListener {

    private static final float PULL_RESISTANCE                 = 1.7f;
    private static final int   BOUNCE_ANIMATION_DURATION       = 700;
    private static final int   BOUNCE_ANIMATION_DELAY          = 100;
    private static final float BOUNCE_OVERSHOOT_TENSION        = 1.4f;
    private static final int   ROTATE_ARROW_ANIMATION_DURATION = 250;

    private static enum State{PULL_TO_REFRESH,RELEASE_TO_REFRESH,REFRESHING}//底部下拉时的各个状态
    private static int measuredHeaderHeight;

    private boolean scrollbarEnabled;
    private boolean bounceBackHeader;
    private boolean lockScrollWhileRefreshing;
    private boolean ifUpdated;//用来标记是否上次已经刷新过
    private String  pullToRefreshText;
    private String  releaseToRefreshText;
    private String  refreshingText;
    private String  lastUpdatedText;
    private SimpleDateFormat lastUpdatedDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");

    private float                   previousY;//
    private int                     headerPadding;
    private boolean                 hasResetHeader;
    private long                    lastUpdated = -1;//这个时间可以考虑存起来
    private State                   state;//标记顶部下拉刷新的当前状态
    private LinearLayout            headerContainer;
    private RelativeLayout          header;
    private RotateAnimation         rotateDown;//下拉过程中箭头反向的动画
    private RotateAnimation         rotateUp;//释放过程中箭头归位的动画
    private ImageView               image;//箭头图标
    private ProgressBar             spinner;//正在刷新的进度条
    private TextView                text;
    private TextView                lastUpdatedTextView;
    private OnItemClickListener     onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private OnRefreshListener       onRefreshListener;//自定义的请求网络接口

    private float mScrollStartY;
    private final int IDLE_DISTANCE = 3;
    //上拉刷新
    private RelativeLayout tailContainer;
    private TextView tv_tail;
    private ImageView iv_tail;
    private ProgressBar pb_tail;
    private boolean scrolling;//标记滚动条状态
    private State state2;//标记底部状态
    private int location=0;//location=0表示下拉刷新，=1表示上拉刷新
	private RotateAnimation rotateDown2;
	private RotateAnimation rotateUp2;

    public PullToRefreshListView(Context context){
        super(context);
        initDown();//下拉刷新初始化
        initUp();//上拉刷新初始化
        initListView();//初始化整个ListView
    }
	public PullToRefreshListView(Context context, AttributeSet attrs){
        super(context, attrs);
        initDown();
        initUp();
        initListView();//初始化整个ListView
    }
    public PullToRefreshListView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        initDown();
        initUp();
        initListView();//初始化整个ListView
    }
    /**
     * 主要是初始化顶部下拉刷新的各个控件和动画
     * @author llb
     */
    private void initDown(){
//      setVerticalFadingEdgeEnabled(false);
  	//获取头部的各个控件对象
      headerContainer = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.ptr_header, null);
      
      header = (RelativeLayout) headerContainer.findViewById(R.id.ptr_id_header);
      text = (TextView) header.findViewById(R.id.ptr_id_text);
      lastUpdatedTextView = (TextView) header.findViewById(R.id.ptr_id_last_updated);
      image = (ImageView) header.findViewById(R.id.ptr_id_image);
      spinner = (ProgressBar) header.findViewById(R.id.ptr_id_spinner);
      //获取上面所要显示的文字内容
      pullToRefreshText = getContext().getString(R.string.ptr_pull_to_refresh);
      releaseToRefreshText = getContext().getString(R.string.ptr_release_to_refresh);
      refreshingText = getContext().getString(R.string.ptr_refreshing);
      lastUpdatedText = getContext().getString(R.string.ptr_last_updated);
      //下拉刷新时箭头的旋转
      rotateDown = new RotateAnimation(0, 180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);//中点绕顺时针旋转设置
      rotateDown.setDuration(ROTATE_ARROW_ANIMATION_DURATION);//旋转时间
      rotateDown.setFillAfter(true);//表示旋转状态保持
		//释放时的旋转动画
      rotateUp = new RotateAnimation(-180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);//顺时针旋转设置
      rotateUp.setDuration(ROTATE_ARROW_ANIMATION_DURATION);
      rotateUp.setFillAfter(true);
  }
/**
 * 主要是初始化底部上拉刷新的各个控件
 * @author llb
 */
  private void initUp() {
  	//上拉刷新初始化 需要放在initDown()前面调用
      tailContainer=(RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.ptr_tail, null);
      tv_tail=(TextView) tailContainer.findViewById(R.id.tv_tail);
      pb_tail=(ProgressBar) tailContainer.findViewById(R.id.pb_tail);
      iv_tail=(ImageView) tailContainer.findViewById(R.id.iv_tail);
      state2=State.PULL_TO_REFRESH;//初始化
      pb_tail.setVisibility(View.INVISIBLE);//正在刷新图标不可见
      
    //下拉刷新时箭头的旋转
      rotateDown2 = new RotateAnimation(180, 360,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);//中点绕顺时针旋转设置
      rotateDown2.setDuration(ROTATE_ARROW_ANIMATION_DURATION);//旋转时间
      rotateDown2.setFillAfter(true);//表示旋转状态保持
		//释放时的旋转动画
      rotateUp2 = new RotateAnimation(0, 180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);//顺时针旋转设置
      rotateUp2.setDuration(ROTATE_ARROW_ANIMATION_DURATION);
      rotateUp2.setFillAfter(true);
	}
/**
 * 初始化整个列表的视图和设置监听
 * @author llb
 */
  private void initListView() {
	  addFooterView(tailContainer);//添加底部视图
      addHeaderView(headerContainer);//给listview添加一个头部视图  Call this before calling setAdapter
      setState(State.PULL_TO_REFRESH);//初始化状态为 下拉刷新
      scrollbarEnabled = isVerticalScrollBarEnabled();
      ViewTreeObserver vto = header.getViewTreeObserver();
      vto.addOnGlobalLayoutListener(this);
      
      super.setOnItemClickListener(this);
      super.setOnItemLongClickListener(this);//一定要是给父类的这个方法设置监听
      super.setOnScrollListener(this);
	}
    /**
     * Activate an OnRefreshListener to get notified on 'pull to refresh'
     * events.
     *
     * @param onRefreshListener The OnRefreshListener to get notified
     */
    public void setOnRefreshListener(OnRefreshListener onRefreshListener){
        this.onRefreshListener = onRefreshListener;
    }
    /**
     * @return If the list is in 'Refreshing' state
     */
    public boolean isRefreshing(){
        return state == State.REFRESHING;
    }
    /**
     * Default is false. When lockScrollWhileRefreshing is set to true, the list
     * cannot scroll when in 'refreshing' mode. It's 'locked' on refreshing.
     *
     * @param lockScrollWhileRefreshing
     */
    public void setLockScrollWhileRefreshing(boolean lockScrollWhileRefreshing){
        this.lockScrollWhileRefreshing = lockScrollWhileRefreshing;
    }
    /**
     * Default is false. Show the last-updated date/time in the 'Pull ro Refresh'
     * header. See 'setLastUpdatedDateFormat' to set the date/time formatting.
     *
     * @param ifUpdated
     */
    public void setShowLastUpdatedText(boolean ifUpdated){
        this.ifUpdated = ifUpdated;
        Log.i("llb","setShowLastUpdatedText()"); 
        if(!ifUpdated) lastUpdatedTextView.setVisibility(View.GONE);
    }
    /**
     * Default: "dd/MM HH:mm". Set the format in which the last-updated
     * date/time is shown. Meaningless if 'ifUpdated == false (default)'.
     * See 'setShowLastUpdatedText'.
     * @param lastUpdatedDateFormat
     */
    public void setLastUpdatedDateFormat(SimpleDateFormat lastUpdatedDateFormat){
        this.lastUpdatedDateFormat = lastUpdatedDateFormat;
    }
    /**
     * Explicitly set the state to refreshing. This
     * is useful when you want to show the spinner and 'Refreshing' text when
     * the refresh was not triggered by 'pull to refresh', for example on start.
     */
    public void setRefreshing(){
        state = State.REFRESHING;
        scrollTo(0, 0);
        setUiRefreshing();
        setHeaderPadding(0);
    }
    /**
     * 当调用类完成网络请求时，会手动调用这个函数
     * the data is finished.
     */
    public void onRefreshComplete(){
    	if(location==0){
//    		Log.i("llb","下拉 onRefreshComplete ");
    		state = State.PULL_TO_REFRESH;//把状态复位
	        resetHeader();//重置头部
	        lastUpdated = System.currentTimeMillis();
//	        Log.i("llb","onRefreshComplete lastUpdated="+lastUpdated);
	        setShowLastUpdatedText(true);//llb 加上才能显示最后更新时间
    	}else{//底部复位
//    		Log.i("llb","上拉 onRefreshComplete ");
    		location=0;
    		iv_tail.setVisibility(View.INVISIBLE);
			pb_tail.setVisibility(View.INVISIBLE);
    		
    		tv_tail.setText(R.string.pull_to_refresh);//上拉刷新
    		state2=State.PULL_TO_REFRESH;
		}
        
    }
    /**
     * Change the label text on state 'Pull to Refresh'
     *改变头部显示标签：下拉刷新
     * @param pullToRefreshText Text
     */
    public void setTextPullToRefresh(String pullToRefreshText){
        this.pullToRefreshText = pullToRefreshText;
        if(state == State.PULL_TO_REFRESH){
            text.setText(pullToRefreshText);
        }
    }
    /**
     * Change the label text on state 'Release to Refresh'
     *改变头部显示标签：释放刷新
     * @param releaseToRefreshText Text
     */
    public void setTextReleaseToRefresh(String releaseToRefreshText){
        this.releaseToRefreshText = releaseToRefreshText;
        if(state == State.RELEASE_TO_REFRESH){
            text.setText(releaseToRefreshText);
        }
    }
    /**
     * Change the label text on state 'Refreshing'
     *改变头部显示标签：正在刷新
     * @param refreshingText Text
     */
    public void setTextRefreshing(String refreshingText){
        this.refreshingText = refreshingText;
        if(state == State.REFRESHING){//正在刷新
            text.setText(refreshingText);
        }
    }

    private void setHeaderPadding(int padding){
        headerPadding = padding;
        MarginLayoutParams mlp = (MarginLayoutParams) header.getLayoutParams();
        mlp.setMargins(0, Math.round(padding), 0, 0);
        header.setLayoutParams(mlp);
    }

   

    private void bounceBackHeader(){
    	//根据此时的状态，决定是回退到全部隐藏or露出正在刷新画面
        int yTranslate=state == State.REFRESHING ?
                header.getHeight() - headerContainer.getHeight() :
                -headerContainer.getHeight() - headerContainer.getTop() + getPaddingTop();
//        Log.i("llb","调用了bounceBackHeader "+yTranslate);
		TranslateAnimation bounceAnimation = new TranslateAnimation(
                TranslateAnimation.ABSOLUTE, 0,
                TranslateAnimation.ABSOLUTE, 0,
                TranslateAnimation.ABSOLUTE, 0,
                TranslateAnimation.ABSOLUTE, yTranslate);

        bounceAnimation.setDuration(BOUNCE_ANIMATION_DURATION);
        bounceAnimation.setFillEnabled(true);
        bounceAnimation.setFillAfter(false);
        bounceAnimation.setFillBefore(true);
        bounceAnimation.setInterpolator(new OvershootInterpolator(BOUNCE_OVERSHOOT_TENSION));
        bounceAnimation.setAnimationListener(new HeaderAnimationListener(yTranslate));

        startAnimation(bounceAnimation);
    }
/**
 * 重置顶部的header view
 */
    private void resetHeader(){
        if(getFirstVisiblePosition() > 0){
            setHeaderPadding(-header.getHeight());
            setState(State.PULL_TO_REFRESH);
            return;
        }
        if(getAnimation() != null && !getAnimation().hasEnded()){
            bounceBackHeader = true;
        }else{
            bounceBackHeader();
//            Log.i("llb","resetHeader 刚调用bounceBackHeader()");
        }
    }
/**
 * 顶部下拉刷新时，负责顶部进度条、图标动画和文字的切换工作
 */
    private void setUiRefreshing(){
        spinner.setVisibility(View.VISIBLE);
        image.clearAnimation();
        image.setVisibility(View.INVISIBLE);
        text.setText(refreshingText);
    }
/**
 * 下面这个类是用来在顶部下拉刷新时，在各个状态之间切换
 * @param state 新状态
 */
    private void setState(State state){
        this.state = state;
        switch(state){
		case PULL_TO_REFRESH:
			spinner.setVisibility(View.INVISIBLE);
			image.setVisibility(View.VISIBLE);
			text.setText(pullToRefreshText);
			if (ifUpdated && lastUpdated != -1) {
//				Log.i("llb", "正在设置上次更新时间");
				lastUpdatedTextView.setVisibility(View.VISIBLE);
				lastUpdatedTextView.setText(String.format(lastUpdatedText,
						lastUpdatedDateFormat.format(new Date(lastUpdated))));
			}
			break;
		case RELEASE_TO_REFRESH:
			spinner.setVisibility(View.INVISIBLE);
			image.setVisibility(View.VISIBLE);
			text.setText(releaseToRefreshText);
			break;
		case REFRESHING:
			setUiRefreshing();
			Log.i("llb", "setState Refreshing!");
			lastUpdated = System.currentTimeMillis();
			if (onRefreshListener == null) {
				Log.i("llb", "setState onRefreshListener == null!");
				setState(State.PULL_TO_REFRESH);// 目前根本没有被调用过
			} else {
				location = 0;// 表示这是顶部下拉刷新
				onRefreshListener.onRefresh();
//				Log.i("llb", "setState onRefresh!");
			}
			break;
        }
    }
/**
 * 以下七个函数都是重写ListView里面的函数
 * @author llb    
 */
    @Override
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    @Override
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
        this.onItemLongClickListener = onItemLongClickListener;
    }
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt){
        super.onScrollChanged(l, t, oldl, oldt);
        if(!hasResetHeader){
            if(measuredHeaderHeight > 0 && state != State.REFRESHING){
                setHeaderPadding(-measuredHeaderHeight);
            }
            hasResetHeader = true;
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
    	//如果运动正在进行中，直接返回true
		if (lockScrollWhileRefreshing
				&& (state == State.REFRESHING || getAnimation() != null
						&& !getAnimation().hasEnded())) {
			return true;
		}else if (state2==State.REFRESHING) {
			return true;
		}
        switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			mScrollStartY = event.getY();
			if(location==0){
				if (getFirstVisiblePosition() == 0) {
					previousY = event.getY();
				} else {
					Log.i("llb", "Action_down getFirstVisiblePosition()"
							+ getFirstVisiblePosition());
					previousY = -1;
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			Log.i("llb", "ACTION_UP！");
			if(location==0){
				if (previousY != -1 && getFirstVisiblePosition() == 0) {// llb 修复bug
					Log.i("llb", "ACTION_UP RELEASE_TO_REFRESH");
					setState(State.REFRESHING);
					bounceBackHeader();
				} else {
					Log.i("llb", "ACTION_UP PULL_TO_REFRESH");
					resetHeader();
				}
			} else {// 底部上拉释放刷新了
				if (tailContainer.getHeight() > 0) {
					if (state2 == State.RELEASE_TO_REFRESH
							&& (mScrollStartY - event.getY()) > IDLE_DISTANCE) {// 底部可见
																				// 手指上滑而且处于释放刷新的状态
						iv_tail.clearAnimation();
						iv_tail.startAnimation(rotateDown2);
						tv_tail.setText(R.string.ptr_refreshing);// 正在刷新
						pb_tail.setVisibility(View.VISIBLE);
						state2 = State.REFRESHING;// 释放可以加载更多了
						// Log.i("llb","刷新中"+state2);
					} else {
						// Log.i("llb","放弃刷新了"+state2);
						tv_tail.setText(R.string.pull_to_refresh);// 上拉刷新
						pb_tail.setVisibility(View.INVISIBLE);
						state2 = State.PULL_TO_REFRESH;// 还原
						iv_tail.clearAnimation();
						iv_tail.startAnimation(rotateDown2);
					}
				}
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if(location==0){
				if (previousY != -1
						&& getFirstVisiblePosition() == 0
						&& Math.abs(mScrollStartY - event.getY()) > IDLE_DISTANCE) {
					float y = event.getY();
					float diff = y - previousY;
					if (diff > 0)
						diff /= PULL_RESISTANCE;
					previousY = y;
					int newHeaderPadding = Math.max(
							Math.round(headerPadding + diff), -header.getHeight());
					if (newHeaderPadding != headerPadding&& state != State.REFRESHING) {
						setHeaderPadding(newHeaderPadding);
						if (state == State.PULL_TO_REFRESH && headerPadding > 0) {
							setState(State.RELEASE_TO_REFRESH);
							image.clearAnimation();
							image.startAnimation(rotateDown);
						} else if (state==State.RELEASE_TO_REFRESH&& headerPadding < 0) {
							setState(State.PULL_TO_REFRESH);
							image.clearAnimation();
							image.startAnimation(rotateUp);
						}
					}
				}
			}else {
				if ((tailContainer.getBottom() - getBottom()) < 0.6 * tailContainer
						.getHeight() && state2 == State.PULL_TO_REFRESH) {// 底部出现了
					state2 = State.RELEASE_TO_REFRESH;
					iv_tail.clearAnimation();
		            iv_tail.startAnimation(rotateUp2);
		            tv_tail.setText(R.string.ptr_release_to_refresh);//释放刷新
//		            Log.i("llb","可以刷新了"+state2);
				}
			} 
			break;
        }
        return super.onTouchEvent(event);
    }
    /**
     * 重写列表的单击响应函数
     */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		hasResetHeader = false;
        if(onItemClickListener != null && state == State.PULL_TO_REFRESH){
            onItemClickListener.onItemClick(parent, view, position - getHeaderViewsCount(), id);
        }
	}
	/**
	 * 重写listview的长按响应
	 */
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,int position, long id) {
		 hasResetHeader = false;
         if(onItemLongClickListener != null && state == State.PULL_TO_REFRESH){
             return onItemLongClickListener.onItemLongClick(parent, view, position - getHeaderViewsCount(), id);
         }
		return false;
	}
	/**
	 * 这个函数是OnGlobalLayoutListener的接口函数，主要负责监听顶部刷新区域的位置
	 * 看是否停止了刷新工作，需要复位隐藏
	 * @author llb
	 */
	@Override
	public void onGlobalLayout() {
		super.onGlobalLayout();
		int headerHeight = header.getHeight();//头部视图高度
//		Log.i("llb",headerHeight+" "+this.getLastVisiblePosition()+" "+this.getChildCount()+" "+this.getCount());
	    if(headerHeight > 0){
	        measuredHeaderHeight = headerHeight;
	        if(measuredHeaderHeight > 0 && state != State.REFRESHING){
	            setHeaderPadding(-measuredHeaderHeight);//头部复位隐藏
	            requestLayout();//重新请求布局
//	            Log.i("llb", "我在onGlobalLayout");
	        }
	    }
	    getViewTreeObserver().removeGlobalOnLayoutListener(this);
	}
/**
 * 下面两个函数是 OnScrollListener接口的函数，在这里主要是用来在底部上拉刷新时，
 * 获得滚动条的位置，并即使调用加载onLoadMore()函数
 * @author llb
 */
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if(getLastVisiblePosition()==this.getCount()-1&&scrollState==OnScrollListener.SCROLL_STATE_IDLE){
		}
	}
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {//第一个可见的索引（0） 可见item数目   总item数目
	//	Log.i("llb","onScroll:"+firstVisibleItem+" "+visibleItemCount+" "+totalItemCount+" "+getLastVisiblePosition());
		if(getLastVisiblePosition()>totalItemCount-2){//快到底部了
			location=1;//表示这是底部上拉刷新
			if(state2==State.REFRESHING){
				state2=State.PULL_TO_REFRESH;
				Log.i("llb","onScroll开始刷新");
				location=1;
				postDelayed(new Runnable(){//包装成一个Handler发送的Message放到了UI消息队列里面，其实也是在UI线程里面执行
	                @Override
	                public void run(){
	                	onRefreshListener.onLoadMore();
	                }
	            }, BOUNCE_ANIMATION_DELAY);
			}
		}else {
			location=0;
			if(state2==State.RELEASE_TO_REFRESH){//说明在释放刷新这一步又退回去了
				iv_tail.clearAnimation();
				iv_tail.startAnimation(rotateDown2);//光标复位
				tv_tail.setText(R.string.pull_to_refresh);//上拉刷新
				state2=State.PULL_TO_REFRESH;
//				Log.i("llb","onScroll 放弃刷新了");
			}
		}	
	}
/**
 * AnimationListener接口实现类
 * @author llb
 */
    private class HeaderAnimationListener implements AnimationListener{
        private int height, translation;
        private State stateAtAnimationStart;
        public HeaderAnimationListener(int translation){
            this.translation = translation;
        }
        @Override
        public void onAnimationStart(Animation animation){
        	if (location==0) {
        		stateAtAnimationStart = state;
                ViewGroup.LayoutParams lp = getLayoutParams();
                height = lp.height;
                lp.height = getHeight() - translation;
//                Log.i("llb","onAnimationStart height2="+height);
                setLayoutParams(lp);
			}
            if(scrollbarEnabled){
                setVerticalScrollBarEnabled(false);
            }
        }
        @Override
        public void onAnimationEnd(Animation animation){
        	if (location==0) {
//        		 Log.i("llb","onAnimationEnd height3="+height);
     			setHeaderPadding(stateAtAnimationStart == State.REFRESHING ? 0
     					: -measuredHeaderHeight - headerContainer.getTop());//设置头部跟页面顶部的距离
     			setSelection(0);
                 ViewGroup.LayoutParams lp = getLayoutParams();
                 lp.height = height;
                 setLayoutParams(lp);
                 if(bounceBackHeader){
                     bounceBackHeader = false;
                     postDelayed(new Runnable(){
                         @Override
                         public void run(){
                             resetHeader();
                         }
                     }, BOUNCE_ANIMATION_DELAY);
                 }else if(stateAtAnimationStart != State.REFRESHING){
                     setState(State.PULL_TO_REFRESH);
                 }
        	}
            if(scrollbarEnabled){
                setVerticalScrollBarEnabled(true);
            }
        }
        @Override
        public void onAnimationRepeat(Animation animation){}
    }
/**
 * 自定义的一个接口
 * 调用类需要实现这个接口才可以实现网络请求刷新的操作，使用setOnRefreshListener(..)来设置接口监听
 * @author llb
 */
	public interface OnRefreshListener{
	    /**
	     * 顶部的下拉刷新的处理函数，在请求网络结束时需要调用这个类里面的onRefreshComplete()
	     */
	    public void onRefresh();
	    /**
	     * 在底部上拉加载更多时的处理函数，在请求网络结束时需要调用这个类里面的onRefreshComplete()
	     */
	    public void onLoadMore();
	}	
/**
 * 这个函数用来改变ListView快速滚动条的图标
 * @param id 新图片的drawable资源的id
 * @author llb
 */
	public void ChangeScrollBarIcon(int id) {
		try {//利用反射来改变快速滑动条的图标
			Field field=AbsListView.class.getDeclaredField("mFastScroller");//得到AbsListView类的成员变量
			field.setAccessible(true);//禁止访问检测功能
			Field field2=field.getType().getDeclaredField("mThumbDrawable");
			field2.setAccessible(true);//禁止访问检测功能
			Drawable drawable=getResources().getDrawable(id);//自定义的滚动条图标
			field2.set(field.get(this), drawable);//更改滚动条图标
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
