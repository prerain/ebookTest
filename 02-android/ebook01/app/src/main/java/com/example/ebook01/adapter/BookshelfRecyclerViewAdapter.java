package com.example.ebook01.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ebook01.AddShelfActivity;
import com.example.ebook01.BookActivity;
import com.example.ebook01.R;
import com.example.ebook01.entity.Book;
import com.example.ebook01.entity.Bookshelf;
import com.example.ebook01.databinding.FragmentBookshelfBinding;

import java.util.List;

/**
 * TODO: Replace the implementation with code for your data type.
 */
public class BookshelfRecyclerViewAdapter extends RecyclerView.Adapter<BookshelfRecyclerViewAdapter.ViewHolder>{

    private final List<Bookshelf> bookshelfList;
    private Context context;

    public BookshelfRecyclerViewAdapter(List<Bookshelf> bookshelfList,Context mContext) {
        this.bookshelfList = bookshelfList;
        this.context =mContext;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(FragmentBookshelfBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.bookshelf = bookshelfList.get(position);
        //绑定数据和实现点击事件
        Log.d("positoon=",""+position);
        Log.d("bookshelf=",""+holder.bookshelf);
//        holder.shelfname.setText(bookshelfList.get(position).getShelfName());
        holder.shelfname.setText(bookshelfList.get(position).getShelfName());
        holder.addbook.setTag(position);

    }

    @Override //返回的行数
    public int getItemCount() {
        return bookshelfList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //        public final TextView mIdView;
        TextView shelfname;
        ImageButton addbook, shelddetail;
        public Bookshelf bookshelf;

        public ViewHolder(FragmentBookshelfBinding binding) {
            super(binding.getRoot());
            shelfname = binding.shelfname;
            addbook = binding.btnAddbook;
            shelddetail = binding.shelfmenu;
            shelfname.setOnClickListener(this);
            addbook.setOnClickListener(this);
            shelddetail.setOnClickListener(this);
        }

        public TextView getShelfname() {
            return shelfname;
        }

        public void setShelfname(TextView shelfname) {
            this.shelfname = shelfname;
        }

        public ImageButton getAddbook() {
            return addbook;
        }

        public void setAddbook(ImageButton addbook) {
            this.addbook = addbook;
        }

        public ImageButton getShelddetail() {
            return shelddetail;
        }

        public void setShelddetail(ImageButton shelddetail) {
            this.shelddetail = shelddetail;
        }

        @Override
        public void onClick(View view) {
            int pos = getAbsoluteAdapterPosition();
            if(listener!=null){
                switch (view.getId()){
                    case R.id.shelfname:
                        listener.itemClick(getAbsoluteAdapterPosition(),bookshelfList.get(pos));
                        break;
                    case R.id.btn_addbook:
                        listener.itemaddIcon(getAbsoluteAdapterPosition(),bookshelf.getShelfId());
                        break;
                    case R.id.shelfmenu:
                        listener.itemmenuIcon(getAbsoluteAdapterPosition(),bookshelf.getShelfId());
                        break;
                }

            }
        }
    }
    private ItemClickListener listener;
    public void setItemClickListener(ItemClickListener itemclick){
        this.listener = itemclick;
    }

    public interface ItemClickListener{
        void itemClick(int pos,Bookshelf bookshelf);
        void itemaddIcon(int pos,int id);
        void itemmenuIcon(int pos,int id);
    }
}