package com.rademotions.mercadoinformal.visao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.rademotions.mercadoinformal.Controle.BancoDados;
import com.rademotions.mercadoinformal.Modelo.Cliente;
import com.rademotions.mercadoinformal.R;

public class CadastroClienteActivity extends AppCompatActivity {

    private EditText txtNome, txtApelido, txtEmail, txtCidade, txtSenha, txtConfirmarSenha, txtTelefone, txtCodigoAdmin;
    private CheckBox cbCodigoAdmin;
    private String txtTipoUsuario;
    private Button btRegistrar;
    private Spinner spinnerCidade;

    private Cliente cliente;

    private BancoDados mDataBase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente);

        //campo de texto ou editText
        txtNome = (EditText) findViewById(R.id.editText_nome_cliente);
        txtApelido = (EditText) findViewById(R.id.editText_apelido_cliente);
        txtEmail = (EditText) findViewById(R.id.editText_email_cliente);
        //txtCidade = (EditText) findViewById(R.id.editText_cidade_cliente);
        txtSenha = (EditText) findViewById(R.id.editText_senha_cliente);
        txtConfirmarSenha = (EditText) findViewById(R.id.editText_confirmar_senha_cliente);
        txtTelefone = (EditText) findViewById(R.id.editText_telefone_cliente);
        txtCodigoAdmin = (EditText) findViewById(R.id.editText_codigo_registro_administrador);
        cbCodigoAdmin = (CheckBox) findViewById(R.id.checkBox_codigo_acesso_administrador);
        spinnerCidade = (Spinner) findViewById(R.id.Spinner_Cidade_registro);
        mDataBase = new BancoDados(getApplicationContext());

        preencherCidades();

        cbCodigoAdmin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b == true){
                    txtCodigoAdmin.setVisibility(View.VISIBLE);
                } else {
                    txtCodigoAdmin.setVisibility(View.INVISIBLE);
                }
            }
        });

        //Botão ou button
        btRegistrar = (Button) findViewById(R.id.button_registrar_cliente);

        btRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cliente registrarCliente = getDadosClienteFormulario();

                if(registrarCliente != null){

                    if(cbCodigoAdmin.isChecked() && (txtCodigoAdmin.getText().toString().equals("1205adm7") || txtCodigoAdmin.getText().toString().equals("7adm1205"))){
                        long result = mDataBase.registrarCliente(registrarCliente);

                        if(result > 0){
                            Toast.makeText(getApplicationContext(), "Registrado como Administrador", Toast.LENGTH_LONG).show();
                            carregarTelaLogin();
                        } else {
                            Toast.makeText(getApplicationContext(), "Não foi possível registrar", Toast.LENGTH_LONG).show();
                        }

                    } else if(cbCodigoAdmin.isChecked() && (txtCodigoAdmin.getText().toString() != "1205adm7" || txtCodigoAdmin.getText().toString() != "7adm1205")){
                        Toast.makeText(getApplicationContext(), "Não tem permissão para ser Administrador", Toast.LENGTH_LONG).show();
                    } else {
                        long result = mDataBase.registrarCliente(registrarCliente);

                        if(result > 0){
                            Toast.makeText(getApplicationContext(), "Usuário registrado", Toast.LENGTH_LONG).show();
                            carregarTelaLogin();
                        } else {
                            Toast.makeText(getApplicationContext(), "Não foi possível registrar", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
    }

    private Cliente getDadosClienteFormulario(){
        cliente = new Cliente();

        if(this.txtNome.getText().toString().isEmpty() == false){
            cliente.setNome(this.txtNome.getText().toString());
        } else {
            Toast.makeText(getApplicationContext(), "Informe o seu Nome", Toast.LENGTH_SHORT).show();
            return null;
        }
        if(this.txtApelido.getText().toString().isEmpty() == false){
            cliente.setApelido(this.txtApelido.getText().toString());
        } else {
            Toast.makeText(getApplicationContext(), "Informe o seu Apelido", Toast.LENGTH_SHORT).show();
            return null;
        }
        if(this.txtEmail.getText().toString().isEmpty() == false){
            cliente.setEmail(this.txtEmail.getText().toString());
        } else {
            cliente.setEmail(null);
        }//if(this.txtCidade.getText().toString().isEmpty() == false){
           // cliente.setCidade(this.txtCidade.getText().toString());
       // } else {
          // Toast.makeText(getApplicationContext(), "Informe a sua Cidade", Toast.LENGTH_SHORT).show();
          //  return null;
        //}
        if(this.txtTelefone.getText().toString().isEmpty() == false){
            int telefone = Integer.parseInt(this.txtTelefone.getText().toString());
            cliente.setTelefone(telefone);
        } else {
            Toast.makeText(getApplicationContext(), "Informe o seu nrº de Telefone", Toast.LENGTH_SHORT).show();
            return null;
        }
        if(this.spinnerCidade.getSelectedItem().toString().equals("Selecionar Cidade")){
            Toast.makeText(getApplicationContext(), "Selecione a sua cidade", Toast.LENGTH_SHORT).show();
            return null;
        } else {
            cliente.setCidade(this.spinnerCidade.getSelectedItem().toString());
        }
        if(this.txtSenha.getText().toString().isEmpty() == false){
            cliente.setSenha(this.txtSenha.getText().toString());
        } else {
            Toast.makeText(getApplicationContext(), "O campo de senha é obrigatório", Toast.LENGTH_SHORT).show();
            return null;
        }
        if(this.txtConfirmarSenha.getText().toString().isEmpty() == false){
            if(this.txtSenha.getText().toString().equals(this.txtConfirmarSenha.getText().toString())){
                cliente.setSenha(this.txtSenha.getText().toString());
            } else {
                Toast.makeText(getApplicationContext(), "Senhas diferentes, preencha novamente", Toast.LENGTH_SHORT).show();
                return null;
            }
        } else {
            Toast.makeText(getApplicationContext(), "O campo confirmar a senha é obrigatório", Toast.LENGTH_SHORT).show();
            return null;
        }
        if(this.txtCodigoAdmin.getText().toString().equals("1205adm7") || this.txtCodigoAdmin.getText().toString().equals("7adm1205")){
            txtTipoUsuario = "A3";
            cliente.setTipoUsuario(txtTipoUsuario);
        } else {
            txtTipoUsuario = "A1";
            cliente.setTipoUsuario(txtTipoUsuario);
        }

        return cliente;
    }

    public  void carregarTelaLogin(){
        Intent i = new Intent(CadastroClienteActivity.this, LoginActivity.class);
        startActivity(i);
    }

    private void preencherCidades(){

        String[] tipoCidade = new String[] {"Selecionar Cidade","Angoche","Balama","Beira","Boane","Buzi","Catandica","Chibuto","Chimoio","Chókwè","Cuamba","Dondo","Gondola"
                ,"Gorongoza","Ilha de Moçambique","Inhambane","Lichinga","Manhiça","Manica","Manjacaze","Maputo","Marromeu","Matola","Maxixe","Metangula","Moatize"
                ,"Mocimbua da praia","Mocuba","Monapo","Montepuez","Nacala","Namaacha","Nampula","Nhamatanda","Pemba","Quelimane","Songo","Tete","Vilanculos"
                ,"Xai-xai"};

        ArrayAdapter<String> adaptarTipoDocumento = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,tipoCidade);
        adaptarTipoDocumento.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCidade.setAdapter(adaptarTipoDocumento);
        spinnerCidade.setDropDownVerticalOffset(5);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CadastroClienteActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
