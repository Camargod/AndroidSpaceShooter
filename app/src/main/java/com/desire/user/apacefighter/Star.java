package com.desire.user.apacefighter;

import java.util.Random;

public class Star {
    private int x;
    private int y;
    private int speed;
    private int maxX;
    private int maxY;
    private int minX;
    private int minY;

    public Star(int screenX, int screenY){
        //Variáveis da estrela
        maxX = screenX;
        maxY = screenY;
        minX = 0;
        minY = 0;
        Random generator = new Random();
        speed = generator.nextInt(10);
        x = generator.nextInt(maxX);
        y = generator.nextInt(maxY);
    }
    //movimentando as estrelas de acordo com as coordenadas x do plauer
    public void update(int playerSpeed){
        //Utiliza junto de sua velocidade a do player para um movimento mais rapido para acompanhar o player
        x -= playerSpeed;
        x -= speed;
        //Caso a estrela chegue ao final da tela
        if(x<0){
            //Reinicia as estrelas plea borda da tela
            x = maxX;
            Random generator = new Random();
            y = generator.nextInt(maxY);
            speed = generator.nextInt(15);
        }
    }

    //Tamanho da estrela
    public float getStarWidth(){
        float minX = 1.0f;
        float maxX = 4.0f ;
        Random rand = new Random();
        float finalX = rand.nextFloat()*(maxX - minX) + minX;
        return finalX;
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}
