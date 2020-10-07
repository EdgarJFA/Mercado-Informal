package com.rademotions.mercadoinformal.Adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rademotions.mercadoinformal.Modelo.ModalidadePagamento;
import com.rademotions.mercadoinformal.R;

import java.util.ArrayList;

/**
 * Created by Valet on 7/29/2019.
 */

public class AdaptadorListaPagamentos extends BaseAdapter {
    private Context contexto;
    private int layout;
    private ArrayList<ModalidadePagamento> listaModalidadePagamento;

    public AdaptadorListaPagamentos(Context contexto, int layout, ArrayList<ModalidadePagamento> listaModalidadePagamento){
        this.contexto = contexto;
        this.layout = layout;
        this.listaModalidadePagamento = listaModalidadePagamento;
    }

    @Override
    public int getCount() {
        return listaModalidadePagamento.size();
    }

    @Override
    public Object getItem(int posicao) {
        return listaModalidadePagamento.get(posicao);
    }

    @Override
    public long getItemId(int posicao) {
        return posicao;
    }

    private class viewHolder{
        TextView txtNomeModalidade;
        TextView txtNumeroModalidade;
        TextView txtDataRegistro;
        //ImageView imgItem;
    }

    @Override
    public View getView(int posicao, View view, ViewGroup viewGroup) {

        View row = view;
        viewHolder holder = new viewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout,null);

            holder.txtNomeModalidade = (TextView) row.findViewById(R.id.TextView_nome_Modalidade_adaptador);
            holder.txtNumeroModalidade = (TextView) row.findViewById(R.id.TextView_Numero_Modalidade_adaptador);
            holder.txtDataRegistro = (TextView) row.findViewById(R.id.TextView_data_Modalidade_adaptador);
            //holder.imgItem = (ImageView) row.findViewById(R.id.ImageView_icone_produto);

            row.setTag(holder);
        } else {
            holder = (viewHolder) row.getTag();
        }

        ModalidadePagamento modalidadePagamento = listaModalidadePagamento.get(posicao);

        holder.txtNomeModalidade.setText(modalidadePagamento.getNome());
        holder.txtNumeroModalidade.setText("Número Registrado: " + modalidadePagamento.getTelefone());
        holder.txtDataRegistro.setText("Data de Registro: " + modalidadePagamento.getData());

        return row;
    }
}
