package com.perasia.picgirls.view;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.perasia.picgirls.R;
import com.perasia.picgirls.data.ImageData;
import com.perasia.picgirls.net.GetMMImgManager;
import com.perasia.picgirls.utils.CommonUtils;

import java.io.File;
import java.util.ArrayList;

public class ShowPicActivity extends AppCompatActivity {
    private static final String TAG = ShowPicActivity.class.getSimpleName();

    private Context mContext;

    private int mInitPos;
    private int mCurrentPos;
    private ArrayList<ImageData> mImageDatas;

    private ViewPager mViewPager;
    private Button mButton;

    private ArrayList<View> mViews;

    private GetMMImgManager mMmImgManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showpic);
        mContext = this;
        getData();

        init();
    }

    private void getData() {
        Intent intent = getIntent();

        if (intent == null) {
            return;
        }

        Bundle bundle = intent.getExtras();

        if (bundle == null) {
            return;
        }

        mInitPos = bundle.getInt(PageFragment.SHOW_PIC_POS, 0);
        mImageDatas = bundle.getParcelableArrayList(PageFragment.SHOW_PIC);

        mCurrentPos = mInitPos;

        Log.e(TAG, "pos=" + mInitPos);
        if (mImageDatas != null) {
            for (int i = 0; i < mImageDatas.size(); ++i) {
                Log.e(TAG, "image=" + mImageDatas.get(i).getUrl());
            }
        }

    }

    private void init() {
        mMmImgManager = new GetMMImgManager();

        mButton = (Button) findViewById(R.id.detail_button);
        mViewPager = (ViewPager) findViewById(R.id.detail_viewpager);
        mViews = new ArrayList<>();

        if (mImageDatas != null) {
            for (int i = 0; i < mImageDatas.size(); ++i) {
                ImageView imageView = new ImageView(mContext);
                Glide.with(mContext).load(mImageDatas.get(i).getUrl())
                        .placeholder(R.mipmap.pictures_no).error(R.mipmap.pictures_no).into(imageView);
                mViews.add(imageView);
            }

            DetailPicViewPagerAdapter adapter = new DetailPicViewPagerAdapter(mViews);
            mViewPager.setAdapter(adapter);

            mViewPager.setCurrentItem(mInitPos);
        }

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = System.currentTimeMillis() + ".jpg";

                String path = getResources().getString(R.string.pic_save)
                        + CommonUtils.getPicFileBasePath(mContext) + File.separator + name;

                Toast.makeText(mContext, path, Toast.LENGTH_SHORT).show();

                mMmImgManager.downloadMMPic(mContext, mImageDatas.get(mCurrentPos).getUrl(), name);
            }
        });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPos = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private class DetailPicViewPagerAdapter extends PagerAdapter {
        private ArrayList<View> views;

        public DetailPicViewPagerAdapter(ArrayList<View> views) {
            this.views = views;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position));
            return views.get(position);
        }

        @Override
        public int getCount() {
            return views == null ? 0 : views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }


    }
}
