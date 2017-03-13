package com.zhaoyun.fangqq.drag;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by zhaoyun on 17-3-13.
 */

public class DragLayout extends FrameLayout {
    public static final String TAG = DragLayout.class.getSimpleName();
    private ViewDragHelper mDragHelper;
    private ViewGroup mMainGroup;
    private ViewGroup mLeftGroup;
    private int mWidth;
    private int mHeight;
    private int mRange;

    public DragLayout(Context context) {
        this(context,null);
    }

    public DragLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mDragHelper = ViewDragHelper.create(this, mCallback);
    }

    ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback(){



        //根据返回结果决定当前child是否可以被拖拽
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            Log.d(TAG, "tryCaptureView: " + child);
            return true;
        }

        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            Log.d(TAG, "onViewCaptured: " + capturedChild);
            super.onViewCaptured(capturedChild, activePointerId);
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            //返回拖拽的范围，不对拖拽真正的生效，仅仅决定动画执行速度
            Log.d(TAG, "getViewHorizontalDragRange: "+ mRange);
            return mRange;
        }

        /**
         * 根据建议值修正将要真正移动的（横向）位置
         * 此时没有发生真正移动
         * @param child 当前拖拽的view
         * @param left 新的位置的建议值
         * @param dx 位置变化量
         * @return
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            Log.d(TAG, "clampViewPositionHorizontal: oldLeft:"+ child.getLeft()+" dx:"+dx+" newLeft:"+left);
            if(child == mMainGroup) {
                left = fixLeft(left);
            }
            return left;
        }
        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return super.clampViewPositionVertical(child, top, dy);
        }

        /**
         * 当view位置改变时，处理要做的事情（更新状态，伴随动画，重绘界面）
         * @param changedView
         * @param left
         * @param top
         * @param dx
         * @param dy
         */
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);

            Log.d(TAG, "onViewPositionChanged: left:"+left+" dx:"+ dx);

            int newLeft = left;

            if(changedView == mLeftGroup){
                //当左面板移动之后，强制放回去
                mLeftGroup.layout(0,0,mWidth,mHeight);

                newLeft = mMainGroup.getLeft()+dx;
                newLeft = fixLeft(newLeft);
                mMainGroup.layout(newLeft,0,newLeft+mWidth,mHeight);
            }

            //为了兼容低版本，每次修改值后，进行重绘
            invalidate();
        }

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
        }




        private int fixLeft(int left) {
            if (left < 0) {
                return 0;
            } else if (left > mRange) {
                return mRange;
            }
            return left;
        }

    };



    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //传递给mDragHelper
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            mDragHelper.processTouchEvent(event);
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected void onFinishInflate() {

        if(getChildCount()<2){
            throw new IllegalStateException("布局至少有两个孩子。 Your ViewGroup must have 2 children at least.");
        }
        if(!(getChildAt(0) instanceof ViewGroup && getChildAt(1) instanceof ViewGroup)){
            throw new IllegalArgumentException("子View必须是ViewGroup的子类. Your children must be an instance of ViewGroup.");
        }

        mLeftGroup = (ViewGroup)getChildAt(0);
        mMainGroup = (ViewGroup)getChildAt(1);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //当尺寸有变化的时候
        mHeight = getMeasuredHeight();
        mWidth = getMeasuredWidth();

        mRange = (int )(mWidth * 0.6f);
    }
}
