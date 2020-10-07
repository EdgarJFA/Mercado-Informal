package com.rademotions.mercadoinformal.Modelo;

import java.sql.Blob;
import java.sql.Date;

/**
 * Created by Valet on 7/3/2019.
 */

public class Categoria {

    private Integer id;
    private String nome;
    private byte[] icone;
    private Date data;
    private int idAdministrador;

    public Categoria(Integer id, String nome, byte[] icone, Date data, int idAdministrador) {
        this.id = id;
        this.nome = nome;
        this.icone = icone;
        this.data = data;
        this.idAdministrador = idAdministrador;
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

    public byte[] getIcone() {
        return icone;
    }

    public void setIcone(byte[] icone) {
        this.icone = icone;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getIdAdministrador() {
        return idAdministrador;
    }

    public void setIdAdministrador(int idAdministrador) {
        this.idAdministrador = idAdministrador;
    }
}
