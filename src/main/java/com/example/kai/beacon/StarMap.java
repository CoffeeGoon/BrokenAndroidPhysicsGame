package com.example.kai.beacon;

/**
 * Created by Kai on 5/18/2015.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;
public class StarMap {
    private int dimensionX = 900;
    private int dimensionY = 900;
    private int startX = 0;
    private int startY = 0;
    private int velocity[] = new int[2];
    private int acceleration[] = new int[2];
    private int driftoff[] = new int[2];
    private int boundary = 150;
    Context c;
    private Bitmap tiles[][] = new Bitmap[150][150];
    private Bitmap dir[] = new Bitmap[12];
    Neutron dwarf;
    public WormHole goal;
    Bitmap swap[] = new Bitmap[7];
    public class WormHole {
        int x;
        int y;
        int framenum = 0;
        Bitmap frames[] = new Bitmap[4];
        Bitmap fr1 = BitmapFactory.decodeResource(c.getResources(), R.drawable.goal1);
        Bitmap fr2 = BitmapFactory.decodeResource(c.getResources(), R.drawable.goal2);
        Bitmap fr3 = BitmapFactory.decodeResource(c.getResources(), R.drawable.goal3);
        Bitmap fr4 = BitmapFactory.decodeResource(c.getResources(), R.drawable.goal4);
        Rect fin;

        WormHole(int awayx, int awayy) {
            Random rander = new Random();
            int sampleX = rander.nextInt(3000) + 700;
            int sampleY = rander.nextInt(3000) + 700;
            int magX = Math.abs(sampleX - awayx);
            int magY = Math.abs(sampleY - awayy);
            int distance = (int) Math.sqrt((magX * magX + magY * magY));
            if (distance < 2500) {
                if (magX < magY) {
                    while (distance < 2500) {
                        if (sampleX <= awayx) {
                            sampleX--;
                        } else {
                            sampleX++;
                        }
                        magX = Math.abs(sampleX - awayx);
                        distance = (int) Math.sqrt((magX * magX + magY * magY));
                    }
                } else {
                    while (distance < 2500) {
                        while (distance < 2500) {
                            if (sampleY <= awayy) {
                                sampleY--;
                            } else {
                                sampleY++;
                            }
                            magY = Math.abs(sampleY - awayy);
                            distance = (int) Math.sqrt((magX * magX + magY * magY));
                        }
                    }
                }
            }
            frames[2] = fr3;
            frames[3] = fr4;
            x = sampleX;
            y = sampleY;
            frames[0] = fr1;
            frames[1] = fr2;
            fin = new Rect(x + 20, y + 20, 80, 80);

        }
        public void update(int dx, int dy) {
           // x += dx;
           // y += dy;
            fin = new Rect(x + 20, y + 20, 80, 80);
            framenum+= 1;
            if (framenum > 23) {
                framenum = 0;

            }
        }

    };

    public StarMap(Context prev){
        c = prev;
        Bitmap tile1 = BitmapFactory.decodeResource(c.getResources(),R.drawable.startile1);
        Bitmap tile2 = BitmapFactory.decodeResource(c.getResources(),R.drawable.startile2);
        Bitmap tile3 = BitmapFactory.decodeResource(c.getResources(),R.drawable.startile3);
        Bitmap tile4 = BitmapFactory.decodeResource(c.getResources(),R.drawable.startile4);
        Bitmap tile5 = BitmapFactory.decodeResource(c.getResources(),R.drawable.startile5);
        Bitmap tile6 = BitmapFactory.decodeResource(c.getResources(),R.drawable.startile6);
        Bitmap tile7 = BitmapFactory.decodeResource(c.getResources(),R.drawable.startile7);
        swap[0] = tile1;
        swap[2] = tile2;
        swap[1] = tile3;
        swap[3] = tile4;
        swap[4] = tile5;
        swap[5] = tile6;
        swap[6] = tile7;
        Random rand = new Random();
        for(int i = 0; i < 150; i++){
            for(int j = 0; j < 150; j++){
                     tiles[i][j] = swap[rand.nextInt(7)];
            }
        }
        Bitmap ar1 = BitmapFactory.decodeResource(c.getResources(), R.drawable.arrow1);
        Bitmap ar2 = BitmapFactory.decodeResource(c.getResources(), R.drawable.arrow2);
        Bitmap ar3 = BitmapFactory.decodeResource(c.getResources(), R.drawable.arrow3);
        Bitmap ar4 = BitmapFactory.decodeResource(c.getResources(), R.drawable.arrow4);
        Bitmap ar5 = BitmapFactory.decodeResource(c.getResources(), R.drawable.arrow5);
        Bitmap ar6 = BitmapFactory.decodeResource(c.getResources(), R.drawable.arrow6);
        Bitmap ar7 = BitmapFactory.decodeResource(c.getResources(), R.drawable.arrow7);
        Bitmap ar8 = BitmapFactory.decodeResource(c.getResources(), R.drawable.arrow8);
        Bitmap ar9 = BitmapFactory.decodeResource(c.getResources(), R.drawable.arrow9);
        Bitmap ar10 = BitmapFactory.decodeResource(c.getResources(), R.drawable.arrow10);
        Bitmap ar11= BitmapFactory.decodeResource(c.getResources(), R.drawable.arrow11);
        Bitmap ar12= BitmapFactory.decodeResource(c.getResources(), R.drawable.arrow12);
         dir[2] = ar1;
        dir[1] = ar2;
        dir[0] = ar3;
        dir[11] = ar4;
        dir[10] = ar5;
        dir[9] = ar6;
        dir[8] = ar7;
        dir[7] = ar8;
        dir[6] = ar9;
        dir[5] = ar10;
        dir[4] = ar11;
        dir[3] = ar12;
        dwarf = new Neutron(c);
        startX = - dwarf.getX();
        startY = - dwarf.getY();
     goal = new WormHole(dwarf.getX(), dwarf.getY());

    }
    public void shift(int dx, int dy){
        startX += dx;
        startY += dy;
        goal.update(dx, dy);
    }
    public int getXshift(){
        return startX;
    }
    public Bitmap getSpot(){
        double xchange = goal.x - 60;
        double ychange = goal.y - 50;


         double ratio = ychange/xchange;
         int degrees =(int) -(Math.atan(ratio) * 180/Math.PI);
        if(degrees < 0){
            degrees = 360 + degrees;
        }
          System.out.println("ratio" + ratio + " " +  degrees + " " + xchange + " " + ychange);
          int index = (((degrees)) % 360) / 30;
        return dir[index];

    }
    public int getYshift(){
        return startY;
    }
    public Bitmap[][] getTiles(){
        return tiles;
    }
    public Bitmap getWormHole(){
        return goal.frames[(goal.framenum/6)];
    }
    public Bitmap getNeutron(){

        return dwarf.getImage();
    }

}
