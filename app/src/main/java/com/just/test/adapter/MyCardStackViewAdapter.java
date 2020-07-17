package com.just.test.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.just.test.R;
import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.StackAdapter;

/**
 * Created by admin on 2017/4/24.
 */

public class MyCardStackViewAdapter extends StackAdapter<Integer> {

    public MyCardStackViewAdapter(Context context) {
        super(context);
    }

    @Override
    public void bindView(Integer data, int position, CardStackView.ViewHolder holder) {
        if (holder instanceof ColorItemLargeHeadViewHoder){
            ColorItemLargeHeadViewHoder h = (ColorItemLargeHeadViewHoder) holder;
            h.onBind(data,position);
        }
        if (holder instanceof ColorItemLargeWithNoHeaderViewHoder){
            ColorItemLargeWithNoHeaderViewHoder h = (ColorItemLargeWithNoHeaderViewHoder) holder;
            h.onBind(data,position);
        }
        if (holder instanceof  ColorItemViewHolder){
            ColorItemViewHolder h = (ColorItemViewHolder) holder;
            h.onBind(data,position);
        }
    }

    @Override
    protected CardStackView.ViewHolder onCreateView(ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case R.layout.item_cardstack_header:
                view = getLayoutInflater().inflate(R.layout.item_cardstack_header,parent,false);
                return new ColorItemLargeHeadViewHoder(view);
            case R.layout.item_cardstack_nohearder:
                view = getLayoutInflater().inflate(R.layout.item_cardstack_nohearder,parent,false);
                return new ColorItemLargeWithNoHeaderViewHoder(view);
            default:
                view = getLayoutInflater().inflate(R.layout.item_cardstack,parent,false);
                return new ColorItemViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 6){
            return R.layout.item_cardstack_header;
        }else if (position == 10){
            return R.layout.item_cardstack_nohearder;
        }else {
            return R.layout.item_cardstack;
        }
    }

    static class ColorItemViewHolder extends CardStackView.ViewHolder{
        View mLayout,mContent;
        TextView mTitle;
        public ColorItemViewHolder(View view) {
            super(view);
            mLayout = view.findViewById(R.id.frame_cardstack_item);
            mContent = view.findViewById(R.id.container_list_content);
            mTitle = (TextView)view.findViewById(R.id.txt_cardview_item);
        }

        @Override
        public void onItemExpand(boolean b) {
            mContent.setVisibility(b ? View.VISIBLE : View.GONE);
        }

        public void onBind(Integer data,int position){
            mLayout.getBackground().setColorFilter(ContextCompat.getColor(getContext(),data),PorterDuff.Mode.SRC_IN);
            mTitle.setText(String.valueOf(position));
        }
    }

    static class ColorItemLargeWithNoHeaderViewHoder extends CardStackView.ViewHolder{

        View mLayout;
        TextView mTitle;
        public ColorItemLargeWithNoHeaderViewHoder(View view) {
            super(view);
            mLayout = view.findViewById(R.id.frame_cardstack_item);
            mTitle = (TextView)view.findViewById(R.id.txt_cardview_item);
        }

        @Override
        public void onItemExpand(boolean b) {

        }

        public void onBind(Integer data,int position){
            mLayout.getBackground().setColorFilter(ContextCompat.getColor(getContext(),data),PorterDuff.Mode.SRC_IN);
            mTitle.setText(String.valueOf(position));
        }
    }

    static class ColorItemLargeHeadViewHoder extends CardStackView.ViewHolder{
        View mLayout,mContent;
        TextView mTitle;
        public ColorItemLargeHeadViewHoder(View view) {
            super(view);
            mLayout = view.findViewById(R.id.frame_cardstack_item);
            mContent = view.findViewById(R.id.container_list_content);
            mTitle = (TextView) view.findViewById(R.id.txt_cardview_item);
        }

        @Override
        public void onItemExpand(boolean b) {
            mContent.setVisibility(b ? View.VISIBLE : View.GONE);
        }

        @Override
        protected void onAnimationStateChange(int state, boolean willBeSelect) {
            super.onAnimationStateChange(state, willBeSelect);
            if (state == CardStackView.ANIMATION_STATE_START && willBeSelect){
                onItemExpand(true);
            }
            if (state == CardStackView.ANIMATION_STATE_END && !willBeSelect){
                onItemExpand(false);
            }
        }

        public void onBind(Integer data,int position){
            mLayout.getBackground().setColorFilter(ContextCompat.getColor(getContext(),data), PorterDuff.Mode.SRC_IN);
            mTitle.setText(String.valueOf(position));

            itemView.findViewById(R.id.txt_cardview_content).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((CardStackView)itemView.getParent()).performItemClick(ColorItemLargeHeadViewHoder.this);
                }
            });
        }
    }
}
