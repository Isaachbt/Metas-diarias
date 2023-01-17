package com.su.conso.metasdiarias.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.su.conso.metasdiarias.R;
import com.su.conso.metasdiarias.model.Tarefas;

import java.util.List;

public class MyAdapter  extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<Tarefas> listTarefas;

    public MyAdapter(List<Tarefas> lista) {
        this.listTarefas = lista;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_card_view_adapter,parent,false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Tarefas tarefa = listTarefas.get(position);

         holder.txtTitulo.setText(tarefa.getTitulo());
         holder.progressBar.setProgress(tarefa.getProgressoTarefa1());
         holder.completou.setText(String.valueOf(tarefa.getCompletou()));
    }

    @Override
    public int getItemCount() {
        return this.listTarefas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView txtTitulo,completou;
        private ProgressBar progressBar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitulo = itemView.findViewById(R.id.txtTitulo);
            completou = itemView.findViewById(R.id.txtCompletaoAdapter);
            progressBar = itemView.findViewById(R.id.progresseBar);

        }
    }
}
