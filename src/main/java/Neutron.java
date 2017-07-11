/**
 * Created by Kai on 5/18/2015.
 */
import java.math.*;
import java.util.Timer;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import java.util.Random;
import android.graphics.Rect;

import com.example.kai.beacon.R;

public class Neutron {
    private int x;
    private int y;
    private Rect center;
    private Bitmap testframe = BitmapFactory.decodeResource(null, R.drawable.beacon9 );
    Neutron(){
        Random rand = new Random();
        x = rand.nextInt(600) + 150;
        y = rand.nextInt(600) + 150;
        center = new Rect(x, y, testframe.getWidth(), testframe.getHeight());
    }
    public int getX(){
        return (x +(testframe.getWidth()/2));
    }
    public int getY(){
        return (y + (testframe.getHeight()/2));
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
        return testframe.getWidth();
    }
    public int getHeight(){
        return testframe.getHeight();
    }


}
