package com.su.conso.metasdiarias.bancoDados;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class BancoDados extends SQLiteOpenHelper {

    public static int VERESAO = 1;
    public static String NOME_BD = "BD_TAREFA_META" ;
    public static String NOME_TABELA = "meta_futuro";

    public BancoDados(@Nullable Context context) {
        super(context, NOME_BD, null, VERESAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String slq = "CREATE TABLE IF NOT EXISTS "+ NOME_TABELA +" " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, titulo TEXT, progresso INTEGER,completou INTEGER)"; //sempre q for criar uma tabela,escolha aqui oq tem q ser salvo

        try{
            db.execSQL(slq);
            Log.i("INFO","Sucesso ao criar a tabela");
        }catch (Exception e){
            Log.i("INFO","Erro ao criar tabela"+e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String slq = "DROP TABLE IF EXISTS "+ NOME_TABELA + " ;";

        try{
            db.execSQL(slq);
            onCreate(db);
            Log.i("INFO","Sucesso ao atualizar tabela");
        }catch (Exception e){
            Log.i("INFO","Erro ao atualizar tabela");
        }
    }
}
