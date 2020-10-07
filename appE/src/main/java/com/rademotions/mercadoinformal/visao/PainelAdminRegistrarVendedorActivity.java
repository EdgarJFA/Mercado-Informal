package com.rademotions.mercadoinformal.visao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.rademotions.mercadoinformal.Controle.BancoDados;
import com.rademotions.mercadoinformal.Modelo.Cliente;
import com.rademotions.mercadoinformal.Modelo.Vendedor;
import com.rademotions.mercadoinformal.R;

public class PainelAdminRegistrarVendedorActivity extends AppCompatActivity {

    private EditText txtNome, txtApelido, txtEmail, txtCelular, txtCidade, txtSenha, txtConfSenha;
    private Spinner spinnerCidade;
    private Button btRegistrar;
    //private String txtTipoUsuario = "V1";

    private Cliente cliente;
    private Bundle bundle1;
    private int admin;
    private BancoDados mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painel_admin_registrar_vendedor);

        //textos ou editText's
        txtNome = (EditText) findViewById(R.id.editText_nome_vendedor);
        txtApelido = (EditText) findViewById(R.id.editText_apelido_vendedor);
        txtEmail = (EditText) findViewById(R.id.editText_email_vendedor);
        txtCelular = (EditText) findViewById(R.id.editText_telefone_vendedor);
        //txtCidade = (EditText) findViewById(R.id.editText_cidade_vendedor);
        txtSenha = (EditText) findViewById(R.id.editText_senha_vendedor);
        txtConfSenha = (EditText) findViewById(R.id.editText_ConfSenha_vendedor);
        spinnerCidade = (Spinner) findViewById(R.id.Spinner_Cidade_registro_vendedor);

        //Botão
         btRegistrar = (Button) findViewById(R.id.button_registrar_vendedor);

        mDataBase = new BancoDados(getApplicationContext());

        Bundle bundle = getIntent().getExtras();
        admin = bundle.getInt("identificador");

        preencherCidades();

        btRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cliente registrarCliente = getDadosVendedorFormulario();

                if(registrarCliente != null){

                    long result = mDataBase.registrarCliente(registrarCliente);
                    bundle1 = new Bundle();
                    bundle1.putString("senha",registrarCliente.getSenha());
                    bundle1.putInt("telefone",registrarCliente.getTelefone());
                    bundle1.putInt("id_admin",admin);

                    if(result > 0){
                        Intent i = new Intent(PainelAdminRegistrarVendedorActivity.this, Vincular_Mercado.class);
                        i.putExtras(bundle1);
                        startActivity(i);
                        limparCampos();
                        Toast.makeText(getApplicationContext(), "Dados adicionados", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Falha", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public void limparCampos(){
        txtNome.setText("");
        txtApelido.setText("");
        txtEmail.setText("");
        txtCelular.setText("");
        txtSenha.setText("");
        txtConfSenha.setText("");
    }

    private Cliente getDadosVendedorFormulario(){
        cliente = new Cliente();

        if(this.txtNome.getText().toString().isEmpty() == false){
            cliente.setNome(this.txtNome.getText().toString());
        } else {
            Toast.makeText(getApplicationContext(), "Campo Nome é obrigatório", Toast.LENGTH_SHORT).show();
            return null;
        }
        if(this.txtApelido.getText().toString().isEmpty() == false){
            cliente.setApelido(this.txtApelido.getText().toString());
        } else {
            Toast.makeText(getApplicationContext(), "Campo Apelido é obrigatório", Toast.LENGTH_SHORT).show();
            return null;
        }
        if(this.txtEmail.getText().toString().isEmpty() == false){
            cliente.setEmail(this.txtEmail.getText().toString());
        } else {
            cliente.setEmail(null);
        }//if(this.txtCidade.getText().toString().isEmpty() == false){
           // cliente.setCidade(this.txtCidade.getText().toString());
        //} else {
         //   Toast.makeText(getApplicationContext(), "Campo Cidade é obrigatório", Toast.LENGTH_SHORT).show();
          //  return null;
        //}
        if(this.spinnerCidade.getSelectedItem().toString().equals("Selecionar Cidade")){
            Toast.makeText(getApplicationContext(), "Selecione a sua Cidade", Toast.LENGTH_SHORT).show();
            return null;
        } else {
            cliente.setCidade(this.spinnerCidade.getSelectedItem().toString());
        }
        if(this.txtCelular.getText().toString().isEmpty() == false){
            int telefone = Integer.parseInt(this.txtCelular.getText().toString());
            cliente.setTelefone(telefone);
        } else {
            Toast.makeText(getApplicationContext(), "Campo Telefone é obrigatório", Toast.LENGTH_SHORT).show();
            return null;
        }
        if(this.txtSenha.getText().toString().isEmpty() == false){
            cliente.setSenha(this.txtSenha.getText().toString());
        } else {
            Toast.makeText(getApplicationContext(), "O campo de senha é obrigatório", Toast.LENGTH_SHORT).show();
            return null;
        }
        if(this.txtConfSenha.getText().toString().isEmpty() == false){
            if(this.txtSenha.getText().toString().equals(this.txtConfSenha.getText().toString())){
                cliente.setSenha(this.txtSenha.getText().toString());
                cliente.setTipoUsuario("A2");
            } else {
                Toast.makeText(getApplicationContext(), "Senhas diferentes, preencha novamente", Toast.LENGTH_SHORT).show();
                return null;
            }
        } else {
            Toast.makeText(getApplicationContext(), "O campo confirmar a senha é obrigatório", Toast.LENGTH_SHORT).show();
            return null;
        }

        return cliente;
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
}
