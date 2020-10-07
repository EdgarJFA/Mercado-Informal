package com.rademotions.mercadoinformal.visao;

import android.content.Intent;
import android.database.Cursor;
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

public class ListaVendedores extends AppCompatActivity {

    private GridView grade;
    private ArrayList<Cliente> listaCliente;
    private AdaptadorListaCliente adaptador = null;

    private BancoDados mDataBase;
    private Cliente cliente;
    private int admin;
    private Bundle bundle1;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_lista_vendedores);
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_clientes);

        listaCliente = new ArrayList<>();
        grade = (GridView) findViewById(R.id.gridView);
        adaptador = new AdaptadorListaCliente(this,R.layout.activity_item_cliente,listaCliente);
        grade.setAdapter(adaptador);
        mDataBase = new BancoDados(getApplicationContext());

        Bundle bundle = getIntent().getExtras();
        admin = bundle.getInt("identificador");

        bundle1 = new Bundle();
        bundle1.putInt("id_cliente", admin);

        Cursor cursor = mDataBase.retornarListaDados("select * FROM cliente where tipo_usuario = 'A2' ORDER BY nome ASC");
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

                Toast.makeText(ListaVendedores.this, "Vendedor: " + usuarioSelecionado.getNome() + " id = " + usuarioSelecionado.getId(), Toast.LENGTH_SHORT).show();
            }
        });
        adaptador.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ListaVendedores.this, Painel_admin_vendedor.class);
        intent.putExtras(bundle1);
        startActivity(intent);
    }
}
