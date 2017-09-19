package com.chungmyung.memorealm.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chungmyung.memorealm.R;
import com.chungmyung.memorealm.activity.Models.Memo;
import com.chungmyung.memorealm.activity.adapter.MemoRecyclerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;


public class MemoListFragment extends Fragment implements MemoRecyclerAdapter.OnItemClickedListener {

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

        setUPRecyclerView();


        return view;
    }

    private void setUPRecyclerView() {

        //리사클러 뷰 레이아웃 매니저
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Realm에서 데이터  읽어오기
        RealmResults<Memo> data = mRealm.where(Memo.class).findAll();

        // 어뎁터에 데이터 설정
        mAdapter= new MemoRecyclerAdapter(data.sort("id", Sort.DESCENDING));

        //ㄱ클릭 이벤트
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);

        // 아래 줄치기..
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL)) ;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mRealm.close();
    }

    @Override
    public void onItemClicked(int position) {
        Toast.makeText(getContext(), "" + position, Toast.LENGTH_SHORT).show();

    }
}
