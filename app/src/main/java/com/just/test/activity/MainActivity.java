package com.just.test.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.just.test.R;

import static com.just.test.R.id.btn_countDown;
import static com.just.test.R.id.btn_customButton;
import static com.just.test.R.id.btn_earPhone;
import static com.just.test.R.id.btn_testLog;
import static com.just.test.R.id.btn_welcomViewPager;

public class MainActivity extends Activity{

	//危险权限（运行时权限）
	private static final String[] PERMISSIONS = new String[]{
			Manifest.permission.READ_CONTACTS,
			Manifest.permission.READ_PHONE_STATE,
			Manifest.permission.CALL_PHONE,
			Manifest.permission.CAMERA,
			Manifest.permission.ACCESS_FINE_LOCATION,
			Manifest.permission.ACCESS_COARSE_LOCATION,
			Manifest.permission.READ_EXTERNAL_STORAGE,
			Manifest.permission.WRITE_EXTERNAL_STORAGE,
			Manifest.permission.RECORD_AUDIO,
			Manifest.permission.READ_SMS,
			Manifest.permission.RECEIVE_SMS
	};
	private static final int PERMISSION_REQUEST_CODE = 0;
	private long exitTime = 0;

	private int [] ids = new int[]{btn_countDown, btn_customButton, btn_testLog, btn_welcomViewPager, btn_earPhone,
			//验证码倒计时//自定义按钮//log测试//欢迎界面//检测耳机是否插入
			R.id.btn_CheackMusic, R.id.btn_AppFragmentHost,R.id.btn_slipDelete,R.id.btn_encryption,R.id.btn_jsonArrayTest,
			//检测手机是否正在播放音乐//底部主流菜单样式//滑动删除//简单加解密//json数组解析
			R.id.btn_GsonTest, R.id.btn_slideDelete,R.id.btn_PullToRefreshListview,R.id.btn_AllSelect,R.id.btn_noteBook,
			//Gson对象解析//侧滑删除//上拉下拉刷新//全选//简易记事本
			R.id.btn_GetPhoneInfo, R.id.btn_customDialog,R.id.btn_IPhoneTextStyle,R.id.btn_autoText,R.id.btn_longClickImage,
			//获取手机信息//自定义对话框//IPhone解锁文字样式//自动提示（最简单的）//图片长按事件
			R.id.btn_progressStyle, R.id.btn_textExpand,R.id.btn_moveToTop,R.id.btn_passwordSee,
			//进度条样式//文字展开收起//置顶//密码可见或隐藏//okhttp网络连接
			R.id.btn_vibrator, R.id.btn_aboutTextview,R.id.btn_airPlainModel,R.id.btn_FileSafe,R.id.btn_loveLayout,
			//震动及获取系统声音//textview相关//飞行模式//代码安全相关//love动画效果
			R.id.btn_createLauncher, R.id.btn_GIFAnimation,R.id.btn_guanggao,R.id.btn_isBanlNumber,R.id.btn_moveItem,
			//创建桌面快捷方式//GIF动画效果//广告展示栏效果//判断银行卡号//拖动item交换顺序
			R.id.btn_Guaguale, R.id.btn_compass,R.id.btn_Volley,R.id.btn_VolleyWeather,R.id.btn_VolleyVidioList,
			//刮刮乐//指南针//volley网络请求//volley天气请求//volley视频列表
			R.id.btn_swipeRefresh, R.id.btn_xListview,R.id.btn_MPAndroidChart,R.id.btn_helloChart2,R.id.btn_createOrDeleteFile,
			//谷歌刷新方法//xlistview//MPAndroidChart图表（折线图、饼状图，条形图）//折线图hellocharts//新建或删除文件夹
			R.id.btn_drawLine, R.id.btn_ringImage,R.id.btn_MPChart2,R.id.btn_checkWiFiState,R.id.btn_GetPackageList,
			//折线图2//圆角图片//折线图2上证指数走势图//每分钟自动检测wifi状态//获取应用列表
			R.id.btn_GetPhoneContactList, R.id.btn_GetPicture,R.id.btn_BGChange,R.id.btn_sparkScreen,R.id.btn_customKeyBroad,
			//获取手机联系人//gridview显示手机所有图片//摇一摇更换主题//火花效果//自定义键盘
			R.id.btn_screenCut, R.id.btn_pullWallFalls,R.id.btn_pullWallFalls2,R.id.btn_checkPhoneNumberInfo,R.id.btn_textSpeech,
			//一键截屏//瀑布流//瀑布流2//查询手机号码归属地//安卓自带文本朗读
			R.id.btn_listviewRefresh, R.id.btn_policeVoice,R.id.btn_listLiandong,R.id.btn_customProgress,R.id.btn_MoveAndTop,
			//listview上拉加载更多//警车音效//list二级联动//自定义进度条//拖动和置顶
			R.id.btn_AndroidTransitions, R.id.btn_picasso,R.id.btn_GuideView,R.id.btn_customSubmit,R.id.btn_MiniChrome,
			//旋转动画//picasso获取图片//透明引导图//自定义提交按钮//迷你浏览器
			R.id.btn_galleryflow, R.id.btn_heartrate,R.id.btn_guaguale,R.id.btn_editMenu,R.id.btn_blackAndWhitePhoto,
			//gallery//心率监测//刮刮乐//菜单编辑//黑白照片
			R.id.btn_FastBlur, R.id.btn_customFont,R.id.btn_commentScroller,R.id.btn_TouMingDu,R.id.btn_APPStartAndBufferImage,
			//高斯模糊//第三方字体//评论滚动//透明度//app启动和缓冲画面
			R.id.btn_getSharedPreferences, R.id.btn_baseFrame,R.id.btn_getPhoneBaseInfo,R.id.btn_topStop,R.id.btn_changeStatusColume,
			//共享首选项//TabWight+ViewPager//手机基本信息//头部悬停//状态栏沉浸
			R.id.btn_qrcode, R.id.btn_myLovelyToast,R.id.btn_customActionbar,R.id.btn_readWordDOC,R.id.btn_FileInputStream,
			//二维码的生成与解析//自定义toast//自定义标题头//读取word文档//流文件的存储与读取
			R.id.btn_timeArea, R.id.btn_spinner,R.id.btn_ZoomImageView,R.id.btn_createImageWdiget,R.id.btn_customLineImage,
			//时区设置//spinner下拉框//图片放大与缩小//动态生成多个图片控件//自定义折线图
			R.id.btn_appFlowTotal, R.id.btn_scrollview_listview,R.id.btn_HorizontalScrollView,R.id.btn_sqliteTest,R.id.btn_jsonTest2,
			//本程序的流量统计//scrollview嵌套listview;//Galley效果（HorizontalScrollView）//sqlite数据库//JSON解析2
			R.id.btn_chronometer, R.id.btn_imageSwitcher,R.id.btn_scrollText,R.id.btn_speakEnglish,R.id.btn_recordSpeak,
			//chronometer计时器//imageSwitcher//可滑动的文字//朗读//录音
			R.id.btn_caculate, R.id.btn_flashlight,R.id.btn_wifiAnd3G,R.id.btn_remerberPwd,R.id.btn_xmlpull,
			//计算器//手电筒//网络变化//记住密码//XML解析
			R.id.btn_androidContacts, R.id.btn_takeCamera,R.id.btn_takeCamera2,R.id.btn_aliPayEditText,R.id.btn_DateAndTimePicker,
			//手机联系人//拍照及相册//拍照2//支付宝密码//时间日期选择
			R.id.btn_touchEventSimple, R.id.btn_homeMoniter,R.id.btn_LongClickSavePiture,R.id.btn_loudSpearker,R.id.btn_voiceSetting,
			//滑动监听//HOME监听//长按保存图片//扬声器//音量设置
			R.id.btn_smsMoniterBroadcastReceiver, R.id.btn_searchHistory,R.id.btn_imagePicture,R.id.btn_littleMoveBall,R.id.btn_FrameLayoutTest,
			//短信监听//搜索历史//图片浏览器//小鼠标//帧布局
			R.id.btn_frameLight, R.id.btn_gridLayoutTest,R.id.btn_textviewAbout,R.id.btn_timeAbout,R.id.btn_stackviewTest,
			//霓虹灯//网格布局//文字相关//时间相关//折叠图片
			R.id.btn_searchTest, R.id.btn_picassoTest,R.id.btn_drawingBoard,R.id.btn_drawingBoard2,R.id.btn_jokeAll,
			//系统搜索框//picasso测试//画板//画板2//笑话大全
			R.id.btn_openningDialog, R.id.btn_IntentTest,R.id.btn_MNXUtilsDB,R.id.btn_PhotoUtils,
			//开源对话框//Intent传值//xutils数据库//快速打开相册
			R.id.btn_guiderPicture, R.id.btn_UserGuide,R.id.btn_CircleImageView,R.id.btn_TextDrawable,R.id.btn_MarqueeView,
			//透明引导图//透明引导图2//圆形头像//文字头像//垂直翻页公告
			R.id.btn_SpinKit, R.id.btn_PhotoView,R.id.btn_PhotoViewPager,R.id.btn_RecyclerViewTest,R.id.btn_PicassoFace,
			//加载进度条样式//图片放大缩小//photo图片翻页//RecyclerView//picasso面部裁剪
			R.id.btn_compressor, R.id.btn_SimpleTagImageView,R.id.btn_LiquidButton,R.id.btn_MetaballLoading,
			//compressor图片压缩//标签图片//随手画//液体动态加载//贝塞尔曲线动画
			R.id.btn_cameraLight, R.id.btn_StepView,R.id.btn_WebViewCache,R.id.btn_liandong2,R.id.btn_CodeView,
			//闪光灯测试//指示器//网页缓存//Spinner联动2//代码加载器
			R.id.btn_ShowcaseView, R.id.btn_CardStackView,R.id.btn_Blurry,R.id.btn_shapeImageView,R.id.btn_simpleToggle,
			//引导页2//卡片交互//图片模糊库//定制各种图片形状//状态框
			R.id.btn_badgeview, R.id.btn_mnvideoplayer,R.id.btn_circyleView,R.id.btn_weixinvideo,R.id.btn_timeSqure,
			//QQ未读消息数量//开源视频框架//圆形图//微信小视频//日历控件
			R.id.btn_interfaceTest, R.id.btn_sensorManager,R.id.btn_kedaxunfei,R.id.btn_gradienter,R.id.btn_NoewLocation,
			//接口测试//传感器//科大讯飞语音合成简单版//水平仪//实时位置
			R.id.btn_backgroundView, R.id.btn_StaticLayout,R.id.btn_paletteimageview,R.id.btn_simplePop,R.id.btn_DiscreteSeekBar,
			//色谱//文字自动换行//图片阴影//pop弹框//滑动条百分比
			R.id.btn_MaterialDesign, R.id.btn_scrollListview,R.id.btn_KeyTextInputInOut,R.id.btn_stateColume,R.id.btn_bubble_sort,
			//5.0新设计//列表横向滑动//关键词飞入飞出//状态栏沉浸//冒泡排序
			R.id.btn_roundImageView, R.id.btn_AppWelComePage,R.id.btn_eventBus,R.id.btn_lottie,R.id.btn_ExpandTextView,
			//圆形图片//欢迎页面//eventBus测试//lottie动画//文字展开收起
			R.id.btn_zaqizaba, R.id.btn_FancyShowCaseView,R.id.btn_swipemenulistview,R.id.btn_PullToZoomListView,R.id.btn_CalendarListview,
			//杂七杂八//控件引导框//侧滑删除//顶部图片列表//日历列表
			R.id.btn_waterDropListView, R.id.btn_EWListView,R.id.btn_openLittleDialog,R.id.btn_ParticleTextView,R.id.btn_pullSepareteListview,
			//水滴列表//定位单词的列表//轻量级开源提示框//粒子动画文字//列表item拉伸
			R.id.btn_SlidingWeixinLayout, R.id.btn_AZExplosion,R.id.btn_PercentageBar,R.id.btn_heartLayout,R.id.btn_numberSwichView,
			//仿微信网页下拉//粒子爆破效果//自定义百分比进度条//心形泡泡//炫酷的计时器
			R.id.btn_ArrowDownloadButton, R.id.btn_Ripple,R.id.btn_sortListView,R.id.btn_ConvenientBanner,R.id.btn_jazzViewPager,
			//下载按钮进度条//波浪效果//列表字母排序//广告栏//ViewPager效果
			R.id.btn_kenburnsview, R.id.btn_shimmerTextview,R.id.btn_pieChartView,R.id.btn_IOSAlert,R.id.btn_androidCrop,
			//会动的图片//淡淡发光的文字//自定义饼状图//IOS提示框//图片裁剪
			R.id.btn_simple_bluetooth, R.id.btn_noteTurn,R.id.btn_CalculatePrimeNnumber,R.id.btn_okhttpDownload,R.id.btn_NBAScoreScrollListview,
			//简单蓝牙运用//小说翻页效果//计算质数//APK下载//NBA球员得分表
			R.id.btn_binarySystem,R.id.btn_popwindows,R.id.btn_LitePal,R.id.btn_filmDB,R.id.btn_regularExpression,
			//二进制与字符串//pop框//LitePal数据库//电影列表数据库//正则表达式的使用
			R.id.btn_imageDB,R.id.btn_stringSplit,R.id.btn_echarts,R.id.btn_view_coordinate,R.id.btn_translateLine,
			//图片数据库//字符串的四种分割方法//webview曲线图//View的坐标系//在线翻译
			R.id.btn_drivingLibrary,R.id.btn_historyToday,R.id.btn_provinceArea,R.id.btn_internationalNews,R.id.btn_famousPeopleSay,
			//驾考宝典//历史上的今天//全国行政区域//国际新闻//名人名言
			R.id.btn_checkPostOffice,R.id.btn_airQuality,R.id.btn_xiehouyu,R.id.btn_JokeAndInterestPic,R.id.btn_okGoTest,
			//全国邮编查询//全国空气质量查询//歇后语//笑话大全//okhttp封装使用
			R.id.btn_boxOffice,R.id.btn_nameOrigin,R.id.btn_DistinguishErCode,R.id.btn_selectFile,R.id.btn_fallView,
			//电影票房//姓氏起源//长按识别二维码//选择文件//飘落的字体
			R.id.btn_randomColor,R.id.btn_goodAnswer,R.id.btn_breathLight,R.id.btn_LoopRotarySwitch,R.id.btn_ScratchView,
			//随机背景//神回复//呼吸灯//3D旋转切换//刮刮乐2
			R.id.btn_rulerview,R.id.btn_SnailBar,R.id.btn_catloading,R.id.btn_superloading,R.id.btn_LyricsSync,
			//直尺//蜗牛进度条//小猫进度条//下载动画//歌词同步
			R.id.btn_flyingEditText,R.id.btn_FlycoRoundView,R.id.btn_xEditText,R.id.btn_Dashboard,R.id.btn_TimerLine,
			//飞入的输入框//圆角矩形按钮//自动添加空格//仪表盘//时光轴
			R.id.btn_MaterialSwitch,R.id.btn_JelloToggle,R.id.btn_autoFinishEmail,R.id.btn_translateWeb,R.id.btn_customCheckBox,
			//开关和刮刮乐//拉扯效果//自动补全邮箱//网页翻译单词//自定义选择框
			R.id.btn_gridPassWordView,R.id.btn_materialIcon,R.id.btn_welcomeVideo,R.id.btn_seekCircle,R.id.btn_materialCode,
			//仿支付宝密码//字体变图标//仿QQ登录播放视频//仪表盘进度条//仿锁屏密码输入样式
			R.id.btn_verticalSeekBar,R.id.btn_twoWaySeekBar,R.id.btn_bottomDialog,R.id.btn_radialButton,R.id.btn_cityBus,
			//垂直拖动条//双向拖动条//底部弹出对话框//展开的按钮//城市公交
			R.id.btn_TrainTicket,R.id.btn_JiXiong,R.id.btn_Tangshisongci,R.id.btn_AskRobot,R.id.btn_longDistanceBus,
			//火车票//号码吉凶//唐诗宋词//问答机器人//长途汽车时间表
			R.id.btn_oilPrice,R.id.btn_brokenview,R.id.btn_pdfViewTest,R.id.btn_statusBarUtil,R.id.btn_fadeInText,
			//今日油价//玻璃破裂效果//加载PDF//状态栏工具//逐字加载
			R.id.btn_xyzInfo,R.id.fiftyShades,R.id.btn_todolist,R.id.btn_modulation,R.id.btn_checkAllFile,
			//状态和身高选择//预加载//删除添加排序//音量调节  仿视频播放页面上下滑动调节音量大小和页面亮度//查询所有文件
			R.id.btn_photopicker,R.id.btn_introducePage,R.id.btn_simpleZxing,R.id.btn_smsVerifyCatcher,R.id.btn_musicSearch,
			//仿微信选择图片//步骤引导页//简易二维码扫描//读取手机验证码//爬虫获取歌曲列表
			R.id.btn_VideoWallpaper,R.id.btn_cardSwitch,R.id.btn_CalendarTest,R.id.btn_fishAnimate,R.id.btn_settingPage,
			//桌面播放视频//卡片切换//日历控件3//金鱼游动//设置页面封装
			R.id.btn_MNImageBrowser
			//图片交互特效
	};

	private Class [] classes = new Class[]{CountDown.class, CustomButtonActivity.class,LoggerTest.class,WelcomPager.class, CheckEarPhone.class,
			CheckMusic.class, FragmentTabHost.class,SlipDelete.class,Encryption.class,JsonTest.class,
			GsonTest.class, SlideDeleteView.class,RefreshListview.class,SelectAll.class,MyNoteBook.class,
			GetPhoneMessage.class, CustomDialogActivity.class,IPhoneText.class,AutoText.class,ImageLongClickActivity.class,
			ProgressStyleActivity.class, TextExpand.class,MoveToTop.class,PassWordSee.class,
			VibratorActivity.class, AboutTextView.class,AirPlainModel.class,FileEncryPtionMain.class,LoveLayoutActivity.class,
			CreateLauncher.class, GIFActivity.class,GuangGao.class,CheckBankNumber.class,MoveItem.class,
			GuaGuaLe.class, Compass.class,VolleyNet.class,VolleyWeather.class,VolleyVideoList.class,
			SwipeRefresh.class, XListViewTest.class,MPAndroidChart.class,HelloChartsTest.class,CreateOrDeleteFile.class,
			DrawLine.class, RingImageView.class,MPChart2.class,AutoCheckWifiState.class,GetPackageList.class,
			GetPhoneContactList.class, GetPicture.class,BGChangeMoving.class,SparkScreen.class,CustomKeyBroad.class,
			ScreenCut.class, PullWallFalls.class,PullWallFalls2.class,CheckPhoneNumberInfo.class,TextSpeechAndroid.class,
			ListViewRefresh.class, PoliceVoice.class,ListViewLianDong.class,MyCustomProgress.class,MoveAndTopActivity.class,
			AndroidTransitions.class, PicassoImage.class,GuideViewActivity.class,CustomSubmit.class,MiniChrome.class,
			GalleryFlowActivity.class, HeartRate.class,GuaGuaLe2.class,EditMenu.class,BlackAndWhitePhoto.class,
			GaoSiMoHu.class, CustomFontActivity.class,CommentScroller.class,TouMingDu.class,APPStartAndBufferImage.class,
			TestSharePreference.class, TestBaseFrame.class,PhoneBaseInfo.class,TopStopListViewActivity.class,ChangeStatusColume.class,
			QRCode.class, MyLovelyToast.class,MyCustomActionBar.class,ReadWordDOCActivity.class,FileInputActivity.class,
			TimeAreaSetting.class, SpinnerActivity.class,MyZoomImageView.class,CreateManyImageWdiget.class,CustomLineImage.class,
			AppFlowTotal.class, ScrollAndListViewAcivity.class,HSActivity.class,SqliteTestList.class,JsonTest2.class,
			ChronoterTimer.class, ImageSwitcherTest.class,MyScrollText.class,SpeakEnglish.class,RecordSpeak.class,
			MyCaculate.class, FlashLight.class,NetWorkChange.class,RemerberPWD.class,XMLPullActivity.class,
			AndroidContacts.class, TakeCamera.class,TakeCamera2.class,AliPayEditTextActivity.class,DateAndTimePicker.class,
			TouchEventSimple.class, HomeMoniter.class,LongClickSavePiture.class,LoudSpearker.class,VoiceSetting.class,
			SMSMoniter.class, SearchHistoryActivity.class,ImagePictureActivity.class,LoudSpearker.class,FrameLayoutTest.class,
			FrameLight.class, GridLayoutTest.class,TextViewAbout.class,TimeAbout.class,StackViewTest.class,
			SearchTest.class, PicassoTest.class,DrawingBoardActivity.class,DrawingBoardActivity2.class,JokeAllActivity.class,
			OpenningDialog.class, IntentTest.class,MNXUtilsDBTest.class,PhotoUtilActivity.class,
			GuiderPicture.class, UserGuideActivity.class,CirclePhotoActivity.class,TextDrawableActivity.class,MarqueeViewActivity.class,
			SpinKitActivity.class, PhotoViewActivity.class,PhotoViewPagerActivity.class,RecyclerViewActivity.class,PicassoFace.class,
			CompressorImage.class, SimpleTagImageViewActivity.class,LiquidButtonActivity.class,MetaballLoadingActivity.class,
            CameraLightTest.class,StepViewActivity.class,WebViewCache.class,SpinnerLianDong.class,CodeViewActivity.class,
			ShowCaseViewActivity.class, CardStackActivity.class,BlurryActivity.class,ShapeImageActivity.class,SimpleToggle.class,
			BadgeViews.class,MNVideoPlayerTest.class,CircleViewActivity.class,WeixinVideoTest.class,TimeSqure.class,
			InterFaceTest.class, SensorTest.class,KeDaXunFeiTest.class,Gradienter.class,NowLocation.class,
			BackgoundView.class, AutoChangeLine.class,SimplePopWindow.class,SimpleSeekBar.class,
			SimpleMaterialDesign.class, MyScrollListview.class,KeyTextInput.class,StateColume.class,BubbleSort.class,
			RoundImageViewActivity.class, AppWelComePage.class,EventBusTest.class,LottieTest.class,ExpandTextTest.class,
			ZaqizabaTest.class, FancyShowCaseTest.class,SwipeMenuListTest.class,PullToZoomListviewTest.class,CalendarListviewTest.class,
			WaterDropListviewTest.class, EWListviewTest.class,OpenLittleDialog.class,ParticleTextViewTest.class,PullSepareteListviewTest.class,
			WeixinSliding.class, AZExplosion.class,PercentBarTest.class,HeartLayoutTest.class,NumberSwichTest.class,
			ArrowDownButton.class, RippleTest.class,SortListTest.class,ConvenientBannerTest.class,JazzyViewPagerTest.class,
			KenburnsViewTest.class, ShimmerTextTest.class,PieChartViewTest.class,IOSAlert.class,AndroidCrop.class,
			SimpleBlueTooth.class, NoteTurnTest.class,CalculatePN.class,OkHttpDownLoadAPK.class,MySportScrollListview.class,
			BinarySystemTest.class,PopWindowTest.class,LitePalTest.class,FilmListDBTest.class,RegularExpression.class,
			ImageDBTest.class,StringSplitTest.class,EChartsTest.class,ViewCoordinate.class,TranslateLine.class,
			DrivingLibrary.class,HistoryToday.class,ProvinceArea.class,InternationalNews.class,FamousPeopleSay.class,
			CheckPostOffice.class,AirQuality.class,XieHouYu.class,JokeAndPic.class,OkHttpUtilsTest.class,
			BoxOffice.class,NameOrigin.class,DistinguishErCode.class,SelectFile.class,FallViewTest.class,
			RandomColor.class,GoodAnswer.class,BreathLight.class,LoopRotarySwitchTest.class,ScratchViewTest.class,
			RulerViewTest.class,SnailBarTest.class,CatLoding.class,SuperLoadingTest.class,LyricsSync.class,
			FlyingEditText.class,FlycoRoundViewTest.class,XEditTextText.class,DashboardTest.class,TimerLine.class,
			MaterialSwitch.class,JelloToggleTest.class,AutoFinishEmail.class,TranslateWeb.class,CustomCheckBox.class,
			GridPassWordTest.class,MaterialIconTest.class,QQWelcomeVideo.class,SeekCircleTest.class,MaterialCode.class,
			VerticalSeekBar.class,TwoWaySeekBar.class,BottomDialog.class,RadialButtonTest.class,CityBusTest.class,
			TrainTicket.class,NunberJiXiong.class,TangShiSongCi.class,AskRobot.class,LongDistanceBus.class,
			OilPrice.class,BrokenViewTest.class,PDFViewTest.class,StatusBarUtilTest.class,FadeInTextTest.class,
			XyzInfoTest.class,FiftyShadesTest.class,ToDoListTest.class,Modulation.class,CheckAllFile.class,
			PhotoPickerTest.class,IntroducePage.class,SimpleZxing.class, SmsVerifyCatcherTest.class,MusicSearch.class,
			VideoWallpaperTest.class,CardSwitchTest.class,CalendarTest3.class,FishAnimate.class,SettingPage.class,
			MNImageBrowserTest.class
	};

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

	/**
	 * 实例化和点击跳转
	 */
	private void initData(){
		for (int i=0;i<ids.length;i++){
			Button button = (Button)findViewById(ids[i]);
			final int finalPosition = i;
			button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent = new Intent(MainActivity.this,classes[finalPosition]);
					startActivity(intent);
				}
			});
		}
	}

	@Override
	protected void onResume() {
        super.onResume();
		//// TODO: 2016/11/28 权限检测，只有在全部权限都同意的情况下才能进入程序，有一个不同意的话，则继续弹出这个权限的对话框
		PackageManager pkm = this.getPackageManager();//包管理器
		String pkName = this.getPackageName();//应用包名
		int len = PERMISSIONS.length;
		//所有权限是否全部允许
		boolean[] permissions = new boolean[len];
		for (int i = 0; i < len; i++){
			permissions[i] =   (PackageManager.PERMISSION_GRANTED
					== pkm.checkPermission(PERMISSIONS[i], pkName));
		}
		boolean isAllPermissionAllowed = true;
		int index = 0;
		String[] tempArray = new String[len];
		for (int j = 0 ; j < len ; j++){
			//将不允许的权限放入这个临时的数组里面
			if (!permissions[j]){
				tempArray[index] = PERMISSIONS[j];
				index ++;
				isAllPermissionAllowed = false;
			}
		}
		//得到所有未允许的权限，再次请求
		String[] array = new String[index];
		for (int k = 0 ; k < index ; k++){
			array[k] = tempArray[k];
		}
		if (isAllPermissionAllowed) {// 这里才开始真的干活的
			initData();
//			init();
		} else {
			ActivityCompat.requestPermissions(this, array, PERMISSION_REQUEST_CODE);
		}
//        updateServiceStatus();
    }
	
	@Override
	protected void onPause() {
		super.onPause();
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
			if ((System.currentTimeMillis() - exitTime) > 2000){
				Toast.makeText(MainActivity.this,"再按一次退出程序",Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			}else {
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
