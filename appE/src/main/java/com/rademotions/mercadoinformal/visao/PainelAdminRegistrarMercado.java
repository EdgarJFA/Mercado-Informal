package com.rademotions.mercadoinformal.visao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.rademotions.mercadoinformal.Controle.BancoDados;
import com.rademotions.mercadoinformal.Modelo.Administrador;
import com.rademotions.mercadoinformal.Modelo.Cliente;
import com.rademotions.mercadoinformal.Modelo.Mercado;
import com.rademotions.mercadoinformal.R;

public class PainelAdminRegistrarMercado extends AppCompatActivity {

    private EditText txtNomeMercado, txtCidadeMercado, txtProvinciaMercado, txtLatitude, txtLongitude;
    private Button btRegistrarMercado;
    private Spinner spinnerCidade, spinnerProvincia;
    private Bundle bundle1;
    private Cliente cliente;
    String[] tipoCidade = new String[] {"Selecionar Cidade"};;
    private BancoDados mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painel_admin_registrar_mercado);

        txtNomeMercado = (EditText) findViewById(R.id.editText_nome_mercado);
        //txtCidadeMercado = (EditText) findViewById(R.id.editText_cidade_mercado);
        //txtProvinciaMercado = (EditText) findViewById(R.id.editText_provincia_mercado);
        txtLatitude = (EditText) findViewById(R.id.editText_latitude_mercado);
        txtLongitude = (EditText) findViewById(R.id.editText_longitude_mercado);
        btRegistrarMercado = (Button) findViewById(R.id.button_registrar_mercado);
        spinnerCidade = (Spinner) findViewById(R.id.Spinner_Cidade_mercado);
        spinnerProvincia = (Spinner) findViewById(R.id.Spinner_Provincia_mercado);
        mDataBase = new BancoDados(getApplicationContext());

        Bundle bundle = getIntent().getExtras();
        cliente = new Cliente();
        cliente.setId(bundle.getInt("identificador"));

        bundle1 = new Bundle();
        bundle1.putInt("id_cliente",cliente.getId());

        preencherProvincias();
        preencherCidades();

        btRegistrarMercado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Mercado registrarMercado = getDadosMercadoFormulario();

                if(registrarMercado != null){

                        long result = mDataBase.registrarMercado(registrarMercado);

                        if(result > 0){
                            Intent i = new Intent(PainelAdminRegistrarMercado.this, Painel_admin_mercado.class);
                            i.putExtras(bundle1);
                            startActivity(i);
                            Toast.makeText(getApplicationContext(), "Mercado registrado", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Não foi possível registrar o mercado", Toast.LENGTH_LONG).show();
                        }
                }
            }
        });
    }

    private Mercado getDadosMercadoFormulario(){
        Mercado mercado = new Mercado();

        if(this.txtNomeMercado.getText().toString().isEmpty() == false){
            mercado.setNome(this.txtNomeMercado.getText().toString());
        } else {
            Toast.makeText(getApplicationContext(), "Insira o nome do mercado", Toast.LENGTH_SHORT).show();
            return null;
        }
        if(this.spinnerCidade.getSelectedItem().toString().equals("Selecionar Cidade")){
            Toast.makeText(getApplicationContext(), "selecione a cidade onde o mercado se encontra", Toast.LENGTH_SHORT).show();
            return null;
        } else {
            mercado.setCidade(this.spinnerCidade.getSelectedItem().toString());
        }
        if(this.spinnerProvincia.getSelectedItem().toString().equals("Selecionar Provincia")){
            Toast.makeText(getApplicationContext(), "selecione a provincia onde o mercado se encontra", Toast.LENGTH_SHORT).show();
            return null;
        } else {
            mercado.setProvincia(this.spinnerProvincia.getSelectedItem().toString());
        }
        if(this.txtLatitude.getText().toString().isEmpty() == false){
            mercado.setLatitude(this.txtLatitude.getText().toString());
        } else {
            Toast.makeText(getApplicationContext(), "insira a coordenada latitude", Toast.LENGTH_SHORT).show();
            return null;
        }
        if(this.txtLongitude.getText().toString().isEmpty() == false){
            mercado.setLongitude(this.txtLongitude.getText().toString());
        } else {
            Toast.makeText(getApplicationContext(), "insira a coordenada longitude", Toast.LENGTH_SHORT).show();
            return null;
        }

        return mercado;
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(PainelAdminRegistrarMercado.this, Painel_admin_mercado.class);
        i.putExtras(bundle1);
        startActivity(i);
    }

    private void preencherCidades(){

         tipoCidade = new String[] {"Selecionar Cidade","Angoche","Balama","Beira","Boane","Buzi","Catandica","Chibuto","Chimoio","Chókwè","Cuamba","Dondo","Gondola"
                ,"Gorongoza","Ilha de Moçambique","Inhambane","Lichinga","Manhiça","Manica","Manjacaze","Maputo","Marromeu","Matola","Maxixe","Metangula","Moatize"
                ,"Mocimbua da praia","Mocuba","Monapo","Montepuez","Nacala","Namaacha","Nampula","Nhamatanda","Pemba","Quelimane","Songo","Tete","Vilanculos"
                ,"Xai-xai"};
        /*tipoCidade = new String[] {"Selecionar Cidade"};
        * if (spinnerProvincia.getSelectedItem().toString().equals("Cabo Delegado")){
                    tipoCidade = new String[] {"Mocimbua da praia","Montepuez","Pemba"};
                } else if (spinnerProvincia.getSelectedItem().toString().equals("Gaza")){
                    tipoCidade = new String[] {"Chibuto","Chókwè","Manjacaze","Xai-xai"};
                } else if (spinnerProvincia.getSelectedItem().toString().equals("Inhambane")){
                    tipoCidade = new String[] {"Inhambane","Maxixe","Vilanculos"};
                } else if (spinnerProvincia.getSelectedItem().toString().equals("Manica")){
                    tipoCidade = new String[] {"Catandica","Chimoio","Gondola","Manica"};
                } else if (spinnerProvincia.getSelectedItem().toString().equals("Maputo Cidade")){
                    tipoCidade = new String[] {"Maputo"};
                } else if (spinnerProvincia.getSelectedItem().toString().equals("Maputo Provincia")){
                    tipoCidade = new String[] {"Boane","Manhiça","Matola","Namaacha"};
                } else if (spinnerProvincia.getSelectedItem().toString().equals("Nampula")){
                    tipoCidade = new String[] {"Angoche","Ilha de Moçambique","Monapo","Nacala","Nampula"};
                } else if (spinnerProvincia.getSelectedItem().toString().equals("Niassa")){
                    tipoCidade = new String[] {"Cuamba","Lichinga","Metangula"};
                } else if (spinnerProvincia.getSelectedItem().toString().equals("Sofala")){
                    tipoCidade = new String[] {"Beira","Buzi","Dondo","Gorongoza","Marromeu","Nhamatanda"};
                } else if (spinnerProvincia.getSelectedItem().toString().equals("Tete")){
                    tipoCidade = new String[] {"Moatize","Songo","Tete"};
                } else if (spinnerProvincia.getSelectedItem().toString().equals("Zambezia")){
                    tipoCidade = new String[] {"Mocuba","Quelimane"};
                } else {
                    tipoCidade = new String[] {"Selecionar Cidade"};
                }

                preencherCidades(tipoCidade);
        *
        * */

        ArrayAdapter<String> adaptarTipoDocumento = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,tipoCidade);
        adaptarTipoDocumento.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCidade.setAdapter(adaptarTipoDocumento);
        spinnerCidade.setDropDownVerticalOffset(5);
    }

    private void preencherProvincias(){

        String[] tipoProvincia = new String[] {"Selecionar Provincia","Cabo Delegado","Gaza","Inhambane","Manica","Maputo Cidade","Maputo Provincia","Nampula","Niassa","Sofala","Tete","Zambezia"};

        ArrayAdapter<String> adaptarTipoDocumento = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,tipoProvincia);
        adaptarTipoDocumento.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProvincia.setAdapter(adaptarTipoDocumento);
        spinnerProvincia.setDropDownVerticalOffset(5);
    }
}
