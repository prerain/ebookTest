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
import com.example.ebook01.entity.Bookmark;

import java.util.List;

public class MarkAdapter extends ArrayAdapter<Bookmark> {
    private Context mContext;
    private int mresource;
    private ViewHolder viewHolder;
    public MarkAdapter(@NonNull Context context, int resource, @NonNull List<Bookmark> objects) {
        super(context, resource, objects);
        this.mresource = resource;
        this.mContext = context;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        if (convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            view = layoutInflater.inflate(mresource,parent,false);
            TextView marktitle = view.findViewById(R.id.markList_title);
            viewHolder = new MarkAdapter.ViewHolder(marktitle);
            view.setTag(viewHolder);

        }else {
            view = convertView;
            viewHolder = (MarkAdapter.ViewHolder) view.getTag();
        }
        Bookmark mark = (Bookmark) getItem(position);
        if (mark!=null){
            viewHolder.marktitle.setText(getItem(position).getMarkName());
        }
        return view;
    }
    private class ViewHolder{
        TextView marktitle;
        public ViewHolder(TextView marktitle){
            this.marktitle = marktitle;
        }

        public TextView getMarktitle() {
            return marktitle;
        }

        public void setMarktitle(TextView marktitle) {
            this.marktitle = marktitle;
        }
    }
}
