package com.rademotions.mercadoinformal.visao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rademotions.mercadoinformal.Controle.BancoDados;
import com.rademotions.mercadoinformal.Modelo.Cliente;
import com.rademotions.mercadoinformal.R;

public class Painel_admin_categoria extends AppCompatActivity {

    private Button btRegistrar, btVerCategorias, btVoltar;
    private Cliente cliente;
    private Bundle bundle1;
    private BancoDados mDataBase;
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painel_admin_categoria);

        btRegistrar = (Button) findViewById(R.id.bt_registrar_Categoria);
        btVerCategorias = (Button) findViewById(R.id.bt_Lista_Categoria);
        btVoltar = (Button) findViewById(R.id.bt_admCat_voltar);

        mDataBase = new BancoDados(getApplicationContext());
        cliente = new Cliente();
        Bundle bundle = getIntent().getExtras();

        cliente.setId(bundle.getInt("id_cliente"));
        bundle1 = new Bundle();

        bundle1.putInt("identificador",cliente.getId());

        btRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Painel_admin_categoria.this, PainelAdminRegistrarCategoria.class);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        btVerCategorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Painel_admin_categoria.this, PainelAdminActivity.class);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Painel_admin_categoria.this, PainelAdminActivity.class);
        intent.putExtras(bundle1);
        startActivity(intent);
    }*/
}
