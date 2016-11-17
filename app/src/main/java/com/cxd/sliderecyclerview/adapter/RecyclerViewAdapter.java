package com.cxd.sliderecyclerview.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cxd.sliderecyclerview.R;
import com.cxd.sliderecyclerview.helper.TouchHelperCallBack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Created by caixd on 2016/11/1.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>
        implements TouchHelperCallBack.OnMoveSwipeListener {

    private ArrayList<String> data;

    public RecyclerViewAdapter(ArrayList<String> data) {
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview, parent,
                false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.image.setImageResource(R.mipmap.ic_launcher);
        holder.text.setText(data.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(holder.itemView, holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Log.d("cxd", "onItemMove: " + fromPosition + "," + toPosition);
        //交换数据源位置
        Collections.swap(data, fromPosition, toPosition);
        //交换列表中数据位置
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemSwipe(int position) {
        Log.d("cxd", "onItemSwipe: " + position);
        //删除数据源中对应数据
        data.remove(position);
        //删除列表中对应位置
        notifyItemRemoved(position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements
            TouchHelperCallBack.OnStateChangedListener {

        private ImageView image;
        private TextView text;

        public MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.item_image);
            text = (TextView) itemView.findViewById(R.id.item_text);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.parseColor("#dddddd"));
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
