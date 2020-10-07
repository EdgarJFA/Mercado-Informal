package com.rademotions.mercadoinformal.visao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rademotions.mercadoinformal.Controle.BancoDados;
import com.rademotions.mercadoinformal.Modelo.Cliente;
import com.rademotions.mercadoinformal.R;

public class PainelVendedorDivulgar extends AppCompatActivity {

    Button btProduto, btServico, btListaDivulgacao, btVoltar;
    private Cliente cliente;
    private Bundle bundle1;
    private BancoDados mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painel_vendedor_divulgar);

        btServico = (Button) findViewById(R.id.bt_registrar_servico);
        btProduto = (Button) findViewById(R.id.bt_registrar_Produto);
        btListaDivulgacao = (Button) findViewById(R.id.bt_ver_divulgacao);
        btVoltar = (Button) findViewById(R.id.bt_vendedor_divulgar_voltar);

        mDataBase = new BancoDados(getApplicationContext());
        cliente = new Cliente();
        final Bundle bundle = getIntent().getExtras();

        cliente.setId(bundle.getInt("id_cliente"));
        bundle1 = new Bundle();
        bundle1.putInt("identificador",cliente.getId());

        btServico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PainelVendedorDivulgar.this, PainelVendedorDivulgarServico.class);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        btProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PainelVendedorDivulgar.this, PainelVendedorDivulgarProduto.class);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        btListaDivulgacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PainelVendedorDivulgar.this, ListaDivulgacao.class);
                intent.putExtras(bundle1);
                startActivity(intent);
                //ListaServico.class
            }
        });

        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PainelVendedorDivulgar.this, PainelVendedorActivity.class);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PainelVendedorDivulgar.this, PainelVendedorActivity.class);
        intent.putExtras(bundle1);
        startActivity(intent);
    }
}
