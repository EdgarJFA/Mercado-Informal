package com.rademotions.mercadoinformal.visao;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.LineRadarDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.rademotions.mercadoinformal.Controle.BancoDados;
import com.rademotions.mercadoinformal.Modelo.Cliente;
import com.rademotions.mercadoinformal.R;

import java.util.ArrayList;

public class PainelAdminEstatistica extends AppCompatActivity {

    //PieChart estatisticas;
    Dialog estUtilizador, estMercado, estPublicacao;
    LinearLayout btEstUtilizador, btEstDivulgacao, btEstMercado, btEstTransacao, btEstCompras, btEstFavoritos;

    String [] nivelUtilizadores = new String[]{"Vendedor","Administrador","Consumidor"};
    //String [] artigosPublicados = new String[]{"Produtos","Serviços"};
    int [] cores = new int[]{Color.rgb(0,255,255), Color.rgb(255,160,122), Color.rgb(240,230,140)};
    int nrVendedores, nrAdminstradores, nrClientes;

    private Cliente cliente;
    private BancoDados mDataBase;
    private Bundle bundle1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painel_admin_estatistica);

        btEstUtilizador = (LinearLayout) findViewById(R.id.btEstatistica_Utilizidores);
        btEstDivulgacao = (LinearLayout) findViewById(R.id.btEstatistica_Divulgacoes);
        btEstMercado = (LinearLayout) findViewById(R.id.btEstatistica_Mercados);

        mDataBase = new BancoDados(getApplicationContext());

        estUtilizador = new Dialog(this);
        estMercado = new Dialog(this);
        estPublicacao = new Dialog(this);

        cliente = new Cliente();
        Bundle bundle = getIntent().getExtras();

        cliente.setId(bundle.getInt("id_cliente"));
        bundle1 = new Bundle();
        bundle1.putInt("identificador",cliente.getId());

        btEstUtilizador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gerarGraficoUsuarios();
            }
        });

        btEstDivulgacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gerarGraficoDivulgacoes();
            }
        });

        btEstMercado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //gerarGraficoMercados();
            }
        });

        //gerarGraficoUsuarios();
        //retornarDivulgacoes();
    }

    public void gerarGraficoUsuarios(){

        PieChart estatisticas;
        TextView txtUtilizadores;

        estUtilizador.setContentView(R.layout.tela_pop_up_estatisticas_utilizadores);

        estatisticas = (PieChart) estUtilizador.findViewById(R.id.painel_admin_estatistica_Grafico_Usuarios);
        txtUtilizadores = (TextView) estUtilizador.findViewById(R.id.estatistica_total_usuarios);

        Description descricao = new Description();
        descricao.setText("");
        //descricao.setText("Nº Total de Utilizadores da aplicação");

        estatisticas.setDescription(descricao);
        ArrayList<PieEntry> dadosUtilizadores = new ArrayList<>();

        retornarUtilizadores(txtUtilizadores);
        dadosUtilizadores.add(new PieEntry(nrVendedores,nrVendedores));
        dadosUtilizadores.add(new PieEntry(nrAdminstradores,nrAdminstradores));
        dadosUtilizadores.add(new PieEntry(nrClientes,nrClientes));

        Legend legend = estatisticas.getLegend();
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

        ArrayList<LegendEntry> legengaUtilizadores = new ArrayList<>();
        for(int i=0; i<nivelUtilizadores.length; i++){
            LegendEntry legendEntry = new LegendEntry();
            legendEntry.label = nivelUtilizadores[i];
            legendEntry.formColor = cores[i];
            legengaUtilizadores.add(legendEntry);
        }
        legend.setCustom(legengaUtilizadores);

        PieDataSet pieDataSet = new PieDataSet(dadosUtilizadores,"");
        pieDataSet.setColors(cores);
        pieDataSet.setValueTextSize(15);

        PieData pieData = new PieData(pieDataSet);
        estatisticas.setData(pieData);

        estUtilizador.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        estUtilizador.show();
    }

    public void retornarUtilizadores(TextView txtUtilizadores){
        Cursor cursor = mDataBase.retornarListaDados("select * FROM cliente where tipo_usuario = 'A2'");
        nrVendedores = cursor.getCount();
        cursor = mDataBase.retornarListaDados("select * FROM cliente  where tipo_usuario = 'A3'");
        nrAdminstradores = cursor.getCount();
        cursor = mDataBase.retornarListaDados("select * FROM cliente  where tipo_usuario = 'A1'");
        nrClientes = cursor.getCount();
        int soma = nrVendedores + nrAdminstradores + nrClientes;
        txtUtilizadores.setText("Nº Total de Utilizadores: " + soma);
    }

    public void gerarGraficoDivulgacoes(){

        BarChart estatisticasDivulgacoes;
        TextView txtPublicacoes;
        String [] artigosPublicados = new String[]{"Produtos","Serviços"};
        int [] cores = new int[]{Color.rgb(255,99,71), Color.rgb(186,85,211)};

        estPublicacao.setContentView(R.layout.tela_pop_up_estatisticas_divulgacoes);

        estatisticasDivulgacoes = (BarChart) estPublicacao.findViewById(R.id.painel_admin_estatistica_Grafico_divulgacoes);
        txtPublicacoes = (TextView) estPublicacao.findViewById(R.id.estatistica_total_divulgacoes);

        Cursor cursor = mDataBase.retornarListaDados("select * FROM produto");
        int nrProdutos = cursor.getCount();
        cursor = mDataBase.retornarListaDados("select * FROM servico");
        int nrServicos = cursor.getCount();
        int soma = nrProdutos + nrServicos;
        txtPublicacoes.setText("Nº Total de Publicações: " + soma);

        Description descricao = new Description();
        descricao.setText("");
        //descricao.setText("Nº Total de Utilizadores da aplicação");

        estatisticasDivulgacoes.setDescription(descricao);

        ArrayList<BarEntry> dadosPublicacoes = new ArrayList<>();

        dadosPublicacoes.add(new BarEntry(1,nrProdutos));
        dadosPublicacoes.add(new BarEntry(2,nrServicos));

        Legend legend = estatisticasDivulgacoes.getLegend();
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

        ArrayList<LegendEntry> legengaUtilizadores = new ArrayList<>();
        for(int i=0; i<artigosPublicados.length; i++){
            LegendEntry legendEntry = new LegendEntry();
            legendEntry.label = artigosPublicados[i];
            legendEntry.formColor = cores[i];
            legengaUtilizadores.add(legendEntry);
        }
        legend.setCustom(legengaUtilizadores);

        BarDataSet barDataSet = new BarDataSet(dadosPublicacoes,"");
        barDataSet.setColors(cores);
        barDataSet.setValueTextSize(15);

        BarData barData = new BarData(barDataSet);
        estatisticasDivulgacoes.setData(barData);

        estPublicacao.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        estPublicacao.show();
    }

    public void gerarGraficoMercados(){

        LineChart estatisticasMercados;
        TextView txtMercados;
        String [] Mercados = new String[]{"Mercados"};
        int [] cores = new int[]{Color.rgb(127,255,0)};

        estMercado.setContentView(R.layout.tela_pop_up_estatisticas_divulgacoes);

        estatisticasMercados = (LineChart) estMercado.findViewById(R.id.painel_admin_estatistica_Grafico_Mercados);
        txtMercados = (TextView) estMercado.findViewById(R.id.estatistica_total_mercados);

        Cursor cursor = mDataBase.retornarListaDados("select * FROM mercados");
        int nrMercados = cursor.getCount();
        txtMercados.setText("Nº Total de Mercados: " + nrMercados);

        Description descricao = new Description();
        descricao.setText("");
        //descricao.setText("Nº Total de Utilizadores da aplicação");

        estatisticasMercados.setDescription(descricao);

        ArrayList<LineRadarDataSet> dadosMercados = new ArrayList<>();

        /*dadosMercados.add(new BarEntry(1,nrProdutos));
        dadosMercados.add(new BarEntry(2,nrServicos));*/

        Legend legend = estatisticasMercados.getLegend();
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

        ArrayList<LegendEntry> legengaMercados = new ArrayList<>();
        for(int i=0; i<Mercados.length; i++){
            LegendEntry legendEntry = new LegendEntry();
            legendEntry.label = Mercados[i];
            legendEntry.formColor = cores[i];
            legengaMercados.add(legendEntry);
        }
        legend.setCustom(legengaMercados);

       /*/LineDataSet lineDataSet = new LineDataSet(dadosMercados);
        lineDataSet.setColors(cores);
        lineDataSet.setValueTextSize(15);

        LineData lineData = new LineData(lineDataSet);
        estatisticasMercados.setData(lineData);*/

        estMercado.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        estMercado.show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PainelAdminEstatistica.this, PainelAdminActivity.class);
        intent.putExtras(bundle1);
        startActivity(intent);
    }
}
