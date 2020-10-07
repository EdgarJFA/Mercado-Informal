package com.rademotions.mercadoinformal.visao;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rademotions.mercadoinformal.Controle.SlideAdapter;
import com.rademotions.mercadoinformal.R;

public class CartaoVisitaActivity extends AppCompatActivity {

    private ViewPager viewPagerSlide;
    private LinearLayout pontos_slide;
    private SlideAdapter slideAdapter;

    private TextView[] nrPontos;

    private Button btVoltar, btProximo, btPular;
    private  int paginaCorrente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartao_visita);

        viewPagerSlide = (ViewPager) findViewById(R.id.viewPagerSlide);
        pontos_slide = (LinearLayout) findViewById(R.id.pontos_slide);
        slideAdapter = new SlideAdapter(this);

        btVoltar = (Button) findViewById(R.id.button_anterior_cartaoVisita);
        btProximo = (Button) findViewById(R.id.button_proximo_cartaoVisita);
        btPular = (Button) findViewById(R.id.button_pular_cartaoVisita);

        viewPagerSlide.setAdapter(slideAdapter);

        addPontoIndicador(0);

        viewPagerSlide.addOnPageChangeListener(viewListener);

        btProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewPagerSlide.setCurrentItem(paginaCorrente + 1);

            }
        });

        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewPagerSlide.setCurrentItem(paginaCorrente - 1);

            }
        });

        btPular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartaoVisitaActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void addPontoIndicador(int position){

        nrPontos = new TextView[5];
        pontos_slide.removeAllViews();

        for(int i = 0; i < nrPontos.length; i++){
            nrPontos[i] = new TextView(this);
            nrPontos[i].setText(Html.fromHtml("&#8226;"));
            nrPontos[i].setTextSize(35);
            nrPontos[i].setTextColor(getResources().getColor(R.color.colorBitterSweet));

            pontos_slide.addView(nrPontos[i]);
        }

        if(nrPontos.length > 0){
            nrPontos[position].setTextColor(getResources().getColor(R.color.colorBitterSweetDark));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addPontoIndicador(position);

            paginaCorrente = position;

            if(position == 0){
                btVoltar.setEnabled(false);
                btProximo.setEnabled(true);
                btVoltar.setVisibility(View.INVISIBLE);
                btProximo.setVisibility(View.VISIBLE);
                btProximo.setText("Próximo");
                btVoltar.setText("");
                btPular.setText("Pular");
            } else if(position == nrPontos.length - 1){
                btVoltar.setEnabled(true);
                btProximo.setEnabled(true);
                btVoltar.setVisibility(View.VISIBLE);
                btProximo.setVisibility(View.INVISIBLE);
                btProximo.setText("");
                btVoltar.setText("Voltar");
                btPular.setText("Fechar");
            } else {
                btVoltar.setEnabled(true);
                btProximo.setEnabled(true);
                btVoltar.setVisibility(View.VISIBLE);
                btProximo.setVisibility(View.VISIBLE);
                btProximo.setText("Próximo");
                btVoltar.setText("Voltar");
                btPular.setText("Pular");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
