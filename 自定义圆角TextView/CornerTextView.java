package com.stdl.electric.electricterminal.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.stdl.electric.electricterminal.R;

/**
 * Create by DXH on 2021/05/17
 * 圆角TextView
 */
@SuppressLint("AppCompatCustomView")
public class CornerTextView extends TextView {
    private int mCornerRadiusSize;//圆角半径大小
    private int mCornerColor;//圆角颜色
    private Paint mCornerPaint;
    private Point pointTop1, pointTop2, pointBottom1, pointBottom2, pointRight1, pointRight2, pointLeft1, pointLeft2;
    private Path path;

    public CornerTextView(Context context) {
        this(context, null);
    }

    public CornerTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CornerTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributeSet(attrs);//初始化配置
        init();
    }


    private void initAttributeSet(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CornerTextView);
        mCornerRadiusSize = typedArray.getDimensionPixelSize(R.styleable.CornerTextView_cornerRadiusSize, 0);
        mCornerColor = typedArray.getColor(R.styleable.CornerTextView_cornerColor, Color.parseColor("#ffffff"));
        typedArray.recycle();
    }

    private void init() {
        mCornerPaint = new Paint();
        mCornerPaint.setAntiAlias(true);
        mCornerPaint.setColor(mCornerColor);
        mCornerPaint.setStyle(Paint.Style.FILL);

        path = new Path();

        pointTop1 = new Point();
        pointTop2 = new Point();
        pointBottom1 = new Point();
        pointBottom2 = new Point();
        pointRight1 = new Point();
        pointRight2 = new Point();
        pointLeft1 = new Point();
        pointLeft2 = new Point();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMeasureSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = 0;
        switch (widthMeasureSpecMode) {
            case MeasureSpec.AT_MOST://wrap_content
                width = getMeasuredWidth();
                break;
            case MeasureSpec.EXACTLY://match_parent
                width = MeasureSpec.getSize(widthMeasureSpec);
                break;
            case MeasureSpec.UNSPECIFIED://value px
                width = MeasureSpec.getSize(widthMeasureSpec);
                break;
        }

        int heightMeasureSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = 0;
        switch (heightMeasureSpecMode) {
            case MeasureSpec.AT_MOST://wrap_content
                height = getMeasuredHeight();
                break;
            case MeasureSpec.EXACTLY://match_parent
                height = MeasureSpec.getSize(heightMeasureSpec);
                break;
            case MeasureSpec.UNSPECIFIED://value px
                height = MeasureSpec.getSize(heightMeasureSpec);
                break;
        }

        setMeasuredDimension(Math.max(width, mCornerRadiusSize * 2), Math.max(height, mCornerRadiusSize * 2));//重写textview大小
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        mCornerPaint.setColor(mCornerColor);

        pointTop1.setXY(mCornerRadiusSize, 0);
        pointTop2.setXY(getMeasuredWidth() - mCornerRadiusSize, 0);
        pointBottom1.setXY(mCornerRadiusSize, getMeasuredHeight());
        pointBottom2.setXY(getMeasuredWidth() - mCornerRadiusSize, getMeasuredHeight());
        pointRight1.setXY(getMeasuredWidth(), mCornerRadiusSize);
        pointRight2.setXY(getMeasuredWidth(), getMeasuredHeight() - mCornerRadiusSize);
        pointLeft1.setXY(0, mCornerRadiusSize);
        pointLeft2.setXY(0, getMeasuredHeight() - mCornerRadiusSize);

        path.reset();
        path.moveTo(pointLeft1.x, pointLeft1.y);
        //绘制圆角矩形路径
        drawPath(pointTop1, true, pointLeft1.x, mCornerRadiusSize / 2, mCornerRadiusSize / 2, pointTop1.y);
        drawPath(pointTop2, false, 0, 0, 0, 0);
        drawPath(pointRight1, true, pointTop2.x + mCornerRadiusSize / 2, pointTop2.y, pointRight1.x, mCornerRadiusSize / 2);
        drawPath(pointRight2, false, 0, 0, 0, 0);
        drawPath(pointBottom2, true, pointRight2.x, pointRight2.y + mCornerRadiusSize / 2, pointBottom2.x + mCornerRadiusSize / 2, pointBottom2.y);
        drawPath(pointBottom1, false, 0, 0, 0, 0);
        drawPath(pointLeft2, true, pointBottom1.x - mCornerRadiusSize / 2, pointBottom1.y, pointLeft2.x, pointLeft2.y + mCornerRadiusSize / 2);
        drawPath(pointLeft1, false, 0, 0, 0, 0);
        path.close();
        canvas.drawPath(path, mCornerPaint);
        canvas.restore();
        super.onDraw(canvas);
    }

    private void drawPath(Point nextPoint, boolean isCircleCorner, int x1, int y1, int x2, int y2) {
        if (isCircleCorner)
            path.cubicTo(x1, y1, x2, y2, nextPoint.x, nextPoint.y);
        else
            path.lineTo(nextPoint.x, nextPoint.y);
    }

    public void setmCornerRadiusSize(int mCornerRadiusSize) {
        this.mCornerRadiusSize = mCornerRadiusSize;
        invalidate();
    }

    public void setmCornerColor(int mCornerColor) {
        this.mCornerColor = mCornerColor;
        invalidate();
    }


    static class Point {
        public int x;
        public int y;

        public Point() {
        }

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void setXY(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
