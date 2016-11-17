package com.cxd.sliderecyclerview.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cxd.sliderecyclerview.R;
import com.cxd.sliderecyclerview.data.Message;
import com.cxd.sliderecyclerview.view.OnItemClickListener;
import com.cxd.sliderecyclerview.view.SlideViewHolder;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by caixd on 2016/11/1.
 */

public class RecyclerMessageAdapter extends RecyclerView.Adapter<SlideViewHolder> implements OnItemClickListener {

    private ArrayList<Message> data;

    public RecyclerMessageAdapter(ArrayList<Message> data) {
        this.data = data;
    }

    @Override
    public SlideViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new SlideViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SlideViewHolder holder, int position) {
        Message message = data.get(position);
        holder.image.setImageSrc(message.getResId());
        holder.name.setText(message.getName());
        holder.msg.setText(message.getLastMsg());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Log.d("cxd", "onItemMove: " + fromPosition + "," + toPosition);
        Collections.swap(data, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDelete(int position) {
        Log.d("cxd", "onItemDelete: " + position);
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.d("cxd", "onItemClick: " + position);
    }
}
