package com.rademotions.mercadoinformal.Modelo;

import java.sql.Date;

/**
 * Created by Valet on 11/21/2018.
 */

public class Produto {

    private Integer id;
    private String nome;
    private double preco;
    private String descricao;
    private String categoria;
    private String tipoProduto;
    private String unidadeMassa;
    private Integer quantidade;
    private byte[] imagem;
    private Integer id_vendedor;
    private String dataPublicacao;
    private String disponibilidade;


    public Produto() {
    }

    public Produto(Integer id, String nome, double preco, String descricao, byte[] imagem, String disponibilidade, Integer id_vendedor) {
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.imagem = imagem;
        this.disponibilidade = disponibilidade;
        this.id_vendedor = id_vendedor;
        this.id = id;
    }

    public Produto(Integer id, String nome, double preco, String descricao, String unidadeMassa, Integer quantidade, byte[] imagem, Integer id_vendedor, String dataPublicacao, String disponibilidade) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.unidadeMassa = unidadeMassa;
        this.quantidade = quantidade;
        this.imagem = imagem;
        this.id_vendedor = id_vendedor;
        this.dataPublicacao = dataPublicacao;
        this.disponibilidade = disponibilidade;
    }

    public void setId_vendedor(Integer id_vendedor) {
        this.id_vendedor = id_vendedor;
    }

    public String getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(String dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public String getDiasponibilidade() {
        return disponibilidade;
    }

    public void setDiasponibilidade(String diasponibilidade) {
        disponibilidade = diasponibilidade;
    }

    public Integer getId_vendedor() {
        return id_vendedor;
    }

    public void setId_vendedor(int id_vendedor) {
        this.id_vendedor = id_vendedor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(String tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    public String getUnidadeMassa() {
        return unidadeMassa;
    }

    public void setUnidadeMassa(String unidadeMassa) {
        this.unidadeMassa = unidadeMassa;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", preco=" + preco +
                ", descricao='" + descricao + '\'' +
                ", categoria='" + categoria + '\'' +
                ", tipoProduto='" + tipoProduto + '\'' +
                ", unidadeMassa='" + unidadeMassa + '\'' +
                ", quantidade=" + quantidade +
                '}';
    }
}
