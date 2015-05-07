package com.example.arthur.graficos;

/**
 * Created by arthur on 07/05/15.
 */
public class Linha {
    private String descricao;
    private String status;
    private int id;

    public Linha(int id, String descricao, String status) {
        this.id = id;
        this.descricao = descricao;
        this.status = status;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }
}
