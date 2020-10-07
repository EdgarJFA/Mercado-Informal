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
import android.widget.TextView;
import android.widget.Toast;

import com.rademotions.mercadoinformal.Controle.BancoDados;
import com.rademotions.mercadoinformal.Modelo.Cliente;
import com.rademotions.mercadoinformal.Modelo.Mercado;
import com.rademotions.mercadoinformal.Modelo.Vendedor;
import com.rademotions.mercadoinformal.R;

import java.util.List;

public class Vincular_Mercado extends AppCompatActivity {

    private CheckBox cbCategoriaVendedor, cbCategoriaPrestador;
    private RadioGroup btGrupoSexo;
    private RadioButton btnSexoMasculino, btnSexoFemenino;
    private EditText txtNrDocumento, txtLicensa;
    private Vendedor vendedor;
    private Cliente cliente;
    private String categoria, sexo;
    private Button btRegistrar;
    Spinner spinnerDocumento, spinnerMercado;
    Bundle bundle1;
    private int admin;
    private BancoDados mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vincular_mercado);

        txtNrDocumento = (EditText) findViewById(R.id.editText_docm_vendedor);
        txtLicensa = (EditText) findViewById(R.id.editText_licenca_vendedor);

        //caixas de selecão ou checkbox's
        cbCategoriaVendedor = (CheckBox) findViewById(R.id.checkBox_categoria_vendedor);
        cbCategoriaPrestador = (CheckBox) findViewById(R.id.checkBox_categoria_prestador);

        //Radiogrupo e radiobutton
        btGrupoSexo = (RadioGroup) findViewById(R.id.radioGrupo_sexo);
        btnSexoFemenino = (RadioButton) findViewById(R.id.radioButton_sexo_femenino);
        btnSexoMasculino = (RadioButton) findViewById(R.id.radioButton_sexo_masculino);

        //Spinners
        spinnerDocumento = (Spinner) findViewById(R.id.spinner_tipoDocumento_vendedor);
        spinnerMercado = (Spinner) findViewById(R.id.spinner_selecionar_mercado);

        mDataBase = new BancoDados(getApplicationContext());

        btRegistrar = (Button) findViewById(R.id.button_registrar_vendedor_vinculado);

        Bundle bundle = getIntent().getExtras();

        cliente = new Cliente();
        cliente.setTelefone(bundle.getInt("telefone"));
        cliente.setSenha(bundle.getString("senha"));
        admin = bundle.getInt("id_admin");

        preencherDocumentos();
        carregarMercados();

        btRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Mercado selecionarMercado = (Mercado) spinnerMercado.getSelectedItem();
                Integer mercadoId = selecionarMercado.getId();

                Integer b = mDataBase.validaLogin(String.valueOf(cliente.getTelefone()),cliente.getSenha());

                Vendedor registrarVendedor = getDadosVendedorFormularioVinculo();
                registrarVendedor.setTipoDocumento(String.valueOf(spinnerDocumento.getSelectedItem()));
                registrarVendedor.setId_mercado(mercadoId);

                if(registrarVendedor != null){

                    long result = mDataBase.registrarVendedor( b, vendedor);
                    bundle1 = new Bundle();
                    bundle1.putInt("id_cliente",admin);

                    if(result > 0){
                        Intent i = new Intent(Vincular_Mercado.this, Painel_admin_vendedor.class);
                        i.putExtras(bundle1);
                        startActivity(i);
                        Toast.makeText(getApplicationContext(), "Vendedor registrado no mercado com id = " + mercadoId, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Falha *******", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }

    public int verficarGrupoAtividade(){
        if(cbCategoriaPrestador.isChecked()){
            categoria = "P";
            return 1;
        }
        if(cbCategoriaVendedor.isChecked()){
            categoria = "V";
            return 1;
        }
        if(cbCategoriaPrestador.isChecked() && cbCategoriaVendedor.isChecked()){
            categoria = "T";
            return 1;
        }
        return 0;
    }

    public int verificarGrupoSexo(){
        if(btnSexoMasculino.isChecked()){
            sexo = btnSexoMasculino.getText().toString();
            return 1;
        }
        if(btnSexoFemenino.isChecked()){
            sexo = btnSexoFemenino.getText().toString();
            return 1;
        }
        return 0;
    }

    private Vendedor getDadosVendedorFormularioVinculo(){
        vendedor = new Vendedor();

        if(this.txtNrDocumento.getText().toString().isEmpty() == false){
            vendedor.setN_documento(this.txtNrDocumento.getText().toString());
        } else {
            Toast.makeText(getApplicationContext(), "Insira o número de identificção", Toast.LENGTH_SHORT).show();
            return null;
        }
        if(this.txtLicensa.getText().toString().isEmpty() == false){
            vendedor.setLicensa(this.txtLicensa.getText().toString());
        } else {
            Toast.makeText(getApplicationContext(), "Insirá a liçensa comercial", Toast.LENGTH_SHORT).show();
            return null;
        }
        if(verficarGrupoAtividade() == 1){
            vendedor.setAtividade(categoria);
        } else {
            Toast.makeText(getApplicationContext(), "Selecionar atividade", Toast.LENGTH_SHORT).show();
            return null;
        }
        if(verificarGrupoSexo() == 1){
            vendedor.setSexo(sexo);
        } else {
            Toast.makeText(getApplicationContext(), "Definir sexo", Toast.LENGTH_SHORT).show();
            return null;
        }

        return vendedor;
    }

    private void preencherDocumentos(){

        String[] tipoDocumento = new String[] {"BI","Carta de Condução","Cartão de Eleitor","Dire","Passaporte"};

        ArrayAdapter<String> adaptarTipoDocumento = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,tipoDocumento );
        adaptarTipoDocumento.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDocumento.setAdapter(adaptarTipoDocumento);
        //spinerNrQuestao.setDropDownVerticalOffset(3);
    }

    private void carregarMercados(){
        //mDataBase = new BancoDados(getApplicationContext());  //Ter atenção...
        List<Mercado> mercados = mDataBase.retornarTodosMercados();

        ArrayAdapter<Mercado> adaptarMercado = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mercados);
        adaptarMercado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMercado.setAdapter(adaptarMercado);
        spinnerMercado.setDropDownVerticalOffset(7);
    }
}
