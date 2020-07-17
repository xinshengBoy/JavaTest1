package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.custom.ExpandTextView;
import com.just.test.widget.MyActionBar;

/**
 * 文字展开收起
 * Created by admin on 2017/5/9.
 */

public class ExpandTextTest extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_expandtext);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "文字展开收起");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        ExpandTextView txt_expandTest = (ExpandTextView) findViewById(R.id.txt_expandTest);
        txt_expandTest.setText("最近在公司忙着做新产品，UI准备设计一个比较炫酷的动画，于是乎我就在github上面关望了一些好看实用的动画库，前两天也忙完了，现在来分享给大家。" +
                "以上动画看起来是不是很爽啊，哈哈，这个就是现在比较火的开源框架Lottie动画，动画的业务全部在一个json 文件中，这个json文件呢不用程序员考虑了，直接UI通过AE可以编辑出来，这个动画性能也很不错的，这里是github上的项目地址：https://github.com/airbnb/lottie-Android\n" +
                "\n" +
                "现在来说下简单实用吧，用起来真的很简单，\n" +
                "\n" +
                "第一步、\n" +
                "\n" +
                "把Lottie的module引入项目中，当然也可以把module里面的代码拷贝出来，如图：最近Google开源了一个叫flex-box的库，它的思路是参照的CSS的Flex布局设计的，所以属性基本都是和CSS上的Flex布局保持一致，但因为是两个不同的平台，所以减少了几个不适用于Android的属性，新增了几个属性，具体我们下面会说到。\n" +
                "关于CSS的Flex布局\n" +
                "\n" +
                "在了解Google开源的flex-box，我觉得有必要先了解下CSS的Flex布局，这有助于我们理解那些属性定义。关于这方面的知识，可以阅读这篇博客。下面我们只是简要的了解下，具体属性我们放到讲解Google的flex-box中去说明。\n" +
                "\n" +
                "//这段文字说明，来源于上面说到的博客\n" +
                "采用Flex布局的元素，称为Flex容器（flex container），简称\"容器\"。它的所有子元素自动成为容器成员，称为Flex项目（flex item），简称\"项目\"。\n" +
                "容器默认存在两根轴：水平的主轴（main axis）和垂直的交叉轴（cross axis）。主轴的开始位置（与边框的交叉点）叫做main start，结束位置叫做main end；交叉轴的开始位置叫做cross start，结束位置叫做cross end。\n" +
                "项目默认沿主轴排列。单个项目占据的主轴空间叫做main size，占据的交叉轴空间叫做cross s注意：以下文中用到的flexbox-layout指的是Google开源的库，Flex指的是CSS中的Flex布局。\n" +
                "\n" +
                "首先我们需要知道的是，flexbox-layout的基本概念和Flex是一样的，最外面的这层布局称为容器，在flexbox-layout指的是com.google.android.flexbox.FlexboxLayout，里面的子元素称为项目。容器默认存在两条轴，其中水平(这里是就上面的概念图而言，因为主轴的方向是可以指定的)的这条轴称为主轴(个人理解，可以存在多条，具体原因后面讲)，垂直的这条称为交叉轴。其他属性比如start，end等等也是一样的，具体可以看上面的基本概念图。\n" +
                "\n" +
                "下面我们着重讲下flexbox-layout支持的属性，因为它既支持容器属性，也支持项目属性，所以我们分成两个篇幅来说明。");
    }
}
