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
    private final TextView nameTV, distTV, dateTV;
    static RunListAdapter.OnNoteListener onNoteListener;
    private Runs run;

    public RunViewHolder(View itemView, RunListAdapter.OnNoteListener onNoteListener) {
        super(itemView);
        nameTV = itemView.findViewById(R.id.textView);
        distTV = itemView.findViewById(R.id.distView);
        dateTV = itemView.findViewById(R.id.dateView);
        this.onNoteListener = onNoteListener;

        itemView.setOnClickListener(this);
    }

    public void bind(Runs run) {
        this.run = run;
        nameTV.setText(run.runName);
        if(run.runId == 0){
            distTV.setVisibility(View.GONE);
            dateTV.setVisibility(View.INVISIBLE);
        }
        else {
            distTV.setText(String.format("%.02f", run.totalLength));
            dateTV.setText(run.created);
        }
    }

    public RunViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new RunViewHolder(view, onNoteListener);
    }

    @Override
    public void onClick(View v) {
        onNoteListener.onNoteClick(run.runId);
    }
}