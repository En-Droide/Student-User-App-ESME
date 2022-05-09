package com.example.esme;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapterCours extends RecyclerView.Adapter<CustomAdapterCours.ViewHolder>{
    private ArrayList<Cours> localDataSet;
    private TextView estCustom;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewMatiere,textViewIntitule,textViewDateCours,textViewHeures,textViewSalle;

        public ViewHolder(@NonNull View view) {
            super(view);
            textViewMatiere = (TextView) view.findViewById(R.id.textViewMatiereCours);
            textViewIntitule = (TextView) view.findViewById(R.id.textViewIntituleCours);
            textViewDateCours = (TextView) view.findViewById(R.id.textViewDateCours);
            textViewHeures = (TextView) view.findViewById(R.id.textViewHeures);
            textViewSalle = (TextView) view.findViewById(R.id.textViewSalle);
            estCustom=(TextView) view.findViewById(R.id.textViewEstCustom);
        }
        public TextView getTextViewMatiere() {
            return textViewMatiere;
        }
        public TextView getTextViewIntitule() {
            return textViewIntitule;
        }
        public TextView getTextViewDateCours() { return textViewDateCours;}
        public TextView getTextViewHeures() { return textViewHeures;}
        public TextView getTextViewSalle() { return textViewSalle;}
        public TextView getTextViewCustom() { return estCustom;}
    }
    public CustomAdapterCours(ArrayList<Cours> dataSet) {
        localDataSet = dataSet;
    }

    @NonNull
    @Override
    public CustomAdapterCours.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.custom_adapter_cours, viewGroup, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CustomAdapterCours.ViewHolder viewHolder, int position) {
        viewHolder.getTextViewMatiere().setText(localDataSet.get(position).matiere);
        viewHolder.getTextViewIntitule().setText(localDataSet.get(position).intitule);
        viewHolder.getTextViewHeures().setText(localDataSet.get(position).heureDebut+" - "+localDataSet.get(position).heureFin);
        viewHolder.getTextViewSalle().setText(localDataSet.get(position).salle);
        viewHolder.getTextViewDateCours().setText(localDataSet.get(position).date);
        if(localDataSet.get(position).estPerso){
            viewHolder.getTextViewCustom().setText("Perso");
        }else{
            viewHolder.getTextViewCustom().setText("");
        }

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
