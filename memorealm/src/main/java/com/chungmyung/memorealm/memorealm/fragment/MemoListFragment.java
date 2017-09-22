package com.chungmyung.memorealm.memorealm.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chungmyung.memorealm.R;
import com.chungmyung.memorealm.memorealm.Models.Memo;
import com.chungmyung.memorealm.memorealm.activity.DetailActivity;
import com.chungmyung.memorealm.memorealm.adapter.MemoRecyclerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;


public class MemoListFragment extends Fragment implements MemoRecyclerAdapter.OnItemClickListener {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    Unbinder unbinder;

    private Realm mRealm;
    private MemoRecyclerAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmemt_memo_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        mRealm = Realm.getDefaultInstance();

        setUPRecyclerView();
        return view;
    }

    private void setUPRecyclerView() {

        //리사클러 뷰 레이아웃 매니저
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // animation효과
//       RecyclerView.ItemAnimator animator = new DefaultItemAnimator();
//        animator.setAddDuration(3000);
//        mRecyclerView.setItemAnimator(animator);


        // Realm에서 데이터  읽어오기
        RealmResults<Memo> data = mRealm.where(Memo.class).findAll();

        // 터치
        ItemTouchHelper touchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                return makeFlag(ItemTouchHelper.ACTION_STATE_DRAG, ItemTouchHelper.UP | ItemTouchHelper.DOWN) |
                        makeFlag(ItemTouchHelper.ACTION_STATE_SWIPE, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.LEFT) {
                    mRealm.beginTransaction();
                    Memo memo = ((MemoRecyclerAdapter.ViewHolder) viewHolder).memo;
                    memo.deleteFromRealm();
                    mRealm.commitTransaction();
                }
            }
        });
        touchHelper.attachToRecyclerView(mRecyclerView);

        // 어뎁터에 데이터 설정
        mAdapter = new MemoRecyclerAdapter(data.sort("id", Sort.DESCENDING));

        //ㄱ클릭 이벤트
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);

        // 아래 줄치기..
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL)) ;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mRealm.close();
    }

    @Override
    public void onItemClicked(Memo memo) {
        // 수정화면으로 이동.

        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra("id", memo.getId());  // 메모에 id만 보냈다.
        startActivity(intent);

//        Toast.makeText(getContext(), "" + memo.toString(), Toast.LENGTH_SHORT).show();
//        mRealm.beginTransaction();
//        mRealm.commitTransaction();
    }
}
