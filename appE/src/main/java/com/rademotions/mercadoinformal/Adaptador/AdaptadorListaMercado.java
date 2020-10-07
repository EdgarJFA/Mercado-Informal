package com.rademotions.mercadoinformal.Adaptador;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rademotions.mercadoinformal.Modelo.Mercado;
import com.rademotions.mercadoinformal.R;

import java.util.ArrayList;

/**
 * Created by Valet on 7/29/2019.
 */

public class AdaptadorListaMercado extends BaseAdapter {
    private Context contexto;
    private int layout;
    private ArrayList<Mercado> listaMercado;

    public AdaptadorListaMercado(Context contexto, int layout, ArrayList<Mercado> listaProduto){
        this.contexto = contexto;
        this.layout = layout;
        this.listaMercado = listaProduto;
    }

    @Override
    public int getCount() {
        return listaMercado.size();
    }

    @Override
    public Object getItem(int posicao) {
        return listaMercado.get(posicao);
    }

    @Override
    public long getItemId(int posicao) {
        return posicao;
    }

    private class viewHolder{
        TextView txtNomeMercado;
        TextView txtProvinciaMercado;
        TextView txtCidadeMercado;
        //ImageView imgItem;
    }

    @Override
    public View getView(int posicao, View view, ViewGroup viewGroup) {

        View row = view;
        viewHolder holder = new viewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout,null);

            holder.txtNomeMercado = (TextView) row.findViewById(R.id.TextView_nome_Mercado_adaptador);
            holder.txtProvinciaMercado = (TextView) row.findViewById(R.id.TextView_Provincia_Mercado_adaptador);
            holder.txtCidadeMercado = (TextView) row.findViewById(R.id.TextView_cidade_mercado_adaptador);
            //holder.imgItem = (ImageView) row.findViewById(R.id.ImageView_icone_produto);

            row.setTag(holder);
        } else {
            holder = (viewHolder) row.getTag();
        }

        Mercado mercado = listaMercado.get(posicao);

        holder.txtNomeMercado.setText("Mercado " + mercado.getNome());
        holder.txtProvinciaMercado.setText(mercado.getProvincia());
        holder.txtCidadeMercado.setText(mercado.getCidade());

        return row;
    }
}
