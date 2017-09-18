package com.chungmyung.memorealm.activity.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chungmyung.memorealm.activity.Models.Memo;

import java.util.Stack;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by user on 2017-09-18.
 */

public class MemoRecyclerAdapter extends RealmRecyclerViewAdapter<Memo, MemoRecyclerAdapter.ViewHolder> {

    private Stack<Memo> mUndoStack;

    public void delete(Memo memo) {
        Realm realm = Realm.getDefaultInstance();
        mUndoStack.push(realm.copyFromRealm(memo));
        realm.beginTransaction();

        memo.deleteFromRealm();

        realm.commitTransaction();
        realm.close();
    }


    public void undo() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        Memo memo = mUndoStack.pop();
        realm.insert(memo);

        realm.commitTransaction();
        realm.close();
    }

    public interface OnItemClickListener {
        void onItemClicked(Memo memo);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public MemoRecyclerAdapter(@Nullable OrderedRealmCollection<Memo> data, boolean autoUpdate) {
        super(data, autoUpdate);
        setHasStableIds(true);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_2, parent, false);
        return new MemoRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Memo memo = getItem(position);
        holder.titleTextView.setText(memo.getTitle());
        holder.memoTextView.setText(memo.getMemo());
        holder.memo = memo;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView memoTextView;
        public Memo memo;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(android.R.id.text1);
            memoTextView = itemView.findViewById(android.R.id.text2);
        }
    }
}
