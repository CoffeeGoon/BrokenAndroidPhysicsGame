package com.example.kai.beacon;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.AnimationUtils;

import java.util.Random;
import java.util.Timer;


public class MainActivity extends ActionBarActivity implements SurfaceHolder.Callback{
    public SurfaceView window;
    public SurfaceHolder hold;
    boolean surfaceExists = false;
    public StarMap map;
    private boolean once = false;
    public Beacon beacons[];
    public Canvas canvas;
    private View.OnTouchListener test;
    Bitmap[][] blah;
    int dx = 0;
    int dy = 0;

    private Paint paint;

    private Timer time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        map = new StarMap(this);
        paint = new Paint();
        blah = map.getTiles();
        canvas = new Canvas();
        beacons = new Beacon[80];
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                canvas.drawBitmap(blah[i][j], map.getXshift() + (30 * i), map.getYshift() + (30 * j), null);
            }
        }


        test = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int tempx = (int) event.getX();
                int tempy = (int) event.getY();

                if (tempx > window.getWidth() / 2) {
                    dx = -3;
                } else {
                    dx = 3;
                }
                if (tempy > window.getHeight() / 2) {
                    dy = -3;
                } else {
                    dy = 3;
                }

                int beaconCx;
                int beaconCy;
                for(int i = 0; i < 80; i++){
                    Beacon temper = beacons[i];

                    int beaconCX = 1;
                    int beaconCY =  1;
                    if(temper.quadrant == 1){
                        beaconCX = -1;
                    }
                    if(temper.quadrant == 2) {
                        beaconCY = -1;
                        beaconCX = -1;
                    }
                    if(temper.quadrant == 3){
                        beaconCX = -1;
                    }

                    beaconCX *= tempx;
                    beaconCY *= tempy;
                    beaconCX  +=  map.getXshift() + map.dwarf.getX() + window.getWidth()/2;
                    beaconCY +=  map.getYshift() + map.dwarf.getY() + window.getHeight()/2;
                    beacons[i].checkLoc(beaconCX, beaconCY);
                }
                return true;
            }

        };
        generateBeacons(canvas, map);
        System.out.println("goal coords: " + map.goal.x + " " + map.goal.y);
        canvas.drawBitmap(map.getWormHole(), map.goal.x + map.getXshift(), map.goal.y + map.getYshift(), null);
        canvas.drawBitmap(map.getNeutron(), 100, 200, null);

    }
    public void generateBeacons(Canvas c, StarMap mp){
        int targetX = mp.goal.x;
        int targetY = mp.goal.y;
        boolean posx = targetX < mp.dwarf.getX();
        boolean posy = targetY < mp.dwarf.getY();
        double slope = (double)(targetY) / (double)(targetX);
        double inverse = - (double)(targetX)/(double)(targetY);
        double dist = Math.sqrt((targetX)*targetX + (targetY)*(targetY));
        double incr = dist/15;
        System.out.println("dist " + dist + " slope" + slope);
        boolean up = true;
        double startX1 = targetX;
        double startY1 = targetY;
        double changeX = 0;
        double changeY = 0;
        for(int i = 0; i < 50; i++){
               changeX = Math.sqrt((incr*incr)/((slope*slope) + 1));
                changeY  = changeX*slope;



            Random rand = new Random();
            if(up){
                //int mag = rand.nextInt(100) + 1;
                //changeX += mag *(inverse);
                //changeY += mag;
            }
            else{
                //int mag =-1 *( rand.nextInt(1) + 1);
                //changeX += mag *(inverse);
                //changeY += mag;
            }
            up = !up;
            //if(!posx){
              //  changeX = -changeX;
               //startX1 += changeX;
           // }
            //if(!posy){
              //  changeY = -changeY;
                //startY1 += changeY;
            //}
            //
            int quadrant = 4;
            if(targetX + map.getXshift() < 0){
               quadrant = 3;
                if(targetY + map.getYshift() < 0){
                    quadrant = 2;
                }
            }
            else if(targetY + map.getYshift() < 0 ){
                //if(startY1 > 20) {
                  //  startY1 = -startY1;
                //}
                //changeY = -changeY;
              quadrant = 1;

            }
            startX1 -= changeX;
            startY1 -= changeY;



            changeX = 0;
            changeY = 0;
            System.out.println("Beacon coord: " + i + " " + startX1 + " " + startY1);
            Beacon temp = new Beacon(this,(int)(startX1), (int)(startY1));
            temp.quadrant = quadrant;
            beacons[i] = temp;
        }
        int magnitude = 15;
        Random cap = new Random();

        int initialx = 2200;
        int initialy = 2200;
        int count = 5;
        int prev1 = 1;
        int prev2 = 1;
        double initangle = 2 * Math.PI;
       for(int i = 16; i < 80; i++){
           magnitude = prev1 + prev2;
           count--;
           int loon = cap.nextInt(70);
           int sign =cap.nextInt(2);
           if(sign == 0){
               loon = - loon;
           }
           int spreadx = (int)( Math.cos(initangle) * (magnitude)) + loon ;
            sign =cap.nextInt(2);
           loon = cap.nextInt(70);
           if(sign == 0){
               loon = - loon;
           }
           int spready = (int)( Math.sin(initangle) * (magnitude) ) + loon;

           beacons[i] = new Beacon(this,(int)(spreadx), (int)(spready));
           initangle += Math.PI/8;
           if(count < 0){
               count = 5;
               prev2 = prev1;
               prev1 = magnitude;
           }
       }
    }

    @Override
    protected void onResume(){
       super.onResume();
        window = (SurfaceView) findViewById(R.id.game);
        hold = window.getHolder();
        hold.addCallback(this);
        window.setOnTouchListener(test);
        window.draw(canvas);
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {

    }


    public void surfaceCreated(SurfaceHolder holder)
    {
        surfaceExists = true;
        MyThread thread = new MyThread();
        thread.execute(null);
    }

    public void surfaceDestroyed(SurfaceHolder holder)
    {
        surfaceExists = false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public class MyThread extends AsyncTask {
        @Override
        protected Object doInBackground(Object... params) {

            while(surfaceExists){
                canvas = window.getHolder().lockCanvas();

                // reset the canvas to blank at the start
                canvas.drawColor(Color.BLACK);
                if(map.getXshift() + 4000 < window.getWidth()){
                    dx = 3;
                }
                if(map.getXshift()  > 30){
                    dx = -3;
                }
                if(map.getYshift() + 4000 < window.getHeight()){
                    dy = 3;
                }
                if(map.getYshift()  > 30){
                    dy = -3;
                }

                map.shift(dx, dy);
                for(int i = 0; i < 100; i++){
                    for(int j = 0; j < 100; j++){
                        canvas.drawBitmap(blah[i][j], map.getXshift() + (30*i) ,map.getYshift() + (30 * j), null);
                    }
                }
                int xvalue = window.getWidth()/2;
                int yvalue = window.getHeight()/2;
                canvas.drawBitmap(map.getWormHole(),map.goal.x + map.getXshift() + xvalue - 120, map.goal.y + map.getYshift() + yvalue - 120, null);
                canvas.drawBitmap(map.getNeutron(), window.getWidth() / 2, window.getHeight() / 2, null);
                for(int k = 0; k < 80; k++){
                    //beacons[k].x += dx;
                    //beacons[k].y += dy;
                    Beacon tempy = beacons[k];
                    int xsign = 1;
                    int ysign =  1;
                    if(tempy.quadrant == 1){
                        ysign = -1;
                    }
                    if(tempy.quadrant == 2) {
                        ysign = -1;
                        xsign = -1;
                    }
                    if(tempy.quadrant == 3){
                        xsign = -1;
                    }
                    int time = (int) AnimationUtils.currentAnimationTimeMillis();
                    canvas.drawBitmap(tempy.getFrame(time), (xsign * tempy.x) + map.getXshift() + map.dwarf.getX() + xvalue, (ysign * tempy.y) + yvalue + map.getYshift() + map.dwarf.getY(), null);
                    if(!once){ System.out.println("coords: " + k + " " + (tempy.x + map.getXshift() + map.dwarf.getX()) + " " + (tempy.y + map.getYshift() + map.dwarf.getY()));}

                }
                once = true;
                String info = "X: " + (map.goal.x + map.getXshift()) + "  Y: " +  (map.goal.y + map.getYshift());
                Paint temp = new Paint();
                temp.setColor(Color.BLUE);
                temp.setTextSize(30);
                canvas.drawText(info, 60, window.getHeight() - window.getHeight() / 8, temp);

                 //
                 //canvas.drawBitmap(map.getSpot(), window.getWidth() / 2, window.getHeight() / 2, null);
                window.getHolder().unlockCanvasAndPost(canvas);

                // increment y so that bitmap is 1 pixel further down next call

            }
            return null;
        }


    }
}
