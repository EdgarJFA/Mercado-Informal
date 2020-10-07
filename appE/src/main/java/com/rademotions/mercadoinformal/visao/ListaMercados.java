package com.rademotions.mercadoinformal.visao;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.rademotions.mercadoinformal.Adaptador.AdaptadorListaMercado;
import com.rademotions.mercadoinformal.Controle.BancoDados;
import com.rademotions.mercadoinformal.Modelo.Cliente;
import com.rademotions.mercadoinformal.Modelo.Mercado;
import com.rademotions.mercadoinformal.R;

import java.util.ArrayList;

public class ListaMercados extends AppCompatActivity {

    GridView grade;
    ArrayList<Mercado> listaMercado;
    AdaptadorListaMercado adaptador = null;

    private BancoDados mDataBase;
    private Cliente cliente;
    private int admin;
    private Bundle bundle1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_mercados);

        grade = (GridView) findViewById(R.id.gridViewListaMercados);
        listaMercado = new ArrayList<>();
        adaptador = new AdaptadorListaMercado(this,R.layout.activity_lista_mercado_item,listaMercado);
        grade.setAdapter(adaptador);
        mDataBase = new BancoDados(getApplicationContext());

        Bundle bundle = getIntent().getExtras();
        admin = bundle.getInt("identificador");

        bundle1 = new Bundle();
        bundle1.putInt("id_cliente", admin);

        Cursor cursor = mDataBase.retornarListaDados("select * FROM mercado");
        listaMercado.clear();

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String nome = cursor.getString(1);
            String cidade = cursor.getString(2);
            String provincia = cursor.getString(3);
            String latitude = cursor.getString(4);
            String longitude = cursor.getString(5);

            listaMercado.add(new Mercado(id,nome,cidade, provincia,latitude,longitude));
        }

        grade.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long l) {

                Mercado mercadoSelecionado = (Mercado) adaptador.getItem(posicao);
//
//                Bundle bundle = new Bundle();
//                bundle.putInt("id_item",produtoSelecionado.getId());
//                bundle.putString("nome_item",produtoSelecionado.getNome());
//                bundle.putString("descricao_item",produtoSelecionado.getDescricao());
//                bundle.putDouble("preco_item",produtoSelecionado.getPreco());
//                bundle.putByteArray("imagem",produtoSelecionado.getImagem());
//                bundle.putString("disponibilidade",produtoSelecionado.getDiasponibilidade());
//                bundle.putInt("id_vendedor", produtoSelecionado.getId_vendedor());
//                bundle.putInt("id_cliente", cliente.getId());
//                bundle.putString("lista",lista);
//
//                Intent intent = new Intent(ListaEquipamento.this, TelaCompra.class);
//                intent.putExtras(bundle);
//                startActivity(intent);
                Toast.makeText(ListaMercados.this, "Editar mercado "+ mercadoSelecionado.getNome() +" com id= " + mercadoSelecionado.getId(),Toast.LENGTH_SHORT).show();

            }
        });

        adaptador.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ListaMercados.this, Painel_admin_mercado.class);
        intent.putExtras(bundle1);
        startActivity(intent);
    }
}
