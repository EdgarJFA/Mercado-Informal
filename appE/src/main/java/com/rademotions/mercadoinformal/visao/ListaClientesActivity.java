package com.rademotions.mercadoinformal.visao;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.rademotions.mercadoinformal.Adaptador.AdaptadorListaCliente;
import com.rademotions.mercadoinformal.Controle.BancoDados;
import com.rademotions.mercadoinformal.Modelo.Cliente;
import com.rademotions.mercadoinformal.R;

import java.util.ArrayList;

public class ListaClientesActivity extends AppCompatActivity {

    private GridView grade;
    private ArrayList<Cliente> listaCliente;
    private AdaptadorListaCliente adaptador = null;

    private BancoDados mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_clientes);

        listaCliente = new ArrayList<>();
        grade = (GridView) findViewById(R.id.gridView);
        adaptador = new AdaptadorListaCliente(this,R.layout.activity_item_cliente,listaCliente);
        grade.setAdapter(adaptador);
        mDataBase = new BancoDados(getApplicationContext());

        Cursor cursor = mDataBase.retornarListaDados("select * FROM cliente ORDER BY nome ASC");
        listaCliente.clear();

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String nome = cursor.getString(1) + " " + cursor.getString(2);
            int telefone = cursor.getInt(4);
            String email = cursor.getString(3);
            String usuario = cursor.getString(7);

            listaCliente.add(new Cliente(id,nome,email,telefone, usuario));
        }

        grade.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) {

                Cliente usuarioSelecionado = (Cliente) adaptador.getItem(posicao);

                final CharSequence [] itens = {"Editar","Excluir"};

                AlertDialog.Builder selecionarFoto = new AlertDialog.Builder(ListaClientesActivity.this);
                selecionarFoto.setTitle("Gerir Utilizador");

                selecionarFoto.setItems(itens, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(itens[i].equals("Editar")){

                        }else if (itens[i].equals("Excluir")){

                        }
                    }
                });

                selecionarFoto.show();

                //Toast.makeText(ListaClientesActivity.this, "usuario: " + usuarioSelecionado.getNome() + " id = " + usuarioSelecionado.getId(), Toast.LENGTH_SHORT).show();
            }
        });
        adaptador.notifyDataSetChanged();
    }
}
