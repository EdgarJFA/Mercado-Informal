package com.rademotions.mercadoinformal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rademotions.mercadoinformal.Controle.BancoDados;
import com.rademotions.mercadoinformal.Modelo.Cliente;
import com.rademotions.mercadoinformal.visao.ListaCalcado;
import com.rademotions.mercadoinformal.visao.ListaEquipamento;
import com.rademotions.mercadoinformal.visao.ListaProdutoActivity;
import com.rademotions.mercadoinformal.visao.ListaProdutoMoveisActivity;
import com.rademotions.mercadoinformal.visao.ListaRoupa;
import com.rademotions.mercadoinformal.visao.ListaServico;
import com.rademotions.mercadoinformal.visao.ListaTecnologia;
import com.rademotions.mercadoinformal.visao.LoginActivity;
import com.rademotions.mercadoinformal.visao.PainelAdminActivity;
import com.rademotions.mercadoinformal.visao.PainelClienteActivity;

public class MercadoActivity extends AppCompatActivity {

    private LinearLayout btMoveis, btCalçados, btSobreNos, btAlimentacao, btServico, btRoupa, btEquipamento, btTecnologia;

    private Bundle bundle1;
    private Cliente cliente;
    private BancoDados mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mercado);

        btAlimentacao = (LinearLayout) findViewById(R.id.btMercado_Alimento);
        btMoveis = (LinearLayout) findViewById(R.id.btMercado_Moveis);
        btServico = (LinearLayout) findViewById(R.id.btMercado_Servico);
        btSobreNos = (LinearLayout) findViewById(R.id.btMercado_Ler_Mais);
        btCalçados = (LinearLayout) findViewById(R.id.btMercado_Calcados);
        btRoupa = (LinearLayout) findViewById(R.id.btMercado_Roupa);
        btEquipamento = (LinearLayout) findViewById(R.id.btMercado_Equipamentos);
        btTecnologia = (LinearLayout) findViewById(R.id.btMercado_Tecnologia);
        mDataBase = new BancoDados(getApplicationContext());

        cliente = new Cliente();
        Bundle bundle = getIntent().getExtras();
        cliente.setId(bundle.getInt("id_cliente"));

        bundle1 = new Bundle();
        bundle1.putInt("identificador", cliente.getId());

        btRoupa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MercadoActivity.this, ListaRoupa.class);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        btCalçados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MercadoActivity.this, ListaCalcado.class);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        btMoveis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MercadoActivity.this, ListaProdutoMoveisActivity.class);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        btAlimentacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MercadoActivity.this, ListaProdutoActivity.class);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        btServico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MercadoActivity.this, ListaServico.class);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        btTecnologia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MercadoActivity.this, ListaTecnologia.class);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        btEquipamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MercadoActivity.this, ListaEquipamento.class);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        btSobreNos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if(cliente.getId() == 0){
            Intent intent = new Intent(MercadoActivity.this, LoginActivity.class);
            intent.putExtras(bundle1);
            startActivity(intent);
        } else {
            cliente = mDataBase.retornarDadosCliente(cliente.getId());
            String nivelUsuario = mDataBase.verificarUsuario(String.valueOf(cliente.getTelefone()),cliente.getSenha());

            if (nivelUsuario.equals("A3")){
                Intent intent = new Intent(MercadoActivity.this, PainelAdminActivity.class);
                intent.putExtras(bundle1);
                startActivity(intent);
            } else if(nivelUsuario.equals("A1")){
                Intent intent = new Intent(MercadoActivity.this, PainelClienteActivity.class);
                intent.putExtras(bundle1);
                startActivity(intent);
            } else {
                Toast.makeText(MercadoActivity.this, "Nivel de usuário não encontrado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
