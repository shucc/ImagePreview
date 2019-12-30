package org.cchao.test;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

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

    private String tag = "";

    public ImageAdapter(List<String> data) {
        this.data = data;
    }

    public ImageAdapter(List<String> data, String tag) {
        this.data = data;
        this.tag = tag;
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.imgDetail.setTransitionName(context.getString(R.string.image_preview_transition_name, position).concat(tag));
            holder.imgDetail.setTag(position);
        }
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
