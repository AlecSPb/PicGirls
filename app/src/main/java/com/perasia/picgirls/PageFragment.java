package com.perasia.picgirls;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.perasia.picgirls.adapter.MyRecycleViewAdapter;
import com.perasia.picgirls.net.GetMMImgManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PageFragment extends Fragment {
    private static final String TAG = PageFragment.class.getSimpleName();

    public static final String ARG_PAGE = "ARG_PAGE";

    private int mFragmentPage;

    private int mReqPage;

    private String mBaseUrl;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshLayout;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private MyRecycleViewAdapter myRecycleViewAdapter;

    private GetMMImgManager mMmImgManager;

    private int[] mLastVisibleItem;

    public PageFragment() {

    }

    public static PageFragment newInstance(int page) {
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_PAGE, page);
        PageFragment f = new PageFragment();
        f.setArguments(bundle);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mFragmentPage = args.getInt(ARG_PAGE);
        }

        mReqPage = 1;
        mMmImgManager = new GetMMImgManager();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.frag_recycleview);
        mRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.frag_refresh_layout);

        initView();
        return rootView;
    }

    private void initView() {
        mRefreshLayout.setColorSchemeColors(R.color.color_refresh_one, R.color.color_refresh_two, R.color.color_refresh_three);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefreshLayout.setRefreshing(false);
            }
        });

        mRefreshLayout.setProgressViewOffset(false, 0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mLastVisibleItem == null || myRecycleViewAdapter == null) {
                    return;
                }


                if (newState == RecyclerView.SCROLL_STATE_IDLE && isLastPage()) {
                    mRefreshLayout.setRefreshing(true);
                    mReqPage++;// next page pic
                    mMmImgManager.loadMMPic(mBaseUrl + mReqPage, new GetMMImgManager.OnGetMMListener() {
                        @Override
                        public void onSuccess(List<String> lists) {
                            myRecycleViewAdapter.appendToList(lists);
                        }
                    });
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mLastVisibleItem = mStaggeredGridLayoutManager.findLastVisibleItemPositions(null);
            }
        });

        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        MainActivity parent = (MainActivity) getActivity();
        Map<Integer, String> tabState = parent.getTabState();
        mBaseUrl = tabState.get(mFragmentPage);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (isVisible()) {
            mReqPage = 1;
            mMmImgManager.loadMMPic(mBaseUrl + mReqPage, new GetMMImgManager.OnGetMMListener() {
                @Override
                public void onSuccess(List<String> lists) {
                    doPostInitResult(lists);
                }
            });
        }
    }

    private boolean isLastPage() {
        if (mLastVisibleItem == null || mLastVisibleItem.length < 2) {
            return false;
        }

        if (mLastVisibleItem[0] + 1 == myRecycleViewAdapter.getItemCount()) {
            return true;
        }

        if (mLastVisibleItem[1] + 1 == myRecycleViewAdapter.getItemCount()) {
            return true;
        }

        return false;
    }

    private void doPostInitResult(List<String> lists) {
        mRefreshLayout.setRefreshing(false);

        if (lists == null) {
            lists = new ArrayList<>();
        }

        myRecycleViewAdapter = new MyRecycleViewAdapter(getActivity(), lists);
        mRecyclerView.setAdapter(myRecycleViewAdapter);

        myRecycleViewAdapter.setOnItemActionListener(new MyRecycleViewAdapter.OnItemActionListener() {
            @Override
            public void onItemClickListener(View v, int pos, String url) {
                Toast.makeText(getActivity(), "pos=" + pos, Toast.LENGTH_SHORT).show();
            }
        });
    }


}
