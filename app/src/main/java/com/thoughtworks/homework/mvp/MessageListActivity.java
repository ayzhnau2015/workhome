package com.thoughtworks.homework.mvp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.thoughtworks.homework.R;
import com.thoughtworks.homework.model.MessageBean;
import com.thoughtworks.homework.model.UserBean;
import com.thoughtworks.homework.util.ImageUtil;
import com.thoughtworks.homework.util.MessageConstants;
import com.thoughtworks.homework.util.StatusToolUtil;
import com.thoughtworks.homework.widget.StatusView;
import com.thoughtworks.homework.widget.swipe.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    @BindView(R.id.sv_status)
    StatusView mStatusView;

    private List<MessageBean> mMessageBeans = new ArrayList<>();
    private MessageListAdapter mMessageListAdapter;
    private int mAppBarLayoutHeight;
    private int mTitleViewHeight;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initRecyclerView();
        mPresenter = new MessageListPresenter(this);
        mPresenter.start();
        mSwipeRefreshLayout.setRefreshing(true);
        mAppBarLayout.post(() -> {
            mTitleViewHeight = mRlTitleView.getHeight();
            mAppBarLayoutHeight = mAppBarLayout.getHeight();
        });
    }

    private void initRecyclerView() {
        mMessageListAdapter = new MessageListAdapter(this,mMessageBeans);
        mRecyclerView.setAdapter(mMessageListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mRecyclerView.setLayoutManager(layoutManager);
        mRlTitleView.setOnClickListener(v -> mRecyclerView.smoothScrollToPosition(0));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mSwipeRefreshLayout.setOnRefreshListener(()-> mPresenter.loadMore());
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                boolean isStateIdle = newState == RecyclerView.SCROLL_STATE_IDLE;
                int lastCompletelyVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition();
                if((lastCompletelyVisibleItemPosition == layoutManager.getItemCount() - 1) && isStateIdle) {
                    showLoading(MessageConstants.LOADING);
                    mPresenter.loadMore();
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        mAppBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (verticalOffset >= 0) {
                mSwipeRefreshLayout.setEnabled(true);
                //将标题栏的颜色设置为完全不透明状态
                mRlTitleView.setAlpha(0f);
                mStatusView.setAlpha(0f);
                StatusToolUtil.setImmersiveStatusBar(MessageListActivity.this, false);
            } else {
                if (!mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setEnabled(false);
                }
                int abs = Math.abs(verticalOffset);
                if (abs <= mAppBarLayoutHeight - (mTitleViewHeight + StatusToolUtil.getStatusBarHeight(MessageListActivity.this))) {
                    float alpha = (float) abs / mAppBarLayoutHeight;
                    mRlTitleView.setAlpha(alpha);
                    mStatusView.setAlpha(alpha);
                    StatusToolUtil.setImmersiveStatusBar(MessageListActivity.this, false);
                } else {
                    mRlTitleView.setAlpha(1.0f);
                    mStatusView.setAlpha(1.0f);
                    StatusToolUtil.setImmersiveStatusBar(MessageListActivity.this, true,
                            ContextCompat.getColor(MessageListActivity.this, R.color.home_status_bar_color));
                }
            }
        });

    }

    @Override
    public void showUserInfo(UserBean userInfoBean) {
        mTvSelfName.setText(userInfoBean.getUserName());
        ImageUtil.load(this, userInfoBean.getProfileImage(), mIvSelfBg, R.mipmap.default_place_img);
        ImageUtil.load(this, userInfoBean.getAvatar(), mIvSelfHead, R.mipmap.icon_default_small_head);
    }

    @Override
    public void showAllMessage(List<MessageBean> messageBeans) {
        mSwipeRefreshLayout.setRefreshing(false);
        if(mMessageBeans == null){
            mMessageBeans = new ArrayList<>();
        }
        int oldSize = mMessageBeans.size();
        Log.w(TAG,"show allmessage " + messageBeans);
        mMessageBeans.addAll(messageBeans);
        mMessageListAdapter.notifyItemRangeInserted(oldSize,messageBeans.size());
    }

    @Override
    public void showLoading(int state) {
        mMessageListAdapter.setFootView(state);
        if(state == MessageConstants.LOADING_NO_MORE
                || state == MessageConstants.LOADING_COMPLETE){
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
}
