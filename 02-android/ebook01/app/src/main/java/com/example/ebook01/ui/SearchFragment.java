package com.example.ebook01.ui;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ebook01.R;
import com.example.ebook01.adapter.BookSearchAdapter;
import com.example.ebook01.entity.Book;
import com.example.ebook01.utils.Httpurl;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchFragment extends Fragment {
    String baseUrl = Httpurl.getUrl();
    private EditText etkeyword;
    private Button searchbtn;
    BookSearchAdapter brsAdapter;
    RecyclerView recyclerView;

    public SearchFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search,container,false);

        etkeyword = view.findViewById(R.id.keyword);
        searchbtn = view.findViewById(R.id.btn_search);

        Context context = view.getContext();
        recyclerView = view.findViewById(R.id.search_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyword1 = etkeyword.getText().toString();
                Log.d("获得的keyword是",""+keyword1);
                List<Book> booksearch = new ArrayList<>();
                if (keyword1.equals("")){
                    Toast.makeText(context, "内容为空", Toast.LENGTH_SHORT).show();
                }else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //联网查询并记录到list中
                            try {
                                OkHttpClient okHttpClient = new OkHttpClient();
                                FormBody formBody = new FormBody.Builder().
                                        add("keyword",""+keyword1).build();
                                Log.d("formbody",""+formBody);
                                Request request = new Request.Builder()
                                        .url(baseUrl+"/app/searchbook")
                                        .post(formBody)
                                        .build();
                                Response response = okHttpClient.newCall(request).execute();
                                String result = response.body().string();//获取后端接口返回过来的JSON格式的结果
                                JSONArray jsonArray = new JSONArray(result);//将文本格式的JSON转换为JSON数组
                                for(int i=0;i<jsonArray.length();i++){ //遍历这个数组
                                    JSONObject jsonObject = jsonArray.getJSONObject(i); //取出JSON元素
                                    Book book =new Book();
                                    book.setBookId(jsonObject.getInt("bookId"));
                                    book.setBookName(jsonObject.getString("bookName"));
                                    book.setBookAuthor(jsonObject.getString("bookAuthor"));
                                    book.setBookPrice(jsonObject.getInt("bookPrice"));
                                    book.setBookDetalis(jsonObject.getString("bookDetalis"));
                                    book.setBookPath(jsonObject.getString("bookPath"));
                                    book.setSource("下载");
                                    booksearch.add(book);
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }).start();
                }
                brsAdapter = new BookSearchAdapter(booksearch,getActivity());
                recyclerView.setAdapter(brsAdapter);
                itemclick();
            }
        });
        return view;
    }

    private void itemclick() {
        brsAdapter.setItemClickListener(new BookSearchAdapter.ItemClickListener() {
            @Override
            public void itemClick(int pos, Book book) {
                Log.d("你点击的是",""+pos);
                Log.d("具体内容是",""+book);
                if (book.getBookPath().equals("")){
                    Log.d("路径为空","");
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "文件不存在", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("你要下载"+book.getBookName()+"吗？")
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Log.d("下载内容",""+book.getBookName());
//                                download(book);
                            }
                        })
                        .setNeutralButton("取消",null).create().show();
            }
            }
        });
    }

}