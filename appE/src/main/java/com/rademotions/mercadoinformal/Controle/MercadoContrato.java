package com.rademotions.mercadoinformal.Controle;

import android.provider.BaseColumns;

/**
 * Created by Valet on 10/29/2018.
 */

public final class MercadoContrato {

    private MercadoContrato(){

    }

    public final class tabelaUsuario implements BaseColumns {
        public static final String NOME_TABELA = "usuario";
        public static final String NOME_COLUNA = "nome";
        public static final String APELIDO_COLUNA = "apelido";
        public static final String EMAIL_COLUNA = "email";
        public static final String TELEFONE_COLUNA = "telefone";
        public static final String CIDADE_COLUNA = "cidade";
        public static final String SENHA_COLUNA = "senha";
        public static final String USUARIO_COLUNA = "tipo_usuario";
        public static final String BI_COLUNA = "bi";
        public static final String PROVINCIA_COLUNA = "provincia";
        public static final String MERCADO_COLUNA = "mercado";
        public static final String SEXO_COLUNA = "sexo";
        public static final String CATEGORIA_COLUNA = "categoria";
    }

    public final class tabelaCliente implements BaseColumns {
        public static final String NOME_TABELA = "cliente";
        public static final String NOME_COLUNA = "nome";
        public static final String APELIDO_COLUNA = "apelido";
        public static final String EMAIL_COLUNA = "email";
        public static final String TELEFONE_COLUNA = "telefone";
        public static final String CIDADE_COLUNA = "cidade";
        public static final String SENHA_COLUNA = "senha";
        public static final String USUARIO_COLUNA = "tipo_usuario";
    }

    public final class tabelaProduto implements BaseColumns {
        public static final String NOME_TABELA = "produto";
        public static final String NOME_COLUNA = "nome";
        public static final String PRECO_COLUNA = "preco";
        public static final String DESCRICAO_COLUNA = "descricao";
        public static final String CATEGORIA_COLUNA = "categoria";
        public static final String TIPO_COLUNA = "tipo_produto";
        public static final String UNIDADE_COLUNA = "unidade_massa";
        public static final String QUANTIDADE_COLUNA = "quantidade";
    }

    public final class tabelaServico implements BaseColumns {
        public static final String NOME_TABELA = "servico";
        public static final String NOME_COLUNA = "nome";
        public static final String PRECO_COLUNA = "preco";
        public static final String DESCRICAO_COLUNA = "descricao";
        public static final String MODALIDADE_PAGAMENTO_COLUNA = "modalidade_pagamento";
        public static final String MODALIDADE_PRECO_COLUNA = "modalidade_preco";
    }

    public final class tabelaMercado implements BaseColumns {
        public static final String NOME_TABELA = "mercado";
        public static final String NOME_COLUNA = "nome";
        public static final String CIDADE_COLUNA = "cidade";
        public static final String PROVINCIA_COLUNA = "provincia";
        public static final String LATITUDE_COLUNA = "latitude";
        public static final String LONGITUDE = "longitude";
    }
}
