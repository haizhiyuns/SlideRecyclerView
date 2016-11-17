package com.cxd.sliderecyclerview.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cxd.sliderecyclerview.R;
import com.cxd.sliderecyclerview.adapter.RecyclerViewAdapter;
import com.cxd.sliderecyclerview.helper.RecyclerViewDivider;
import com.cxd.sliderecyclerview.helper.TouchHelperCallBack;

import java.util.ArrayList;

/**
 * Created by caixd on 2016/10/31.
 */

public class LinearFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i <= 20; i++) {
            data.add(String.valueOf(i));
        }
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.view_recycler);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(data);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);//固定高度
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new RecyclerViewDivider(getContext(), LinearLayoutManager
                .HORIZONTAL));

        ItemTouchHelper touchHelper = new ItemTouchHelper(new TouchHelperCallBack(adapter));
        touchHelper.attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }
        });

    }
}
