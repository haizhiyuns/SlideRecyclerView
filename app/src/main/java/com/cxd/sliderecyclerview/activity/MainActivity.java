package com.cxd.sliderecyclerview.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.cxd.sliderecyclerview.R;
import com.cxd.sliderecyclerview.fragment.GridFragment;
import com.cxd.sliderecyclerview.fragment.LinearFragment;
import com.cxd.sliderecyclerview.fragment.MenuFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.main_btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new LinearFragment(), "");
            }
        });
        findViewById(R.id.main_btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new GridFragment(), "");
            }
        });
        findViewById(R.id.main_btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new MenuFragment(), "");
            }
        });

    }


    public void replaceFragment(Fragment fragment, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (TextUtils.isEmpty(tag)) {
            transaction.replace(R.id.main_content, fragment);
        } else {
            transaction.replace(R.id.main_content, fragment, tag);
            transaction.addToBackStack(tag);
        }
        transaction.commitAllowingStateLoss();
    }
}
