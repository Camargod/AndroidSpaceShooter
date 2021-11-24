package com.desire.user.apacefighter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.media.MediaPlayer;
import com.desire.user.apacefighter.ActivityPrincipal;

import java.util.ArrayList;

public class GameView extends SurfaceView implements Runnable {
        //boolean variable to track if the game is playing or not
        volatile boolean playing;
        //the game thread
        private Thread gameThread = null;
        private final Player player;
        private final Enemy enemy;
        private final Paint paint;

        private Canvas canvas;
        private final SurfaceHolder surfaceHolder;

        //Variavel para cor branca
        private final Paint glow;

        //Varivel e tinta para o tiro
        private final Projectile projectile;
        private Paint pew;
        private final int screenx;
        private boolean shooting = false;

        //Lista para as estrelas
        private ArrayList<Star> stars = new ArrayList<>();


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                //Aqui quando a tela não estivesse mais sendo pressionada iria parar o tiro
                player.stopBoosting();
                ActivityPrincipal.projectile.pause();

                //Booleana de tiro fica falsa
                shooting = false;

                break;
            case MotionEvent.ACTION_DOWN:
                //Aqui quando a tela estivesse sendo pressionada iria ter o tiro
                player.setBoosting();

                //Booleana de tiro fica verdadeira
                shooting = true;

                //Barulho do tiro
                ActivityPrincipal.projectile.start();
                break;
        }
        return true;
    }

        //Class constructor
    public GameView(Context context, int screenX, int screenY) {
        super(context);
        //initializing player object
        //this time also passing screen size to player constructor
        player = new Player(context, screenX, screenY);
        enemy = new Enemy(context,screenX,screenY);
        //initializing drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();

        //Inicializando o tiro
        projectile = new Projectile(context, screenX,player.getX(),player.getY());

        //Alocando a variável de tela para ser utilizada fora do GameView
        screenx = screenX;


        //Cor branca
        glow = new Paint();
        glow.setColor(ContextCompat.getColor(context, R.color.white));

        //Cor que seria utilizado pelo tiro
        //pew = new Paint();
        //pew.setColor(ContextCompat.getColor(context,R.color.colorAccent));

        //Estrelas sendo adicionadas na lista
        int starNums = 100;
        for (int i = 0; i < starNums; i++){
            Star s = new Star(screenX, screenY);
            stars.add(s);
        }
    }   @Override
        public void run() {
            while (playing) {
                //to update the frame
                update();
                //to draw the frame
                draw();
                //to control
                control();
            }
        }

        private void update() {
            //updating player position
            player.update();
            enemy.update();
            //Tiros saindo da nave
            if(shooting){
                if(projectile.x > screenx){
                    projectile.x = player.getX() + 300;
                    projectile.y = player.getY() + 75;
                }
            }
            int projX1 = projectile.x + projectile.getBitmap().getWidth();
            int projY1 = projectile.y + projectile.getBitmap().getHeight();

            boolean colided = enemy.validateColision(projectile.x,projectile.y,projX1,projY1);
            if(colided){
                shooting = false;
                projectile.x = player.getX() + 300;
                projectile.y = player.getY() + 75;
            }
            projectile.update();

            //Atualizando as estrelas
            for (Star s : stars){
                s.update(player.getSpeed());
            }
        }

        private void draw() {
            //checking if surface is valid
            if (surfaceHolder.getSurface().isValid()) {
                //locking the canvas
                canvas = surfaceHolder.lockCanvas();
                //drawing a background color for canvas
                canvas.drawColor(Color.BLACK);

                //Desenhando as estrelas
                for (Star s : stars){
                    glow.setStrokeWidth(s.getStarWidth());
                    canvas.drawPoint(s.getX(), s.getY(), glow);
                }

                //Drawing the shoot
                canvas.drawBitmap(
                        projectile.getBitmap(),
                        projectile.getX(),
                        projectile.getY(),
                        paint);

                //Drawing the player
                canvas.drawBitmap(
                        player.getBitmap(),
                        player.getX(),
                        player.getY(),
                        paint);
                //Unlocking the canvas
                canvas.drawBitmap(enemy.bitmap,enemy.posX,enemy.posY,paint);

                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
        private void control() {
            try {
                gameThread.sleep(17);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void pause() {
            //when the game is paused
            //setting the variable to false
            playing = false;
            try {
                //stopping the thread
                gameThread.join();
            } catch (InterruptedException e) {
            }
        }
        public void resume() {
            //when the game is resumed
            //starting the thread again
            playing = true;
            gameThread = new Thread(this);
            gameThread.start();
        }
    }





