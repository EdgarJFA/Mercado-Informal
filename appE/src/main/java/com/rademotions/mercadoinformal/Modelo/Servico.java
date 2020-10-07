package com.rademotions.mercadoinformal.Modelo;

import java.util.Date;

/**
 * Created by Valet on 11/21/2018.
 */

public class Servico {

    private Integer id;
    private String nome;
    private double preco;
    private String descricao;
    private byte[] imagem;
    private String disponibilidade;
    private String data;
    private String categoria;
    private Integer idVendedor;

    public Servico() {
    }

    public Servico(int id, String nome, Double preco, String descricao,  byte[] imagem, String disponibilidade, Integer idVendedor) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.imagem = imagem;
        this.disponibilidade = disponibilidade;
        this.idVendedor = idVendedor;
    }

    public Servico(Integer id, String nome, double preco, String descricao, byte[] imagem, String disponibilidade, Integer idVendedor, String categoria, String data_publicacao) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.imagem = imagem;
        this.disponibilidade = disponibilidade;
        this.idVendedor = idVendedor;
        this.categoria = categoria;
        this.data = data_publicacao;
    }

    public String getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(String disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    public Integer getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(Integer idVendedor) {
        this.idVendedor = idVendedor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
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


    /*
    @Override
    public String toString() {
        return "Servico{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", preco=" + preco +
                ", descricao='" + descricao + '\'' +
                ", modalidadePagamento='" + modalidadePagamento + '\'' +
                ", modalidadePreco='" + modalidadePreco + '\'' +
                '}';
    }*/
}
