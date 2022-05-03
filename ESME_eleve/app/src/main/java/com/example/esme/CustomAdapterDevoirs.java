package com.example.esme;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapterDevoirs extends RecyclerView.Adapter<CustomAdapterDevoirs.ViewHolder>{
    private ArrayList<Devoir> localDataSet;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewMatiere,textViewObjet,textViewDateDebut,textViewDateFin;

        public ViewHolder(@NonNull View view) {
            super(view);
            textViewMatiere = (TextView) view.findViewById(R.id.textViewMatiere);
            textViewObjet = (TextView) view.findViewById(R.id.textViewObjet);
            textViewDateDebut = (TextView) view.findViewById(R.id.textViewDateDebut);
            textViewDateFin = (TextView) view.findViewById(R.id.textViewDateFin);
        }
        public TextView getTextViewMatiere() {
            return textViewMatiere;
        }

        public TextView getTextViewObjet() { return textViewObjet; }

        public TextView getTextViewDateDebut() { return textViewDateDebut; }

        public TextView getTextViewDateFin() { return textViewDateFin; }
    }
    public CustomAdapterDevoirs(ArrayList<Devoir> dataSet) {
        localDataSet = dataSet;
    }

    @NonNull
    @Override
    public CustomAdapterDevoirs.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.custom_adapter_devoirs, viewGroup, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CustomAdapterDevoirs.ViewHolder viewHolder, int position) {
        viewHolder.getTextViewMatiere().setText(localDataSet.get(position).matiere);
        viewHolder.getTextViewObjet().setText(localDataSet.get(position).objet);
        viewHolder.getTextViewDateDebut().setText(localDataSet.get(position).dateDebut);
        viewHolder.getTextViewDateFin().setText(localDataSet.get(position).dateFin);

        if(position % 2 == 0){
            viewHolder.itemView.setBackgroundColor(Color.rgb(150,245,170));
        }
        else{
            viewHolder.itemView.setBackgroundColor(Color.rgb(150,170,245));
        }
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }


}
