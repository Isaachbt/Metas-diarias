package com.su.conso.metasdiarias.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.su.conso.metasdiarias.R;
import com.su.conso.metasdiarias.adapter.MyAdapter;
import com.su.conso.metasdiarias.adapter.RecyclerItemClick;
import com.su.conso.metasdiarias.bancoDados.TarefaDAO;
import com.su.conso.metasdiarias.model.Tarefas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ImageButton editarTarefa,btnInfo,btnSemAcao;
    private List<Tarefas> listTarefa = new ArrayList<>();
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private TarefaDAO tarefaDAO;
    private Tarefas tarefasSelec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tarefaDAO = new TarefaDAO(getApplicationContext());

        findView();
        clicksBtns();
        deletarVazio();
        clickRecycler();

    }

    public void findView(){
        btnSemAcao = findViewById(R.id.imageButton2);
        btnInfo = findViewById(R.id.btnInfo);
        editarTarefa = findViewById(R.id.editarMetas);
        recyclerView = findViewById(R.id.recyclerView);
    }

    private void carregarListaTarefa(){
        listTarefa = tarefaDAO.listar();

        adapter = new MyAdapter(listTarefa);

        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        Collections.reverse(listTarefa);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        carregarListaTarefa();
    }

    private void clicksBtns(){
        editarTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), NovaTarefaActivity.class));
            }
        });
        
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Criado por: Isaac lima, esse app e para minha metas!", Toast.LENGTH_LONG).show();
            }
        });
        
        btnSemAcao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Em manutenção.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deletarVazio(){
        Tarefas tarefas = new Tarefas();
        tarefas.setTitulo("");
        TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
        if (tarefaDAO.deletar(tarefas)){

        }
    }

    private void clickRecycler(){
        recyclerView.addOnItemTouchListener(new RecyclerItemClick(getApplicationContext(), recyclerView,
                new RecyclerItemClick.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Tarefas tarefasSelecionadas = listTarefa.get(position);

                        Intent i = new Intent(getApplicationContext(),TarefaEditActivity.class);
                        i.putExtra("tarefasSelecionadas", tarefasSelecionadas);
                        startActivity(i);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

                        tarefasSelec = listTarefa.get(position);

                        dialog.setTitle("Excluir");
                        dialog.setIcon(R.drawable.ic_delete);
                        dialog.setMessage("Tem certeza que deseja excluir a: '"+tarefasSelec.getTitulo()+"'?" );

                        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
                                if (tarefaDAO.deletar(tarefasSelec)){
                                    carregarListaTarefa();
                                    Toast.makeText(MainActivity.this, "Apagado com sucesso", Toast.LENGTH_SHORT).show();
                                }else{

                                }
                            }
                        });
                        dialog.setNegativeButton("Não",null);

                        dialog.create();
                        dialog.show();

                    }
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }));
    }
}