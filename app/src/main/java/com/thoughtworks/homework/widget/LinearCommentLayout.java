package com.thoughtworks.homework.widget;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.thoughtworks.homework.model.CommentBean;
import com.thoughtworks.homework.model.SenderBean;

import java.util.List;

public class LinearCommentLayout extends LinearLayout {
    private List<CommentBean> mCommentBeanList;
    public LinearCommentLayout(Context context) {
        super(context);
    }

    public LinearCommentLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LinearCommentLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setCommentBeanList(List<CommentBean> commentBeanList){
        mCommentBeanList = commentBeanList;
    }

    public void showCommit(){
        removeAllViews();
        if (mCommentBeanList == null || mCommentBeanList.size() <= 0) {
            return;
        }
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 10, 0, 10);
        for (int index = 0; index < mCommentBeanList.size(); index++) {
            View view = getView(index);
            if (view != null) {
                addView(view, index, layoutParams);
            }
        }
    }

    private View getView(int index) {
        CommentBean commentBean = mCommentBeanList.get(index);
        TextView textView = getTextView();
        SpannableStringBuilder builder = new SpannableStringBuilder();
        SenderBean comUser = commentBean.getSenderBean();
        String name = comUser.getUserName();
        builder.append(setClickableSpan(name));
        builder.append(" : ");
        builder.append(setClickableSpanContent(commentBean.getContent(), index));
        textView.setText(builder);
        return textView;
    }

    public SpannableString setClickableSpanContent(final String item, final int position) {
        final SpannableString string = new SpannableString(item);
        ClickableSpan span = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(0xff686868);
                ds.setUnderlineText(false);
            }
        };
        string.setSpan(span, 0, string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return string;
    }

    public SpannableString setClickableSpan(final String item) {
        final SpannableString string = new SpannableString(item);
        ClickableSpan span = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(0xff387dcc);
                ds.setUnderlineText(false);
            }
        };

        string.setSpan(span, 0, string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return string;
    }

    private TextView getTextView(){
        TextView textView = new TextView(getContext());
        textView.setTextSize(15);
        textView.setTextColor(0xff686868);
        return textView;
    }
}
