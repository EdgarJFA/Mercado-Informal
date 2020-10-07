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

import com.rademotions.mercadoinformal.Modelo.Desejo;
import com.rademotions.mercadoinformal.R;

import java.util.ArrayList;

/**
 * Created by Valet on 9/21/2019.
 */

public class AdaptadorListaFavoritos extends BaseAdapter {

    private Context contexto;
    private int layout;
    private ArrayList<Desejo> listaProduto;

    /*public AdaptadorListaProdutoAlimentacao(Context contexto, int layout, ArrayList<Produto> listaProduto){
        this.contexto = contexto;
        this.layout = layout;
        this.listaProduto = listaProduto;
    }*/

    @Override
    public int getCount() {
        return listaProduto.size();
    }

    @Override
    public Object getItem(int posicao) {
        return listaProduto.get(posicao);
    }

    @Override
    public long getItemId(int posicao) {
        return posicao;
    }

    private class viewHolder{
        TextView txtNomeProduto;
        TextView txtPrecoProduto;
        TextView txtDisponibilidade;
        ImageView imgItem;
    }

    @Override
    public View getView(int posicao, View view, ViewGroup viewGroup) {

        View row = view;
        viewHolder holder = new viewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout,null);

            holder.txtNomeProduto = (TextView) row.findViewById(R.id.TextView_nome_Produto_adaptador);
            holder.txtPrecoProduto = (TextView) row.findViewById(R.id.TextView_Preco_Produto_adaptador);
            holder.txtDisponibilidade = (TextView) row.findViewById(R.id.TextView_disponibilidade_produto);
            holder.imgItem = (ImageView) row.findViewById(R.id.ImageView_icone_produto);

            row.setTag(holder);
        } else {
            holder = (viewHolder) row.getTag();
        }

        /*Desejo desejo = listaDesejo.get(posicao);

        holder.txtNomeProduto.setText(produto.getNome());
        holder.txtPrecoProduto.setText(String.valueOf(produto.getPreco()) + " mt");
        holder.txtDisponibilidade.setText(produto.getDiasponibilidade());

        byte[] imagemItem = produto.getImagem();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imagemItem, 0, imagemItem.length);
        holder.imgItem.setImageBitmap(bitmap);*/

        return row;
    }

}
