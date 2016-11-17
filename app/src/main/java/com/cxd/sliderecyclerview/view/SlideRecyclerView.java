package com.cxd.sliderecyclerview.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;

/**
 * Created by caixd on 2016/11/1.
 */

public class SlideRecyclerView extends RecyclerView {

    private Scroller scroller;
    //检测手指滑动速度
    private VelocityTracker tracker;
    //菜单状态   0：关闭 1：将要关闭 2：将要打开 3：打开
    private int mMenuState;
    //item对应的布局
    private View mItemView;
    //当前触摸的item的位置
    private int mPosition;
    //最大滑动距离(即菜单的宽度)
    private int mMaxLength;
    //上一次的触摸点
    private int mLastX, mLastY;
    //是否在垂直滑动列表
    private boolean isDragging;
    //item是在否跟随手指移动
    private boolean isItemMoving;
    //item是否开始自动滑动
    private boolean isStartScroll;

    public SlideRecyclerView(Context context) {
        this(context, null);
    }

    public SlideRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        scroller = new Scroller(context, new LinearInterpolator(context, null));
        tracker = VelocityTracker.obtain();
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        tracker.addMovement(e);
        int x = (int) e.getX();
        int y = (int) e.getY();
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mMenuState == 0) {
                    View view = findChildViewUnder(x, y);
                    if (view == null) {
                        return false;
                    }
                    SlideViewHolder viewHolder = (SlideViewHolder) getChildViewHolder(view);
                    mPosition = viewHolder.getAdapterPosition();
                    mItemView = viewHolder.itemView;
                    mMaxLength = viewHolder.menuLayout.getWidth();
                    Log.d("cxd", "onTouchEvent: " + mMaxLength);
                    viewHolder.topBtn.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (listener != null) {
                                listener.onItemMove(mPosition, 0);
                                mItemView.scrollTo(0, 0);
                                mMenuState = 0;
                            }
                        }
                    });
                    viewHolder.deleteBtn.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (listener != null) {
                                listener.onItemDelete(mPosition);
                                mItemView.scrollTo(0, 0);
                                mMenuState = 0;
                            }
                        }
                    });
                } else if (mMenuState == 3) {//有菜单打开, 则关闭
                    scroller.startScroll(mItemView.getScrollX(), 0, -mMaxLength, 0, 200);
                    invalidate();
                    mMenuState = 0;
                    return false;//不再执行MOVE,UP
                } else {
                    return false;
                }

                break;
            case MotionEvent.ACTION_MOVE:
                int dx = mLastX - x;
                int dy = mLastY - y;
                int scrollX = mItemView.getScrollX();
                if (Math.abs(dx) > Math.abs(dy)) {//左右滑动
                    isItemMoving = true;
                    if (scrollX + dx <= 0) {//判断左边界
                        mItemView.scrollTo(0, 0);
                        return true;
                    } else if (scrollX + dx >= mMaxLength) {//判断右边界
                        mItemView.scrollTo(mMaxLength, 0);
                        return true;
                    }
                    mItemView.scrollBy(dx, 0);//跟随手指滑动
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!isItemMoving && !isDragging && listener != null) {
                    listener.onItemClick(mItemView, mPosition);
                }
                isItemMoving = false;
                tracker.computeCurrentVelocity(1000);//初始化速率单位: 计算1000ms内移动的像素
                float xVelocity = tracker.getXVelocity();//水平方向速度, 向左为负
                float yVelocity = tracker.getYVelocity();

                int deltaX = 0;
                int upScrollX = mItemView.getScrollX();
                Log.d("cxd", "onTouchEvent: up: " + upScrollX);
                if (Math.abs(xVelocity) > 100 && Math.abs(xVelocity) > Math.abs(yVelocity)) {//快速滑
                    if (xVelocity <= -100) {//左滑速度大于100像素/秒
                        deltaX = mMaxLength - upScrollX;//快速左滑, 左滑剩余部分
                        mMenuState = 2;
                    } else if (xVelocity > 100) {
                        deltaX = -upScrollX;//快速右滑回退
                        mMenuState = 1;
                    }
                } else {//慢滑
                    if (upScrollX >= mMaxLength / 2) {//显示一半, 则滑动显示全部
                        deltaX = mMaxLength - upScrollX;
                        mMenuState = 2;
                    } else {
                        deltaX = -upScrollX;
                        mMenuState = 1;
                    }
                }
                scroller.startScroll(upScrollX, 0, deltaX, 0, 200);
                isStartScroll = true;
                invalidate();
                tracker.clear();
                break;
        }
        mLastX = x;
        mLastY = y;
        return super.onTouchEvent(e);
    }

    @Override
    public void computeScroll() {
        //scroller是否执行完毕, true未执行完毕
        if (scroller.computeScrollOffset()) {
            mItemView.scrollTo(scroller.getCurrX(), scroller.getCurrY());
            //通过重绘, 不断调用computeScroll, invalidate->draw->computeScroll
            invalidate();
        } else if (isStartScroll) {
            isStartScroll = false;
            if (mMenuState == 2) {
                mMenuState = 3;
            }
            if (mMenuState == 1) {
                mMenuState = 0;
            }
        }
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
