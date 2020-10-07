package com.rademotions.mercadoinformal.Controle;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rademotions.mercadoinformal.R;

/**
 * Created by Valet on 10/28/2018.
 */

public class SlideAdapter extends PagerAdapter {

    Context contexto;
    LayoutInflater inflarPlano;

    public SlideAdapter(Context contexto){

        this.contexto = contexto;

    }

    //Arranjo ou Array

    public int[] imagem_Slide = {
            R.drawable.group_1,
            R.drawable.group_3,
            R.drawable.group_2,
            R.drawable.group_5,
            R.drawable.group_12
    };

    public String[] titulo_Slide = {
            "PESSOAS",
            "PRODUTOS",
            "PREÇOS",
            "DESIGN",
            "SERVIÇOS"
    };

    public String[] descricao_Slide = {
            "Criamos experiências e estabelecemos ações estratégicas que conectam vendedores e consumidores. ",
            "Na hora de fazer as compras da semana ou do mês prefira sempre o mercado informal. Tudo em gêneros alimentícios, materiais de higiene e limpeza e utilidades domésticas. ",
            "Vai custar mais barato! Ao fazer suas escolhas no mercado informal você vai encontrar tudo o que procura em um só lugar.",
            "Colocamos ao seu dispor um aplicativo com design moderno e atrativo, onde é possivel viajar por varios mercados em um piscar de olhos.",
            "Disponibilizamos uma aplicação simples e fácil de ser usada, onde vendedores de produtos e prestadores de serviços dos mercados informais tem a possibilidade de publicar o seu negócio de forma segura e confiavél. "
    };

    @Override
    public int getCount() {
        return titulo_Slide.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (RelativeLayout) object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        inflarPlano = (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
        View view = inflarPlano.inflate(R.layout.slide_layout, container, false);

        ImageView slideImageView = (ImageView) view.findViewById(R.id.imageView_cartaoVisita);
        TextView slideTitulo = (TextView) view.findViewById(R.id.textView_cartaoVisita_titulo);
        TextView slideDescricao = (TextView) view.findViewById(R.id.textView_cartaoVisita_descricao);

        slideImageView.setImageResource(imagem_Slide[position]);
        slideTitulo.setText(titulo_Slide[position]);
        slideDescricao.setText(descricao_Slide[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
