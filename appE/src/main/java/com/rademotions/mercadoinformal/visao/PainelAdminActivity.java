package com.rademotions.mercadoinformal.visao;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.rademotions.mercadoinformal.Controle.BancoDados;
import com.rademotions.mercadoinformal.MercadoActivity;
import com.rademotions.mercadoinformal.Modelo.Cliente;
import com.rademotions.mercadoinformal.R;

public class PainelAdminActivity extends AppCompatActivity {

    private LinearLayout btVendedor, btMercado, btRelatorio, btEstatistica, btHome, btPerfil, btLista, btMapa;

    private Cliente cliente;
    private BancoDados mDataBase;
    private Bundle bundle1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painel_admin);

        btVendedor = (LinearLayout) findViewById(R.id.btAdmin_reg_Vendedor);
        btPerfil = (LinearLayout) findViewById(R.id.button_Administrador_Perfil);
        btLista = (LinearLayout) findViewById(R.id.btAdmin_lista);
        btMercado = (LinearLayout) findViewById(R.id.btAdmin_reg_Mercado);
        btRelatorio = (LinearLayout) findViewById(R.id.btAdmin_reg_Relatorio);
        btEstatistica = (LinearLayout) findViewById(R.id.btAdmin_Estatistica);
        btHome = (LinearLayout) findViewById(R.id.button_Administrador_Home);
        btMapa = (LinearLayout) findViewById(R.id.button_Administrador_Mapa);

        mDataBase = new BancoDados(getApplicationContext());

        cliente = new Cliente();
        Bundle bundle = getIntent().getExtras();

        cliente.setId(bundle.getInt("identificador"));
        bundle1 = new Bundle();
        cliente = mDataBase.retornarDadosCliente(cliente.getId());

        Integer b = mDataBase.validaLogin(String.valueOf(cliente.getTelefone()),cliente.getSenha());
        bundle1.putInt("id_cliente",b);

        btVendedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PainelAdminActivity.this, Painel_admin_vendedor.class);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        btPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PainelAdminActivity.this, PainelPerfilActivity.class);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        btLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PainelAdminActivity.this, ListaClientesActivity.class);
                startActivity(intent);
            }
        });

        btMercado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PainelAdminActivity.this, Painel_admin_mercado.class);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        btRelatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PainelAdminActivity.this, PainelAdminRelatorios.class);
                //intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        btEstatistica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PainelAdminActivity.this, PainelAdminEstatistica.class);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        btHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PainelAdminActivity.this, MercadoActivity.class);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        btMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PainelAdminActivity.this, MapsActivity.class);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder confirmarSair = new AlertDialog.Builder(PainelAdminActivity.this);
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
                Intent intent = new Intent(PainelAdminActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        confirmarSair.create().show();
    }
}
