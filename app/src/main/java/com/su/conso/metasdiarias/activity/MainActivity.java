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
import com.su.conso.metasdiarias.databinding.ActivityMainBinding;
import com.su.conso.metasdiarias.model.Tarefas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private List<Tarefas> listTarefa = new ArrayList<>();
    private MyAdapter adapter;
    private TarefaDAO tarefaDAO;
    private Tarefas tarefasSelec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        tarefaDAO = new TarefaDAO(getApplicationContext());

        clicksBtns();
        deletarVazio();
        clickRecycler();

    }

    private void carregarListaTarefa(){
        listTarefa = tarefaDAO.listar();

        adapter = new MyAdapter(listTarefa);

        binding.recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setHasFixedSize(true);
        Collections.reverse(listTarefa);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        carregarListaTarefa();
    }

    private void clicksBtns(){
        binding.editarMetas.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), NovaTarefaActivity.class))
        );
        
        binding.btnInfo.setOnClickListener(view ->
        msg("Criado por: Isaac lima, esse app e para minha metas!"));
        
        binding.imgProgressoTotal.setOnClickListener(view ->
        msg("Em desenvolvimento!"));
    }

    public void deletarVazio(){
        Tarefas tarefas = new Tarefas();
        tarefas.setTitulo("");
        TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
        if (tarefaDAO.deletar(tarefas)){

        }
    }

    private void clickRecycler(){
        binding.recyclerView.addOnItemTouchListener(new RecyclerItemClick(getApplicationContext(),   binding.recyclerView,
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
                                    msg("Apagado com sucesso");
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

    private void msg(String txt){
        Toast.makeText(this, txt, Toast.LENGTH_SHORT).show();
    }
}