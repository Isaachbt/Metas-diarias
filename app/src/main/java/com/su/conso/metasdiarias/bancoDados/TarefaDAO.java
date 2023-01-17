package com.su.conso.metasdiarias.bancoDados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.su.conso.metasdiarias.model.Tarefas;

import java.util.ArrayList;
import java.util.List;

public class TarefaDAO implements ITarefas{

    private SQLiteDatabase escreve;
    private SQLiteDatabase le;
    private Context context;


    public TarefaDAO(Context context){
        this.context = context;
        BancoDados bd = new BancoDados(context);
        escreve = bd.getWritableDatabase();
        le = bd.getReadableDatabase();

    }

    @Override
    public boolean salvar(Tarefas tarefas) {
        ContentValues cv = new ContentValues();
        cv.put("titulo",tarefas.getTitulo());
       cv.put("progresso",tarefas.getProgressoTarefa1());
       cv.put("completou",tarefas.getCompletou());

        try{
            escreve.insert(BancoDados.NOME_TABELA,null,cv);
            Log.i("INFO","salvo com sucesso");
        }catch (Exception e){
            Log.e("INFO","Erro ao salvar");
            return false;
        }
        return true;
    }

    @Override
    public boolean atualizar(Tarefas tarefas) {
        ContentValues cv = new ContentValues();
        cv.put("titulo",tarefas.getTitulo());
        cv.put("progresso",tarefas.getProgressoTarefa1());
        cv.put("completou",tarefas.getCompletou());
        String[] argas = {String.valueOf(tarefas.getId())};
        try{
            escreve.update(BancoDados.NOME_TABELA,cv,"id=?",argas);

        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public boolean deletar(Tarefas tarefas) {
        String[] argas = {String.valueOf(tarefas.getId())};
        try{
            escreve.delete(BancoDados.NOME_TABELA,"id=?",argas);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public List<Tarefas> listar() {
        List<Tarefas> tarefasList = new ArrayList<>();
        //recuperando os dados da tabela
        String sql = "SELECT * FROM "+BancoDados.NOME_TABELA+" ;";
        Cursor c = le.rawQuery(sql,null);

        while (c.moveToNext()){
            Tarefas tarefas = new Tarefas();
            int columnIndex = c.getColumnIndex("id");

            String tarefa1columnIndex = String.valueOf(c.getColumnIndex("titulo"));
            String progresso1columnIndex = String.valueOf(c.getColumnIndex("progresso"));
            String completoucolumnIndex = String.valueOf(c.getColumnIndex("completou"));

            String titulo = c.getString(Integer.parseInt(tarefa1columnIndex));
            String completo = c.getString(Integer.parseInt(completoucolumnIndex));
            String progresso1Salve = c.getString(Integer.parseInt(progresso1columnIndex));
            Long id = c.getLong(columnIndex);

            tarefas.setId(id);
            tarefas.setTitulo(titulo);
            tarefas.setCompletou(Integer.parseInt(completo));
            tarefas.setProgressoTarefa1(Integer.parseInt(progresso1Salve));
            tarefasList.add(tarefas);
        }
        return tarefasList;
    }
}
