package com.rademotions.mercadoinformal.Modelo;

/**
 * Created by Valet on 10/28/2018.
 */

public class Usuario {

    private String nome, apelido, email, cidade;
    private int telefone;

    public Usuario(){

    }

    public Usuario(String nome, String apelido, String email, String cidade, int telefone) {
        this.nome = nome;
        this.apelido = apelido;
        this.email = email;
        this.cidade = cidade;
        this.telefone = telefone;
    }

    public Usuario( String nome, String email, int telefone){
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public int getTelefone() {
        return telefone;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }

}
