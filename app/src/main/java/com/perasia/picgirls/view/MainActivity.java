package com.perasia.picgirls.view;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.perasia.picgirls.Config;
import com.perasia.picgirls.R;
import com.perasia.picgirls.adapter.SimpleFragmentPagerAdapter;
import com.perasia.picgirls.utils.CommonUtils;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private Context mContext;

    private SimpleFragmentPagerAdapter mPagerAdapter;

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private ListView mDrawerListView;

    private Map<Integer, String> mTabMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        toolbar.setNavigationIcon(R.drawable.drawer_shadow);

        initTabState();

        init();
    }

    private void init() {
        mViewPager = (ViewPager) findViewById(R.id.container_viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.drawer_shadow, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        String[] tags = getResources().getStringArray(R.array.fragment_item_tag);

        mPagerAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), tags);
        mViewPager.setAdapter(mPagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        Log.e(TAG,"mypath="+CommonUtils.getPicFileBasePath(mContext));

    }

    private void initTabState() {
        mTabMap = new HashMap<>();
        mTabMap.put(1, Config.DB_BREAST);
        mTabMap.put(2, Config.DB_BUTT);
        mTabMap.put(3, Config.DB_SILK);
        mTabMap.put(4, Config.DB_LEG);
        mTabMap.put(5, Config.DB_FACE);
        mTabMap.put(6, Config.DB_SOME);
        mTabMap.put(7, Config.DB_RANK);
    }

    public Map<Integer, String> getTabState() {
        return mTabMap;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (mActionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
