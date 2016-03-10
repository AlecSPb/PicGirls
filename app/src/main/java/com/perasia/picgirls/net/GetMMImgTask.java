package com.perasia.picgirls.net;


import android.os.AsyncTask;

import com.perasia.picgirls.utils.CatchImgUtil;

import java.util.List;

public class GetMMImgTask extends AsyncTask<String, Integer, List<String>> {
    private static final String TAG = GetMMImgTask.class.getSimpleName();

    private OnExecuteCallback callback;

    public interface OnExecuteCallback {
        Void onPostResult(List<String> result);
    }

    public GetMMImgTask() {

    }

    public void setOnExecuteCallback(OnExecuteCallback callback) {
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<String> doInBackground(String... params) {
        return CatchImgUtil.getImgs(params[0]);
    }

    @Override
    protected void onPostExecute(List<String> lists) {
        super.onPostExecute(lists);
        if (callback != null) {
            callback.onPostResult(lists);
        }
    }
}
