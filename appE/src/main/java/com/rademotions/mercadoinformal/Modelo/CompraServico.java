package com.rademotions.mercadoinformal.Modelo;

import java.sql.Date;

/**
 * Created by Valet on 7/4/2019.
 */

public class CompraServico {

    private int id;
    private Date date;
    private int idServico, idCliente, idTransacao;

    public CompraServico(int id, Date date, int idServico, int idCliente, int idTransacao) {
        this.id = id;
        this.date = date;
        this.idServico = idServico;
        this.idCliente = idCliente;
        this.idTransacao = idTransacao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getIdServico() {
        return idServico;
    }

    public void setIdServico(int idServico) {
        this.idServico = idServico;
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
