package com.example.catchapp3.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catchapp3.R;
import com.example.catchapp3.dbTables.Runs;


import java.util.ArrayList;
import java.util.List;

class RunViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final TextView runItemView;
    static RunListAdapter.OnNoteListener onNoteListener;

    public RunViewHolder(View itemView, RunListAdapter.OnNoteListener onNoteListener) {
        super(itemView);
        runItemView = itemView.findViewById(R.id.textView);
        this.onNoteListener = onNoteListener;

        itemView.setOnClickListener(this);
    }

    public void bind(String text) {
        runItemView.setText(text);
    }

    public RunViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new RunViewHolder(view, onNoteListener);
    }

    @Override
    public void onClick(View v) {
        onNoteListener.onNoteClick(getAdapterPosition());
    }
}


//public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{
//    private Context context;
//    private ArrayList<String> items;
//
//    private Runs[] localDataSet;
//
//    static OnNoteListener mOnNoteListener;
//
//
//    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
//        private final TextView textView;
//        OnNoteListener onNoteListener;
//
//        public ViewHolder(View view, OnNoteListener onNoteListener){
//            super(view);
//            textView = (TextView) view.findViewById(R.id.tv_item_name);
//            this.onNoteListener = onNoteListener;
//            itemView.setOnClickListener(this);
//
//        }
//
//        static ViewHolder create(ViewGroup parent){
//            View view = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.item_custom_row, parent, false);
//            return new ViewHolder(view, mOnNoteListener);
//        }
//
//        public TextView getTextView(){
//            return textView;
//        }
//
//        @Override
//        public void onClick(View v) {
//            onNoteListener.onNoteClick(getAdapterPosition());
//        }
//    }
//
//    public interface OnNoteListener{
//        void onNoteClick(int position);
//    }
//
//    public CustomAdapter(List<Runs> dataSet, OnNoteListener onNoteListener){
//        if(dataSet != null) {
//            localDataSet = new Runs[dataSet.size()];
//            dataSet.toArray(localDataSet);
//        }
//
//        this.mOnNoteListener = onNoteListener;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
//        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_custom_row, viewGroup, false);
//        return new ViewHolder(view, mOnNoteListener);
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder viewHolder, final int position){
//        viewHolder.getTextView().setText(localDataSet[position].runName);
//    }
//
//    @Override
//    public int getItemCount(){
//        return localDataSet.length;
//    }
//}
//
///*
//public class RunsListAdapter extends ListAdapter<Runs, CustomAdapter.ViewHolder> {
//    public RunsListAdapter(@NonNull DiffUtil.ItemCallback<Runs> diffCallback) {
//        super(diffCallback);
//    }
//
//    @Override
//    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return CustomAdapter.ViewHolder.create(parent);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {
//
//    }
//
////    @Override
////    public void onBindViewHolder(CustomAdapter.ViewHolder holder, int position) {
////        Runs current = getItem(position);
////        holder.bind(current.getWord());
////    }
//
////    static class WordDiff extends DiffUtil.ItemCallback<Word> {
////
////        @Override
////        public boolean areItemsTheSame(@NonNull Word oldItem, @NonNull Word newItem) {
////            return oldItem == newItem;
////        }
////
////        @Override
////        public boolean areContentsTheSame(@NonNull Word oldItem, @NonNull Word newItem) {
////            return oldItem.getWord().equals(newItem.getWord());
////        }
//*/