package com.example.ebook01.ui;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ebook01.BookActivity;
import com.example.ebook01.R;
import com.example.ebook01.ReadbookActivity;
import com.example.ebook01.UpdateNoteActivity;
import com.example.ebook01.adapter.NoteListAdapter;
import com.example.ebook01.dao.NotesDao;
import com.example.ebook01.entity.Notes;

import java.util.ArrayList;
import java.util.List;

public class NoteFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int bookId;
    ListView lv_notelist;
    List<Notes> notes;
    NotesDao notesDao;
    public static NoteFragment newInstance(String param1, String param2) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note,container,false);
        bookId = Integer.parseInt(mParam2);
        lv_notelist = view.findViewById(R.id.note_list);
        notesDao = new NotesDao(getActivity());


        notes = notesDao.findnoteList(bookId);
        NoteListAdapter noteAdapter = new NoteListAdapter(getActivity(),R.layout.fragment_note_item,notes);
        lv_notelist.setAdapter(noteAdapter);
        ItemClick itemClick = new ItemClick();
        lv_notelist.setOnItemClickListener(itemClick);
        lv_notelist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Notes note = notes.get(pos);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("要删除笔记吗")
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            int result = notesDao.delNotebyNoteId(note.getNoteId(),bookId);
                            if (result == 1){
                                Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                            }
                                //重新加载
                                notes = notesDao.findnoteList(bookId);
                                NoteListAdapter noteAdapter = new NoteListAdapter(getActivity(),R.layout.fragment_note_item,notes);
                                lv_notelist.setAdapter(noteAdapter);
                            }
                        })
                        .setNeutralButton("取消",null).create().show();
                return true;
            }
        });
        return view;
    }

    private class ItemClick implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Notes note= notes.get(i);
            //数据查询对应内容
            Log.d("你点到我了",""+i);
            Log.d("note的内容",""+note);
            Intent intent_toupdate = new Intent(getActivity(), UpdateNoteActivity.class);
            intent_toupdate.putExtra("noteId",note.getNoteId());
            intent_toupdate.putExtra("noteTitle",note.getNoteTitle());
            intent_toupdate.putExtra("noteContent", note.getNoteContent());
            intent_toupdate.putExtra("bookId",note.getBookId());
            startActivity(intent_toupdate);

        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}