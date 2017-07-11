package com.example.kai.beacon;

/**
 * Created by Kai on 5/23/2015.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Beacon {
    int x;
    int y;
    int quadrant = 4;
    double accelx = 0;
    double accely = 0;
    Bitmap frames[];
    int framenum;
    boolean touched = false;
    boolean attraction = false;
    BitmapFactory fact;
    public Beacon(Context c, int a, int b){
        x = a;
        y = b;
        fact = new BitmapFactory();
        frames = new Bitmap[9];
        Bitmap un = BitmapFactory.decodeResource(c.getResources(), R.drawable.beacon1);
        frames[0] = un;
        Bitmap dos = BitmapFactory.decodeResource(c.getResources(), R.drawable.beacon2);
        frames[1] = dos;
        Bitmap tre = BitmapFactory.decodeResource(c.getResources(), R.drawable.beacon3);
        frames[2] = tre;
        Bitmap four = BitmapFactory.decodeResource(c.getResources(), R.drawable.beacon4);
        frames[3] = four;
        Bitmap five = BitmapFactory.decodeResource(c.getResources(), R.drawable.beacon5);
        frames[4] = five;
        Bitmap six = BitmapFactory.decodeResource(c.getResources(), R.drawable.beacon6);
        frames[5] = six;

        Bitmap seven = BitmapFactory.decodeResource(c.getResources(), R.drawable.beacon7);
        frames[6] = seven;

        Bitmap eight = BitmapFactory.decodeResource(c.getResources(), R.drawable.beacon8);
        frames[7] = eight;

        Bitmap nine = BitmapFactory.decodeResource(c.getResources(), R.drawable.beacon9);
        frames[8] = nine;
     framenum  = 0;

    }
    public Bitmap getFrame(int time){
        if(touched){
            framenum = 6 + (time % 60)/20;
        }
        else if(!touched){
            framenum = (time % 100)/20;
        }
        return(frames[framenum]);
    }
    public void checkLoc(int pointx, int pointy){
        boolean change = false;
        if (pointx < x + 20 && pointx > x - 20){
            if(pointy > y - 20 && pointy < y + 20){
                change = true;
            }
        }
        if(change){
            touched = !touched;
        }

    }
    public void calculatePolarity(int xshift, int yshift, double centerX, double centerY, int xsign, int ysign, int dwx, int dwy){
        if(touched){
            int distx = (int) (xsign*(xshift + x + centerX + dwx) );
            int disty = (int) (ysign*(yshift + y + centerY + dwy));
            int maxaccel = 8;
            double angle = Math.PI/2;
            if(distx != 0){
                angle = Math.atan(disty/distx);
            }
            double totaldist = Math.sqrt((distx*distx) + (disty*disty));
            double accel = maxaccel/(totaldist/50);
            accelx = -(accel * Math.cos(angle));
            accely = (accel * Math.sin(angle));
        }
        else{ accelx = 0; accely = 0; }
    }
    public double getAX(){
       return accelx;
    }

    public double getAY(int ypos){
        return accely;
    }

}
