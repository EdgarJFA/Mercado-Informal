package com.rademotions.mercadoinformal.visao;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rademotions.mercadoinformal.Controle.BancoDados;
import com.rademotions.mercadoinformal.MercadoActivity;
import com.rademotions.mercadoinformal.Modelo.Cliente;
import com.rademotions.mercadoinformal.R;

public class PainelClienteActivity extends AppCompatActivity {

    private TextView txtVerNomeUsuario, txtVerEmailUsuario;
    private LinearLayout btPerfil, btRota, btMercado, btCompra, btFavorito, btAssistencia;
    private Bundle bundle1;

    private Cliente cliente;
    private BancoDados mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painel_cliente);

        txtVerEmailUsuario = (TextView) findViewById(R.id.textView_email_usuario);
        txtVerNomeUsuario =  (TextView) findViewById(R.id.textView_nome_usuario);

        btPerfil = (LinearLayout) findViewById(R.id.button_Cliente_Perfil);
        btMercado = (LinearLayout) findViewById(R.id.button_Cliente_Mercado);
        btRota = (LinearLayout) findViewById(R.id.button_Cliente_Rotas);
        btCompra = (LinearLayout) findViewById(R.id.button_Cliente_Compra);
        btFavorito = (LinearLayout) findViewById(R.id.button_Cliente_Desejo);
        btAssistencia = (LinearLayout) findViewById(R.id.button_Cliente_Assistencia);

        mDataBase = new BancoDados(getApplicationContext());


        cliente = new Cliente();
        Bundle bundle = getIntent().getExtras();

        cliente.setId(bundle.getInt("identificador"));
        bundle1 = new Bundle();
        cliente = mDataBase.retornarDadosCliente(cliente.getId());

        txtVerNomeUsuario.setText(cliente.getNome() + " " + cliente.getApelido());
        txtVerEmailUsuario.setText(cliente.getEmail());

        Integer b = mDataBase.validaLogin(String.valueOf(cliente.getTelefone()),cliente.getSenha());
        bundle1.putInt("id_cliente",b);

        btPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PainelClienteActivity.this, PainelPerfilActivity.class);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        btMercado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PainelClienteActivity.this, MercadoActivity.class);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        btFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PainelClienteActivity.this, PainelClienteFavoritos.class);
                //intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        btCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PainelClienteActivity.this, PainelClienteCompras.class);
                //intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        btRota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PainelClienteActivity.this, MapsActivity.class);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        btAssistencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PainelClienteActivity.this, TelaClienteAssistencia.class);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder confirmarSair = new AlertDialog.Builder(PainelClienteActivity.this);
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
                Intent intent = new Intent(PainelClienteActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        confirmarSair.create().show();
    }
}
