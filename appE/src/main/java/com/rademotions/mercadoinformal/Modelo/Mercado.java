package com.rademotions.mercadoinformal.Modelo;

/**
 * Created by Valet on 6/26/2019.
 */

public class Mercado {
    private Integer id;
    private String nome, cidade, provincia;
    private String latitude, longitude;

    public Mercado(Integer id, String nome, String cidade, String provincia, String latitude, String longitude) {
        this.id = id;
        this.nome = nome;
        this.cidade = cidade;
        this.provincia = provincia;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public Mercado(){

    }

    public Mercado(String nome) {
        this.nome = nome;
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

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return getNome();
    }
}
