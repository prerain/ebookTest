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
import com.example.ebook01.entity.Chapter;

import java.util.List;

public class CatlogAdapter extends ArrayAdapter<Chapter> {
    private Context mContext;
    private int mresource;
    private ViewHolder viewHolder;

    public CatlogAdapter(@NonNull Context context, int resource, @NonNull List<Chapter> objects) {
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
            TextView catlogtitle = view.findViewById(R.id.catlog_title);
            viewHolder = new ViewHolder(catlogtitle);
            view.setTag(viewHolder);

        }else {
            view = convertView;
            viewHolder = (CatlogAdapter.ViewHolder) view.getTag();
        }
        Chapter chapter = (Chapter) getItem(position);
        if (chapter!=null){
            viewHolder.catlogtitle.setText(getItem(position).getChaptitle());
        }
        return view;
    }
    private class ViewHolder{
        TextView catlogtitle;
        public ViewHolder(TextView catlogtitle){
            this.catlogtitle = catlogtitle;
        }

        public TextView getCatlogtitle() {
            return catlogtitle;
        }

        public void setCatlogtitle(TextView catlogtitle) {
            this.catlogtitle = catlogtitle;
        }
    }
}
