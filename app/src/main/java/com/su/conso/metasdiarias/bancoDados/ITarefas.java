package com.su.conso.metasdiarias.bancoDados;

import com.su.conso.metasdiarias.model.Tarefas;

import java.util.List;

public interface ITarefas {

    public boolean salvar(Tarefas tarefas);
    public boolean atualizar(Tarefas tarefas);
    public boolean deletar(Tarefas tarefas);
    public List<Tarefas> listar();
}
