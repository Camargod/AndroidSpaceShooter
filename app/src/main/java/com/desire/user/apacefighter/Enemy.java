package com.desire.user.apacefighter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Enemy {

    public Bitmap bitmap;
    public float posX;
    public float posY;

    private int maxY;
    private int maxX;
    private int life = 100;

    private int speed;

    public boolean colisionEnabled = true;

    private Context context;
    private Random generator = new Random();


    public Enemy(Context context, int screenX, int screenY) {
        this.context = context;
        this.maxY = screenY;
        this.maxX = screenX;
        bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.enemy);
        this.maxY -= bitmap.getHeight();
        this.posY = maxY - bitmap.getHeight();
        this.posX = maxX - bitmap.getWidth();
        speed = generator.nextInt(7) + 2;
    }

    public boolean validateProjectileColision(int posX, int posY, int posX1, int posY1){
        if(colisionEnabled && colisionValidate(posX,posY,posX1,posY1)){
            life -= 50;
            if(life <= 0){
                try {
                    explode();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }
        return false;
    }

    public boolean colisionValidate(int posX, int posY, int posX1, int posY1){
        return this.posX <= posX && this.posX+bitmap.getWidth() <= posX1 && this.posY <= posY && this.posY+bitmap.getHeight() >= posY1;
    }

    private void resetEnemy(){
        posY = generator.nextInt(maxY);
        posX = maxX + bitmap.getWidth();
        life = 100;
        bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.enemy);
        speed = generator.nextInt(7) + 2;
        colisionEnabled = true;
    }

    public void update(){
        posX -= 4;
        posY += Math.cos(generator.nextDouble()) * speed;
        if(posX <= -bitmap.getWidth() - 20){
            resetEnemy();
        }
        if(posY < 0 || posY > maxY - bitmap.getHeight()){
            speed = -speed;
        }
    }

    private void explode() throws InterruptedException, ExecutionException, TimeoutException {
        bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.boom);
        colisionEnabled = false;
        final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(this::resetEnemy, 2, TimeUnit.SECONDS);
    }



}
