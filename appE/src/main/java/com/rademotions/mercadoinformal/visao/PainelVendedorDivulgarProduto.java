package com.rademotions.mercadoinformal.visao;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.rademotions.mercadoinformal.Controle.BancoDados;
import com.rademotions.mercadoinformal.Modelo.Cliente;
import com.rademotions.mercadoinformal.Modelo.Produto;
import com.rademotions.mercadoinformal.Modelo.Vendedor;
import com.rademotions.mercadoinformal.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PainelVendedorDivulgarProduto extends AppCompatActivity {

    private EditText txtnomeProduto, txtprecoProduto, txtdescricaoProduto, txtquantidadeProduto;
    private RadioGroup rbgDisponibilidade, rbgUnidadeMassaProduto;
    private RadioButton rbtDisponivel, rbtEncomenda, rbtIndisponivel;
    private RadioButton rbtUnidadeKilograma, rbtUnidadeQuantidade;
    private Button btCarregarImagem, btRegistrarNovoProduto;
    private Spinner spinnerCategoria;
    private ImageView imagemProduto;
    private String disponibilidadeProduto, unidadeMassa, dateString;

    final int SOLICITAR_CODIGO_GALERIA = 999;
    Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;
    private Produto produto;
    private Vendedor vendedor;
    private Cliente cliente;
    private Bundle bundle1;
    private BancoDados mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painel_vendedor_divulgar_produto);

        imagemProduto = (ImageView) findViewById(R.id.ImageView_foto_produto);

        txtnomeProduto = (EditText) findViewById(R.id.editText_nome_produto);
        txtprecoProduto = (EditText) findViewById(R.id.editText_preco_produto);
        txtdescricaoProduto = (EditText) findViewById(R.id.editText_descricao_produto);
        txtquantidadeProduto = (EditText) findViewById(R.id.editText_quantidade_produto);
        spinnerCategoria = (Spinner) findViewById(R.id.Spinner_Categoria_produto);

        rbgDisponibilidade = (RadioGroup) findViewById(R.id.radioGrupo_disponibilidade_produto);
        rbgUnidadeMassaProduto = (RadioGroup) findViewById(R.id.radioGrupo_unidadeMassa_Produto);

        rbtDisponivel = (RadioButton) findViewById(R.id.radioButton_disponivel_produto);
        rbtIndisponivel = (RadioButton) findViewById(R.id.radioButton_Indisponivel_produto);
        rbtEncomenda = (RadioButton) findViewById(R.id.radioButton_disponivelEncomenda_produto);
        rbtUnidadeKilograma = (RadioButton) findViewById(R.id.radioButton_unidadeMassa_kilogramas);
        rbtUnidadeQuantidade = (RadioButton) findViewById(R.id.radioButton_unidadeMassa_quantidade);

        btRegistrarNovoProduto = (Button) findViewById(R.id.button_registrar_vendedor_Produto);
        btCarregarImagem = (Button) findViewById(R.id.button_carregar_imagem_Produto);
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

        rbtUnidadeQuantidade.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b == true){
                    txtquantidadeProduto.setVisibility(View.VISIBLE);
                }
                else{
                    txtquantidadeProduto.setVisibility(View.INVISIBLE);
                }
            }
        });

        btCarregarImagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*ActivityCompat.requestPermissions(PainelVendedorDivulgarProduto.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},SOLICITAR_CODIGO_GALERIA);*/

                selectImage();
            }
        });

        btRegistrarNovoProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                vendedor = new Vendedor();

                Produto registrarProduto = getDadosProdutosFormulario();
                //registrarProduto.setImagem(imagemParaByte(imagemProduto));
                registrarProduto.setDataPublicacao(dateString);
                Integer idVendedor = mDataBase.retornarIdVendedor(cliente.getId());

                if(registrarProduto != null){

                    long resultProduto = mDataBase.registrarProduto(registrarProduto,idVendedor,imagemParaByte(imagemProduto));

                    if(resultProduto > 0){
                        Intent intent = new Intent(PainelVendedorDivulgarProduto.this, PainelVendedorDivulgar.class);
                        intent.putExtras(bundle1);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "Produto Registrado", Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), "Id do vendedor = " + idVendedor + " Data " + dateString, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Produto não Registrado", Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), "Falha **** Id do vendedor = " + idVendedor + " Data " + dateString, Toast.LENGTH_LONG).show();
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

    private void selectImage(){
        final CharSequence [] itens = {"Camera","Galeria","Cancelar"};

        AlertDialog.Builder selecionarFoto = new AlertDialog.Builder(PainelVendedorDivulgarProduto.this);
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
                    //startActivityForResult(imagem.createChooser(imagem, "selecionar ficheiro"), SELECT_FILE);
                    startActivityForResult(imagem, SELECT_FILE);
                }else{
                    dialogInterface.dismiss();
                }
            }
        });

        selecionarFoto.show();
    }

    /*
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == SOLICITAR_CODIGO_GALERIA){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){


                //Intent acao = new Intent(Intent.ACTION_PICK);
                //acao.setType("image/*");
                //startActivityForResult(acao, SOLICITAR_CODIGO_GALERIA);
            } else {
                Toast.makeText(getApplicationContext(), "Você não tem permissão para aceder a localização do ficheiro.", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode,resultCode,data);

        if(resultCode == Activity.RESULT_OK){

            if(requestCode == REQUEST_CAMERA){

                //Bundle bundle = data.getExtras();
                final Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imagemProduto.setImageBitmap(bitmap);
                //imagemProduto.setMaxHeight(500);

            }else if(requestCode == SELECT_FILE){
                Uri uri = data.getData();
                //imagemProduto.setImageURI(uri);

                try {
                    InputStream fluxoEntrada = getContentResolver().openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(fluxoEntrada);
                    imagemProduto.setImageBitmap(bitmap);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                /*
                try {
                    InputStream fluxoEntrada = getContentResolver().openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(fluxoEntrada);
                    imagemProduto.setImageBitmap(bitmap);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }*/
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private Produto getDadosProdutosFormulario(){

        produto = new Produto();

        if(this.txtnomeProduto.getText().toString().isEmpty() == false){
            produto.setNome(this.txtnomeProduto.getText().toString());
        } else {
            Toast.makeText(getApplicationContext(), "Informe um nome para o produto", Toast.LENGTH_SHORT).show();
            return null;
        }
        if(this.txtprecoProduto.getText().toString().isEmpty() == false){
            Double precoProduto = Double.parseDouble(this.txtprecoProduto.getText().toString());
            produto.setPreco(precoProduto);
        } else {
            Toast.makeText(getApplicationContext(), "O preço do Produto é obrigatório", Toast.LENGTH_SHORT).show();
            return null;
        }
        if(this.txtdescricaoProduto.getText().toString().isEmpty() == false){
            produto.setDescricao(this.txtdescricaoProduto.getText().toString());
        } else {
            Toast.makeText(getApplicationContext(), "A descrição do Produto é obrigatória", Toast.LENGTH_SHORT).show();
            return null;
        }
        if(this.spinnerCategoria.getSelectedItem().toString().equals("Selecionar Categoria")){
            Toast.makeText(getApplicationContext(), "Selecione uma categoria para o produto", Toast.LENGTH_SHORT).show();
            return null;
        } else {
            produto.setCategoria(this.spinnerCategoria.getSelectedItem().toString());
        }
        if(this.txtquantidadeProduto.getText().toString().isEmpty() == false){
            Integer quantidadeProduto = Integer.parseInt(this.txtquantidadeProduto.getText().toString());
            produto.setQuantidade(quantidadeProduto);
        } else {
            int quantidadeProduto = 0;
            produto.setQuantidade(quantidadeProduto);
        }

        if(this.rbtDisponivel.isChecked()){
            disponibilidadeProduto = this.rbtDisponivel.getText().toString();
            produto.setDiasponibilidade(disponibilidadeProduto);
        } else if(this.rbtEncomenda.isChecked()){
            disponibilidadeProduto = this.rbtEncomenda.getText().toString();
            produto.setDiasponibilidade(disponibilidadeProduto);
        } else if(this.rbtIndisponivel.isChecked()){
            disponibilidadeProduto = this.rbtIndisponivel.getText().toString();
            produto.setDiasponibilidade(disponibilidadeProduto);
        } else {
            Toast.makeText(getApplicationContext(), "Selecione a disponibilidade do produto", Toast.LENGTH_SHORT).show();
            return null;
        }

        if(this.rbtUnidadeKilograma.isChecked()){
            unidadeMassa = this.rbtUnidadeKilograma.getText().toString();
            produto.setUnidadeMassa(unidadeMassa);
        } else if(this.rbtUnidadeQuantidade.isChecked()){
            unidadeMassa = this.rbtUnidadeQuantidade.getText().toString();
            produto.setUnidadeMassa(unidadeMassa);
        } else {
            Toast.makeText(getApplicationContext(), "Selecione a unidade de massa do produto", Toast.LENGTH_SHORT).show();
            return null;
        }

        return produto;
    }

    private void preencherCategorias(){

        String[] tipoCategoria = new String[] {"Selecionar Categoria","Alimentação","Calçados","Equipamentos","Movéis","Roupa","Tecnologia","Outro"};

        ArrayAdapter<String> adaptarTipoDocumento = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,tipoCategoria);
        adaptarTipoDocumento.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adaptarTipoDocumento);
        spinnerCategoria.setDropDownVerticalOffset(5);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PainelVendedorDivulgarProduto.this, PainelVendedorDivulgar.class);
        intent.putExtras(bundle1);
        startActivity(intent);
    }
}
