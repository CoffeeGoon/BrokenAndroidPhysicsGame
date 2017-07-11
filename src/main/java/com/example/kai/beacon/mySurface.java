package com.example.kai.beacon;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Kai on 5/19/2015.
 */
public class mySurface extends SurfaceView implements SurfaceHolder.Callback {
    private Context context;
    public mySurface(Context context)
    {
        super(context);
        this.context = context;



        getHolder().addCallback(this);
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
    }

    public void surfaceCreated(SurfaceHolder holder)
    {

    }

    public void surfaceDestroyed(SurfaceHolder holder)
    {

    }

}
