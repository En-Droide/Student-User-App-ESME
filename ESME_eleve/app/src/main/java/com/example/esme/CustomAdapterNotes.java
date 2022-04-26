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

public class CustomAdapterNotes extends RecyclerView.Adapter<CustomAdapterNotes.ViewHolder>{
    private ArrayList<Note> localDataSet;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewMatiere,textViewIntitule,textViewNote,textViewCoeff,textViewDateNote,textViewPos;

        public ViewHolder(@NonNull View view) {
            super(view);
            textViewMatiere = (TextView) view.findViewById(R.id.textViewMatiere);
            textViewIntitule = (TextView) view.findViewById(R.id.textViewIntitule);
            textViewNote = (TextView) view.findViewById(R.id.textViewNote);
            textViewCoeff = (TextView) view.findViewById(R.id.textViewCoeff);
            textViewDateNote = (TextView) view.findViewById(R.id.textViewDateNote);
            textViewPos = (TextView) view.findViewById(R.id.textViewPos);
        }
        public TextView getTextViewMatiere() {
            return textViewMatiere;
        }
        public TextView getTextViewIntitule() {
            return textViewIntitule;
        }
        public TextView getTextViewNote() {
            return textViewNote;
        }
        public TextView getTextViewCoeff() {
            return textViewCoeff;
        }
        public TextView getTextViewDateNote() {
            return textViewDateNote;
        }
        public TextView getTextViewPos() {
            return textViewPos;
        }
    }
    public CustomAdapterNotes(ArrayList<Note> dataSet) {
        localDataSet = dataSet;
    }

    @NonNull
    @Override
    public CustomAdapterNotes.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.custom_adapter_notes, viewGroup, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CustomAdapterNotes.ViewHolder viewHolder, int position) {
        viewHolder.getTextViewMatiere().setText(localDataSet.get(position).matiere);
        viewHolder.getTextViewIntitule().setText(localDataSet.get(position).intitule);
        viewHolder.getTextViewNote().setText(localDataSet.get(position).note.toString());
        viewHolder.getTextViewCoeff().setText(localDataSet.get(position).coefficient.toString());
        viewHolder.getTextViewDateNote().setText(localDataSet.get(position).date);
        viewHolder.getTextViewPos().setText(String.valueOf(position));

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
