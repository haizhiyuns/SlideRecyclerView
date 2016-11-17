package com.cxd.sliderecyclerview.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cxd.sliderecyclerview.R;

/**
 * Created by caixd on 2016/11/1.
 */

public class SlideViewHolder extends RecyclerView.ViewHolder {

    public CircleImageView image;
    public TextView name;
    public TextView msg;
    public Button topBtn;
    public Button deleteBtn;
    public LinearLayout menuLayout;

    public SlideViewHolder(View itemView) {
        super(itemView);
        image = (CircleImageView) itemView.findViewById(R.id.item_message_image);
        name = (TextView) itemView.findViewById(R.id.item_message_name);
        msg = (TextView) itemView.findViewById(R.id.item_message_lastMsg);
        topBtn = (Button) itemView.findViewById(R.id.item_message_top);
        deleteBtn = (Button) itemView.findViewById(R.id.item_message_delete);
        menuLayout = (LinearLayout) itemView.findViewById(R.id.layout_menu);
    }
}
