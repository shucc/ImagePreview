package org.cchao.test;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.cchao.imagepreviewlib.ImagePreviewBuilder;
import org.cchao.imagepreviewlib.ImagePreviewExitListener;

import java.util.List;

/**
 * Created by shucc on 17/12/7.
 * cc@cchao.org
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {

    private List<CommentModel> data;

    private AppCompatActivity appCompatActivity;

    public CommentAdapter(List<CommentModel> data) {
        this.data = data;
    }

    @Override
    public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        appCompatActivity = (AppCompatActivity) parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        CommentHolder commentHolder = new CommentHolder(view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(parent.getContext(), 3);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        commentHolder.rvImage.setLayoutManager(gridLayoutManager);
        commentHolder.rvImage.setNestedScrollingEnabled(false);
        return commentHolder;
    }

    @Override
    public void onBindViewHolder(final CommentHolder holder, int position) {
        final CommentModel commentModel = data.get(position);
        holder.textContent.setText(commentModel.getContent());
        ImageAdapter imageAdapter = new ImageAdapter(commentModel.getImages(), String.valueOf(position));
        holder.rvImage.setAdapter(imageAdapter);
        imageAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(View view, int pos) {
                ImagePreviewBuilder.from(appCompatActivity)
                        .setInitPosition(pos)
                        .setTag(String.valueOf(holder.getAdapterPosition()))
                        .setImageUrlArray(commentModel.getImages())
                        .setPairView(view)
                        .setImagePreviewExitListener(new ImagePreviewExitListener() {
                            @Override
                            public View exitView(int exitPosition) {
                                return holder.rvImage.findViewWithTag(exitPosition);
                            }
                        })
                        .startActivity();
            }
        });
    }

    @Override
    public int getItemCount() {
        return null == data ? 0 : data.size();
    }

    protected class CommentHolder extends RecyclerView.ViewHolder {

        TextView textContent;
        RecyclerView rvImage;

        public CommentHolder(View itemView) {
            super(itemView);
            textContent = itemView.findViewById(R.id.text_content);
            rvImage = itemView.findViewById(R.id.rv_image);
        }
    }
}
