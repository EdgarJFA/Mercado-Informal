package com.rademotions.mercadoinformal.Modelo;

import java.sql.Date;

/**
 * Created by Valet on 7/4/2019.
 */

public class CompraProduto {

    private int id;
    private Date data;
    private int idProduto, idCliente, idTransacao;

    public CompraProduto(int id, Date data, int idProduto, int idCliente, int idTransacao) {
        this.id = id;
        this.data = data;
        this.idProduto = idProduto;
        this.idCliente = idCliente;
        this.idTransacao = idTransacao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdTransacao() {
        return idTransacao;
    }

    public void setIdTransacao(int idTransacao) {
        this.idTransacao = idTransacao;
    }
}
