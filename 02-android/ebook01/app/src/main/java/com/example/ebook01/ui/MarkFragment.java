package com.example.ebook01.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ebook01.BookActivity;
import com.example.ebook01.R;
import com.example.ebook01.ReadbookActivity;
import com.example.ebook01.adapter.MarkAdapter;
import com.example.ebook01.dao.BookmarkDao;
import com.example.ebook01.entity.Book;
import com.example.ebook01.entity.Bookmark;
import com.example.ebook01.entity.Notes;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MarkFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class MarkFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int bookId;
    BookmarkDao markDao;
    ListView lv_marklist;
    List<Bookmark> bookmarkList;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MarkFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MarkFragment newInstance(String param1, String param2) {
        MarkFragment fragment = new MarkFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MarkFragment() {
        // Required empty public constructor
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
        View view = inflater.inflate(R.layout.fragment_mark,container,false);
        bookId = Integer.parseInt(mParam2);
        markDao = new BookmarkDao(getActivity());
        Log.d("Mark,bookid=",""+bookId);
        lv_marklist = view.findViewById(R.id.mark_list);

        Log.d("booklist",""+bookmarkList);

        init();

        ItemClick itemClick = new ItemClick();
        lv_marklist.setOnItemClickListener(itemClick);
        return view;
    }

    private void init() {
        bookmarkList = markDao.findbybookid(bookId);//查询当前书籍所有书签
        MarkAdapter mkAdapter = new MarkAdapter(getActivity(),R.layout.fragment_mark_item,bookmarkList);
        lv_marklist.setAdapter(mkAdapter);
    }

    private class ItemClick implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
            Bookmark bookmark = bookmarkList.get(pos);
            //数据查询对应内容
            Log.d("你点到我了",""+pos);
            Log.d("mark的内容",""+bookmark);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("要做什么操作？")
                    .setNegativeButton("删除", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            markDao.delMark(bookmark.getLocation(),bookId);
                            Toast.makeText(getActivity(), "删除了", Toast.LENGTH_SHORT).show();
                            init();
                        }

                    })
                    .create().show();

        }
    }
}