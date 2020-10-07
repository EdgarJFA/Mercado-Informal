package com.rademotions.mercadoinformal.visao;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rademotions.mercadoinformal.Controle.BancoDados;
import com.rademotions.mercadoinformal.Modelo.Cliente;
import com.rademotions.mercadoinformal.Modelo.ModalidadePagamento;
import com.rademotions.mercadoinformal.R;

import java.text.SimpleDateFormat;

public class DefinirPagamento extends AppCompatActivity {

    Button btRegistrar;
    Spinner spinnerServicos;
    EditText txtTelefone, txtPin;
    TextView txtDigito;
    ImageView imagemIcone;

    private Cliente cliente;
    private ModalidadePagamento modalidadePagamento;
    private Bundle bundle1;
    private String dateString, digitoModalidade = "Adira ao serviço via USSD digitando ";
    private BancoDados mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definir_pagamento);

        spinnerServicos = (Spinner) findViewById(R.id.spinner_selecionar_modalidade);
        txtTelefone = (EditText) findViewById(R.id.editText_tela_pagamentos_telefone);
        txtPin = (EditText) findViewById(R.id.editText_tela_pagamentos_pin);
        txtDigito = (TextView) findViewById(R.id.textView_digito);
        btRegistrar = (Button) findViewById(R.id.button_tela_pagamentos_registrar);
        imagemIcone = (ImageView) findViewById(R.id.imageView_pagamentos);

        mDataBase = new BancoDados(getApplicationContext());
        cliente = new Cliente();
        Bundle bundle = getIntent().getExtras();

        cliente.setId(bundle.getInt("identificador"));
        bundle1 = new Bundle();
        bundle1.putInt("id_cliente",cliente.getId());

        preencherModalidades();
        verificarModalidade();
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        dateString = sdf.format(date);

        btRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ModalidadePagamento registrarModalidade = getDadosModalidadePagamento();
                registrarModalidade.setData(dateString);
                Integer idVendedor = mDataBase.retornarIdVendedor(cliente.getId());
                registrarModalidade.setIdVendedor(idVendedor);

                if(registrarModalidade != null){

                    long resultProduto = mDataBase.registrarModalidade(registrarModalidade);

                    if(resultProduto > 0){
                        Intent intent = new Intent(DefinirPagamento.this, PainelVendedorPagamentos.class);
                        intent.putExtras(bundle1);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "Modalidade Registrada", Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext()," Data " + dateString, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Modalidade não Registrada", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void verificarModalidade(){
        if(spinnerServicos.getSelectedItem().toString().equals("mKesh")){
            digitoModalidade += "*500#";
            txtDigito.setText(digitoModalidade);
            imagemIcone.setImageDrawable(Drawable.createFromPath("MercadoInformal\\appE\\src\\main\\res\\drawable\\img_mcel_mesh.png"));
        }
        else if(spinnerServicos.getSelectedItem().toString().equals("mPesa")){
            digitoModalidade += "*150#";
            txtDigito.setText(digitoModalidade);
            imagemIcone.setImageDrawable(Drawable.createFromPath("MercadoInformal\\appE\\src\\main\\res\\drawable\\img_vodacom_mpesa.png"));
        }
        else if(spinnerServicos.getSelectedItem().toString().equals("e-Mola")){
            digitoModalidade += "*---#";
            txtDigito.setText(digitoModalidade);
            imagemIcone.setImageDrawable(Drawable.createFromPath("MercadoInformal\\appE\\src\\main\\res\\drawable\\img_movitel_emola.png"));
        }
        else if(spinnerServicos.getSelectedItem().toString().equals("Millennium IZI")){
            digitoModalidade += "*181#";
            txtDigito.setText(digitoModalidade);
            imagemIcone.setImageDrawable(Drawable.createFromPath("MercadoInformal\\appE\\src\\main\\res\\drawable\\img_bim.png"));
        }
        else if(spinnerServicos.getSelectedItem().toString().equals("BCI Ponto 24")){
            digitoModalidade += "*124#";
            txtDigito.setText(digitoModalidade);
            imagemIcone.setImageDrawable(Drawable.createFromPath("MercadoInformal\\appE\\src\\main\\res\\drawable\\img_bci.png"));
        } else {
            digitoModalidade += "*exemplo#";
            txtDigito.setText(digitoModalidade);
            imagemIcone.setImageDrawable(Drawable.createFromPath("MercadoInformal\\appE\\src\\main\\res\\drawable\\ic_local_atm_black_24dp.png"));
        }
    }

    private ModalidadePagamento getDadosModalidadePagamento(){

        modalidadePagamento = new ModalidadePagamento();

        if(this.spinnerServicos.getSelectedItem().toString().equals("Selecionar Modalidade")){
            Toast.makeText(getApplicationContext(), "Selecione um serviço a registrar", Toast.LENGTH_SHORT).show();
            return null;
        } else {
            modalidadePagamento.setNome(this.spinnerServicos.getSelectedItem().toString());
        }
        if(this.txtTelefone.getText().toString().isEmpty() == false){
            int telefone = Integer.parseInt(this.txtTelefone.getText().toString());
            modalidadePagamento.setTelefone(telefone);
        } else {
            Toast.makeText(getApplicationContext(), "digite o número de telefone a associar", Toast.LENGTH_SHORT).show();
            return null;
        }
        if(this.txtPin.getText().toString().isEmpty() == false){
            //int pin = Integer.parseInt(this.txtPin.getText().toString());
            //modalidadePagamento.setTelefone(telefone);
        } else {
            Toast.makeText(getApplicationContext(), "O Pin é obrigatório para confirmação da conta", Toast.LENGTH_SHORT).show();
            return null;
        }
        return modalidadePagamento;
    }

    private void preencherModalidades(){

        String[] tipoModalidade = new String[] {"Selecionar Modalidade","mKesh","mPesa","e-Mola","Millennium IZI","BCI Ponto 24"};

        ArrayAdapter<String> adaptarTipoDocumento = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,tipoModalidade);
        adaptarTipoDocumento.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerServicos.setAdapter(adaptarTipoDocumento);
        spinnerServicos.setDropDownVerticalOffset(5);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DefinirPagamento.this, PainelVendedorPagamentos.class);
        intent.putExtras(bundle1);
        startActivity(intent);
    }
}
