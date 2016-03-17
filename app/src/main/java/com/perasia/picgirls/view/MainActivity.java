package com.perasia.picgirls.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

import com.perasia.picgirls.Config;
import com.perasia.picgirls.R;
import com.perasia.picgirls.adapter.SimpleFragmentPagerAdapter;
import com.perasia.picgirls.utils.AdUtil;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.service.XGPushService;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private Context mContext;

    private SimpleFragmentPagerAdapter mPagerAdapter;

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    private WebView mAdWebView;

//    private DrawerLayout mDrawerLayout;
//    private ActionBarDrawerToggle mActionBarDrawerToggle;
//    private ListView mDrawerListView;

    private Map<Integer, String> mTabMap;

    private int mCount;
    private long mLastTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(null);

        initTabState();

        init();

        txXinge();
    }

    private void init() {
        mViewPager = (ViewPager) findViewById(R.id.container_viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);

        mAdWebView = (WebView) findViewById(R.id.ad_banner_wv);

        AdUtil.setAds(mContext, mAdWebView);

//        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
//        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

//        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
//                R.drawable.drawer_shadow, R.string.drawer_open, R.string.drawer_close) {
//            @Override
//            public void onDrawerClosed(View drawerView) {
//                invalidateOptionsMenu();
//            }
//
//            @Override
//            public void onDrawerOpened(View drawerView) {
//                invalidateOptionsMenu();
//            }
//        };
//
//        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }


        String[] tags = getResources().getStringArray(R.array.fragment_item_tag);

        mPagerAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), tags);
        mViewPager.setAdapter(mPagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
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

    private void txXinge() {
        // 开启logcat输出，方便debug，发布时请关闭
        XGPushConfig.enableDebug(this, false);
// 如果需要知道注册是否成功，请使用registerPush(getApplicationContext(), XGIOperateCallback)带callback版本
// 如果需要绑定账号，请使用registerPush(getApplicationContext(),account)版本
// 具体可参考详细的开发指南
// 传递的参数为ApplicationContext
        Context context = getApplicationContext();
        XGPushManager.registerPush(context);

// 2.36（不包括）之前的版本需要调用以下2行代码
        Intent service = new Intent(context, XGPushService.class);
        context.startService(service);

// 其它常用的API：
// 绑定账号（别名）注册：registerPush(context,account)或registerPush(context,account, XGIOperateCallback)，其中account为APP账号，可以为任意字符串（qq、openid或任意第三方），业务方一定要注意终端与后台保持一致。
// 取消绑定账号（别名）：registerPush(context,"*")，即account="*"为取消绑定，解绑后，该针对该账号的推送将失效
// 反注册（不再接收消息）：unregisterPush(context)
// 设置标签：setTag(context, tagName)
// 删除标签：deleteTag(context, tagName)
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdWebView.destroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
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

//        if (mActionBarDrawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    public Map<Integer, String> getTabState() {
        return mTabMap;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            long curTime = System.currentTimeMillis();
            if (mLastTime + 2000 > curTime) {
                mCount += 1;
            } else {
                mCount = 1;
            }
            mLastTime = curTime;

            if (mCount < 2) {
                Toast.makeText(mContext, R.string.main_back_exit, Toast.LENGTH_SHORT).show();
                return false;
            }

            if (mCount >= 2) {
                return super.onKeyDown(keyCode, event);
            }
        }

        return false;
    }
}
