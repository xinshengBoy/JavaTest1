package com.just.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.just.test.R;
import com.just.test.bean.Hotals;
import com.just.test.widget.MyActionBar;
import com.yalantis.beamazingtoday.interfaces.AnimationType;
import com.yalantis.beamazingtoday.interfaces.BatModel;
import com.yalantis.beamazingtoday.listeners.BatListener;
import com.yalantis.beamazingtoday.listeners.OnItemClickListener;
import com.yalantis.beamazingtoday.listeners.OnOutsideClickedListener;
import com.yalantis.beamazingtoday.ui.adapter.BatAdapter;
import com.yalantis.beamazingtoday.ui.animator.BatItemAnimator;
import com.yalantis.beamazingtoday.ui.callback.BatCallback;
import com.yalantis.beamazingtoday.ui.widget.BatRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 删除添加排序  todolist
 * https://github.com/Yalantis/ToDoList
 * compile 'com.github.yalantis:todolist:v1.0.1'
 * Created by admin on 2017/6/23.
 */

public class ToDoListTest extends Activity implements BatListener,OnItemClickListener,OnOutsideClickedListener{

    private BatRecyclerView view_batRecycler;
    private BatItemAnimator mAnimotor;
    private BatAdapter mAdapter;
    private List<BatModel> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_todolist);

        //// TODO: 2016/12/21 actionbar
        LinearLayout headerLayout = (LinearLayout)findViewById(R.id.headerLayout);
        Bundle bundle = new Bundle();
        bundle.putBoolean("back", true);
        bundle.putString("leftText", null);
        bundle.putString("title", "删除添加排序");
        bundle.putBoolean("rightImage", false);
        bundle.putString("rightText", null);
        MyActionBar.actionbar(this,headerLayout,bundle);

        initData();
        initView();
    }

    private void initView() {
        view_batRecycler = (BatRecyclerView)findViewById(R.id.view_batRecycler);
        LinearLayout todolist_root = (LinearLayout) findViewById(R.id.todolist_root);
        mAnimotor = new BatItemAnimator();

        view_batRecycler.getView().setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new BatAdapter(mList,ToDoListTest.this,mAnimotor);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnOutsideClickListener(this);
        view_batRecycler.getView().setAdapter(mAdapter);

        ItemTouchHelper helper = new ItemTouchHelper(new BatCallback(this));
        helper.attachToRecyclerView(view_batRecycler.getView());
        view_batRecycler.getView().setItemAnimator(mAnimotor);
        view_batRecycler.setAddItemListener(this);

        todolist_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view_batRecycler.revertAnimation();
            }
        });
    }

    private void initData(){
        mList.add(new Hotals("first"));
        mList.add(new Hotals("second"));
        mList.add(new Hotals("third"));
        mList.add(new Hotals("fourth"));
        mList.add(new Hotals("fifth"));
        mList.add(new Hotals("sixth"));
        mList.add(new Hotals("seventh"));
        mList.add(new Hotals("eighth"));
        mList.add(new Hotals("ninth"));
        mList.add(new Hotals("tenth"));
    }

    @Override
    public void add(String s) {
        mList.add(0,new Hotals(s));
        mAdapter.notify(AnimationType.ADD,0);
    }

    @Override
    public void delete(int i) {
        mList.remove(i);
        mAdapter.notify(AnimationType.REMOVE,i);
    }

    @Override
    public void move(int from, int to) {
        if (from >= 0 && to >= 0){
            mAnimotor.setPosition(to);
            BatModel model = mList.get(from);
            mList.remove(model);
            mList.add(to,model);
            mAdapter.notify(AnimationType.MOVE,from,to);

            if (from == 0 || to == 0){
                view_batRecycler.getView().scrollToPosition(Math.min(from,to));
            }
        }
    }

    @Override
    public void onClick(BatModel batModel, int position) {
        Toast.makeText(this, batModel.getText(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOutsideClicked() {
        view_batRecycler.revertAnimation();
    }
}
