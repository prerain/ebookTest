package com.example.ebook01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ebook01.dao.BookmarkDao;
import com.example.ebook01.dao.NotesDao;
import com.example.ebook01.entity.Notes;


public class UpdateNoteActivity extends AppCompatActivity {
    TextView title;
    EditText content;
    Button submit;
    int noteId;
    String  noteContent;
    String  noteTitle;
    int bookId;
    NotesDao notesDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);
        title = findViewById(R.id.update_note_title);
        content = findViewById(R.id.update_note_content);
        submit = findViewById(R.id.update_note_submit);

        notesDao = new NotesDao(UpdateNoteActivity.this);
        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId",0);
        noteTitle = intent.getStringExtra("noteTitle");
        noteContent = intent.getStringExtra("noteContent");
        bookId = intent.getIntExtra("bookId",0);

        title.setText(noteTitle);
        content.setText(noteContent);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("修改的笔记编号是",""+noteId);
                Notes note = new Notes();
                note.setNoteId(noteId);
                note.setNoteTitle(title.getText().toString());
                note.setNoteContent(content.getText().toString());
                Log.d("note",""+note);
                int result =  notesDao.update(note);
                Log.d("是否修改成功",""+result);
                Intent intent1 = new Intent(UpdateNoteActivity.this,MarkNoteActivity.class);
                intent1.putExtra("bookId",bookId);
                startActivity(intent1);
            }
        });
    }
}