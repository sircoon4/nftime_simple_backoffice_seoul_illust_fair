package com.example.nftime_simple_backoffice_seoul_illust_fair;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class WorksListAdapter extends ListAdapter<WorkInfo, WorksListAdapter.ViewHolder> {
    private OnWorksListListener onWorksListListener;

    protected WorksListAdapter(
            OnWorksListListener onWorksListListener,
            @NonNull DiffUtil.ItemCallback<WorkInfo> diffCallback) {
        super(diffCallback);
        this.onWorksListListener = onWorksListListener;
    }

    public interface OnWorksListListener {
        void onWorkClicked(WorkInfo workInfo);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_items_works, parent, false);
        return new WorksListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(getItem(position));
    }

    public static class WorksDiff extends DiffUtil.ItemCallback<WorkInfo>{

        @Override
        public boolean areItemsTheSame(@NonNull WorkInfo oldItem, @NonNull WorkInfo newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull WorkInfo oldItem, @NonNull WorkInfo newItem) {
            return oldItem.getWorkId() == newItem.getWorkId();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvWorkName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvWorkName = itemView.findViewById(R.id.tv_work_name);
        }

        void onBind(WorkInfo item){
            String fullText = item.getArtistName() + " | " + item.getWorkName();
            tvWorkName.setText(fullText);
            tvWorkName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onWorksListListener.onWorkClicked(item);
                }
            });
        }
    }
}
