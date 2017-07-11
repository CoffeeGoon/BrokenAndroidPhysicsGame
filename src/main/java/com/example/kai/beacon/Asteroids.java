package com.example.kai.beacon;

/**
 * Created by Kai on 5/25/2015.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

public class Asteroids {
    int X;
    int Y;
    int VelocityX;
    int VelocityY;
    int type;
    Context c;
    Bitmap[] frames;
    int framenum;
    Asteroids(int dimx, int dimy, Context nw){
        c = nw;
        frames = new Bitmap[6];
        Bitmap temp = BitmapFactory.decodeResource(c.getResources(), R.drawable.asteroid1);
        frames[0] = temp;
         temp = BitmapFactory.decodeResource(c.getResources(), R.drawable.asteroid2);
        frames[1] = temp;
        temp = BitmapFactory.decodeResource(c.getResources(), R.drawable.asteroid3);
        frames[2] = temp;
        temp = BitmapFactory.decodeResource(c.getResources(), R.drawable.asteroid4);
        frames[3] = temp;
        temp = BitmapFactory.decodeResource(c.getResources(), R.drawable.asteroid6);
        frames[4] = temp;
        temp = BitmapFactory.decodeResource(c.getResources(), R.drawable.asteroid5);
        frames[5] = temp;
        Random rand = new Random();
        VelocityX = rand.nextInt(8) + 1;
        VelocityY = rand.nextInt(8) + 1;
        int sign = rand.nextInt(2);
        if(sign == 0){
            VelocityX *= -1;
        }
         sign = rand.nextInt(2);
        if(sign == 0){
            VelocityY *= -1;
        }
    }
    public Bitmap getFrame(){
        return frames[framenum];
    }
    public void update(int time){
        X += VelocityX;
        Y += VelocityY;
        if(X > 4200){
            X = 0;
        }
        if(Y > 4200){
            Y = 0;
        }
        if(X < -30){
            X = 4000;
        }
        if(Y < -30){
            Y = 4000;
        }
        framenum = (time % 5999)/1000;
    }

}
