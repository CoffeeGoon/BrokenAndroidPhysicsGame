package com.example.jmodorsk.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by jmodorsk on 6/2/15.
 */

public class Drain extends View {

    private Paint paint = new Paint();
    public static double xAccel;
    public static double yAccel;
    public static double xFriction = .01;
    public static double yFriction = .01;
    public static double radiusDrain = 30;
    public float xDrain, yDrain, xLeftWall, xTopWall, xRightWall, xLowerWall, yLeftWall, yTopWall,
            yRightWall, yLowerWall;

    public Runnable animator = new Runnable() {
        @Override
        public void run() {
            physics();
            invalidate();
            postDelayed(this, 20);
        }
    };

    public Drain(Context context) {
        super(context);
    }
    public Drain(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        xDrain = (getMeasuredWidth() - 40);
        yDrain = (getMeasuredHeight() - 40);
        xLeftWall = 0;
        xTopWall = getMeasuredHeight()/3;
        xRightWall = (getMeasuredWidth()/4) * 3;
        xLowerWall = xTopWall + 10;
        yLeftWall = getMeasuredWidth()/4;
        yTopWall = (getMeasuredHeight()/3) * 2;
        yRightWall = getMeasuredWidth();
        yLowerWall = yTopWall + 10;
        setMeasuredDimension(width, height);
    }

    protected void onDraw(Canvas c) {
        paint.setColor(Color.BLACK);
        c.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), paint);

        paint.setColor(Color.YELLOW);
        c.drawRect(xLeftWall, xTopWall, xRightWall, xLowerWall, paint);

        paint.setColor(Color.YELLOW);
        c.drawRect(yLeftWall, yTopWall, yRightWall, yLowerWall, paint);

        paint.setColor(Color.WHITE);
        c.drawCircle(xDrain, yDrain, (float)radiusDrain, paint);

        paint.setColor(Color.RED);
        c.drawCircle((float) Ball.x, (float) Ball.y, Ball.radius, paint);
    }

    protected void physics() {
        Ball.xVel += (.2 * xAccel) + xFriction;
        Ball.yVel += (.2 * yAccel) + yFriction;
        if(Ball.xVel == 0) {
            xFriction = 0;
        }
        if(Ball.yVel == 0) {
            yFriction = 0;
        }
        if(Ball.xVel < 0) {
            xFriction = .05;
        }
        if(Ball.xVel > 0) {
            xFriction = -.05;
        }
        if(Ball.yVel < 0) {
            yFriction = .05;
        }
        if(Ball.xVel > 0) {
            yFriction = -.05;
        }
        if(Ball.xVel <= -5) {
            Ball.xVel = -5;
        }
        if(Ball.xVel >= 5) {
            Ball.xVel = 5;
        }
        if(Ball.yVel <= -6) {
            Ball.yVel = -6;
        }
        if(Ball.yVel >= 6) {
            Ball.yVel = 6;
        }
        Ball.x = Ball.x + (Ball.xVel);
        Ball.y = Ball.y + (Ball.yVel);
        double difx = Ball.x - (xDrain);
        double dify = Ball.y - (yDrain);
        double dif = Math.sqrt(difx*difx + dify*dify);
        if(dif < (Ball.radius + radiusDrain)) {
            win();
        }
        if(Ball.y + Ball.radius > 0 && Ball.x + Ball.radius > 0 && Ball.y + Ball.radius > xTopWall
                && Ball.x - Ball.radius < xRightWall && Ball.y + Ball.radius < xLowerWall - 1) {
            Ball.y = xTopWall - Ball.radius;
            Ball.yVel = -Ball.yVel;
        }
        if(Ball.y + Ball.radius < yTopWall && Ball.x + Ball.radius > 0 &&
                Ball.y - Ball.radius > xTopWall + 1 && Ball.x - Ball.radius < xRightWall &&
                Ball.y - Ball.radius < xLowerWall) {
            Ball.y = xLowerWall + Ball.radius;
            Ball.yVel = -Ball.yVel;
        }
        if(Ball.y - Ball.radius > xLowerWall && Ball.x - Ball.radius > 0 && Ball.y + Ball.radius > yTopWall
                && Ball.x + Ball.radius > yLeftWall && Ball.y + Ball.radius < yLowerWall - 1) {
            Ball.y = yTopWall - Ball.radius;
            Ball.yVel = -Ball.yVel;
        }
        if(Ball.y - Ball.radius < getMeasuredHeight() && Ball.x - Ball.radius > 0 &&
                Ball.y - Ball.radius > yTopWall + 1 && Ball.x + Ball.radius > yLeftWall &&
                Ball.y - Ball.radius < yLowerWall) {
            Ball.y = yLowerWall + Ball.radius;
            Ball.yVel = -Ball.yVel;
        }
        if(Ball.y - Ball.radius < 0) {
            Ball.y = 0 + Ball.radius;
            Ball.yVel = -Ball.yVel;
        }
        if((Ball.x - Ball.radius) < 0) {
            Ball.x = 0 + Ball.radius;
            Ball.xVel = -Ball.xVel;
        }
        if(Ball.y + Ball.radius > getMeasuredHeight()) {
            Ball.y = getMeasuredHeight() - Ball.radius;
            Ball.yVel = -Ball.yVel;
        }
        if(Ball.x + Ball.radius > getMeasuredWidth()) {
            Ball.x = getMeasuredWidth() - Ball.radius;
            Ball.xVel = -Ball.xVel;
        }

    }

    public void win() {
        Ball.x = 20;
        Ball.y = 20;
        Ball.xVel = 0;
        Ball.yVel = 0;
    }
}
