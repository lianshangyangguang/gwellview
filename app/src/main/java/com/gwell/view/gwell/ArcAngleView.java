package com.gwell.view.gwell;


import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xiyingzhu on 2017/4/5.
 */
public class ArcAngleView extends View {
    private Context mContext;
    private Paint paint;
    private Path path;
    private float currentAngle;

    public ArcAngleView(Context context) {
        this(context, null);
    }

    public ArcAngleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        paint = new Paint();
        path = new Path();
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int mWidth = 0;
        int mHeight = 0;
        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        } else {
            mWidth = Utils.dip2px(mContext, 120);
        }


        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        } else {
            mHeight = Utils.dip2px(mContext, 80);
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();

        drawArc(canvas);
        drawPoint(canvas);

        canvas.restore();
        super.onDraw(canvas);
    }

    private void drawPoint(Canvas canvas) {
        paint.reset();
        path.reset();
        paint.setColor(Color.rgb(0x49,0x90,0xff));
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        canvas.rotate(currentAngle, Utils.dip2px(mContext, 71.5f), Utils.dip2px(mContext, 67.75f));
        path.moveTo(Utils.dip2px(mContext, 71.5f), Utils.dip2px(mContext, 56));
        path.lineTo(Utils.dip2px(mContext, 68f), Utils.dip2px(mContext, 65));
        path.lineTo(Utils.dip2px(mContext, 75f), Utils.dip2px(mContext, 65));
        path.close();
        canvas.drawPath(path, paint);
        paint.setColor(Color.WHITE);
        canvas.drawCircle(Utils.dip2px(mContext, 71.5f), Utils.dip2px(mContext, 67.75f), Utils.dip2px(mContext, 4.25f), paint);
    }

    private void drawArc(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(Utils.dip2px(mContext, 1));
        paint.setAntiAlias(true);
        paint.setTextSize(Utils.dip2px(mContext, 9));
        canvas.drawArc(Utils.dip2px(mContext, 44), Utils.dip2px(mContext, 40), Utils.dip2px(mContext, 99), Utils.dip2px(mContext, 87), -35, -110, false, paint);
        canvas.drawText("0°", Utils.dip2px(mContext, 35), Utils.dip2px(mContext, 54), paint);
        canvas.drawText("110°", Utils.dip2px(mContext, 100), Utils.dip2px(mContext, 54), paint);
        canvas.drawLine(Utils.dip2px(mContext, 46), Utils.dip2px(mContext, 47), Utils.dip2px(mContext, 52), Utils.dip2px(mContext, 53), paint);
        canvas.drawLine(Utils.dip2px(mContext, 91), Utils.dip2px(mContext, 53), Utils.dip2px(mContext, 97), Utils.dip2px(mContext, 47), paint);
    }

    public void setAngle(float percent) {
        float angle = percent * 110;
        angle -= 55;
        float from = 0;
        if (currentAngle != 0) {
            from = currentAngle;
        }
        angle = angle > 360 ? angle % 360 : angle;
        angle = angle < -360 ? angle % 360 : angle;
        if (angle > 55) {
            angle = 55;
        } else if (angle < -55) {
            angle = -55;
        }
        ValueAnimator animator = ValueAnimator.ofFloat(from, angle);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentAngle = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.setDuration(500);
        animator.start();
    }
}
