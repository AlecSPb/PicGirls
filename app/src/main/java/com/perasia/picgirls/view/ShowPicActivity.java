package com.perasia.picgirls.view;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.perasia.picgirls.R;

public class ShowPicActivity extends AppCompatActivity {
    private static final String TAG = ShowPicActivity.class.getSimpleName();

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showpic);
        mContext = this;
    }
}
