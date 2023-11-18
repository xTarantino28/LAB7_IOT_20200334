package com.example.lab7_20200334_iot.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab7_20200334_iot.R;
import com.example.lab7_20200334_iot.dtos.Cita;

import java.util.List;

public class ListaCitasAdapter extends RecyclerView.Adapter<ListaCitasAdapter.CitaViewHolder> {
    public List<Cita> getListaCitas() {
        return listaCitas;
    }

    public void setListaCitas(List<Cita> listaCitas) {
        this.listaCitas = listaCitas;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private List<Cita> listaCitas;
    private Context context;

    @NonNull
    @Override
    public CitaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_citas_rv, parent, false);
        return new CitaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CitaViewHolder holder, int position) {
        Cita cita = listaCitas.get(position);
        holder.cita = cita;

        TextView citaText = holder.itemView.findViewById(R.id.infoCitaTextView);
        String content = cita.getHora() + " - " + cita.getCliente() + " - " + cita.getEmailCliente();
        citaText.setText(content);


    }

    @Override
    public int getItemCount() {
        return listaCitas.size();
    }


    public class CitaViewHolder extends RecyclerView.ViewHolder{

        Cita cita;

        public CitaViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


}
