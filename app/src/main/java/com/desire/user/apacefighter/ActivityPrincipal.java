package com.desire.user.apacefighter;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ActivityPrincipal extends AppCompatActivity implements View.OnClickListener {
   private ImageButton buttonPlay;

   //Variavel para a música
   private MediaPlayer music;
   public static MediaPlayer projectile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_principal);
        buttonPlay = (ImageButton)findViewById(R.id.buttonPlay);
        //adding a click listener
        buttonPlay.setOnClickListener(this);

        //Adicionando a musica utilizando o arquivo na pasta Raw
        music = MediaPlayer.create(this, R.raw.musica);
        music.setLooping(true);
        music.start();

        projectile = MediaPlayer.create(this,R.raw.disparo);
        projectile.setLooping(true);
    }
    @Override
    public void onClick(View view) {
        //starting game activity
        startActivity(new Intent(this, GameActivity.class));


    }

}
