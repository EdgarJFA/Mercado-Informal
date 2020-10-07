package com.rademotions.mercadoinformal.visao;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.rademotions.mercadoinformal.Adaptador.AdaptadorListaProdutoAlimentacao;
import com.rademotions.mercadoinformal.Controle.BancoDados;
import com.rademotions.mercadoinformal.MercadoActivity;
import com.rademotions.mercadoinformal.Modelo.Cliente;
import com.rademotions.mercadoinformal.Modelo.Produto;
import com.rademotions.mercadoinformal.R;

import java.util.ArrayList;

/**
 * Created by Valet on 7/20/2019.
 */

public class ListaEquipamento extends AppCompatActivity {

    GridView grade;
    ArrayList<Produto> listaProduto;
    AdaptadorListaProdutoAlimentacao adaptador = null;

    private BancoDados mDataBase;
    private Cliente cliente;
    private String lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produto);

        grade = (GridView) findViewById(R.id.gridViewProduto);
        listaProduto = new ArrayList<>();
        adaptador = new AdaptadorListaProdutoAlimentacao(this,R.layout.activity_lista_produto_item,listaProduto);
        grade.setAdapter(adaptador);
        mDataBase = new BancoDados(getApplicationContext());

        cliente = new Cliente();
        Bundle bundle = getIntent().getExtras();
        cliente.setId(bundle.getInt("identificador"));
        this.lista = "Equipamento";

        Cursor cursor = mDataBase.retornarListaDados("select * FROM produto where categoria = 'Equipamentos'");
        listaProduto.clear();

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String nome = cursor.getString(1);
            double preco = cursor.getDouble(2);
            String descricao = cursor.getString(3);
            byte[] imagem = cursor.getBlob(4);
            String disponibilidade = cursor.getString(6);
            int id_vendedor = cursor.getInt(9);

            listaProduto.add(new Produto(id,nome,preco,descricao,imagem, disponibilidade,id_vendedor));
        }

        grade.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long l) {

                Produto produtoSelecionado = (Produto) adaptador.getItem(posicao);

                Bundle bundle = new Bundle();
                bundle.putInt("id_item",produtoSelecionado.getId());
                bundle.putString("nome_item",produtoSelecionado.getNome());
                bundle.putString("descricao_item",produtoSelecionado.getDescricao());
                bundle.putDouble("preco_item",produtoSelecionado.getPreco());
                bundle.putByteArray("imagem",produtoSelecionado.getImagem());
                bundle.putString("disponibilidade",produtoSelecionado.getDiasponibilidade());
                bundle.putInt("id_vendedor", produtoSelecionado.getId_vendedor());
                bundle.putInt("id_cliente", cliente.getId());
                bundle.putString("lista",lista);

                Intent intent = new Intent(ListaEquipamento.this, TelaCompra.class);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        adaptador.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        Bundle bundle = new Bundle();
        bundle.putInt("id_cliente",cliente.getId());
        Intent intent = new Intent(ListaEquipamento.this, MercadoActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
