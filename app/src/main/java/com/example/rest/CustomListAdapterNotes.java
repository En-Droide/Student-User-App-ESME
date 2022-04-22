package com.example.rest;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListAdapterNotes extends BaseAdapter {

    private ArrayList<Note> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public CustomListAdapterNotes(Context aContext, ArrayList<Note> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.activity_custom_list_view_notes, null);
            holder = new ViewHolder();
            holder.id = (TextView) convertView.findViewById(R.id.textId);
            holder.id_eleve = (TextView) convertView.findViewById(R.id.textID_eleve);
            holder.matiere = (TextView) convertView.findViewById(R.id.textMatiere);
            holder.note = (TextView) convertView.findViewById(R.id.textNote);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            Log.v("position",""+position);
        }

        if(position % 2 == 0){
            convertView.setBackgroundColor(Color.rgb(150,245,170));
        }

        Note note = this.listData.get(position);
        holder.id.setText(""+note.getId());
        holder.id_eleve.setText(note.getId_eleve());
        holder.matiere.setText(note.getMatiere());
        holder.note.setText(""+note.getNote());
        Log.v("CUSTOM",""+note.getMatiere()+" "+note.getNote());

        return convertView;
    }

    static class ViewHolder {
        TextView id;
        TextView id_eleve;
        TextView matiere;
        TextView note;
    }

    public int getCount() {
        return ((listData!=null)?listData.size():0);
    }
    public Object getItem(int position) {
        return listData.get(position);
    }
    public long getItemId(int position) {
        return position;
    }
}