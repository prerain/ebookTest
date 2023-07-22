package com.example.ebook01;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ebook01.adapter.BookListAdapter;
import com.example.ebook01.dao.BookDao;
import com.example.ebook01.dao.BookmarkDao;
import com.example.ebook01.dao.NotesDao;
import com.example.ebook01.dao.NovelContentPageDao;
import com.example.ebook01.entity.Book;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity {

    ListView listView;
    List<Book> bookList;
    BookDao bookDao;
    BookmarkDao bookmarkDao;
    NotesDao notesDao;
    NovelContentPageDao pageDao;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_book);
        //接收参数
        Intent intentget = getIntent();
        id = intentget.getIntExtra("shelfId",0);
        Log.d("接收的shelfId",""+id);
        bookDao = new BookDao(BookActivity.this);
        bookmarkDao = new BookmarkDao(BookActivity.this);
        notesDao = new NotesDao(BookActivity.this);
        pageDao = new NovelContentPageDao(BookActivity.this);

        listView = findViewById(R.id.lv_booklist);
        if (id == 0){
            Toast.makeText(this, "出现异常，请稍后再试", Toast.LENGTH_SHORT).show();
            finish();
        }
        bookList=bookDao.findbooklist(id);
        Log.d("booklist",""+bookList);


        InitBookList();

        ItemClick itemClick = new ItemClick();
        ItemLongClick itemLongClick = new ItemLongClick();
        listView.setOnItemClickListener(itemClick);
        listView.setOnItemLongClickListener(itemLongClick);

        TextView returnshelf = findViewById(R.id.tv_returnshelf);
        //返回按钮
        returnshelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("点击","了返回书架");
                finish();
            }
        });
    }

    public void InitBookList() {
        BookListAdapter bookListAdapter = new BookListAdapter(
                BookActivity.this,R.layout.activity_book_item,bookList);
        listView.setAdapter(bookListAdapter);
    }

    private class ItemClick implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            Book book = bookList.get(position);
            Log.d("book",""+book);
//            Toast.makeText(BookActivity.this, "你点击了"+book.getSource(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(BookActivity.this,ReadbookActivity.class);
            //传数据到下一个页面
            intent.putExtra("bookId",book.getBookId());
            startActivity(intent);
        }
    }
    private class ItemLongClick implements AdapterView.OnItemLongClickListener{
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
            Book book = bookList.get(position);
            AlertDialog.Builder builder = new AlertDialog.Builder(BookActivity.this);
            builder.setTitle("你要删除这本书吗")
                    .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            int result = bookDao.delBook(book);
                            bookmarkDao.delMarkALL(book.getBookId());
                            notesDao.delNoteALL(book.getBookId());
                            pageDao.delPage(book.getBookId());
                            if (result == 1){
                                Toast.makeText(BookActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                            }
                            //重新加载
                            bookList = bookDao.findbooklist(id);
                            InitBookList();
                            Log.d("你要删的书是",""+book);
                        }
                    })
                    .setNeutralButton("取消",null).create().show();
            //表示长按事件在本方法内解决
            return true;
        }
    }
}