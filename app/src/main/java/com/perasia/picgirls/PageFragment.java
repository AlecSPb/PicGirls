package com.perasia.picgirls;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.perasia.picgirls.adapter.MyRecycleViewAdapter;
import com.perasia.picgirls.net.GetMMImgTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PageFragment extends Fragment {
    private static final String TAG = PageFragment.class.getSimpleName();

    public static final String ARG_PAGE = "ARG_PAGE";

    private int mFragmentPage;

    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private MyRecycleViewAdapter myRecycleViewAdapter;

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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.frag_recycleview);

        initView();
        return rootView;
    }

    private void initView() {
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        GetMMImgTask task = new GetMMImgTask();

        MainActivity parent = (MainActivity) getActivity();
        Map<Integer, String> tabState = parent.getTabState();
        String baseUrl = tabState.get(mFragmentPage);

        task.execute(baseUrl + 1);
        task.setOnExecuteCallback(new GetMMImgTask.OnExecuteCallback() {
            @Override
            public Void onPostResult(List<String> lists) {
                // TODO: 16/3/10  delete
                if (lists != null) {
                    for (int i = 0; i < lists.size(); ++i) {
                        Log.e(TAG, "url=" + lists.get(i));
                    }
                }


                doPostResult(lists);

                return null;
            }
        });


    }


    private void doPostResult(List<String> lists) {
        if (lists == null) {
            lists = new ArrayList<>();
        }

        myRecycleViewAdapter = new MyRecycleViewAdapter(getActivity(), lists);
        mRecyclerView.setAdapter(myRecycleViewAdapter);

        myRecycleViewAdapter.setOnItemActionListener(new MyRecycleViewAdapter.OnItemActionListener() {
            @Override
            public void onItemClickListener(View v, int pos) {
                Toast.makeText(getActivity(), "pos=" + pos, Toast.LENGTH_SHORT).show();
            }
        });
    }


}
