package com.desire.user.apacefighter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Projectile {
    public int x;
    public int y;
    private int speed;
    private int maxX;
    private int width;
    private Bitmap bitmap;
    Context context;

    public Projectile (Context context, int screenX, int playerX, int playerY){
        maxX = screenX;
        x = 100000;
        y = playerY + 46;
        speed = 60;
        width = 33;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.shoot);
    }

    public void update(){
        x+=speed;
    }
    public int getShotWidth(){
        return width;
    }
    public Bitmap getBitmap() { return bitmap; }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}
