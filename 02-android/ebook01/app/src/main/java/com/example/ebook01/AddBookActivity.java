package com.example.ebook01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ebook01.dao.BookDao;
import com.example.ebook01.dao.BookDaoImpl;
import com.example.ebook01.dao.IBookDao;
import com.example.ebook01.entity.Book;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddBookActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 0;
    ImageButton returnicon;
    TextView filepath;
    Button addbtn;
    ListView fileshow;
    List<Book> bookList;
    File currentParents;
    File[] currentFlie;
    File root;
    int shelfId;
    String name,path;
    SimpleAdapter simpleAdapter;
    IBookDao bookDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除标题栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_book);

        returnicon = findViewById(R.id.addbook_btn_return);
        filepath = findViewById(R.id.addbook_filepath);
        fileshow = findViewById(R.id.addbook_lv_file);
        addbtn = findViewById(R.id.addbook_btn_add);
        Intent intent = getIntent();
        shelfId = intent.getIntExtra("shelfId",0);
        bookDao = new BookDaoImpl(this);
        //ListView多选模式
        fileshow.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        bookList = new ArrayList<>();

        //判断是否有SD卡
        boolean isLoadSDcard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        //判断是否有权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        }
        if (isLoadSDcard){
            //获取SD卡根目录
            root = Environment.getExternalStorageDirectory();
            Log.d("root",""+root);
            currentParents = root;
            //获取目录下文件
            currentFlie = currentParents.listFiles();
            Log.d("currentFlie",""+currentFlie.length);
            inflateListView(currentFlie);

        }else {
            Toast.makeText(this, "找不到SD卡", Toast.LENGTH_SHORT).show();
        }
        setLinster();
    }



    private void setLinster() {
        //item的点击
        fileshow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                List<Integer> selectpos = new ArrayList<>();
                if(currentFlie[pos].isFile()){
                    LinearLayout fileitem = view.findViewById(R.id.file_item);
                    name = currentFlie[pos].getName();
                    path = currentParents.getAbsolutePath()+"/"+name;
                    //改变选中内容颜色
                    if (fileshow.isItemChecked(pos)){
                        fileitem.setBackgroundColor(getResources().getColor(R.color.item_selector,null));
                        Book book =new Book();
                        book.setBookName(name);
                        book.setBookPath(path);
                        book.setShelfId(shelfId);
                        book.setSource("本地");
                        Log.d("book",""+book);
                        bookList.add(book);
                    }else {
                        fileitem.setBackgroundColor(getResources().getColor(R.color.white,null));
                        bookList = bookdel(bookList,name,path,shelfId);
                    }
                    return;
                }
                File[] temp = currentFlie[pos].listFiles();
                if (temp ==null || temp.length == 0){
                    Toast.makeText(AddBookActivity.this, "文件夹为空或无法访问", Toast.LENGTH_SHORT).show();
                }else {
                    currentParents = currentFlie[pos];
                    currentFlie = temp;
                    //重新加载内容
                    inflateListView(currentFlie);
                }
            }
        });
        //返回上一级
        returnicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //是根目录就退出
                if (currentParents.getAbsolutePath().equals(root.getAbsolutePath())){
                    AddBookActivity.this.finish();
                }else {
                    currentParents = currentParents.getParentFile();
                    currentFlie = currentParents.listFiles();
                    inflateListView(currentFlie);
                }
            }
        });
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("添加,目前的booklist里有",""+bookList);
                bookDao.addBook(bookList);
                Intent intent = new Intent(AddBookActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void inflateListView(File[] currentFlie) {
        List<Map<String,Object>> list = new ArrayList<>();
        for (int i = 0 ;i<currentFlie.length;i++){
            Map<String,Object> map = new HashMap<>();
            map.put("filename",currentFlie[i].getName());
            if (currentFlie[i].isFile()){
                map.put("icon",R.mipmap.texticon);
            }else {
                map.put("icon",R.mipmap.floder);
            }
            list.add(map);
        }
        simpleAdapter= new SimpleAdapter(AddBookActivity.this,list,
                R.layout.addbook_listitem,new String[]{"filename","icon"},new int[]{R.id.filename,R.id.fileicon});
        fileshow.setAdapter(simpleAdapter);
        filepath.setText("当前路径"+currentParents.getAbsolutePath());
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 用户已授予SD卡读写权限
                    setLinster();
                } else {
                    // 用户拒绝了SD卡读写权限
                }
                return;
            }
        }
    }

    public List<Book> bookdel(List<Book> books,String name,String path,int shelfId){
        List<Book> books1 = new ArrayList<>();
        Book book =new Book();
        book.setBookName(name);
        book.setBookPath(path);
        book.setShelfId(shelfId);
        books1.remove(book);
        return books1;
    }
}