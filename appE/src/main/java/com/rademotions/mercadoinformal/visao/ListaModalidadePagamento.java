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
import com.rademotions.mercadoinformal.Adaptador.AdaptadorListaPagamentos;
import com.rademotions.mercadoinformal.Controle.BancoDados;
import com.rademotions.mercadoinformal.Modelo.Cliente;
import com.rademotions.mercadoinformal.Modelo.ModalidadePagamento;
import com.rademotions.mercadoinformal.Modelo.Vendedor;
import com.rademotions.mercadoinformal.R;

import java.util.ArrayList;

public class ListaModalidadePagamento extends AppCompatActivity {

    GridView grade;
    ArrayList<ModalidadePagamento> listaModalidade;
    AdaptadorListaPagamentos adaptador = null;

    private BancoDados mDataBase;
    private Cliente cliente;
    private Vendedor vendedor;
    private Bundle bundle1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_modalidade_pagamento);

        grade = (GridView) findViewById(R.id.gridViewListaModalidade);
        listaModalidade = new ArrayList<>();
        adaptador = new AdaptadorListaPagamentos(this,R.layout.activity_lista_modalidade_item, listaModalidade);
        grade.setAdapter(adaptador);
        mDataBase = new BancoDados(getApplicationContext());

        cliente = new Cliente();
        vendedor = new Vendedor();
        Bundle bundle = getIntent().getExtras();

        cliente.setId(bundle.getInt("identificador"));
        bundle1 = new Bundle();
        bundle1.putInt("id_cliente",cliente.getId());
        //Integer b = mDataBase.retornarIdVendedor(cliente.getId());
        vendedor.setId(mDataBase.retornarIdVendedor(cliente.getId()));

        Cursor cursor = mDataBase.retornarListaDados("select * FROM modalidade_pagamento where id_vendedor = "+ vendedor.getId() +"");
        listaModalidade.clear();

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String data = cursor.getString(1);
            String nome = cursor.getString(2);
            int telefone = cursor.getInt(3);
            int idVendedor = cursor.getInt(4);

            listaModalidade.add(new ModalidadePagamento(id,nome,data, telefone,idVendedor));
        }

        grade.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long l) {

                ModalidadePagamento modalidadeSelecionada = (ModalidadePagamento) adaptador.getItem(posicao);
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
                Toast.makeText(ListaModalidadePagamento.this, "Remover modalidade "+ modalidadeSelecionada.getNome() +" com id= " + modalidadeSelecionada.getId(),Toast.LENGTH_SHORT).show();

            }
        });

        adaptador.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ListaModalidadePagamento.this, PainelVendedorPagamentos.class);
        intent.putExtras(bundle1);
        startActivity(intent);
    }
}
