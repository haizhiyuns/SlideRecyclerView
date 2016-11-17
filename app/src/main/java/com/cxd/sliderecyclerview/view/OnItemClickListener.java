package com.cxd.sliderecyclerview.view;

import android.view.View;

/**
 * Created by caixd on 2016/11/2.
 */

public interface OnItemClickListener {
    void onItemMove(int fromPosition, int toPosition);

    void onItemDelete(int position);

    void onItemClick(View view, int position);
}
