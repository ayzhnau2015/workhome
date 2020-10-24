package com.thoughtworks.homework.mvp;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.thoughtworks.homework.R;
import com.thoughtworks.homework.model.MessageBean;
import com.thoughtworks.homework.model.UserBean;
import com.thoughtworks.homework.util.GlideUtil;
import com.thoughtworks.homework.util.MessageConstants;
import com.thoughtworks.homework.util.StatusBarUtil;
import com.thoughtworks.homework.widget.StatusView;

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

        mAppBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (verticalOffset >= 0) {
                mSwipeRefreshLayout.setEnabled(true);
                //将标题栏的颜色设置为完全不透明状态
                mRlTitleView.setAlpha(0f);
                mStatusView.setAlpha(0f);
                StatusBarUtil.setImmersiveStatusBar(MessageListActivity.this, false);
            } else {
                if (!mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setEnabled(false);
                }
                int abs = Math.abs(verticalOffset);
                if (abs <= mAppBarLayoutHeight - (mTitleViewHeight + getStatusBarHeight(MessageListActivity.this))) {
                    float alpha = (float) abs / mAppBarLayoutHeight;
                    mRlTitleView.setAlpha(alpha);
                    mStatusView.setAlpha(alpha);
                    StatusBarUtil.setImmersiveStatusBar(MessageListActivity.this, false);
                } else {
                    mRlTitleView.setAlpha(1.0f);
                    mStatusView.setAlpha(1.0f);
                    StatusBarUtil.setImmersiveStatusBar(MessageListActivity.this, true, ContextCompat.getColor(MessageListActivity.this, R.color.home_status_bar_color));
                }
            }
        });

    }

    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        return resources.getDimensionPixelSize(resources.getIdentifier("status_bar_height", "dimen", "android"));
    }

    @Override
    public void showUserInfo(UserBean userInfoBean) {
        mTvSelfName.setText(userInfoBean.getUserName());
        GlideUtil.load(this, userInfoBean.getProfileImage(), mIvSelfBg, R.mipmap.default_place_img);
        GlideUtil.load(this, userInfoBean.getAvatar(), mIvSelfHead, R.mipmap.icon_default_small_head);
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
        mRecyclerView.setAdapter(mMessageListAdapter);
        mMessageListAdapter.notifyItemRangeInserted(oldSize,messageBeans.size());
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
