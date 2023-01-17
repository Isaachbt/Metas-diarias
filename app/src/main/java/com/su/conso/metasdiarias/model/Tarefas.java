package com.su.conso.metasdiarias.model;

import java.io.Serializable;

public class Tarefas implements Serializable {

    private String titulo;
    private int progressoTarefa1;
    private int completou;
    private Long id;

    public int getCompletou() {
        return completou;
    }

    public void setCompletou(int completou) {
        this.completou = completou;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getProgressoTarefa1() {
        return progressoTarefa1;
    }

    public void setProgressoTarefa1(int progressoTarefa1) {
        this.progressoTarefa1 = progressoTarefa1;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
