package org.cchao.test;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by shucc on 17/10/19.
 * cc@cchao.org
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageHolder> {

    private List<String> data;

    private Context context;

    private OnItemClickListener onItemClickListener;

    public ImageAdapter(List<String> data) {
        this.data = data;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        final ImageHolder holder = new ImageHolder(view);
        if (null != onItemClickListener) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick(holder.imgDetail, holder.getAdapterPosition());
                }
            });
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ImageHolder holder, int position) {
        Glide.with(context)
                .load(data.get(position))
                .into(holder.imgDetail);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    protected class ImageHolder extends RecyclerView.ViewHolder {

        ImageView imgDetail;

        public ImageHolder(View itemView) {
            super(itemView);
            imgDetail = (ImageView) itemView.findViewById(R.id.img_detail);
        }
    }
}
