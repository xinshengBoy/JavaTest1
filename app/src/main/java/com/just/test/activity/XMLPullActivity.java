package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.just.test.R;
import com.just.test.bean.Students;
import com.just.test.widget.MyActionBar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/21.
 */

public class XMLPullActivity extends Activity implements View.OnClickListener{

    private Button btn_saveXML,btn_readXML;
    private TextView txt_xmlresult;
    private static final String PATH = "/sdcard/zzh/XML/";
    private static final String NAMESPACE = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_xmlpull);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "倒计时和圆形进度条");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        btn_saveXML = (Button)findViewById(R.id.btn_saveXML);
        btn_readXML = (Button)findViewById(R.id.btn_readXML);
        txt_xmlresult = (TextView)findViewById(R.id.txt_xmlresult);

        btn_saveXML.setOnClickListener(this);
        btn_readXML.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btn_saveXML){
            List<Students> mList = new ArrayList<>();
            mList.add(new Students(1001,"张三","男",23,"湖南省"));
            mList.add(new Students(1002,"李四","女",25,"湖南省"));
            mList.add(new Students(1003,"王五","男",21,"湖南省"));
            mList.add(new Students(1004,"赵六","女",27,"湖南省"));
            mList.add(new Students(1005,"朱七","男",25,"湖南省"));
            mList.add(new Students(1006,"酒吧","男",29,"湖南省"));

            SaveXML(mList);
            Toast.makeText(XMLPullActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
        }else if (view == btn_readXML){
            List<Students> mlist = readXML();
            if (mlist != null && mlist.size() != 0) {
                txt_xmlresult.setText(mlist.toString());
            }
        }
    }

    private void SaveXML(List<Students> mList){
        XmlSerializer xs = Xml.newSerializer();
        File file = new File(Environment.getExternalStorageDirectory(),"student.xml");
        FileOutputStream os;
        try {
            os = new FileOutputStream(file);
            xs.setOutput(os,"UTF-8");//设置编码格式
            //开始文档
            xs.startDocument("UTF-8",true);//是否依赖于其他文档
            xs.startTag(NAMESPACE,"students");
            for (Students s : mList){
                xs.startTag(NAMESPACE,"student");

                xs.attribute(NAMESPACE,"id",String.valueOf(s.id));
                //name
                xs.startTag(NAMESPACE,"name");
                xs.text(s.name);
                xs.endTag(NAMESPACE,"name");
                //sex
                xs.startTag(NAMESPACE,"sex");
                xs.text(s.sex);
                xs.endTag(NAMESPACE,"sex");
                //age
                xs.startTag(NAMESPACE,"age");
                xs.text(String.valueOf(s.age));
                xs.endTag(NAMESPACE,"age");
                //address
                xs.startTag(NAMESPACE,"address");
                xs.text(s.address);
                xs.endTag(NAMESPACE,"address");

                xs.endTag(NAMESPACE,"student");
            }
            xs.endTag(NAMESPACE,"students");
            xs.endDocument();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Students> readXML(){
        List<Students> mList = new ArrayList<>();
        XmlPullParser xp = Xml.newPullParser();

        File file = new File(Environment.getExternalStorageDirectory(),"student.xml");
        FileInputStream in;

        try {
            in = new FileInputStream(file);
            xp.setInput(in,"UTF-8");
            //pull解析优点：节省内存，基于时间，根据时间类型判断解析的位置
            int type = xp.getEventType();
            Students s = null;
            //解析到文档结束位置
            while (type != XmlPullParser.END_DOCUMENT){
                //标签的开始
                if (type == XmlPullParser.START_TAG){
                    //是哪一个标签的开始
                    String tagName = xp.getName();
                    if ("student".equals(tagName)){
                        //获取id属性值
                        String id = xp.getAttributeValue(NAMESPACE,"id");
                        s = new Students();
                        s.id = Integer.parseInt(id);
                    }else if ("name".equals(tagName)){
                        //获取姓名属性值,xml中文本也是一个节点
                        String name = xp.nextText();
                        s.name = name;
                    }else if ("sex".equals(tagName)){
                        //获取性别属性值
                        String sex = xp.nextText();
                        s.sex = sex;
                    }else if ("age".equals(tagName)){
                        //获取年龄属性值
                        String age = xp.nextText();
                        s.age = Integer.parseInt(age);
                    }else if ("address".equals(tagName)){
                        //获取地址属性值
                        String address = xp.nextText();
                        s.address = address;

                        //所有属性赋值完毕添加到集合中
                        mList.add(s);
                    }
                }
                //下一个标签
                type = xp.next();
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mList;
    }
}
