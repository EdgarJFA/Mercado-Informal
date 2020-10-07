package com.rademotions.mercadoinformal.visao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rademotions.mercadoinformal.Controle.BancoDados;
import com.rademotions.mercadoinformal.Modelo.Cliente;
import com.rademotions.mercadoinformal.R;

public class Painel_admin_vendedor extends AppCompatActivity {

    private Button btRegistrarNovoVen;
    private Button btListaVen;
    private Button btVoltar;

    private Cliente cliente;
    private Bundle bundle1;
    private BancoDados mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painel_admin_vendedor);

        btRegistrarNovoVen = (Button) findViewById(R.id.bt_registrar_vendedor);
        btListaVen = (Button) findViewById(R.id.bt_Lista_vendedor);
        btVoltar = (Button) findViewById(R.id.bt_admVend_voltar);

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
                Intent intent = new Intent(Painel_admin_vendedor.this, PainelAdminActivity.class);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        btRegistrarNovoVen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Painel_admin_vendedor.this, PainelAdminRegistrarVendedorActivity.class);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        btListaVen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Painel_admin_vendedor.this, ListaVendedores.class);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Painel_admin_vendedor.this, PainelAdminActivity.class);
        intent.putExtras(bundle1);
        startActivity(intent);
    }
}
