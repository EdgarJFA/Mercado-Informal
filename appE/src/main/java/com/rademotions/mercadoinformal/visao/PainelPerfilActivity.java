package com.rademotions.mercadoinformal.visao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rademotions.mercadoinformal.Controle.BancoDados;
import com.rademotions.mercadoinformal.Modelo.Cliente;
import com.rademotions.mercadoinformal.Modelo.Mercado;
import com.rademotions.mercadoinformal.Modelo.Vendedor;
import com.rademotions.mercadoinformal.R;

public class PainelPerfilActivity extends AppCompatActivity {

    private TextView txtVerNomeUsuario, txtVerEmailUsuario, txtVerTelemovel, txtVerCidade, txtVerBi, txtVerMercado, txtVerCategoria, txtVerProvincia;
    private TextView txtVerNomeUsuarioPainelPerfil, txtVerEmailUsuarioPainelPerfil, tituloDadosAdicionais;
    private ImageView btDefinicaoConta, btEditarConta;

    private Cliente cliente;
    private Vendedor vendedor;
    private Mercado mercado;
    private BancoDados mDataBase;
    private Bundle bundle1;
    private int b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painel_perfil);

        txtVerEmailUsuarioPainelPerfil = (TextView) findViewById(R.id.textView_email_perfil);
        txtVerNomeUsuarioPainelPerfil = (TextView) findViewById(R.id.textView_nome_perfil);
        txtVerNomeUsuario = (TextView) findViewById(R.id.TextView_nome_perfil_dublicata);
        txtVerEmailUsuario = (TextView) findViewById(R.id.TextView_email_perfil_dublicata);
        txtVerTelemovel = (TextView) findViewById(R.id.TextView_telefone_perfil);
        txtVerCidade = (TextView) findViewById(R.id.TextView_cidade_perfil);
        txtVerBi = (TextView) findViewById(R.id.TextView_nrBi_perfil);
        txtVerMercado = (TextView) findViewById(R.id.TextView_mercado_perfil);
        txtVerCategoria = (TextView) findViewById(R.id.TextView_categoria_perfil);
        txtVerProvincia = (TextView) findViewById(R.id.TextView_provincia_perfil);
        tituloDadosAdicionais = (TextView) findViewById(R.id.titulo_dados_adicionais_perfil);
        btEditarConta = (ImageView) findViewById(R.id.ImageView_editar_dados_perfil);
        btDefinicaoConta = (ImageView) findViewById(R.id.ImageView_definicao_conta_perfil);

        mDataBase = new BancoDados(getApplicationContext());
        cliente = new Cliente();
        vendedor = new Vendedor();
        mercado = new Mercado();
        Bundle bundle = getIntent().getExtras();

        //b = bundle1.getInt("id_cliente");
        cliente.setId(bundle.getInt("id_cliente"));
        Integer b = cliente.getId();
        cliente = mDataBase.retornarDadosCliente(cliente.getId());
        cliente.setId(b);

        bundle1 = new Bundle();
        bundle1.putInt("identificador",cliente.getId());

        txtVerNomeUsuarioPainelPerfil.setText(cliente.getNome() + " " + cliente.getApelido());
        txtVerEmailUsuarioPainelPerfil.setText(cliente.getEmail());

        txtVerNomeUsuario.setText("    " + cliente.getNome() + " " + cliente.getApelido());
        txtVerTelemovel.setText("    " + cliente.getTelefone());

        if(cliente.getEmail() == null){
            txtVerEmailUsuario.setText("    ");
        } else {
            txtVerEmailUsuario.setText("    " + cliente.getEmail());
        }
        txtVerCidade.setText("    " + cliente.getCidade());

        String nivelUsuario = mDataBase.verificarUsuario(String.valueOf(cliente.getTelefone()),cliente.getSenha());

        if(!nivelUsuario.equals("A2")){
            btEditarConta.setVisibility(View.VISIBLE);
            btEditarConta.setEnabled(true);
        } else {
            btEditarConta.setVisibility(View.INVISIBLE);
            btEditarConta.setEnabled(false);
        }

        //final Integer c = mDataBase.validaLogin(String.valueOf(cliente.getTelefone()), cliente.getSenha());

        if(nivelUsuario.equals("A2")){
            vendedor = mDataBase.retornarDadosVendedor(b);
            mercado = mDataBase.retornarDadosMercado(vendedor.getId_mercado());

            tituloDadosAdicionais.setVisibility(View.VISIBLE);
            txtVerBi.setVisibility(View.VISIBLE);
            txtVerBi.setText( "    " + vendedor.getTipoDocumento() + ": " + vendedor.getN_documento());
            txtVerProvincia.setVisibility(View.VISIBLE);
            txtVerProvincia.setText("    " + mercado.getProvincia());
            txtVerMercado.setVisibility(View.VISIBLE);
            txtVerMercado.setText("    Mercado " + mercado.getNome());
            txtVerBi.setText(vendedor.getTipoDocumento() + ": " + vendedor.getN_documento());

            if(vendedor.getAtividade().equals("P")){
                txtVerCategoria.setVisibility(View.VISIBLE);
                txtVerCategoria.setText("    Prestador de Serviço");
            } else if(vendedor.getAtividade().equals("V")){
                txtVerCategoria.setVisibility(View.VISIBLE);
                txtVerCategoria.setText("    Vendedor de Produtos");
            } else if(vendedor.getAtividade().equals("T")){
                txtVerCategoria.setVisibility(View.VISIBLE);
                txtVerCategoria.setText("    Produtos e Serviço");
            } else {
                txtVerCategoria.setVisibility(View.INVISIBLE);
            }
        } else {
            txtVerCategoria.setVisibility(View.INVISIBLE);
            tituloDadosAdicionais.setVisibility(View.INVISIBLE);
            txtVerBi.setVisibility(View.INVISIBLE);
            txtVerProvincia.setVisibility(View.INVISIBLE);
            txtVerMercado.setVisibility(View.INVISIBLE);
        }

        /*
       if(c != null){
        } */

        btDefinicaoConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Deve ir as definições da Conta do usuario com id = " + cliente.getId() , Toast.LENGTH_SHORT).show();
                //mDataBase.removerCliente(b);
                //Intent intent = new Intent(PainelPerfilActivity.this, LoginActivity.class);
                //startActivity(intent);

            }
        });

        btEditarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Deve Editar Dados da Conta com id = " + cliente.getId() , Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //cliente = mDataBase.retornarDadosCliente(cliente.getId());
        String nivelUsuario = mDataBase.verificarUsuario(String.valueOf(cliente.getTelefone()),cliente.getSenha());

        if(nivelUsuario.equals("A3")){
            Intent intent = new Intent(PainelPerfilActivity.this, PainelAdminActivity.class);
            intent.putExtras(bundle1);
            startActivity(intent);
        } else if(nivelUsuario.equals("A1")){
            Intent intent = new Intent(PainelPerfilActivity.this, PainelClienteActivity.class);
            intent.putExtras(bundle1);
            startActivity(intent);
        } else if(nivelUsuario.equals("A2")){
            Intent intent = new Intent(PainelPerfilActivity.this, PainelVendedorActivity.class);
            intent.putExtras(bundle1);
            startActivity(intent);
        } else{
            Toast.makeText(PainelPerfilActivity.this, "Nivel de usuário não encontrado", Toast.LENGTH_SHORT).show();
        }
    }
}
