package com.perasia.picgirls.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.perasia.picgirls.R;
import com.perasia.picgirls.data.ImageData;
import com.perasia.picgirls.view.RatioImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.ViewHolder> {
    private static final String TAG = MyRecycleViewAdapter.class.getSimpleName();

    public interface OnItemActionListener {
        void onItemClickListener(View v, int pos, String url);
    }

    private Context context;
    private ArrayList<ImageData> datas = null;

    private OnItemActionListener listener;

    public void setOnItemActionListener(OnItemActionListener l) {
        listener = l;
    }

    public MyRecycleViewAdapter(Context context, ArrayList<ImageData> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (datas == null) {
            return;
        }

        Glide.with(context).load(datas.get(position).getUrl()).placeholder(R.mipmap.pictures_no)
                .error(R.mipmap.pictures_no).into(holder.imageView);
        if (listener != null) {
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClickListener(v, holder.getPosition(), datas.get(holder.getPosition()).getUrl());
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return null == datas ? 0 : datas.size();
    }


    public ImageData getItem(int position) {
        if (datas == null) {
            return null;
        }

        if (position > datas.size() - 1) {
            return null;
        }
        return datas.get(position);
    }

    public List<ImageData> getList() {
        return datas;
    }

    public void append(ImageData data) {
        if (null == data) {
            return;
        }

        if (datas == null) {
            return;
        }
        datas.add(data);
    }

    public void appendToList(List<ImageData> datas) {
        if (datas == null) {
            return;
        }

        if (this.datas == null) {
            return;
        }

        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        if (datas == null) {
            return;
        }

        if (position < datas.size() - 1 && position >= 0) {
            datas.remove(position);
        }
    }

    public void clear() {
        if (datas != null) {
            datas.clear();
        }
    }

    private int[] getFixedImage(String url) {
        try {
            Bitmap bitmap = Glide.with(context).load(url).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
            int[] size = new int[2];
            size[0] = bitmap.getWidth();
            size[1] = bitmap.getHeight();

            return size;

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public RatioImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (RatioImageView) itemView.findViewById(R.id.recycleview_item_iv);
        }
    }
}
