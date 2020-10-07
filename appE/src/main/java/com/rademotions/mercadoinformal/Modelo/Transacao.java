package com.rademotions.mercadoinformal.Modelo;

import java.sql.Date;

/**
 * Created by Valet on 7/4/2019.
 */

public class Transacao {

    private int id;
    private Date date;
    private Double valor;
    private int idModalidade;

    public Transacao(int id, Date date, Double valor, int idModalidade) {
        this.id = id;
        this.date = date;
        this.valor = valor;
        this.idModalidade = idModalidade;
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

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public int getIdModalidade() {
        return idModalidade;
    }

    public void setIdModalidade(int idModalidade) {
        this.idModalidade = idModalidade;
    }
}
