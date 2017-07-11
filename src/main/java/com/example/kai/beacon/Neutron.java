package com.example.kai.beacon; /**
 * Created by Kai on 5/18/2015.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;


import java.util.Random;

public class Neutron {
    private int x;
    private int width;
    private int y;
    private int height;
    private Rect center;
    Context now;
    int count;
    private Bitmap testframes[];
    Neutron(Context prev){
         count = 0;
        now = prev;
        Random rand = new Random();
        x = rand.nextInt(1000) + 200;
        y = rand.nextInt(1000) + 200;
        testframes = new Bitmap[6];
        Bitmap testframe = BitmapFactory.decodeResource(now.getResources(),R.drawable.neutron1);
        testframes[0] = testframe;
        testframe = BitmapFactory.decodeResource(now.getResources(),R.drawable.neutron2);
        testframes[1] = testframe;
        testframe = BitmapFactory.decodeResource(now.getResources(),R.drawable.neutron3);
        testframes[2] = testframe;
        testframe = BitmapFactory.decodeResource(now.getResources(),R.drawable.neutron4);
        testframes[3] = testframe;
        testframe = BitmapFactory.decodeResource(now.getResources(),R.drawable.neutron5);
        testframes[4] = testframe;
        testframe = BitmapFactory.decodeResource(now.getResources(),R.drawable.neutron6);
        testframes[5] = testframe;

        width = testframe.getWidth();
        height = testframe.getHeight();

        center = new Rect(x, y, testframe.getWidth() + x, testframe.getHeight() + y);
    }
    public int getX(){
        return ( x +(center.width()/2) );
    }
    public int getY(){
        return ( y + (center.height()/2 ));
    }
    public void setX(int dx){
        x = dx;
    };
    public void setY(int dy){
        y = dy;
    }
    public void warp(int dx, int dy){
        x = dx;
        y = dy;
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    public Bitmap getImage(){ count++;
    int value = (count % 12) /2;
        if(count == 240){ count = 0; }
        return testframes[value];
    }

}
