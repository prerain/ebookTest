package com.example.ebook01.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.ebook01.R;
import com.example.ebook01.ReadbookActivity;
import com.example.ebook01.adapter.CatlogAdapter;
import com.example.ebook01.dao.BookDao;
import com.example.ebook01.dao.ChapterDao;
import com.example.ebook01.entity.Book;
import com.example.ebook01.entity.Chapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CatlogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CatlogFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int bookId;
    ChapterDao chapterDao;
    BookDao bookDao;
    private List<Chapter> chapters;
    ListView catloglist;
    public CatlogFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CatlogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CatlogFragment newInstance(String param1, String param2) {
        CatlogFragment fragment = new CatlogFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_catlog, container, false);
        catloglist = view.findViewById(R.id.catlog_list);
        bookId = Integer.parseInt(mParam2);
        bookDao = new BookDao(getActivity());
        chapterDao = new ChapterDao(getActivity());
        chapters = new ArrayList<>();

        Book book = new Book();
        List<Book> bookfind = new ArrayList<>();
        book = bookDao.findBookByid(bookId);
        Log.d("book",""+book);
        bookfind = bookDao.findBookByname(book.getBookName());
        Log.d("bookfind.size= ",""+bookfind.size());
        Log.d("first id",""+bookfind.get(0).getBookId());
        chapters = chapterDao.chapFindAll(bookfind.get(0).getBookId());
        Log.d("chapters.size",""+chapters.size());

        CatlogAdapter catlogAdapter = new CatlogAdapter(getActivity(),R.layout.fragment_catlog_item,chapters);
        catloglist.setAdapter(catlogAdapter);
        return view;
    }
}