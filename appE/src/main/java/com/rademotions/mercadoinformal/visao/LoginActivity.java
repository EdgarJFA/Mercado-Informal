package com.rademotions.mercadoinformal.visao;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.system.Os;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rademotions.mercadoinformal.Controle.BancoDados;
import com.rademotions.mercadoinformal.MercadoActivity;
import com.rademotions.mercadoinformal.Modelo.Cliente;
import com.rademotions.mercadoinformal.Modelo.Produto;
import com.rademotions.mercadoinformal.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class LoginActivity extends AppCompatActivity {

    Dialog  criarNovaConta;
    private Button btLerMais, btvisitar, btEntrar;
    private TextView btCadastro;
    private TextView txtEmailTelefone, txtSenha, txtTelefone;

    private BancoDados mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btLerMais = (Button) findViewById(R.id.button_Ler_mais);
        btvisitar = (Button) findViewById(R.id.button_visitar);
        btEntrar = (Button) findViewById(R.id.button_entrar);
        btCadastro = (TextView) findViewById(R.id.text_View_btCadastro_cliente);

        txtEmailTelefone = (TextView) findViewById(R.id.editText_email_telefone);
        txtSenha = (TextView) findViewById(R.id.editText_senha);

        criarNovaConta = new Dialog(this);

        mDataBase = new BancoDados(this);
        File dataBase = getApplicationContext().getDatabasePath(BancoDados.BASEDADOS_NOME);

        if(false == dataBase.exists()){
            mDataBase.getReadableDatabase();
            if(copyDatabase(this)){
                Toast.makeText(this, "Banco de dados copiado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Erro ao copiar", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        btLerMais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, CartaoVisitaActivity.class);
                startActivity(intent);
            }
        });

        btCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(LoginActivity.this, CadastroClienteActivity.class);
                //startActivity(intent);
                showPopUpCriarNovaConta();
            }
        });

        btvisitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                int idVisitante = 0;
                bundle.putInt("id_cliente",idVisitante);
                Intent intent = new Intent(LoginActivity.this, MercadoActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailTel = txtEmailTelefone.getText().toString();
                String senha = txtSenha.getText().toString();

                Integer b = mDataBase.validaLogin(emailTel,senha);
                String usuario = mDataBase.verificarUsuario(emailTel,senha);
                Bundle bundle = new Bundle();
                bundle.putInt("identificador",b);


                if(b != 0){
                    if(usuario.equals("A1")){
                        Intent i = new Intent(LoginActivity.this, PainelClienteActivity.class);
                        i.putExtras(bundle);
                        startActivity(i);
                    } else if(usuario.equals("A3")){
                        Intent i = new Intent(LoginActivity.this, PainelAdminActivity.class);
                        i.putExtras(bundle);
                        startActivity(i);
                    } else if(usuario.equals("A2")){
                        Intent i = new Intent(LoginActivity.this, PainelVendedorActivity.class);
                        i.putExtras(bundle);
                        startActivity(i);
                    } else{
                        Toast.makeText(getApplicationContext(),"usuário nao existe", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getApplicationContext(),"Telefone  ou senha incorretos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean copyDatabase(Context context) {
        try {

            InputStream inputStream = context.getAssets().open(BancoDados.BASEDADOS_NOME);
            String outFileName = BancoDados.DBLOCATION + BancoDados.BASEDADOS_NOME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[]buff = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            Log.w("MainActivity","Banco Copiado");
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void showPopUpCriarNovaConta(){
        ImageView btFechar;
        final Button btCriarContaFacebook, btCriarContaGoogle, btCriarContaPadrao;

        criarNovaConta.setContentView(R.layout.tela_pop_up_cadastro);
        btFechar = (ImageView) criarNovaConta.findViewById(R.id.button_tela_cadastro_fechar);
        btCriarContaFacebook = (Button) criarNovaConta.findViewById(R.id.button_tela_cadastro_CriarContaFacebook);
        btCriarContaGoogle = (Button) criarNovaConta.findViewById(R.id.button_tela_cadastro_CriarContaGoogle);
        btCriarContaPadrao = (Button) criarNovaConta.findViewById(R.id.button_tela_cadastro_CriarContaNormal);

        btFechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                criarNovaConta.dismiss();
            }
        });

        btCriarContaFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(LoginActivity.this, CadastroClienteActivity.class);
                //startActivity(intent);
                Toast.makeText(getApplicationContext(),"Criação de conta com facebook ainda em desenvolvimento", Toast.LENGTH_SHORT).show();
            }
        });

        btCriarContaGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(LoginActivity.this, CadastroClienteActivity.class);
                //startActivity(intent);
                Toast.makeText(getApplicationContext(),"Criação de conta com Google ainda em desenvolvimento", Toast.LENGTH_SHORT).show();
            }
        });

        btCriarContaPadrao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, CadastroClienteActivity.class);
                startActivity(intent);
            }
        });

        criarNovaConta.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        criarNovaConta.show();
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder confirmarSair = new AlertDialog.Builder(LoginActivity.this);
        confirmarSair.setMessage("Deseja sair da aplicação?");

        confirmarSair.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        confirmarSair.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //System.exit(0);
                //finish();
                //System.exit(0);
                //onDestroy();
                finishAffinity();
                System.exit(0);
            }
        });
        confirmarSair.create().show();
    }
}
