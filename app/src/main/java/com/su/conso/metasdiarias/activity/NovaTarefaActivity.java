package com.su.conso.metasdiarias.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.su.conso.metasdiarias.R;
import com.su.conso.metasdiarias.bancoDados.TarefaDAO;
import com.su.conso.metasdiarias.model.Tarefas;

import java.time.LocalDate;

public class NovaTarefaActivity extends AppCompatActivity {
    private EditText editTituloTarefa;
    private TextView txtPadrao;
    private Button btnSalvar;
    private ImageButton btnVoltar;
    private ProgressBar progressBar;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_tarefa);
        findView();

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarCampos();
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSalvar.setText("Salvar");
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void findView(){
        txtPadrao = findViewById(R.id.txtPadrao_edicao);
        editTituloTarefa = findViewById(R.id.editTxtRecuperado);
        btnSalvar = findViewById(R.id.btnAtualizar);
        btnVoltar = findViewById(R.id.btnVoltarPadrao);
        progressBar = findViewById(R.id.progresso_da_meta);
    }

    private void validarCampos(){
        try {
            TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
            if (!editTituloTarefa.getText().toString().isEmpty()){
                Tarefas tarefas = new Tarefas();
                tarefas.setTitulo(editTituloTarefa.getText().toString());
                tarefas.setProgressoTarefa1(0);
                tarefas.setCompletou(0);

                if (tarefaDAO.salvar(tarefas)) {
                    finish();
                }else{
                    Toast.makeText(this, "Erro ao salvar", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "Campo vazio!", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}