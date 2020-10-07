package com.rademotions.mercadoinformal.Adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rademotions.mercadoinformal.Modelo.Cliente;
import com.rademotions.mercadoinformal.R;

import java.util.ArrayList;

/**
 * Created by Valet on 11/25/2018.
 */

public class AdaptadorListaCliente extends BaseAdapter{

    private Context contexto;
    private int layout;
    private ArrayList<Cliente> listaCliente;
    private String nivel;

    public AdaptadorListaCliente(Context contexto, int layout, ArrayList<Cliente> listaCliente){
        this.contexto = contexto;
        this.layout = layout;
        this.listaCliente = listaCliente;
    }

    @Override
    public int getCount() {
        return this.listaCliente.size();
    }

    @Override
    public Object getItem(int posicao) {
        return this.listaCliente.get(posicao);
    }

    @Override
    public long getItemId(int posicao) {
        return posicao;
    }

    public void removerUsuario(int posicao){
        this.listaCliente.remove(posicao);
    }

    private class viewHolder{
        TextView txtNome;
        TextView txtContacto;
        TextView txtEmail;
        TextView txtNivelUsuario;
    }


    @Override
    public View getView(int posicao, View view, ViewGroup viewGroup) {

        View row = view;
        viewHolder holder = new viewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout,null);

            holder.txtNome = (TextView) row.findViewById(R.id.TextView_nome_usuario_adaptador);
            holder.txtContacto = (TextView) row.findViewById(R.id.TextView_numero_usuario);
            holder.txtEmail = (TextView) row.findViewById(R.id.TextView_email_usuario);
            holder.txtNivelUsuario = (TextView) row.findViewById(R.id.TextView_nivel_usuario);
            row.setTag(holder);
        } else {
            holder = (viewHolder) row.getTag();
        }

        Cliente cliente = listaCliente.get(posicao);

        holder.txtNome.setText(cliente.getNome());
        holder.txtContacto.setText(String.valueOf(cliente.getTelefone()));
        holder.txtEmail.setText(cliente.getEmail());

        if(cliente.getTipoUsuario().equals("A1")){
            nivel = "Cliente";
        } else if (cliente.getTipoUsuario().equals("A2")){
            nivel = "Vendedor";
        } else if (cliente.getTipoUsuario().equals("A3")){
            nivel = "Administrador";
        } else {
            nivel = "Nivel Desconhecido";
        }

        holder.txtNivelUsuario.setText(nivel);

        return row;
    }
}
