package com.rademotions.mercadoinformal.visao;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.rademotions.mercadoinformal.Controle.BancoDados;
import com.rademotions.mercadoinformal.Controle.SlideAdapter;
import com.rademotions.mercadoinformal.Modelo.Cliente;
import com.rademotions.mercadoinformal.Modelo.Vendedor;
import com.rademotions.mercadoinformal.R;

public class PainelVendedorActivity extends AppCompatActivity {

    private LinearLayout btDivulgar, btPerfil, btVendas, btPagamentos, btRelatorios, btPedidos;

    private ViewPager viewPagerPainelVendedor;

    private Vendedor vendedor;
    private Cliente cliente;
    private BancoDados mDataBase;
    private Bundle bundle1;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painel_vendedor);

        btDivulgar = (LinearLayout) findViewById(R.id.btVendedor_divulgar);
        btPerfil = (LinearLayout) findViewById(R.id.button_Vendedor_Perfil);
        btPagamentos = (LinearLayout) findViewById(R.id.btVendedor_pagamentos);
        btPedidos = (LinearLayout) findViewById(R.id.btVendedor_pedidos);
        btVendas = (LinearLayout) findViewById(R.id.btVendedor_vendas);
        btRelatorios = (LinearLayout) findViewById(R.id.btVendedor_relatorios);

        mDataBase = new BancoDados(getApplicationContext());

        cliente = new Cliente();
        Bundle bundle = getIntent().getExtras();

        cliente.setId(bundle.getInt("identificador"));

        bundle1 = new Bundle();
        cliente = mDataBase.retornarDadosCliente(cliente.getId());
        Integer b = mDataBase.validaLogin(String.valueOf(cliente.getTelefone()),cliente.getSenha());
        bundle1.putInt("id_cliente",b);

        btDivulgar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PainelVendedorActivity.this, PainelVendedorDivulgar.class);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        btPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PainelVendedorActivity.this, PainelPerfilActivity.class);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        btPagamentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PainelVendedorActivity.this, PainelVendedorPagamentos.class);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        btVendas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PainelVendedorActivity.this, PainelVendedorVendas.class);
                //intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        btPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PainelVendedorActivity.this, PainelVendedorPedidos.class);
                //intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        btRelatorios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PainelVendedorActivity.this, PainelVendedorRelatorios.class);
                //intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder confirmarSair = new AlertDialog.Builder(PainelVendedorActivity.this);
        confirmarSair.setMessage("Deseja sair da conta?");

        confirmarSair.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        confirmarSair.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(PainelVendedorActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        confirmarSair.create().show();
    }
}
