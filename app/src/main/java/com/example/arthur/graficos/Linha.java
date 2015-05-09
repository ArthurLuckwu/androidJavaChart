package com.example.arthur.graficos;

/**
 * Created by arthur on 07/05/15.
 */
public class Linha {
    private String descricao;
    private String status;
    private int id;
    private int planta;
    private int fabrica;

    public Linha(int id, String descricao, String status, int planta, int fabrica) {
        this.id = id;
        this.descricao = descricao;
        this.status = status;
        this.planta = planta;
        this.fabrica = fabrica;
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

    public int getPlanta() {
        return planta;
    }

    public void setPlanta(int planta) {
        this.planta = planta;
    }

    public int getFabrica() {
        return fabrica;
    }

    public void setFabrica(int fabrica) {
        this.fabrica = fabrica;
    }
}
