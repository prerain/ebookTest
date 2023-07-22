package com.example.ebook01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ebook01.dao.BookshelfDao;
import com.example.ebook01.entity.Bookshelf;

public class AddShelfActivity extends AppCompatActivity {
    EditText shelfname;
    Button addsubmit;
    BookshelfDao bookshelfDao;
    Bookshelf bookshelf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shelf);
        shelfname = findViewById(R.id.shelfname);
        addsubmit = findViewById(R.id.btn_shelfadd);
        bookshelfDao = new BookshelfDao(AddShelfActivity.this);

        addsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = shelfname.getText().toString();
                if (name.equals("")){
                    Toast.makeText(AddShelfActivity.this, "书架名不能为空", Toast.LENGTH_SHORT).show();
                }else{
                    bookshelf = new Bookshelf(-1,name);
                    Log.d("bookshelfadd",""+bookshelf);
                    int result =  bookshelfDao.addShlef(bookshelf);
                    if(result==1){
                        Toast.makeText(AddShelfActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                    } else if (result==0) {
                        Toast.makeText(AddShelfActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(AddShelfActivity.this,MainActivity.class);
                    startActivity(intent);
                }

            }
        });
    }
}