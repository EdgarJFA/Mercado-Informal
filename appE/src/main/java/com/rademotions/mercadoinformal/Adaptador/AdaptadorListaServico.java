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

import com.rademotions.mercadoinformal.Modelo.Servico;
import com.rademotions.mercadoinformal.R;

import java.util.ArrayList;

/**
 * Created by Valet on 7/2/2019.
 */

public class AdaptadorListaServico extends BaseAdapter {

    private Context contexto;
    private int layout;
    private ArrayList<Servico> listaServico;

    public AdaptadorListaServico(Context contexto, int layout, ArrayList<Servico> listaServico){
        this.contexto = contexto;
        this.layout = layout;
        this.listaServico = listaServico;
    }

    @Override
    public int getCount() {
        return listaServico.size();
    }

    @Override
    public Object getItem(int posicao) {
        return listaServico.get(posicao);
    }

    @Override
    public long getItemId(int posicao) {
        return posicao;
    }

    private class viewHolder{
        TextView txtNome;
        TextView txtPreco;
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

            holder.txtNome = (TextView) row.findViewById(R.id.TextView_nome_servico_adaptador);
            holder.txtPreco = (TextView) row.findViewById(R.id.TextView_valor_servico);
            holder.txtDisponibilidade = (TextView) row.findViewById(R.id.TextView_disponibilidade_servico);
            holder.imgItem = (ImageView) row.findViewById(R.id.ImageView_icone_servico);

            row.setTag(holder);
        } else {
            holder = (viewHolder) row.getTag();
        }

        Servico servico = listaServico.get(posicao);

        holder.txtNome.setText(servico.getNome());
        holder.txtPreco.setText(String.valueOf(servico.getPreco())+ " mt");
        holder.txtDisponibilidade.setText(servico.getDisponibilidade());

        byte[] imagem = servico.getImagem();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imagem, 0, imagem.length);
        holder.imgItem.setImageBitmap(bitmap);

        return row;
    }
}
