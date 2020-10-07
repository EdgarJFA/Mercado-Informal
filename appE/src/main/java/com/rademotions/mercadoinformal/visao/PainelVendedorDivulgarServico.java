package com.rademotions.mercadoinformal.visao;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.rademotions.mercadoinformal.Controle.BancoDados;
import com.rademotions.mercadoinformal.Modelo.Cliente;
import com.rademotions.mercadoinformal.Modelo.Servico;
import com.rademotions.mercadoinformal.Modelo.Vendedor;
import com.rademotions.mercadoinformal.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

public class PainelVendedorDivulgarServico extends AppCompatActivity {

    EditText txtNomeServico, txtPrecoServico, txtDescricaoServico;
    RadioGroup rbgDisponibilidade;
    RadioButton rbtDisponivel, rbtIndisponivel;
    Button btRegistrarServico, btCarregarImagem;
    private Spinner spinnerCategoria;
    String disponibilidade, dateString;
    ImageView fotoServico;

    Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;
    private Servico servico;
    private Cliente cliente;
    private Vendedor vendedor;
    private Bundle bundle1;
    private BancoDados mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painel_vendedor_divulgar_servico);

        txtNomeServico = (EditText) findViewById(R.id.editText_nome_servico);
        txtPrecoServico = (EditText) findViewById(R.id.editText_preco_servico);
        txtDescricaoServico = (EditText) findViewById(R.id.editText_descricao_servico);
        spinnerCategoria = (Spinner) findViewById(R.id.Spinner_Categoria_Servico);
        rbgDisponibilidade = (RadioGroup) findViewById(R.id.radioGrupo_disponibilidade_servico);
        rbtDisponivel = (RadioButton) findViewById(R.id.radioButton_disponivel_servico);
        rbtIndisponivel = (RadioButton) findViewById(R.id.radioButton_Indisponivel_servico);
        btRegistrarServico = (Button) findViewById(R.id.button_registrar_vendedor_servico);
        fotoServico = (ImageView) findViewById(R.id.ImageView_foto_servico);
        btCarregarImagem = (Button) findViewById(R.id.button_carregar_imagem_Produto);

        //Conexção Banco de dados
        mDataBase = new BancoDados(getApplicationContext());

        cliente = new Cliente();
        Bundle bundle = getIntent().getExtras();

        cliente.setId(bundle.getInt("identificador"));
        bundle1 = new Bundle();
        bundle1.putInt("id_cliente",cliente.getId());

        preencherCategorias();
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        dateString = sdf.format(date);

        btCarregarImagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        btRegistrarServico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                vendedor = new Vendedor();

                Servico registrarServico = getDadosServicoFormulario();
                registrarServico.setData(dateString);
                Integer idVendedor = mDataBase.retornarIdVendedor(cliente.getId());

                if(registrarServico != null){

                    long resultServico = mDataBase.registrarServico(registrarServico,idVendedor,imagemParaByte(fotoServico));

                    if(resultServico > 0){
                        Intent intent = new Intent(PainelVendedorDivulgarServico.this, PainelVendedorDivulgar.class);
                        intent.putExtras(bundle1);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "Serviço Registrado", Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), "Id do vendedor = " + idVendedor, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Serviço não Registrado", Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), "falha ****** Id do vendedor = " + idVendedor, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

    public byte[] imagemParaByte(ImageView imagem) {
        Bitmap bitmap = ((BitmapDrawable)imagem.getDrawable()).getBitmap();
        ByteArrayOutputStream fluxo = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fluxo);
        byte[] byteArray = fluxo.toByteArray();
        return byteArray;
    }

    private Servico getDadosServicoFormulario(){
        servico = new Servico();

        if(this.txtNomeServico.getText().toString().isEmpty() == false){
            servico.setNome(this.txtNomeServico.getText().toString());
        } else {
            Toast.makeText(getApplicationContext(), "Informe um nome para o serviço", Toast.LENGTH_SHORT).show();
            return null;
        }
        if(this.txtPrecoServico.getText().toString().isEmpty() == false){
            Double precoServico = Double.parseDouble(this.txtPrecoServico.getText().toString());
            servico.setPreco(precoServico);
        } else {
            Toast.makeText(getApplicationContext(), "O preço do Serviço é obrigatório", Toast.LENGTH_SHORT).show();
            return null;
        }
        if(this.txtDescricaoServico.getText().toString().isEmpty() == false){
            servico.setDescricao(this.txtDescricaoServico.getText().toString());
        } else {
            Toast.makeText(getApplicationContext(), "A descrição do serviço é obrigatória", Toast.LENGTH_SHORT).show();
            return null;
        }

        if(this.rbtDisponivel.isChecked()){
            disponibilidade = this.rbtDisponivel.getText().toString();
            servico.setDisponibilidade(disponibilidade);
        } else if(this.rbtIndisponivel.isChecked()){
            disponibilidade = this.rbtIndisponivel.getText().toString();
            servico.setDisponibilidade(disponibilidade);
        } else {
            Toast.makeText(getApplicationContext(), "Selecione a disponibilidade atual do serviço", Toast.LENGTH_SHORT).show();
            return null;
        }
        if(this.spinnerCategoria.getSelectedItem().toString().equals("Selecionar Categoria")){
            Toast.makeText(getApplicationContext(), "Selecione uma categoria para o Serviço", Toast.LENGTH_SHORT).show();
            return null;
        } else {
            servico.setCategoria(this.spinnerCategoria.getSelectedItem().toString());
        }

        return servico;
    }

    private void selectImage(){
        final CharSequence [] itens = {"Camera","Galeria","Cancelar"};

        AlertDialog.Builder selecionarFoto = new AlertDialog.Builder(PainelVendedorDivulgarServico.this);
        selecionarFoto.setTitle("Selecionar Imagem");

        selecionarFoto.setItems(itens, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(itens[i].equals("Camera")){
                    Intent imagem = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(imagem, REQUEST_CAMERA);
                }else if (itens[i].equals("Galeria")){
                    Intent imagem = new Intent(Intent.ACTION_PICK);
                    imagem.setType("image/*");
                    startActivityForResult(imagem, SELECT_FILE);
                }else{
                    dialogInterface.dismiss();
                }
            }
        });

        selecionarFoto.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == REQUEST_CAMERA){
                final Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                fotoServico.setImageBitmap(bitmap);
            }else if(requestCode == SELECT_FILE){
                Uri uri = data.getData();
                try {
                    InputStream fluxoEntrada = getContentResolver().openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(fluxoEntrada);
                    fotoServico.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void preencherCategorias() {

        String[] tipoCategoria = new String[]{"Selecionar Categoria", "Alimentação", "Calçados", "Equipamentos", "Movéis", "Roupa", "Tecnologia", "Outro Produto"};

        ArrayAdapter<String> adaptarTipoDocumento = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipoCategoria);
        adaptarTipoDocumento.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adaptarTipoDocumento);
        spinnerCategoria.setDropDownVerticalOffset(5);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PainelVendedorDivulgarServico.this, PainelVendedorDivulgar.class);
        intent.putExtras(bundle1);
        startActivity(intent);
    }
}
