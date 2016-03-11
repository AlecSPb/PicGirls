package com.perasia.picgirls.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.perasia.picgirls.R;

import java.util.List;

public class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.ViewHolder> {
    private static final String TAG = MyRecycleViewAdapter.class.getSimpleName();

    public interface OnItemActionListener {
        void onItemClickListener(View v, int pos, String url);
    }

    private Context context;
    private List<String> datas = null;

    private OnItemActionListener listener;

    public void setOnItemActionListener(OnItemActionListener l) {
        listener = l;
    }

    public MyRecycleViewAdapter(Context context, List<String> datas) {
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

        Glide.with(context).load(datas.get(position)).placeholder(R.mipmap.pictures_no)
                .error(R.mipmap.pictures_no).into(holder.imageView);
        if (listener != null) {
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClickListener(v, holder.getPosition(), datas.get(holder.getPosition()));
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return null == datas ? 0 : datas.size();
    }


    private String getItem(int position) {
        if (datas == null) {
            return null;
        }

        if (position > datas.size() - 1) {
            return null;
        }
        return datas.get(position);
    }

    public List<String> getList() {
        return datas;
    }

    public void append(String data) {
        if (null == data) {
            return;
        }

        if (datas == null) {
            return;
        }
        datas.add(data);
    }

    public void appendToList(List<String> datas) {
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.recycleview_item_iv);
        }
    }
}
