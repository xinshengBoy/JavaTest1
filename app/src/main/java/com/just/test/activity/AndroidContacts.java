package com.just.test.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.bean.Contact;
import com.just.test.tools.CommonAdapter;
import com.just.test.tools.ViewHolder;
import com.just.test.widget.MyActionBar;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取手机联系人列表
 * Created by Administrator on 2017/2/6.
 */

public class AndroidContacts extends Activity {

    private ListView listview_androidContact;
    private static final String[] PHONE_PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER
    };
    private static final int PHONE_NAME_INDEX = 0;
    private static final int PHONE_NUMBER_INDEX = 1;
    private List<Contact> mList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_androidcontacts);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "获取手机联系人列表");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        getContactInfo();

        listview_androidContact = (ListView)findViewById(R.id.listview_androidContact);
        ContactsAdapter adapter = new ContactsAdapter(AndroidContacts.this,mList,R.layout.item_androidcontact);
        listview_androidContact.setAdapter(adapter);
        listview_androidContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent phoneIntent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + mList.get(i).getNumber()));
                startActivity(phoneIntent);
            }
        });
    }

    private void getContactInfo(){
        ContentResolver resolver = getContentResolver();
        //获取手机联系人
        Cursor cursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,PHONE_PROJECTION,null,null,null);
        if (cursor != null){
            while (cursor.moveToNext()){
                String number = cursor.getString(PHONE_NUMBER_INDEX);
                if (TextUtils.isEmpty(number)){
                    continue;
                }
                String name = cursor.getString(PHONE_NAME_INDEX);

                Contact contact = new Contact();
                contact.setName(name);
                contact.setNumber(number);
                mList.add(contact);
            }
        }
    }

    public class ContactsAdapter extends CommonAdapter<Contact>{

        public ContactsAdapter(Context context, List<Contact> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, Contact item) {
            TextView simple = helper.getView(R.id.txt_contactSimple);
            TextView name = helper.getView(R.id.txt_contactName);
            TextView number = helper.getView(R.id.txt_contactNumber);

            String names = item.getName();
            simple.setText(names.substring(names.length()-1,names.length()));
            name.setText(names);
            number.setText(item.getNumber());
        }
    }
}
