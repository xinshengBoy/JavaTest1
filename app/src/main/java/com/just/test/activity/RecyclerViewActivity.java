package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.just.test.R;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView
 * Created by admin on 2017/4/5.
 */

public class RecyclerViewActivity extends Activity {

    private List<Bundle> mList = new ArrayList<>();
    private RecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recyclerview);

        initData();

        RecyclerView recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        recycler_view.setAdapter(adapter = new RecyclerViewAdapter());
    }

    private void initData(){
        for (int i=1;i<40;i++){
            Bundle bundle = new Bundle();
            bundle.putString("name","人民的名义第"+i+"集");
            bundle.putString("author","最高人民检察院反贪总局");
            mList.add(bundle);
        }
    }

    private class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{

        @Override
        public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(RecyclerViewActivity.this).inflate(R.layout.item_recyclerview,parent,false));
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerViewAdapter.MyViewHolder holder, int position) {
            holder.name.setText(mList.get(position).getString("name"));
            holder.author.setText(mList.get(position).getString("author"));
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView name,author;
            public MyViewHolder(View itemView) {
                super(itemView);
                name = (TextView)itemView.findViewById(R.id.txt_recycler_name);
                author = (TextView)itemView.findViewById(R.id.txt_recycler_author);
            }
        }

    }
}
