package com.rademotions.mercadoinformal.visao;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rademotions.mercadoinformal.Controle.BancoDados;
import com.rademotions.mercadoinformal.Modelo.Cliente;
import com.rademotions.mercadoinformal.Modelo.Desejo;
import com.rademotions.mercadoinformal.Modelo.Mercado;
import com.rademotions.mercadoinformal.Modelo.Produto;
import com.rademotions.mercadoinformal.Modelo.Servico;
import com.rademotions.mercadoinformal.Modelo.Vendedor;
import com.rademotions.mercadoinformal.R;

public class TelaCompra extends AppCompatActivity {

    Dialog dialogo, zoomImagem, criarConta;
    TextView txtNomeItem, txtPrecoItem, txtDescricaoItem, txtDisponibilidade, txtAvaliacaoResultado;
    TextView txtVendedor, txtContato, txtMercado, txtFormaPagamento;
    CheckedTextView txtDesejo;
    RatingBar baraAvaliacao;
    ImageView imgItemCompra, iconeDisponibilidade, iconeListaDesejo, sms, email;
    Integer idItem, idCliente, idVendedor, idMercado, idComprador;
    LinearLayout btContactoVendedor;
    byte[] imagemAmpliada;
    Button btComprar;
    Produto produto;

    private Cliente cliente;
    private Vendedor vendedor;
    private Mercado mercado;
    private Desejo desejo; //acrescentado
    private BancoDados mDataBase;
    private Bundle bundle1;
    private String lista, emailVendedor;
    public  static final int REQUEST_CALL = 1;
    public  static final int REQUEST_SMS = 1;
    private int requisicao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_compra);

        txtNomeItem = (TextView) findViewById(R.id.label_nome_item);
        txtPrecoItem = (TextView) findViewById(R.id.label_preco_item);
        txtDescricaoItem = (TextView) findViewById(R.id.label_descricao_item);
        txtDisponibilidade = (TextView) findViewById(R.id.label_disponibilidade_item);
        txtAvaliacaoResultado = (TextView) findViewById(R.id.label_avaliacao_item_resultado);
        txtVendedor = (TextView) findViewById(R.id.txtViewTelaCompra_nomeVendedor);
        txtContato = (TextView) findViewById(R.id.txtViewTelaCompra_contatoVendedor);
        txtMercado = (TextView) findViewById(R.id.txtViewTelaCompra_mercadoVendedor);
        txtFormaPagamento = (TextView) findViewById(R.id.txtViewTelaCompra_disponibilidade_pagamento);
        txtDesejo = (CheckedTextView) findViewById(R.id.label_lista_desejo_item);
        baraAvaliacao = (RatingBar) findViewById(R.id.label_avaliacao_item);
        imgItemCompra = (ImageView) findViewById(R.id.ImageView_compra_item);
        sms = (ImageView) findViewById(R.id.ImageView_compra_sms);
        email = (ImageView) findViewById(R.id.ImageView_compra_email);
        iconeDisponibilidade = (ImageView) findViewById(R.id.label_disponibilidade_bem_estado);
        iconeListaDesejo = (ImageView) findViewById(R.id.icone_Desejo_lista);
        btContactoVendedor = (LinearLayout) findViewById(R.id.label_contacto_vendedor);
        btComprar = (Button) findViewById(R.id.bt_comprar_item);

        dialogo = new Dialog(this);
        zoomImagem = new Dialog(this);
        criarConta = new Dialog(this);
        produto = new Produto();
        desejo = new Desejo();

        mDataBase = new BancoDados(getApplicationContext());
        cliente = new Cliente();
        vendedor = new Vendedor();
        mercado = new Mercado();
        bundle1 = new Bundle();

        Bundle bundle = getIntent().getExtras();
        idItem = bundle.getInt("id_item");
        txtNomeItem.setText(bundle.getString("nome_item"));
        txtDescricaoItem.setText(bundle.getString("descricao_item"));
        txtPrecoItem.setText(String.valueOf(bundle.getDouble("preco_item")));
        idVendedor = bundle.getInt("id_vendedor");
        idComprador = bundle.getInt("id_cliente");
        lista = bundle.getString("lista");

        bundle1.putInt("identificador", idComprador);

        vendedor.setId(idVendedor);
        vendedor = mDataBase.retornarDadosVendedorCompra(vendedor.getId());
        cliente = mDataBase.retornarDadosCliente(vendedor.getId_cliente());
        mercado = mDataBase.retornarDadosMercado(vendedor.getId_mercado());

        byte[] imagemItem = bundle.getByteArray("imagem");
        imagemAmpliada = bundle.getByteArray("imagem");
        Bitmap bitmap = BitmapFactory.decodeByteArray(imagemItem, 0, imagemItem.length);
        imgItemCompra.setImageBitmap(bitmap);
        txtDisponibilidade.setText(bundle.getString("disponibilidade"));

        if(cliente.getEmail() == null){
            email.setVisibility(View.INVISIBLE);
        } else {
            emailVendedor = cliente.getEmail();
        }

        if(txtDisponibilidade.getText().equals("Disponivél por Encomenda")){
            iconeDisponibilidade.setColorFilter(Color.YELLOW);
        }else if(txtDisponibilidade.getText().equals("Indisponivél")){
            iconeDisponibilidade.setColorFilter(Color.RED);
        } else {
            iconeDisponibilidade.setColorFilter(Color.GREEN);
        }


        txtVendedor.setText("Divulgado por: " + cliente.getNome());
        txtContato.setText(String.valueOf(cliente.getTelefone()));
        txtMercado.setText("Mercado " + mercado.getNome());

        baraAvaliacao.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                txtAvaliacaoResultado.setVisibility(View.VISIBLE);
            }
        });

        txtDesejo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(idComprador == 0){
                    showPopUpCriarConta();
                } else {
                    if(txtDesejo.getText().equals("Adicionar a lista de desejos")){
                        desejo.setIdCliente(idComprador);
                        desejo.setIdProduto(idItem);

                        long resultProduto = mDataBase.listarfavoritos(desejo);

                        if(resultProduto > 0){
                            txtDesejo.setText("Remover da lista de desejos");
                            iconeListaDesejo.setColorFilter(Color.RED);
                            Toast.makeText(getApplicationContext(), "Item adicionado a lista aos favoritos", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Item não adicionado", Toast.LENGTH_LONG).show();
                        }

                    } else if (txtDesejo.getText().equals("Remover da lista de desejos")){
                        txtDesejo.setText("Adicionar a lista de desejos");
                        iconeListaDesejo.setColorFilter(Color.BLACK);
                    } else {
                        txtDesejo.setText("Adicionar a lista de desejos");
                        iconeListaDesejo.setColorFilter(Color.BLACK);
                    }
                }
            }
        });

        btComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(idComprador == 0){
                    showPopUpCriarConta();
                } else {
                    showPopUp();
                }
            }
        });

        btContactoVendedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requisicao = 1;
                fazerChamada();
            }
        });

        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requisicao = 2;
                enviarSMS();
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarEmail();
            }
        });

    }

    private void enviarEmail(){
        String email = emailVendedor;
        String[] receptor = email.split(",");


        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, receptor);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Escolher um Email"));

    }

    private void fazerChamada(){
        String numero = txtContato.getText().toString();
        if(numero.trim().length() > 0){
            if(ContextCompat.checkSelfPermission(TelaCompra.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(TelaCompra.this, new String[] {Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + numero;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }
    }

    private void enviarSMS(){
        String numero = txtContato.getText().toString();
            if(ContextCompat.checkSelfPermission(TelaCompra.this, Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(TelaCompra.this, new String[] {Manifest.permission.SEND_SMS}, REQUEST_SMS);
            } else {
                //String dial = "tel:" + numero;
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms",numero , null )));
                ActivityCompat.requestPermissions(TelaCompra.this, new String[] {Manifest.permission.SEND_SMS}, REQUEST_SMS);
            }
    }

    public void showPopUp(){
        ImageView btFechar;
        final Button btContinuar;
        ImageView btMkesh, btMpesa, btEmola, btBimIzi, btBci24;

        dialogo.setContentView(R.layout.tela_pop_up_compra);
        btFechar = (ImageView) dialogo.findViewById(R.id.txtPopUp_fecharCompra);
        btMkesh = (ImageView) dialogo.findViewById(R.id.bt_Canal_mKesh);
        btContinuar = (Button) dialogo.findViewById(R.id.btPopUp_continuarCompra);

        btFechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogo.dismiss();
            }
        });

        btMkesh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btContinuar.setEnabled(true);
                //btMkesh.setImageDrawable(getDrawable(R.drawable.ic_weekend_black_24dp));
                //mMap.addMarker(new MarkerOptions().position(posicaoMercado).title("Mercado " +mercados[m].getNome()).icon(BitmapDescriptorFactory.fromResource(R.drawable.mi)));

            }
        });
        
        btContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TelaCompra.this, "Compra efetivada com Sucesso", Toast.LENGTH_SHORT).show();
            }
        });

        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.show();
    }

    public void showImageZoom(View v){
        ImageView zoom;

        zoomImagem.setContentView(R.layout.tela_pop_up_zoom_imagem);
        zoom = (ImageView) zoomImagem.findViewById(R.id.ImageView_Zoom_imagem_compra);

        Bitmap bitmap = BitmapFactory.decodeByteArray(imagemAmpliada, 0, imagemAmpliada.length);
        zoom.setImageBitmap(bitmap);

        zoomImagem.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        zoomImagem.show();
    }

    public void showPopUpCriarConta(){
        ImageView btFechar;
        final Button btCriarConta;
        TextView btEntrar;

        criarConta.setContentView(R.layout.tela_pop_up_registrar_conta);
        btFechar = (ImageView) criarConta.findViewById(R.id.button_tela_comprar_fechar);
        btCriarConta = (Button) criarConta.findViewById(R.id.button_tela_comprar_CriarConta);
        btEntrar = (TextView) criarConta.findViewById(R.id.button_tela_comprar_Entrar);

        btFechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                criarConta.dismiss();
            }
        });

        btCriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TelaCompra.this, CadastroClienteActivity.class);
                startActivity(intent);
            }
        });

        btEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TelaCompra.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        criarConta.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        criarConta.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requisicao == 1){
            if(requestCode == REQUEST_CALL){
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    fazerChamada();
                } else {
                    Toast.makeText(TelaCompra.this, "Permissão Negada", Toast.LENGTH_SHORT).show();
                }
            }
        } else if(requisicao == 2){
            if(requestCode == REQUEST_SMS){
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    enviarSMS();
                } else {
                    Toast.makeText(TelaCompra.this, "Permissão Negada", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(lista.equals("Roupa")){
            Intent intent = new Intent(TelaCompra.this, ListaRoupa.class);
            intent.putExtras(bundle1);
            startActivity(intent);
        } else if(lista.equals("Calçado")){
            Intent intent = new Intent(TelaCompra.this, ListaCalcado.class);
            intent.putExtras(bundle1);
            startActivity(intent);
        } else if(lista.equals("Equipamento")){
            Intent intent = new Intent(TelaCompra.this, ListaEquipamento.class);
            intent.putExtras(bundle1);
            startActivity(intent);
        } else if(lista.equals("Serviço")){
            Intent intent = new Intent(TelaCompra.this, ListaServico.class);
            intent.putExtras(bundle1);
            startActivity(intent);
        } else if(lista.equals("Tecnologia")){
            Intent intent = new Intent(TelaCompra.this, ListaTecnologia.class);
            intent.putExtras(bundle1);
            startActivity(intent);
        } else if(lista.equals("Alimentação")){
            Intent intent = new Intent(TelaCompra.this, ListaProdutoActivity.class);
            intent.putExtras(bundle1);
            startActivity(intent);
        } else if(lista.equals("Moveis")){
            Intent intent = new Intent(TelaCompra.this, ListaProdutoMoveisActivity.class);
            intent.putExtras(bundle1);
            startActivity(intent);
        } else {

        }


    }

    /*private void retornarDadosAdicionais( int identificador, BancoDados banco){

        //retificar o select por causa da condição do retorno: deve retornar somente os serviços publicados por um determinado vendedor
        Cursor cursor = banco.retornarListaDados("select c.nome,c.telefone,m.nome FROM cliente c inner  join vendedor v inner join mercado m where c._id = v." + identificador+" and v.id_mercado = m._id");

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String nome = cursor.getString(1);
            Double preco = cursor.getDouble(2);
            String descricao = cursor.getString(3);
            byte[] imagem = cursor.getBlob(4);
            String disponibilidade = cursor.getString(6);
        }
    }*/
}
