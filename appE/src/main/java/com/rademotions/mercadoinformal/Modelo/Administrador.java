package com.rademotions.mercadoinformal.Modelo;

/**
 * Created by Valet on 11/23/2018.
 */

public class Administrador extends Usuario {

    private Integer id;
    private String senha;
    private String tipoUsuario;


    public Administrador(String nome, String apelido, String email, String cidade, int telefone, String senha) {
        super(nome, apelido, email, cidade, telefone);
        this.senha = senha;
    }

    public Administrador(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
