package com.su.conso.metasdiarias.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.su.conso.metasdiarias.R;
import com.su.conso.metasdiarias.bancoDados.TarefaDAO;
import com.su.conso.metasdiarias.model.Tarefas;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TarefaEditActivity extends AppCompatActivity {

    private TextView txtTituloActivity;
    private ImageButton btnAddProgresso,btnVoltar;
    private ProgressBar progressBar;
    private EditText editTituloTarefa;
    private Button btnAtualizar;
    private CheckBox reiniciarProgressoCheck;

    private boolean clicado = false;
    private int progressoRecuperado = 0;
    private int completoRecuperado = 0;

    private TarefaDAO tarefaDAO;
    private Tarefas tarefasAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarefa_edit);
        findView();
        tarefaClidada();
        btns();

        btnAtualizar.setText("Atualizar");
        txtTituloActivity.setText("Editar meta");
        progressBar.setVisibility(View.VISIBLE);
        btnAddProgresso.setVisibility(View.VISIBLE);
        tarefaDAO = new TarefaDAO(getApplicationContext());
        reiniciarProgressoCheck.setVisibility(View.VISIBLE);

    }

    private void atualizarMetas() {

        if (reiniciarProgressoCheck.isChecked()){
           progressoRecuperado = 0;
           clicado = false;
        }
        
        if (!editTituloTarefa.getText().toString().isEmpty()){
            Tarefas tarefas = new Tarefas();
            tarefas.setTitulo(editTituloTarefa.getText().toString());
            if (clicado){
               progressoRecuperado += 1;
            }

            //            if (progressoRecuperado >= 30){
//                int n = completoRecuperado += 1;
//                tarefas.setCompletou(n);
//                progressoRecuperado = 0;
//            }
            tarefas.setProgressoTarefa1(progressoRecuperado);
            tarefas.setId(tarefasAtual.getId());
            if (tarefaDAO.atualizar(tarefas)) {
                finish();
            } else {
                Toast.makeText(this, "erro", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void findView(){
        btnVoltar = findViewById(R.id.btnVoltarPadrao);
        btnAddProgresso = findViewById(R.id.btn_add_progresso);
        progressBar = findViewById(R.id.progresso_da_meta);
        txtTituloActivity = findViewById(R.id.txtPadrao_edicao);
        editTituloTarefa = findViewById(R.id.editTxtRecuperado);
        btnAtualizar = findViewById(R.id.btnAtualizar);
        reiniciarProgressoCheck = findViewById(R.id.checkBox_atualizar);
    }

    public void tarefaClidada(){
        tarefasAtual = (Tarefas) getIntent().getSerializableExtra("tarefasSelecionadas");

        if (tarefasAtual != null){
            editTituloTarefa.setText(tarefasAtual.getTitulo());
            progressBar.setProgress(tarefasAtual.getProgressoTarefa1());
            try {
                progressoRecuperado = tarefasAtual.getProgressoTarefa1();
                completoRecuperado = tarefasAtual.getCompletou();

            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }

    private void btns(){

        btnAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                atualizarMetas();
            }
        });

        btnAddProgresso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               clicado = true;
                btnAddProgresso.setVisibility(View.INVISIBLE);
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}