package com.example.catchapp3.adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.catchapp3.dbTables.Runs;

public class RunListAdapter extends ListAdapter<Runs, RunViewHolder> {
    private OnNoteListener mOnNoteListener;
    private RunViewHolder rvh;

    public RunListAdapter(@NonNull DiffUtil.ItemCallback<Runs> diffCallback, OnNoteListener onNoteListener) {
        super(diffCallback);
        mOnNoteListener = onNoteListener;
    }

    @Override
    public RunViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        rvh = new RunViewHolder(parent, mOnNoteListener);
        return rvh.create(parent);
    }

    @Override
    public void onBindViewHolder(RunViewHolder holder, int position) {
        Runs current = getItem(position);
        holder.bind(current.runName);
    }


    public interface OnNoteListener{
        void onNoteClick(int Position);
    }


    public static class RunDiff extends DiffUtil.ItemCallback<Runs> {

        @Override
        public boolean areItemsTheSame(@NonNull Runs oldItem, @NonNull Runs newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Runs oldItem, @NonNull Runs newItem) {
            return (oldItem.runId == newItem.runId &&
                    oldItem.runName.equals(newItem.runName) &&
                    oldItem.created.equals(newItem.created) &&
                    oldItem.totalLength == newItem.totalLength &&
                    oldItem.totalTime == newItem.totalTime &&
                    oldItem.userId == newItem.userId);
        }
    }

}
