package com.rademotions.mercadoinformal.visao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rademotions.mercadoinformal.Controle.BancoDados;
import com.rademotions.mercadoinformal.Modelo.Cliente;
import com.rademotions.mercadoinformal.R;

public class TelaClienteAssistencia extends AppCompatActivity {

    private Bundle bundle1;

    private Cliente cliente;
    private BancoDados mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cliente_assistencia);

        cliente = new Cliente();
        Bundle bundle = getIntent().getExtras();

        cliente.setId(bundle.getInt("id_cliente"));
        bundle1 = new Bundle();
        bundle1.putInt("identificador",cliente.getId());
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TelaClienteAssistencia.this, PainelClienteActivity.class);
        intent.putExtras(bundle1);
        startActivity(intent);
    }
}
