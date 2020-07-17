package com.just.test.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.just.test.R;
import com.just.test.widget.MyActionBar;

import net.lemonsoft.lemonhello.LemonHello;
import net.lemonsoft.lemonhello.LemonHelloAction;
import net.lemonsoft.lemonhello.LemonHelloInfo;
import net.lemonsoft.lemonhello.LemonHelloView;
import net.lemonsoft.lemonhello.interfaces.LemonHelloActionDelegate;

/**开源对话框
 * https://github.com/1em0nsOft/LemonHello4Android
 * Created by Administrator on 2017/3/17.
 */

public class OpenningDialog extends Activity implements View.OnClickListener{

    private Button success,error,warning,information;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_openningdialog);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "笑话大全");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        success = (Button)findViewById(R.id.btn_openningdialog_success);
        error = (Button)findViewById(R.id.btn_openningdialog_error);
        warning = (Button)findViewById(R.id.btn_openningdialog_warning);
        information = (Button)findViewById(R.id.btn_openningdialog_information);
        information = (Button)findViewById(R.id.btn_openningdialog_information);

        success.setOnClickListener(this);
        error.setOnClickListener(this);
        warning.setOnClickListener(this);
        information.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == success){
            getSuccess(OpenningDialog.this,"成功","数据获取成功","知道了");
        }else if (view == error){
            getError(OpenningDialog.this,"错误","数据解析出错","好吧");
        }else if (view == warning){
            getWarning(OpenningDialog.this,"警告","内存不足","懂了");
        }else if (view == information){
            getInformation(OpenningDialog.this,"消息","您有新消息，请注意查收","好的");
        }
    }

    /**
     * 成功对话框
     * @param context
     * @param title
     * @param content
     * @param title2
     */
    private void getSuccess(Context context,String title, String content, String title2){
        LemonHello.getSuccessHello(title,content).addAction(new LemonHelloAction(title2, new LemonHelloActionDelegate() {
            @Override
            public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                lemonHelloView.hide();//对话框隐藏或消失
            }
        })).show(context);
    }

    /**
     * 错误对话框
     * @param context
     * @param title
     * @param content
     * @param title2
     */
    private void getError(Context context,String title, String content, String title2){
        LemonHello.getErrorHello(title,content).addAction(new LemonHelloAction(title2, new LemonHelloActionDelegate() {
            @Override
            public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                lemonHelloView.hide();//对话框隐藏或消失
            }
        })).show(context);
    }

    /**
     * 警告对话框
     * @param context
     * @param title
     * @param content
     * @param title2
     */
    private void getWarning(Context context,String title, String content, String title2){
        LemonHello.getWarningHello(title,content).addAction(new LemonHelloAction(title2, new LemonHelloActionDelegate() {
            @Override
            public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                lemonHelloView.hide();//对话框隐藏或消失
            }
        })).show(context);
    }

    /**
     * 信息对话框
     * @param context
     * @param title
     * @param content
     * @param title2
     */
    private void getInformation(Context context,String title, String content, String title2){
        LemonHello.getInformationHello(title,content).addAction(new LemonHelloAction(title2, new LemonHelloActionDelegate() {
            @Override
            public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                lemonHelloView.hide();//对话框隐藏或消失
            }
        })).show(context);
    }
}
