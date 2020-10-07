package com.rademotions.mercadoinformal.Modelo;


import java.sql.Date;

/**
 * Created by Valet on 7/3/2019.
 */

public class ModalidadePagamento {
    private Integer id;
    private String nome;
    private String data;
    private Integer telefone;
    private Integer idVendedor;

    public ModalidadePagamento(){

    }

    public ModalidadePagamento(Integer id, String nome, String data,Integer telefone, Integer idVendedor) {
        this.id = id;
        this.nome = nome;
        this.data = data;
        this.telefone = telefone;
        this.idVendedor = idVendedor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(Integer idVendedor) {
        this.idVendedor = idVendedor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getTelefone() {
        return telefone;
    }

    public void setTelefone(Integer telefone) {
        this.telefone = telefone;
    }
}
