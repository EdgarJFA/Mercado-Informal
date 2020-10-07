package com.rademotions.mercadoinformal.visao;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.rademotions.mercadoinformal.Adaptador.AdaptadorListaServico;
import com.rademotions.mercadoinformal.Controle.BancoDados;
import com.rademotions.mercadoinformal.MercadoActivity;
import com.rademotions.mercadoinformal.Modelo.Cliente;
import com.rademotions.mercadoinformal.Modelo.Servico;
import com.rademotions.mercadoinformal.R;

import java.util.ArrayList;

public class ListaServico extends AppCompatActivity {

    GridView grade;
    ArrayList<Servico> listaServico;
    AdaptadorListaServico adaptador = null;

    private BancoDados mDataBase;
    private Cliente cliente;
    private String lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produto);

        grade = (GridView) findViewById(R.id.gridViewProduto);
        listaServico = new ArrayList<>();
        adaptador = new AdaptadorListaServico(this,R.layout.activity_item_servico,listaServico);
        grade.setAdapter(adaptador);
        mDataBase = new BancoDados(getApplicationContext());

        cliente = new Cliente();
        Bundle bundle = getIntent().getExtras();
        cliente.setId(bundle.getInt("identificador"));
        this.lista = "Serviço";

        //retificar o select por causa da condição do retorno: deve retornar somente os serviços publicados por um determinado vendedor
        Cursor cursor = mDataBase.retornarListaDados("select * FROM servico");
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
        grade.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) {

                Servico servicoSelecionado = (Servico) adaptador.getItem(posicao);

                Bundle bundle = new Bundle();
                bundle.putInt("id_item",servicoSelecionado.getId());
                bundle.putString("nome_item",servicoSelecionado.getNome());
                bundle.putString("descricao_item",servicoSelecionado.getDescricao());
                bundle.putDouble("preco_item",servicoSelecionado.getPreco());
                bundle.putByteArray("imagem",servicoSelecionado.getImagem());
                bundle.putString("disponibilidade",servicoSelecionado.getDisponibilidade());
                bundle.putInt("id_vendedor", servicoSelecionado.getIdVendedor());
                bundle.putInt("id_cliente", cliente.getId());
                bundle.putString("lista",lista);

                Intent intent = new Intent(ListaServico.this, TelaCompra.class);
                intent.putExtras(bundle);
                startActivity(intent);

                //Toast.makeText(ListaServico.this, "servico: " + servicoSelecionado.getNome() + " id = " + servicoSelecionado.getId(), Toast.LENGTH_SHORT).show();
            }
        });
        adaptador.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        Bundle bundle = new Bundle();
        bundle.putInt("id_cliente",cliente.getId());
        Intent intent = new Intent(ListaServico.this, MercadoActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
