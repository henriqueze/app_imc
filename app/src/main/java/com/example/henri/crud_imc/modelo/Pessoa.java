package com.example.henri.crud_imc.modelo;

import java.io.Serializable;

/**
 * Created by henri on 26/08/2018.
 */

public class Pessoa implements Serializable{

    private int id;
    private String nome;
    private String dataNascimento;
    private String altura;
    private String peso;
    private Float imc;

    public Pessoa() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public Float getImc() {
        return imc;
    }

    public void setImc(Float imc) {
        this.imc = imc;
    }

    @Override
    public String toString() {
        return nome.toString();
    }
}
