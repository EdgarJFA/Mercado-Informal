package com.rademotions.mercadoinformal.Modelo;

/**
 * Created by Valet on 10/28/2018.
 */

public class Cliente extends Usuario {

    private Integer id;
    private String senha;
    private String tipoUsuario;


    public Cliente( String nome, String apelido, String email, int telefone, String cidade,  String senha) {
        super(nome, apelido, email, cidade, telefone);
        this.senha = senha;
    }

    public Cliente(){
    }

    public Cliente(Integer id, String nome, String email, int telefone, String tipoUsuario){
        super(nome, email, telefone);
        this.tipoUsuario = tipoUsuario;
        this.id = id;
    }

    public Cliente(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public Cliente(int anInt) {
        this.id = anInt;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
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
}
