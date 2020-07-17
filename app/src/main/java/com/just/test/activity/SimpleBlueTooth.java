package com.just.test.activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.bean.BlueToothBean;
import com.just.test.tools.CommonAdapter;
import com.just.test.tools.ViewHolder;
import com.just.test.widget.MyActionBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 简单蓝牙运用
 * Created by admin on 2017/5/16.
 */

public class SimpleBlueTooth extends Activity {

    private int REQUEST_ENABLE = 1;
    private TextView result;
    private TextView txt_bluetooth;
    private ListView listview_bluetooth;
    private List<BlueToothBean> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_simple_bluetooth);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "简单蓝牙运用");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);


        txt_bluetooth = (TextView)findViewById(R.id.txt_bluetooth);
        listview_bluetooth = (ListView)findViewById(R.id.listview_bluetooth);

        //获取蓝牙适配器
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        //开启蓝牙
        if (!adapter.isEnabled()){
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent,REQUEST_ENABLE);
        }else {
            String address = adapter.getAddress();
            String name = adapter.getName();
            int mode = adapter.getScanMode();
            int state = adapter.getState();
            txt_bluetooth.setText("地址："+address+"-----名称："+name+"-----模式："+mode+"-----状态"+state);

            Set<BluetoothDevice> devices = adapter.getBondedDevices();//获取已配对蓝牙设备
            for (BluetoothDevice info : devices){
                BlueToothBean bean = new BlueToothBean();
                bean.setName(info.getName());
                bean.setAddress(info.getAddress());
                bean.setState(info.getBondState());
                bean.setType(info.getType());
                mList.add(bean);
            }

            BlueToothAdapter blueToothAdapter = new BlueToothAdapter(SimpleBlueTooth.this,mList,R.layout.item_bluetooth);
            listview_bluetooth.setAdapter(blueToothAdapter);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE && resultCode == RESULT_OK){

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private class BlueToothAdapter extends CommonAdapter<BlueToothBean>{

        public BlueToothAdapter(Context context, List<BlueToothBean> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, BlueToothBean item) {
            TextView name = helper.getView(R.id.item_bluetooth_name);
            TextView address = helper.getView(R.id.item_bluetooth_address);
            TextView state = helper.getView(R.id.item_bluetooth_state);
            TextView type = helper.getView(R.id.item_bluetooth_type);

            name.setText(item.getName());
            address.setText(item.getAddress());
            state.setText(item.getState()+"");
            type.setText(item.getType()+"");
        }
    }
}
