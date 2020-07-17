package com.just.test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.just.test.R;
import com.just.test.bean.TimerLineBean;
import com.just.test.bean.TimerLineDetailBean;

import java.util.List;

/**
 * 时光轴适配器
 */
public class StatusExpandAdapter extends BaseExpandableListAdapter {
    private LayoutInflater inflater = null;
    private List<TimerLineBean> groupList;

    /**
     * 构造方法
     *
     * @param context 上下文
     * @param group_list 填充的数据
     */
    public StatusExpandAdapter(Context context, List<TimerLineBean> group_list) {
        this.groupList = group_list;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * 返回一级Item总数
     */
    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    /**
     * 返回二级Item总数
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        if (groupList.get(groupPosition).getChild() == null) {
            return 0;
        } else {
            return groupList.get(groupPosition).getChild().size();
        }
    }

    /**
     * 获取一级Item内容
     */
    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    /**
     * 获取二级Item内容
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groupList.get(groupPosition).getChild().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,View convertView, ViewGroup parent) {
        GroupViewHolder holder = new GroupViewHolder();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_timer_line, null);
        }
        holder.groupName = (TextView) convertView.findViewById(R.id.txt_item_timerLine_date);
        holder.groupName.setText(groupList.get(groupPosition).getTimer());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder viewHolder;
        TimerLineDetailBean entity = (TimerLineDetailBean) getChild(groupPosition,childPosition);
        if (convertView != null) {
            viewHolder = (ChildViewHolder) convertView.getTag();
        } else {
            viewHolder = new ChildViewHolder();
            convertView = inflater.inflate(R.layout.item_timer_content, null);
            viewHolder.twoStatusTime = (TextView) convertView.findViewById(R.id.txt_item_timerContent);
        }
        viewHolder.twoStatusTime.setText(entity.getFinishTime());
        convertView.setTag(viewHolder);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private class GroupViewHolder {
        TextView groupName;
    }

    private class ChildViewHolder {
        private TextView twoStatusTime;
    }

}
