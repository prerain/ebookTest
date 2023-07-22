package com.example.ebook01.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ebook01.R;
import com.example.ebook01.databinding.FragmentSearchBinding;
import com.example.ebook01.entity.Book;

import java.util.List;

/*
* 用于fragment——search
* */
public class BookSearchAdapter extends RecyclerView.Adapter<BookSearchAdapter.ViewHolder> {
    private Context mContext;

    private final List<Book> bookList;
    private int mresource;
    private ViewHolder viewHolder;
    public BookSearchAdapter(List<Book> bookList,Context mContext) {
        this.bookList = bookList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_search_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.book =bookList.get(position);

        holder.bookname.setText(bookList.get(position).getBookName());
        holder.bookauthor.setText(bookList.get(position).getBookAuthor());
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView bookname,bookauthor;
        public Book book;

        public ViewHolder(@NonNull View itemview) {
            super(itemview);
            bookname = itemview.findViewById(R.id.search_bookname);
            bookauthor = itemview.findViewById(R.id.search_bookauthor);
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener!=null){
                        listener.itemClick(getAbsoluteAdapterPosition(),book);
                    }
                }
            });
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
    }
    private ItemClickListener listener;
    public void setItemClickListener(ItemClickListener itemclick){
        this.listener=itemclick;
    }
    public interface ItemClickListener{
        void itemClick(int pos,Book book);
    }
}
