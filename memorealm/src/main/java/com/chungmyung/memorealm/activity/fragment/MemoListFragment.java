package com.chungmyung.memorealm.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chungmyung.memorealm.R;
import com.chungmyung.memorealm.activity.Models.Memo;
import com.chungmyung.memorealm.activity.adapter.MemoRecyclerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;


public class MemoListFragment extends Fragment {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    Unbinder unbinder;

    private Realm mRealm ;
    private MemoRecyclerAdapter mAdapter ;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmemt_memo_list, container, true);
        unbinder = ButterKnife.bind(this, view);
        mRealm = Realm.getDefaultInstance();

        RealmResults<Memo> data = mRealm.where(Memo.class).findAll();
        mAdapter= new MemoRecyclerAdapter(data);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mRealm.close();
    }

}
