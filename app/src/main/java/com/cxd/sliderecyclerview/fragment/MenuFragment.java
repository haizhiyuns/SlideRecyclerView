package com.cxd.sliderecyclerview.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cxd.sliderecyclerview.R;
import com.cxd.sliderecyclerview.adapter.RecyclerMessageAdapter;
import com.cxd.sliderecyclerview.data.Message;
import com.cxd.sliderecyclerview.helper.RecyclerViewDivider;
import com.cxd.sliderecyclerview.view.SlideRecyclerView;

import java.util.ArrayList;

/**
 * Created by caixd on 2016/11/17.
 */

public class MenuFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_slide_menu, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int[] resLists = new int[]{R.drawable.cat3, R.drawable.cat4, R.drawable.dog1, R.drawable.dog2, R.drawable.girl1, R.drawable.girl2};

        ArrayList<Message> data = new ArrayList<>();
        for (int i = 0; i < resLists.length; i++) {
            Message msg = new Message(resLists[i], "name" + i, "msg" + i, i + 1);
            data.add(msg);
        }
        SlideRecyclerView recyclerView = (SlideRecyclerView) view.findViewById(R.id.menu_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        RecyclerMessageAdapter adapter = new RecyclerMessageAdapter(data);
        recyclerView.setHasFixedSize(true);//固定高度
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new RecyclerViewDivider(getContext(), LinearLayoutManager.HORIZONTAL));

        recyclerView.setOnItemClickListener(adapter);
    }
}
