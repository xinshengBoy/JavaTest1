package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.OnCompositionLoadedListener;
import com.just.test.R;
import com.just.test.widget.MyActionBar;

/**
 * lottie动画
 * compile 'com.airbnb.android:lottie:2.0.0-beta4'
 * https://github.com/airbnb/lottie-Android
 * 主要是使用动画，下载网上的JSON文件，放入assets目录下面，
 * Created by admin on 2017/5/9.
 */

public class LottieTest extends Activity implements View.OnClickListener{

    private LottieAnimationView lottie_test;
    private Button squares_alboardman,A,AnimatedGraph,Apostrophe,ATM,B,BeatingHeart,BlinkingCursor,C,Camera,CheckSwich,Colon,Comma;
    private Button Countdown,D,E,EmojiShock,EmojiTongue,EmojiWink,EmptyState,F,FavoriteStart,Fill,G;
    private Button Gears,GradientFill,H,HamburgerArrow,I,ImThirsty,J,K,KeyframeTypes,L,Laugh4,Loading1,Loading2,Loading3,Loading4,LogoSmall,LoopPlayOnce;
    private Button LottieLogo1,LottieLogo2,M,O,Octopus,P,Parenting,Permission,PinJump,PlayandLikelt,Postcard,Precomps,Preloader,ProgressSuccess,Q;
    private Button RR,Retweet,S,ShapeTypes,Spider_Loader,SplitDimensions,StarWarsRey,Stroke,T,TadahImage,TadahVideo,TouchID,TrackMattes;
    private Button TrimPaths,TwitterHeart,U,V,VideoCamera,W,Walkthrough,WeAccept,X,Y,Z;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_lottie);

        initView();
    }

    private void initView(){
        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "lottie动画");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        lottie_test = (LottieAnimationView)findViewById(R.id.lottie_test);

        squares_alboardman = (Button) findViewById(R.id.btn_lottie_1);
        A = (Button) findViewById(R.id.btn_lottie_2);
        AnimatedGraph = (Button) findViewById(R.id.btn_lottie_3);
        Apostrophe = (Button) findViewById(R.id.btn_lottie_4);
        ATM = (Button) findViewById(R.id.btn_lottie_5);
        B = (Button) findViewById(R.id.btn_lottie_6);
        BeatingHeart = (Button) findViewById(R.id.btn_lottie_7);
        BlinkingCursor = (Button) findViewById(R.id.btn_lottie_8);
        C = (Button) findViewById(R.id.btn_lottie_9);
        Camera = (Button) findViewById(R.id.btn_lottie_10);
        CheckSwich = (Button) findViewById(R.id.btn_lottie_11);
        Colon = (Button) findViewById(R.id.btn_lottie_12);
        Comma = (Button) findViewById(R.id.btn_lottie_13);
        Countdown = (Button) findViewById(R.id.btn_lottie_14);
        D = (Button) findViewById(R.id.btn_lottie_15);
        E = (Button) findViewById(R.id.btn_lottie_16);
        EmojiShock = (Button) findViewById(R.id.btn_lottie_17);
        EmojiTongue = (Button) findViewById(R.id.btn_lottie_18);
        EmojiWink = (Button) findViewById(R.id.btn_lottie_19);
        EmptyState = (Button) findViewById(R.id.btn_lottie_20);
        F = (Button) findViewById(R.id.btn_lottie_21);
        FavoriteStart = (Button) findViewById(R.id.btn_lottie_22);
        Fill = (Button) findViewById(R.id.btn_lottie_23);
        G = (Button) findViewById(R.id.btn_lottie_24);


        Gears = (Button) findViewById(R.id.btn_lottie_25);
        GradientFill = (Button) findViewById(R.id.btn_lottie_26);
        H = (Button) findViewById(R.id.btn_lottie_27);
        HamburgerArrow = (Button) findViewById(R.id.btn_lottie_28);
        I = (Button) findViewById(R.id.btn_lottie_29);
        ImThirsty = (Button) findViewById(R.id.btn_lottie_30);
        J = (Button) findViewById(R.id.btn_lottie_31);
        K = (Button) findViewById(R.id.btn_lottie_32);
        KeyframeTypes = (Button) findViewById(R.id.btn_lottie_33);
        L = (Button) findViewById(R.id.btn_lottie_34);
        Laugh4 = (Button) findViewById(R.id.btn_lottie_35);
        Loading1 = (Button) findViewById(R.id.btn_lottie_36);
        Loading2 = (Button) findViewById(R.id.btn_lottie_37);
        Loading3 = (Button) findViewById(R.id.btn_lottie_38);
        Loading4 = (Button) findViewById(R.id.btn_lottie_39);
        LogoSmall = (Button) findViewById(R.id.btn_lottie_40);
        LoopPlayOnce = (Button) findViewById(R.id.btn_lottie_41);
        LottieLogo1 = (Button) findViewById(R.id.btn_lottie_42);
        LottieLogo2 = (Button) findViewById(R.id.btn_lottie_43);
        M = (Button) findViewById(R.id.btn_lottie_44);
        O = (Button) findViewById(R.id.btn_lottie_45);
        Octopus = (Button) findViewById(R.id.btn_lottie_46);
        P = (Button) findViewById(R.id.btn_lottie_47);
        Parenting = (Button) findViewById(R.id.btn_lottie_48);
        Permission = (Button) findViewById(R.id.btn_lottie_49);


        PinJump = (Button) findViewById(R.id.btn_lottie_50);
        PlayandLikelt = (Button) findViewById(R.id.btn_lottie_51);
        Postcard = (Button) findViewById(R.id.btn_lottie_52);
        Precomps = (Button) findViewById(R.id.btn_lottie_53);
        Preloader = (Button) findViewById(R.id.btn_lottie_54);
        ProgressSuccess = (Button) findViewById(R.id.btn_lottie_55);
        Q = (Button) findViewById(R.id.btn_lottie_56);
        RR = (Button) findViewById(R.id.btn_lottie_57);
        Retweet = (Button) findViewById(R.id.btn_lottie_58);
        S = (Button) findViewById(R.id.btn_lottie_59);
        ShapeTypes = (Button) findViewById(R.id.btn_lottie_60);
        Spider_Loader = (Button) findViewById(R.id.btn_lottie_61);
        SplitDimensions = (Button) findViewById(R.id.btn_lottie_62);
        StarWarsRey = (Button) findViewById(R.id.btn_lottie_63);
        Stroke = (Button) findViewById(R.id.btn_lottie_64);
        T = (Button) findViewById(R.id.btn_lottie_65);
        TadahImage = (Button) findViewById(R.id.btn_lottie_66);
        TadahVideo = (Button) findViewById(R.id.btn_lottie_67);
        TouchID = (Button) findViewById(R.id.btn_lottie_68);
        TrackMattes = (Button) findViewById(R.id.btn_lottie_69);
        TrimPaths = (Button) findViewById(R.id.btn_lottie_70);
        TwitterHeart = (Button) findViewById(R.id.btn_lottie_71);
        U = (Button) findViewById(R.id.btn_lottie_72);
        V = (Button) findViewById(R.id.btn_lottie_73);
        VideoCamera = (Button) findViewById(R.id.btn_lottie_74);
        W = (Button) findViewById(R.id.btn_lottie_75);
        Walkthrough = (Button) findViewById(R.id.btn_lottie_76);
        WeAccept = (Button) findViewById(R.id.btn_lottie_77);
        X = (Button) findViewById(R.id.btn_lottie_78);
        Y = (Button) findViewById(R.id.btn_lottie_79);
        Z = (Button) findViewById(R.id.btn_lottie_80);


        squares_alboardman.setOnClickListener(this);
        A.setOnClickListener(this);
        AnimatedGraph.setOnClickListener(this);
        Apostrophe.setOnClickListener(this);
        ATM.setOnClickListener(this);
        B.setOnClickListener(this);
        BeatingHeart.setOnClickListener(this);
        BlinkingCursor.setOnClickListener(this);
        C.setOnClickListener(this);
        Camera.setOnClickListener(this);
        CheckSwich.setOnClickListener(this);
        Colon.setOnClickListener(this);
        Comma.setOnClickListener(this);
        Countdown.setOnClickListener(this);
        D.setOnClickListener(this);
        E.setOnClickListener(this);
        EmojiShock.setOnClickListener(this);
        EmojiTongue.setOnClickListener(this);
        EmojiWink.setOnClickListener(this);
        EmptyState.setOnClickListener(this);
        F.setOnClickListener(this);
        FavoriteStart.setOnClickListener(this);
        Fill.setOnClickListener(this);
        G.setOnClickListener(this);
        Gears.setOnClickListener(this);
        GradientFill.setOnClickListener(this);
        H.setOnClickListener(this);
        HamburgerArrow.setOnClickListener(this);
        I.setOnClickListener(this);
        ImThirsty.setOnClickListener(this);
        J.setOnClickListener(this);
        K.setOnClickListener(this);
        KeyframeTypes.setOnClickListener(this);
        L.setOnClickListener(this);
        Laugh4.setOnClickListener(this);
        Loading1.setOnClickListener(this);
        Loading2.setOnClickListener(this);
        Loading3.setOnClickListener(this);
        Loading4.setOnClickListener(this);
        LogoSmall.setOnClickListener(this);
        LoopPlayOnce.setOnClickListener(this);
        LottieLogo1.setOnClickListener(this);
        LottieLogo2.setOnClickListener(this);
        M.setOnClickListener(this);
        O.setOnClickListener(this);
        Octopus.setOnClickListener(this);
        P.setOnClickListener(this);
        Parenting.setOnClickListener(this);


        Permission.setOnClickListener(this);
        PinJump.setOnClickListener(this);
        PlayandLikelt.setOnClickListener(this);
        Postcard.setOnClickListener(this);
        Precomps.setOnClickListener(this);
        Preloader.setOnClickListener(this);
        ProgressSuccess.setOnClickListener(this);
        Q.setOnClickListener(this);
        RR.setOnClickListener(this);
        Retweet.setOnClickListener(this);
        S.setOnClickListener(this);
        ShapeTypes.setOnClickListener(this);
        Spider_Loader.setOnClickListener(this);
        SplitDimensions.setOnClickListener(this);
        StarWarsRey.setOnClickListener(this);
        Stroke.setOnClickListener(this);
        T.setOnClickListener(this);
        TadahImage.setOnClickListener(this);
        TadahVideo.setOnClickListener(this);
        TouchID.setOnClickListener(this);
        TrackMattes.setOnClickListener(this);
        TrimPaths.setOnClickListener(this);
        TwitterHeart.setOnClickListener(this);
        U.setOnClickListener(this);
        V.setOnClickListener(this);
        VideoCamera.setOnClickListener(this);
        W.setOnClickListener(this);
        Walkthrough.setOnClickListener(this);
        WeAccept.setOnClickListener(this);
        X.setOnClickListener(this);
        Y.setOnClickListener(this);
        Z.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view == squares_alboardman){
            showAnimation("9squares-AlBoardman");
        }else if (view == A){
            showAnimation("A");
        }else if (view == AnimatedGraph){
            showAnimation("AnimatedGraph");
        }else if (view == Apostrophe){
            showAnimation("Apostrophe");
        }else if (view == ATM){
            showAnimation("ATM");
        }else if (view == B){
            showAnimation("B");
        }else if (view == BeatingHeart){
            showAnimation("BeatingHeart");
        }else if (view == BlinkingCursor){
            showAnimation("BlinkingCursor");
        }else if (view == C){
            showAnimation("C");
        }else if (view == Camera){
            showAnimation("Camera");
        }else if (view == CheckSwich){
            showAnimation("CheckSwitch");
        }else if (view == Colon){
            showAnimation("Colon");
        }else if (view == Comma){
            showAnimation("Comma");
        }else if (view == Countdown){
            showAnimation("Countdown");
        }else if (view == D){
            showAnimation("D");
        }else if (view == E){
            showAnimation("E");
        }else if (view == EmojiShock){
            showAnimation("EmojiShock");
        }else if (view == EmojiTongue){
            showAnimation("EmojiTongue");
        }else if (view == EmojiWink){
            showAnimation("EmojiWink");
        }else if (view == EmptyState){
            showAnimation("EmptyState");
        }else if (view == F){
            showAnimation("F");
        }else if (view == FavoriteStart){
            showAnimation("FavoriteStar");
        }else if (view == Fill){
            showAnimation("Fill");
        }else if (view == G){
            showAnimation("G");
        }else if (view == Gears){
            showAnimation("Gears");
        }else if (view == GradientFill){
            showAnimation("GradientFill");
        }else if (view == H){
            showAnimation("H");
        }else if (view == HamburgerArrow){
            showAnimation("HamburgerArrow");
        }else if (view == I){
            showAnimation("I");
        }else if (view == ImThirsty){
            showAnimation("ImThirsty");
        }else if (view == J){
            showAnimation("J");
        }else if (view == K){
            showAnimation("K");
        }else if (view == KeyframeTypes){
            showAnimation("KeyframeTypes");
        }else if (view == L){
            showAnimation("L");
        }else if (view == Laugh4){
            showAnimation("Laugh4");
        }else if (view == Loading1){
            showAnimation("Loading1");
        }else if (view == Loading2){
            showAnimation("Loading2");
        }else if (view == Loading3){
            showAnimation("Loading3");
        }else if (view == Loading4){
            showAnimation("Loading4");
        }else if (view == LogoSmall){
            showAnimation("LogoSmall");
        }else if (view == LoopPlayOnce){
            showAnimation("LoopPlayOnce");
        }else if (view == LottieLogo1){
            showAnimation("LottieLogo1");
        }else if (view == LottieLogo2){
            showAnimation("LottieLogo2");
        }else if (view == M){
            showAnimation("M");
        }else if (view == O){
            showAnimation("O");
        }else if (view == Octopus){
            showAnimation("Octopus");
        }else if (view == P){
            showAnimation("P");
        }else if (view == Parenting){
            showAnimation("Parenting");
        }else if (view == Permission){
            showAnimation("Permission");
        }else if (view == PinJump){
            showAnimation("PinJump");
        }else if (view == PlayandLikelt){
            showAnimation("PlayandLikeIt");
        }else if (view == Postcard){
            showAnimation("Postcard");
        }else if (view == Precomps){
            showAnimation("Precomps");
        }else if (view == Preloader){
            showAnimation("Preloader");
        }else if (view == ProgressSuccess){
            showAnimation("ProgressSuccess");
        }else if (view == Q){
            showAnimation("Q");
        }else if (view == RR){
            showAnimation("R");
        }else if (view == Retweet){
            showAnimation("Retweet");
        }else if (view == S){
            showAnimation("S");
        }else if (view == ShapeTypes){
            showAnimation("ShapeTypes");
        }else if (view == Spider_Loader){
            showAnimation("Spider_Loader");
        }else if (view == SplitDimensions){
            showAnimation("SplitDimensions");
        }else if (view == StarWarsRey){
            showAnimation("StarWarsRey");
        }else if (view == Stroke){
            showAnimation("Stroke");
        }else if (view == T){
            showAnimation("T");
        }else if (view == TadahImage){
            showAnimation("TadahImage");
        }else if (view == TadahVideo){
            showAnimation("TadahVideo");
        }else if (view == TouchID){
            showAnimation("TouchID");
        }else if (view == TrackMattes){
            showAnimation("TrackMattes");
        }else if (view == TrimPaths){
            showAnimation("TrimPaths");
        }else if (view == TwitterHeart){
            showAnimation("TwitterHeart");
        }else if (view == U){
            showAnimation("U");
        }else if (view == V){
            showAnimation("V");
        }else if (view == VideoCamera){
            showAnimation("VideoCamera");
        }else if (view == W){
            showAnimation("W");
        }else if (view == Walkthrough){
            showAnimation("Walkthrough");
        }else if (view == WeAccept){
            showAnimation("WeAccept");
        }else if (view == X){
            showAnimation("X");
        }else if (view == Y){
            showAnimation("Y");
        }else if (view == Z){
            showAnimation("Z");
        }
    }
    private void showAnimation(String fileName){
        fileName = "lottie/"+fileName+".json";
        LottieComposition.Factory.fromAssetFileName(LottieTest.this, fileName, new OnCompositionLoadedListener() {
            @Override
            public void onCompositionLoaded(LottieComposition composition) {
                lottie_test.cancelAnimation();
                lottie_test.loop(false);
                lottie_test.setComposition(composition);
                lottie_test.playAnimation();
            }
        });
    }
}
