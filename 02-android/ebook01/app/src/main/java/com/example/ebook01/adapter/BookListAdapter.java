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
import com.example.ebook01.entity.Book;

import java.util.List;

/*
* 用于BookActivity
* */
public class BookListAdapter extends ArrayAdapter<Book> {
    private Context mContext;
    private int mresource;
    private ViewHolder viewHolder;

    public BookListAdapter(@NonNull Context context, int resource, @NonNull List<Book> objects) {
        super(context, resource, objects);
        this.mresource = resource;
        this.mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        if (convertView==null){
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            view = layoutInflater.inflate(mresource,parent,false);
            TextView bookname = view.findViewById(R.id.tv_bookname);
            TextView bookauthor = view.findViewById(R.id.tv_bookauthor);
            TextView booksource = view.findViewById(R.id.tv_booksource);
            TextView bookcontent = view.findViewById(R.id.tv_bookcontent);
            viewHolder = new ViewHolder(bookname,bookauthor,bookauthor,bookcontent);
            view.setTag(viewHolder);
        }else {
            view =convertView;
            viewHolder = (BookListAdapter.ViewHolder) view.getTag();
        }
        Book book = (Book) getItem(position);
        if (book!=null){
            viewHolder.bookname.setText(getItem(position).getBookName());
            viewHolder.bookauthor.setText(getItem(position).getBookAuthor());
            viewHolder.booksource.setText(getItem(position).getSource());
            viewHolder.bookcentent.setText(getItem(position).getBookDetalis());
        }
        return view;
    }

    private class ViewHolder{
        private TextView bookname,bookauthor,booksource,bookcentent;

        public ViewHolder(TextView bookname, TextView bookauthor, TextView booksource, TextView bookcentent) {
            this.bookname = bookname;
            this.bookauthor = bookauthor;
            this.booksource = booksource;
            this.bookcentent = bookcentent;
        }

        public TextView getBookname() {
            return bookname;
        }

        public void setBookname(TextView bookname) {
            this.bookname = bookname;
        }

        public TextView getBookauthor() {
            return bookauthor;
        }

        public void setBookauthor(TextView bookauthor) {
            this.bookauthor = bookauthor;
        }

        public TextView getBooksource() {
            return booksource;
        }

        public void setBooksource(TextView booksource) {
            this.booksource = booksource;
        }

        public TextView getBookcentent() {
            return bookcentent;
        }

        public void setBookcentent(TextView bookcentent) {
            this.bookcentent = bookcentent;
        }
    }

}
