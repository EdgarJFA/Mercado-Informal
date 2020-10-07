package com.rademotions.mercadoinformal.Modelo;

/**
 * Created by Valet on 10/28/2018.
 */

public class Vendedor {

    private String atividade, tipoDocumento, n_documento,  sexo, licensa;
    private Integer id, id_cliente, idade, id_mercado;

    public Vendedor( String licensa, String tipoDocumento, String n_documento, String sexo, String atividade, Integer idade, Integer id_cliente, Integer id_mercado) {
        this.atividade = atividade;
        this.tipoDocumento = tipoDocumento;
        this.n_documento = n_documento;
        this.sexo = sexo;
        this.id_cliente = id_cliente;
        this.id_mercado = id_mercado;
        this.idade = idade;
        this.licensa = licensa;
    }

    public Vendedor(int anInt) {
        this.id = anInt;
    }


    public Vendedor(){

    }

    public Integer getId_mercado() {
        return id_mercado;
    }

    public void setId_mercado(Integer id_mercado) {
        this.id_mercado = id_mercado;
    }

    public String getLicensa() {
        return licensa;
    }

    public void setLicensa(String licensa) {
        this.licensa = licensa;
    }

    public String getAtividade() {
        return atividade;
    }

    public void setAtividade(String atividade) {
        this.atividade = atividade;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getN_documento() {
        return n_documento;
    }

    public void setN_documento(String n_documento) {
        this.n_documento = n_documento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Integer id_cliente) {
        this.id_cliente = id_cliente;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }
}
