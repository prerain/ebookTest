package com.example.ebook01.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.ebook01.AddBookActivity;
import com.example.ebook01.AddShelfActivity;
import com.example.ebook01.BookActivity;
import com.example.ebook01.MainActivity;
import com.example.ebook01.R;
import com.example.ebook01.UpdateShelfActivity;
import com.example.ebook01.adapter.BookshelfRecyclerViewAdapter;
import com.example.ebook01.dao.BookDao;
import com.example.ebook01.dao.BookshelfDao;
import com.example.ebook01.entity.Book;
import com.example.ebook01.entity.Bookmark;
import com.example.ebook01.entity.Bookshelf;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class BookshelfFragment extends Fragment implements BookshelfRecyclerViewAdapter.ItemClickListener{
    List<Bookshelf> bookshelfList;
    private BookshelfDao bookshelfDao;
    BookDao bookDao;

    RecyclerView recyclerView;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BookshelfFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookshelf_list, container, false);
        bookshelfDao = new BookshelfDao(getActivity());
        bookDao = new BookDao(getActivity());

        bookshelfList = bookshelfDao.finall();
        if (bookshelfList.size() == 0) {
            Bookshelf bookshelf = new Bookshelf(0,"默认书架");
            bookshelfDao.addShlef(bookshelf);
            //添加之后再次查询
            bookshelfList = bookshelfDao.finall();
        }
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            BookshelfRecyclerViewAdapter bsAdapter = new BookshelfRecyclerViewAdapter(bookshelfList,getActivity());
            recyclerView.setAdapter(bsAdapter);
            Log.d("bookshelflist",""+bookshelfList);
            //布局方式-线性
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            bsAdapter.setItemClickListener(this);
        }

        return view;
    }

    @Override
    public void itemClick(int pos,Bookshelf bookshelf) {
        //点击获取对应的item的位置和内容
        Log.d("点击的名字是第",""+pos);
        Log.d("bookshelf",""+bookshelf);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("想要对书架做什么操作")
                .setNegativeButton("修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent_update = new Intent(getActivity(), UpdateShelfActivity.class);
                        intent_update.putExtra("shelfId",bookshelf.getShelfId());
                        intent_update.putExtra("shelfName",bookshelf.getShelfName());
                        startActivity(intent_update);
                    }
                })
                .setNeutralButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //删除书签、目录、笔记、书
                        if (bookDao.findbooklist(bookshelf.getShelfId()).size()!=0) {
                            Toast.makeText(getActivity(), "书架内有书籍不可删除", Toast.LENGTH_SHORT).show();
                        }else {
                            bookshelfDao.delshelf(bookshelfList.get(pos));
                            bookshelfList = bookshelfDao.finall();
                            BookshelfRecyclerViewAdapter bsAdapter = new BookshelfRecyclerViewAdapter(bookshelfList,getActivity());
                            recyclerView.setAdapter(bsAdapter);
                            Toast.makeText(getActivity(), "删除了", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).create().show();
    }

    @Override
    public void itemaddIcon(int pos,int id) {
        Log.d("点击的是添加第",""+pos);
        Intent intent_add = new Intent(getActivity(), AddBookActivity.class);
        intent_add.putExtra("shelfId",id);
        startActivity(intent_add);
    }

    @Override
    public void itemmenuIcon(int pos,int id) {
        //到bookActivity
        Log.d("点击的是menu第",""+pos);
        Intent intent_tobook = new Intent(getActivity(), BookActivity.class);
        intent_tobook.putExtra("shelfId",id);
        Log.d("传入的id为",""+id);
        startActivity(intent_tobook);

    }
}