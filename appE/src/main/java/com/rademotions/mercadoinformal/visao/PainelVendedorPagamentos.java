package com.rademotions.mercadoinformal.visao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rademotions.mercadoinformal.Controle.BancoDados;
import com.rademotions.mercadoinformal.Modelo.Cliente;
import com.rademotions.mercadoinformal.R;

public class PainelVendedorPagamentos extends AppCompatActivity {

    private Button btRegistrar, btVerModalidades, btVoltar;

    private Cliente cliente;
    private Bundle bundle1;
    private BancoDados mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painel_vendedor_pagamentos);

        btRegistrar = (Button) findViewById(R.id.bt_registrar_Modalidade_Pagamento);
        btVerModalidades = (Button) findViewById(R.id.bt_Visualizar_Modalidades);
        btVoltar = (Button) findViewById(R.id.bt_admPag_voltar);

        mDataBase = new BancoDados(getApplicationContext());
        cliente = new Cliente();
        Bundle bundle = getIntent().getExtras();

        cliente.setId(bundle.getInt("id_cliente"));
        bundle1 = new Bundle();
        bundle1.putInt("identificador",cliente.getId());

        btRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PainelVendedorPagamentos.this, DefinirPagamento.class);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        btVerModalidades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PainelVendedorPagamentos.this, ListaModalidadePagamento.class);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PainelVendedorPagamentos.this, PainelVendedorActivity.class);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PainelVendedorPagamentos.this, PainelVendedorActivity.class);
        intent.putExtras(bundle1);
        startActivity(intent);
    }
}
