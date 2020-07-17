package com.world.play.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.world.play.R;
import com.world.play.models.StoryDetail;
import com.world.play.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashBoardAdapter extends RecyclerView.Adapter<DashBoardAdapter.DashBoardViewHolder> {

    private Context context;
    private Callback callback;
    private List<StoryDetail> storyDetail;

    public DashBoardAdapter(Context context, List<StoryDetail> storyDetail) {
        this.context = context;
        this.storyDetail = storyDetail;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public DashBoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DashBoardViewHolder(LayoutInflater.from(context).inflate(R.layout.item_dashboard_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DashBoardViewHolder holder, int position) {
        try {
            StoryDetail storyDetail = this.storyDetail.get(position);

            holder.textTitle.setText(storyDetail.getTitle());
            holder.textSubTitle.setText(storyDetail.getType());
            holder.textTime.setText(Utils.getDateFromTimeStamp(storyDetail.getTime()));

        } catch (Exception e) {
            Log.e("Exception: ", e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return storyDetail.size();
    }

    public class DashBoardViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.dash_image)
        ImageView dash_image;
        @BindView(R.id.text_title)
        TextView textTitle;
        @BindView(R.id.text_sub_title)
        TextView textSubTitle;
        @BindView(R.id.text_time)
        TextView textTime;

        public DashBoardViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (callback != null) {
                        callback.onPositionSelect(getAdapterPosition());
                    }
                }
            });
        }
    }

    public interface Callback {
        void onPositionSelect(int position);
    }
}
