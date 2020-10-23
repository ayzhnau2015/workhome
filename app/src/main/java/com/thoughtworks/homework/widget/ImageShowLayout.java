package com.thoughtworks.homework.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.thoughtworks.homework.R;

/**
 * show movement img
 *
 * @author zhuyaan
 * @since 2020-10-20
 */
public class ImageShowLayout extends ViewGroup {
    private static final int MAX_SIZE = 9;                   // 最大显示的图片数
    private int mSingleImageWidth = 250;              // 单张图片时的最大大小,单位dp
    private float mSingleImageRatio = 0.7f;          // 单张图片的宽高比(宽/高)
    private int mImgMargin = 3;                    // 宫格间距，单位dp

    private int mColumnCount;    // 列数
    private int mRowCount;       // 行数
    private int mGridWidth;      // 宫格宽度
    private int mGridHeight;     // 宫格高度

    private AbstractImageShowAdapter mImageShowAdapter;
    public ImageShowLayout(Context context) {
        this(context,null);
    }

    public ImageShowLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ImageShowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ImageShowLayout);
        mImgMargin = typedArray.getDimensionPixelOffset(R.styleable.ImageShowLayout_imgMargin,mImgMargin);
        mSingleImageWidth = typedArray.getDimensionPixelOffset(R.styleable.ImageShowLayout_imgSingleWidth,mSingleImageWidth);
        mSingleImageRatio = typedArray.getFloat(R.styleable.ImageShowLayout_imgSingleRatio,mSingleImageRatio);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int imgCount = getChildCount();
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = 0;
        int totalWidth = width - getPaddingLeft() - getPaddingRight();
        if(imgCount == 1){
            mGridWidth = mSingleImageWidth > totalWidth ? totalWidth : mSingleImageWidth;
            mGridHeight = (int) (mGridWidth / mSingleImageRatio);
            if (mGridHeight > mSingleImageWidth) {
                float ratio = mSingleImageWidth * 1.0f / mGridHeight;
                mGridWidth = (int) (mGridWidth * ratio);
                mGridHeight = mSingleImageWidth;
            }
        } else {
            if(imgCount == 4){
                mGridHeight = mGridWidth = (totalWidth - 2 * mImgMargin) / 2;
            } else {
                mGridHeight = mGridWidth = (totalWidth - 2 * mImgMargin) / 3;
            }
        }
        width = mGridWidth * mColumnCount + mGridWidth * (mColumnCount - 1) + getPaddingLeft() + getPaddingRight();
        height = mGridHeight * mRowCount + mGridHeight * (mRowCount - 1) + getPaddingTop() + getPaddingBottom();
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onLayout(boolean b, int left, int top, int right, int bottom) {
        int childrenCount = getChildCount();
        for (int index = 0; index < childrenCount; index++) {
            View childrenView = getChildAt(index);
            if(childrenView instanceof ImageView){
                int rowNum = index / mColumnCount;
                int columnNum = index % mColumnCount;
                int imgLeft = (mGridWidth + mImgMargin) * columnNum + getPaddingLeft();
                int imgTop = (mGridHeight + mImgMargin) * rowNum + getPaddingTop();
                int imgRight = left + mGridWidth;
                int imgBottom = top + mGridHeight;
                childrenView.layout(imgLeft, imgTop, imgRight, imgBottom);
            }
        }
    }

    public void setAdapter(AbstractImageShowAdapter adapter){
        int itemCount = adapter.getItemCount();
        if(itemCount == 0){
            setVisibility(GONE);
            return;
        } else {
            setVisibility(VISIBLE);
        }
        mRowCount = itemCount / 3 + (itemCount % 3 == 0 ? 0 : 1);
        mColumnCount = 3;
        if(itemCount == 4){
            mRowCount = mColumnCount = 2;
        }

        removeAllViews();
        for (int index = 0; index < itemCount; index++) {
            final View view = adapter.createView(LayoutInflater.from(getContext()), this, index);
            adapter.bindView(view, index);
            removeView(view);
            addView(view);
            bindClickEvent(index, view, adapter);
        }
    }

    /**
     * 绑定点击事件
     *
     * @param position
     * @param view
     */
    private void bindClickEvent(final int position, final View view, final AbstractImageShowAdapter adapter) {
        if (adapter == null) {
            return;
        }
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.OnItemClick(position, view);
            }
        });
    }

    public void setSingleImage(int width, int height, ImageView imageView) {
        if (getChildCount() != 1) {
            removeAllViews();
            addView(imageView);
        }
        int singleViewWidth,singleViewHeight = 0;
        if (width >= height) {
            singleViewWidth = mSingleImageWidth;
            singleViewHeight = (int) (mSingleImageWidth * ((double) height / (double) width));
        } else {
            singleViewHeight = mSingleImageWidth;
            singleViewWidth = (int) (mSingleImageWidth * ((double) width / (double) height));
        }
        getChildAt(0).layout(0, 0, singleViewWidth, singleViewHeight);
        setMeasuredDimension(singleViewWidth, singleViewHeight);
    }
}
