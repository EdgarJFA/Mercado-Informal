package com.rademotions.mercadoinformal.visao;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.rademotions.mercadoinformal.Controle.BancoDados;
import com.rademotions.mercadoinformal.Modelo.Cliente;
import com.rademotions.mercadoinformal.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class PainelAdminRegistrarCategoria extends AppCompatActivity {

    private EditText txtNome;
    private ImageView icone;
    private Button btRegistrar, btCarregarImagem;
    private CalendarView data;
    final int SOLICITAR_CODIGO_GALERIA = 999;

    private Cliente cliente;
    private Bundle bundle1;
    private BancoDados mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painel_admin_registrar_categoria);

        //txtNome = (EditText) findViewById(R.id.editText_nome_categoria);
        //icone = (ImageView) findViewById(R.id.ImageView_icone_categoria);
        //btRegistrar = (Button) findViewById(R.id.button_registrar_categoria);
        //data = (CalendarView) findViewById(R.id.calendario_categoria_data);
       // btCarregarImagem = (Button) findViewById(R.id.button_carregar_icone_categoria);

        mDataBase = new BancoDados(getApplicationContext());
        cliente = new Cliente();
        Bundle bundle = getIntent().getExtras();

        cliente.setId(bundle.getInt("identificador"));
        bundle1 = new Bundle();

        bundle1.putInt("id_cliente",cliente.getId());

        btRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btCarregarImagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(PainelAdminRegistrarCategoria.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},SOLICITAR_CODIGO_GALERIA);
            }
        });
    }

    private byte[] imagemParaByte(ImageView imagem) {
        Bitmap bitmap = ((BitmapDrawable)imagem.getDrawable()).getBitmap();
        ByteArrayOutputStream fluxo = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fluxo);
        byte[] byteArray = fluxo.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == SOLICITAR_CODIGO_GALERIA){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent acao = new Intent(Intent.ACTION_PICK);
                acao.setType("image/*");
                startActivityForResult(acao, SOLICITAR_CODIGO_GALERIA);
            } else {
                Toast.makeText(getApplicationContext(), "Você não tem permissão para aceder a localização do ficheiro.", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == SOLICITAR_CODIGO_GALERIA && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();

            try {
                InputStream fluxoEntrada = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(fluxoEntrada);
                icone.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PainelAdminRegistrarCategoria.this, Painel_admin_categoria.class);
        intent.putExtras(bundle1);
        startActivity(intent);
    }
}
