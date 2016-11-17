package com.cxd.sliderecyclerview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.cxd.sliderecyclerview.R;


/**
 * Created by caixd on 2016/11/2.
 */

public class CircleImageView extends View {

    private static final int TYPE_CIRCLE = 0;//圆形图片
    private static final int TYPE_ROUND = 1;//圆角图片
    private int mRadius;//半径
    private int mType;//圆形或圆角
    private Bitmap mSrc;//图片
    private int mWidth, mHeight;
    private Paint mPaint;

    public CircleImageView(Context context) {
        this(context, null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView, defStyleAttr, 0);
        mSrc = BitmapFactory.decodeResource(getResources(), array.getResourceId(R.styleable.CircleImageView_circle_src, 0));
        mType = array.getInt(R.styleable.CircleImageView_circle_type, TYPE_CIRCLE);
        mRadius = array.getDimensionPixelOffset(R.styleable.CircleImageView_round_radius, (int) TypedValue.applyDimension
                (TypedValue.COMPLEX_UNIT_DIP, 10f, getResources().getDisplayMetrics()));
        array.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    public void setImageSrc(int resId) {
        mSrc = BitmapFactory.decodeResource(getResources(), resId);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specWidth = MeasureSpec.getSize(widthMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {//match_parent, 精确值
            mWidth = specWidth;
        } else {
            int desire = getPaddingLeft() + getPaddingRight() + mSrc.getWidth();
            if (specMode == MeasureSpec.AT_MOST) {//wrap_content
                mWidth = Math.min(desire, specWidth);
            } else {
                mWidth = desire;
            }
        }
        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specWidth = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {//match_parent, 精确值
            mHeight = specWidth;
        } else {
            int desire = getPaddingTop() + getPaddingBottom() + mSrc.getHeight();
            if (specMode == MeasureSpec.AT_MOST) {//wrap_content
                mHeight = Math.min(desire, specWidth);
            } else {
                mHeight = desire;
            }
        }
        //圆形取宽高最小值
        if (mType == TYPE_CIRCLE) {
            int min = Math.min(mWidth, mHeight);
            mWidth = mHeight = min;
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //原图片按照宽高缩放
        mSrc = Bitmap.createScaledBitmap(mSrc, mWidth, mHeight, true);
        canvas.drawBitmap(createImage(mSrc, mType), 0, 0, null);
    }

    private Bitmap createImage(Bitmap src, int type) {
        mPaint.reset();
        mPaint.setAntiAlias(true);
        //创建一个空的相同大小bitmap
        Bitmap target = Bitmap.createBitmap(mWidth, mWidth, Bitmap.Config.ARGB_8888);
        //通过bitmap创建画布, 在画布上绘图, 都会绘在bitmap上
        Canvas canvas = new Canvas(target);
        switch (type) {
            case TYPE_CIRCLE:
                canvas.drawCircle(mWidth / 2, mWidth / 2, mWidth / 2, mPaint);
                break;
            case TYPE_ROUND:
                RectF rect = new RectF(0, 0, mWidth, mHeight);
                canvas.drawRoundRect(rect, mRadius, mRadius, mPaint);
                break;
        }
        //取交集, 显示上层图片
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(src, 0, 0, mPaint);
        return target;
    }
}
