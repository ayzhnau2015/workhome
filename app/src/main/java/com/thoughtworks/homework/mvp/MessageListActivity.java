package com.thoughtworks.homework.mvp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.thoughtworks.homework.R;
import com.thoughtworks.homework.model.MessageBean;
import com.thoughtworks.homework.model.UserBean;
import com.thoughtworks.homework.util.GlideUtil;
import com.thoughtworks.homework.util.MessageConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MessageListActivity extends AppCompatActivity implements MessageListContract.MessageView {
    private static final String TAG = "MessageListActivity";
    private MessageListContract.MessagePresenter mPresenter;

    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;

    @BindView(R.id.sfl)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.app_bar)
    AppBarLayout mAppBarLayout;

    @BindView(R.id.rl_bar_title)
    View mRlTitleView;

    @BindView(R.id.iv_user_bg)
    ImageView mIvSelfBg;

    @BindView(R.id.iv_self_head)
    ImageView mIvSelfHead;

    @BindView(R.id.tv_self_name)
    TextView mTvSelfName;

    private List<MessageBean> mMessageBeans;
    private MessageListAdapter mMessageListAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecyclerView();
        mPresenter = new MessageListPresenter(this);
        mSwipeRefreshLayout.setRefreshing(true);
    }

    private void initRecyclerView() {
        mMessageListAdapter = new MessageListAdapter(this,mMessageBeans);
        mRecyclerView.setAdapter(mMessageListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRlTitleView.setOnClickListener(v -> mRecyclerView.scrollToPosition(0));

        mSwipeRefreshLayout.setOnRefreshListener(()-> mPresenter.loadMore());
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                try {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        mMessageListAdapter.setFootView(MessageConstants.LOADING);
                        mPresenter.loadMore();
                    }
                } catch (Exception e) {
                    Log.i(TAG,"load more error!.");
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void showUserInfo(UserBean userInfoBean) {
        mTvSelfName.setText(userInfoBean.getUserName());
        GlideUtil.load(this, userInfoBean.getProfileImage(), mIvSelfBg, R.mipmap.default_place_img);
        GlideUtil.load(this, userInfoBean.getAvatar(), mIvSelfHead, R.mipmap.icon_default_small_head);
    }

    @Override
    public void showAllMessage(List<MessageBean> messageBeans) {
        if(mMessageBeans == null){
            mMessageBeans = new ArrayList<>();
        }
        mMessageBeans.addAll(messageBeans);
        mMessageListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showAllCommit() {

    }

    @Override
    public void hideCommit(int position) {

    }

    @Override
    public void loadMoreMessage(int startPosition) {

    }

    @Override
    public void showLoading(int state) {
        mMessageListAdapter.setFootView(state);
    }
}
