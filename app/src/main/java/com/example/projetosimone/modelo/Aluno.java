package com.example.projetosimone.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Aluno implements Serializable {

    @SerializedName("RA")
    @Expose
    private Long RA;
    @SerializedName("emailAluno")
    @Expose
    private String emailAluno;
    @SerializedName("nome")
    @Expose
    private String nome;

    public Long getRA() {
        return RA;
    }

    public void setRA(Long RA) {
        this.RA = RA;
    }

    public String getEmailAluno() {
        return emailAluno;
    }

    public void setEmailAluno(String emailAluno) {
        this.emailAluno = emailAluno;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}