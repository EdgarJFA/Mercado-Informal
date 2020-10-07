package com.rademotions.mercadoinformal.visao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rademotions.mercadoinformal.Controle.BancoDados;
import com.rademotions.mercadoinformal.Modelo.Cliente;
import com.rademotions.mercadoinformal.R;

public class Painel_admin_mercado extends AppCompatActivity {

    private Button btRegistrarNovoMerc;
    private Button btListaMerc;
    private Button btVoltar;

    private Cliente cliente;
    private Bundle bundle1;
    private BancoDados mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painel_admin_mercado);

        btRegistrarNovoMerc = (Button) findViewById(R.id.bt_registrar_Mercado);
        btListaMerc = (Button) findViewById(R.id.bt_Lista_Mercado);
        btVoltar = (Button) findViewById(R.id.bt_admMerc_voltar);

        mDataBase = new BancoDados(getApplicationContext());
        cliente = new Cliente();
        Bundle bundle = getIntent().getExtras();

        cliente.setId(bundle.getInt("id_cliente"));
        bundle1 = new Bundle();
        cliente = mDataBase.retornarDadosCliente(cliente.getId());

        String senha = cliente.getSenha();
        Integer tel = cliente.getTelefone();
        String telefone =  tel.toString();

        Integer b = mDataBase.validaLogin(telefone,senha);
        bundle1.putInt("identificador",b);

        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Painel_admin_mercado.this, PainelAdminActivity.class);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        btRegistrarNovoMerc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Painel_admin_mercado.this, PainelAdminRegistrarMercado.class);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        btListaMerc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Painel_admin_mercado.this, ListaMercados.class);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Painel_admin_mercado.this, PainelAdminActivity.class);
        intent.putExtras(bundle1);
        startActivity(intent);
    }
}
