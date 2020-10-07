package com.rademotions.mercadoinformal.Modelo;

/**
 * Created by Valet on 9/21/2019.
 */

public class Desejo {
    private Integer id;
    private Integer idCliente;
    private Integer idProduto;

    public Desejo(Integer id, Integer idCliente, Integer idProduto) {
        this.id = id;
        this.idCliente = idCliente;
        this.idProduto = idProduto;
    }

    public Desejo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }
}
