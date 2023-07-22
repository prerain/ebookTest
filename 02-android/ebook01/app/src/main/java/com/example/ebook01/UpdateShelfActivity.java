package com.example.ebook01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ebook01.dao.BookshelfDao;
import com.example.ebook01.entity.Bookshelf;

public class UpdateShelfActivity extends AppCompatActivity {

    TextView shelfId;
    EditText shelfName;
    Button submit;
    BookshelfDao bookshelfDao;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_shelf);
        Intent intent = getIntent();
        id = intent.getIntExtra("shelfId",0);
        String name = intent.getStringExtra("shelfName");
        shelfId = findViewById(R.id.update_shelfid);
        shelfName = findViewById(R.id.update_shelfname);
        submit = findViewById(R.id.update_submit);
        bookshelfDao = new BookshelfDao(UpdateShelfActivity.this);

        Log.d("id=",""+id);
        shelfId.setText("书架编号"+String.valueOf(id));
        shelfName.setText(name);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String change = shelfName.getText().toString();
                if (change.equals("")){
                    Toast.makeText(UpdateShelfActivity.this, "书架名不能为空", Toast.LENGTH_SHORT).show();
                }else{
                    Bookshelf updateshelf = new Bookshelf(id,change);
                    bookshelfDao.updateShlef(updateshelf);
                    Intent intent = new Intent(UpdateShelfActivity.this,MainActivity.class);
                    startActivity(intent);
                }

            }
        });
    }
}