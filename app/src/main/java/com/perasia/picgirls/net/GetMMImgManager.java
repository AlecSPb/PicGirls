package com.perasia.picgirls.net;


import android.os.Handler;

import com.perasia.picgirls.data.ImageData;
import com.perasia.picgirls.utils.CatchImgUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GetMMImgManager {
    private static final String TAG = GetMMImgManager.class.getSimpleName();

    public interface OnGetMMListener {
        void onSuccess(List<ImageData> lists);
    }

    public GetMMImgManager() {

    }

    private ExecutorService executorService = Executors.newFixedThreadPool(5);
    private final Handler handler = new Handler();

    public void loadMMPic(final String url, final OnGetMMListener listener) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                final List<String> lists = CatchImgUtil.getImgs(url);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        List<ImageData> datas = new ArrayList<>();

                        if (lists != null) {
                            for (int i = 0; i < lists.size(); ++i) {
                                ImageData data = new ImageData();
                                data.setUrl(lists.get(i));
                                datas.add(data);
                            }
                        }


                        listener.onSuccess(datas);
                    }
                });
            }
        });
    }

}
