package com.rademotions.mercadoinformal.visao;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.rademotions.mercadoinformal.Adaptador.AdaptadorListaProdutoAlimentacao;
import com.rademotions.mercadoinformal.Adaptador.AdaptadorListaServico;
import com.rademotions.mercadoinformal.Controle.BancoDados;
import com.rademotions.mercadoinformal.MercadoActivity;
import com.rademotions.mercadoinformal.Modelo.Cliente;
import com.rademotions.mercadoinformal.Modelo.Produto;
import com.rademotions.mercadoinformal.Modelo.Servico;
import com.rademotions.mercadoinformal.R;

import java.util.ArrayList;

public class ListaDivulgacao extends AppCompatActivity {

    GridView gradeProduto;
    ArrayList<Produto> listaProduto;
    AdaptadorListaProdutoAlimentacao adaptadorProduto = null;
    GridView gradeServico;
    ArrayList<Servico> listaServico;
    AdaptadorListaServico adaptadorServico = null;

    private BancoDados mDataBase;
    private Cliente cliente;
    private String lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_divulgacoes);

        //Adaptador para Produtos;

        gradeProduto = (GridView) findViewById(R.id.gridViewListaDivulgacaoProduto);
        listaProduto = new ArrayList<>();
        adaptadorProduto = new AdaptadorListaProdutoAlimentacao(this,R.layout.activity_lista_produto_item,listaProduto);
        gradeProduto.setAdapter(adaptadorProduto);
        mDataBase = new BancoDados(getApplicationContext());

        cliente = new Cliente();
        Bundle bundle = getIntent().getExtras();
        cliente.setId(bundle.getInt("identificador"));

        Integer idVendedor = mDataBase.retornarIdVendedor(cliente.getId());

        Cursor cursor = mDataBase.retornarListaDados("select * FROM produto where id_vendedor = "+  idVendedor +"");
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
        adaptadorProduto.notifyDataSetChanged();

        //Adaptador para Serviços;

        gradeServico = (GridView) findViewById(R.id.gridViewListaDivulgacaoServico);
        listaServico = new ArrayList<>();
        adaptadorServico = new AdaptadorListaServico(this,R.layout.activity_item_servico,listaServico);
        gradeServico.setAdapter(adaptadorServico);

        cursor = mDataBase.retornarListaDados("select * FROM servico where id_vendedor = "+  idVendedor +"");
        listaServico.clear();

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String nome = cursor.getString(1);
            Double preco = cursor.getDouble(2);
            String descricao = cursor.getString(3);
            byte[] imagem = cursor.getBlob(4);
            String disponibilidade = cursor.getString(6);
            int id_vendedor = cursor.getInt(8);

            listaServico.add(new Servico(id,nome,preco,descricao,imagem,disponibilidade,id_vendedor));
        }
        adaptadorServico.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        Bundle bundle = new Bundle();
        bundle.putInt("id_cliente",cliente.getId());
        Intent intent = new Intent(ListaDivulgacao.this, PainelVendedorDivulgar.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
