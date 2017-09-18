package com.chungmyung.memorealm.activity;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by user on 2017-09-18.
 */

public class MemoRecyclerAdapter extends RecyclerView.Adapter<Memo,MemoRecyclerAdpater.viewHolder>{

    @Override
    public Memo onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(Memo holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView ;
        TextView memoTextView ;

        public ViewHolder (View itemView){
            super(itemView);
            titleTextView = itemView.findViewById(android.R.id.text1);
            memoTextView = itemView.findViewById(android.R.id.text2);
        }

    }

}
