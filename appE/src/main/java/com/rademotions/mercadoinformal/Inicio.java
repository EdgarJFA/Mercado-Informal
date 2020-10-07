package com.rademotions.mercadoinformal;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.rademotions.mercadoinformal.visao.LoginActivity;

public class Inicio extends AppCompatActivity {

    private static int inicio = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent principal = new Intent(Inicio.this, LoginActivity.class);
                startActivity(principal);
                finish();
            }
        }, inicio);
    }


}
