package com.example.ebook01.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ebook01.R;
import com.example.ebook01.entity.Notes;

import java.util.List;

public class NoteListAdapter extends ArrayAdapter<Notes> {

    private Context mContext;
    private int mresource;
    private ViewHolder viewHolder;

    public NoteListAdapter(@NonNull Context context, int resource, @NonNull List<Notes> objects) {
        super(context, resource, objects);
        this.mresource = resource;
        this.mContext = context;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        if (convertView == null){
            LayoutInflater  layoutInflater = LayoutInflater.from(parent.getContext());
            view = layoutInflater.inflate(mresource,parent,false);
            TextView notetitle = view.findViewById(R.id.noteList_title);
            viewHolder = new NoteListAdapter.ViewHolder(notetitle);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (NoteListAdapter.ViewHolder) view.getTag();
        }
        Notes notes = (Notes) getItem(position);
        if (notes!=null){
            viewHolder.notetitle.setText(getItem(position).getNoteTitle());
        }
        return view;
    }
    private class ViewHolder{
        TextView notetitle;
        public ViewHolder(TextView notetitle){
            this.notetitle = notetitle;
        }

        public TextView getNotetitle() {
            return notetitle;
        }

        public void setNotetitle(TextView notetitle) {
            this.notetitle = notetitle;
        }
    }
}
