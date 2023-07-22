package com.example.ebook01;

import static androidx.viewpager2.widget.ViewPager2.SCROLL_STATE_IDLE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ebook01.adapter.Page2Adapter;
import com.example.ebook01.dao.BookDao;
import com.example.ebook01.dao.BookmarkDao;
import com.example.ebook01.dao.NotesDao;
import com.example.ebook01.dao.NovelContentPageDao;
import com.example.ebook01.entity.Book;
import com.example.ebook01.entity.Bookmark;
import com.example.ebook01.entity.Notes;
import com.example.ebook01.entity.NovelContentPage;
import com.example.ebook01.entity.NovelPageWindow;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ReadbookActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 0;
    private BufferedReader in;
    LinearLayout top,bottom;
    TextView catlog,setting,toshelf;
    ViewPager2 viewpager2;
    ImageView addMark,addNote;
    int currentpagenum;
    int bookId;
    boolean flag=true;
    private List<NovelPageWindow> chapList;
    private int window_page_index;//页面在整个window中的索引
    private int chap_page_index;//页面在自身章节中的索引
    private int chap_index;
    private NovelPageWindow currentNovelChap;
    private enum WindowType{left,right,match}//当前页的类型，左边有章节，右边有章节，在本章节
    WindowType windowType;
    NovelContentPageDao pageDao;
    BookDao bookDao;
    BookmarkDao markDao;
    NotesDao notesDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除标题栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_readbook);
        //控件
        top = findViewById(R.id.read_top_menu);
        bottom = findViewById(R.id.read_bottom_menu);
        catlog =findViewById(R.id.read_catalog);
        setting = findViewById(R.id.read_setting);
        viewpager2 = findViewById(R.id.read1_viewpage2);
        toshelf = findViewById(R.id.read_return);
        addMark = findViewById(R.id.read_bookmark);
        addNote = findViewById(R.id.read_booknote);
        //DAO
        pageDao = new NovelContentPageDao(ReadbookActivity.this);
        markDao = new BookmarkDao(ReadbookActivity.this);
        notesDao = new NotesDao(ReadbookActivity.this);
        bookDao = new BookDao(ReadbookActivity.this);
        //获得信息来自BookActivity
        Intent intent_read=getIntent();
        bookId = intent_read.getIntExtra("bookId",0);
        //监听
        toshelf.setOnClickListener(this);
        addMark.setOnClickListener(this);
        addNote.setOnClickListener(this);
        catlog.setOnClickListener(this);
        setting.setOnClickListener(this);
        //加入数据
        initDate2();
        Page2Adapter adapter = new Page2Adapter(this,currentNovelChap);
        pageLinster(adapter);
        viewpager2.setAdapter(adapter);
        viewpager2.setOffscreenPageLimit(5);
        viewpager2.setCurrentItem(window_page_index,false);
        //翻页样式
        viewpager2.setPageTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                if (position <= 0.0f) {
                    page.setTranslationX(0.0f);
                    page.setTranslationZ(0.0f);
                } else {
                    page.setTranslationX((-page.getWidth() * position));
                    page.setTranslationZ(-position);
                }
            }
        });
        viewpager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                System.out.println("position = " + position);
                int total_window_pages = viewpager2.getAdapter().getItemCount();
                int total_chap_pages = currentNovelChap.getPageNum();
                window_page_index = position;
                switch(windowType){
                    case left:
                        NovelPageWindow left_chap = chapList.get(chap_index - 1);
                        chap_page_index = position - left_chap.getPages().size();
                        break;
                    case right:
                        chap_page_index = position;
                        break;
                    case match:chap_page_index = position;
                        break;
                    default:
                }
                Log.d("page change",String.format("window %d/%d",window_page_index, total_window_pages-1));
                Log.d("page change",String.format("chap %d/%d",chap_page_index, total_chap_pages-1));
            }
            //
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                if (state == SCROLL_STATE_IDLE){

                    int total_window_pages = viewpager2.getAdapter().getItemCount();
                    int total_chap_pages = currentNovelChap.getPages().size();

                    if (window_page_index == total_window_pages -1){
                        Log.d("page change","reach the last page of the window");
                        if ((chap_index+1)>=chapList.size()){
                            Log.d("page change","also the last chap of the book!");
                            return;
                        }
                        window_page_index = chap_page_index;
                        ArrayList<NovelContentPage> temp_pages = new ArrayList<>(currentNovelChap.getPages());
                        temp_pages.addAll(chapList.get(chap_index+1).getPages());//single page
                        NovelPageWindow novelPageWindow = new NovelPageWindow();
                        novelPageWindow.setPages(temp_pages);
                        novelPageWindow.setSingleWindow(false);
                        Page2Adapter adapter = new Page2Adapter(ReadbookActivity.this, novelPageWindow);
                        pageLinster(adapter);
                        viewpager2.post(()->{
                            viewpager2.setAdapter(adapter);
                            viewpager2.setCurrentItem(window_page_index,false);
                        });
                        windowType = WindowType.right;
                    }
                    if (window_page_index == 0){
                        Log.d("page change","reach the first page of the window");
                        if ((chap_index-1)<0){
                            Log.d("page change","also the first chap of the book!");
                            return;
                        }
                        ArrayList<NovelContentPage> temp_pages = new ArrayList<>(chapList.get(chap_index - 1).getPages());
                        window_page_index += temp_pages.size();
                        temp_pages.addAll(currentNovelChap.getPages());
                        NovelPageWindow novelPageWindow = new NovelPageWindow();
                        novelPageWindow.setPages(temp_pages);
                        novelPageWindow.setSingleWindow(false);
                        Page2Adapter adapter = new Page2Adapter(ReadbookActivity.this, novelPageWindow);
                        pageLinster(adapter);
                        viewpager2.post(()->{
                            viewpager2.setAdapter(adapter);
                            viewpager2.setCurrentItem(window_page_index,false);
                        });
                        windowType = WindowType.left;

                    }
                    if (chap_page_index == total_chap_pages){
                        Log.d("page change","start the new chap (next)"+(chap_index+1));
                        chap_index++;
                        if (chap_index>=chapList.size())return;
                        currentNovelChap = chapList.get(chap_index);
                        chap_page_index = 0;
                        windowType = WindowType.left;
                    }
                    if (chap_page_index == -1){
                        Log.d("page change","start the new chap (last)"+(chap_index-1));
                        chap_index--;
                        if (chap_index < 0)return;
                        currentNovelChap = chapList.get(chap_index);
                        chap_page_index = currentNovelChap.getPages().size()-1;
                        windowType = WindowType.right;
                    }
                }
            }
        });
        //判断是否有权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.read_return:
                finish();
                break;
            case R.id.read_bookmark:
                if (flag) {
                    //有书签图标
                    addMark.setBackground(getDrawable(R.drawable.baseline_bookmark));
                    Toast.makeText(ReadbookActivity.this, "添加书签", Toast.LENGTH_SHORT).show();
                    Bookmark bookmark = new Bookmark();
                    bookmark.setMarkName("书签"+currentpagenum);
                    bookmark.setLocation(currentpagenum);
                    bookmark.setBookId(bookId);
                    int result =  markDao.addMark(bookmark);
                    Log.d("添加书签是否成功",""+result);
                    flag = false;
                } else if (!flag) {
                    //无书签图标
                    addMark.setBackground(getDrawable(R.drawable.bookmark_border_24));
                    Toast.makeText(ReadbookActivity.this, "删除书签", Toast.LENGTH_SHORT).show();
                    markDao.delMark(currentpagenum,bookId);
                    flag=true;
                }
                break;
            case R.id.read_booknote:
                Toast.makeText(ReadbookActivity.this, "添加笔记", Toast.LENGTH_SHORT).show();
                //添加到数据库   内容有-笔记名（1）,所属书号，所属章节号，内容。
                Notes note = new Notes();
                note.setNoteTitle("笔记");
                note.setBookId(bookId);
                note.setLocation(currentpagenum);
                Log.d("currentpagenum",""+currentpagenum);
                notesDao.addNote(note,bookId);
                break;
            case R.id.read_catalog:
                Intent intent = new Intent(ReadbookActivity.this,MarkNoteActivity.class);
                intent.putExtra("bookId",bookId);
                startActivity(intent);
                break;
            case R.id.read_setting:
                Toast.makeText(ReadbookActivity.this, "暂不开放", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void pageLinster(Page2Adapter adapter) {
        adapter.setPageListener(new Page2Adapter.PageListener() {
            @Override   //分页部分
            public void onPageSplitDone(NovelPageWindow update_chap, List<NovelContentPage> new_pages, int chapID) {
                chapList.get(chapID).setPages(new_pages);
                //根据chap_page_index 调整window_page_index
                int total_chap = currentNovelChap.getPageNum();
                int total_window = viewpager2.getAdapter().getItemCount();
                currentpagenum = chapID;
                switch(windowType){
                    case left:
                        NovelPageWindow left_chap = chapList.get(chap_index - 1);
                        window_page_index = chap_page_index + left_chap.getPageNum();
                        break;
                    case right:
                        window_page_index = chap_page_index;
                        break;
                    case match:
                        window_page_index = chap_page_index;
                        break;
                }
                viewpager2.post(()->{
                    Page2Adapter adapter = new Page2Adapter(ReadbookActivity.this,update_chap);
                    pageLinster(adapter);
                    viewpager2.setAdapter(adapter);
                    viewpager2.setCurrentItem(window_page_index,false);
                });
                Log.d("page split",String.format("chap %d/%d",chap_page_index, total_chap -1));
                Log.d("page split",String.format("window %d/%d",window_page_index, total_window -1));
        }
            @Override   //菜单的显示隐藏
            public void onMenu() {
                if(top.getVisibility()==View.GONE){
                    Log.d("显示","");
                    top.setVisibility(View.VISIBLE);
                    bottom.setVisibility(View.VISIBLE);
                    markicon();
                }else if(top.getVisibility() == View.VISIBLE){
                    Log.d("隐藏","");
                    top.setVisibility(View.GONE);
                    bottom.setVisibility(View.GONE);
                }
            }
        });

    }
    public void markicon(){
        //判断当前页是否有书签
        Bookmark markfind = markDao.findbyChapAndBook(bookId,currentpagenum);
        Log.d("markfind",""+markfind);
        if (markfind.getMarkId()!=0){
            addMark.setBackground(getDrawable((R.drawable.baseline_bookmark)));//有书签
            flag = false;
        }
        if (markfind.getMarkId()==0){
            addMark.setBackground(getDrawable(R.drawable.bookmark_border_24));//没书签
            flag = true;
        }
    }
    public void initDate2(){
        //章节ID从0开始
        Book currentbook = new Book();
        currentbook = bookDao.findBookByid(bookId);//当前书本信息
        //同名书籍的信息
        List<Book> bookfind = bookDao.findBookByname(currentbook.getBookName());
        Log.d("bookfind.size==",""+bookfind.size());
        Log.d("boofind",""+bookfind);
        int firstbookId = bookfind.get(0).getBookId();
        String path = bookfind.get(0).getBookPath();
        Log.d("path",""+path);
        int pagsize =2000;//每章节字数，固定的
        char[] chars = new char[pagsize];//存放内容
        List<NovelContentPage> pages = new ArrayList<>();
        int count=1;//章节编号
        if (pageDao.findByBookId(firstbookId).size()==0){ //不存在则加入
            try {
                in = new BufferedReader(new FileReader(path));
                while ((in.read(chars,0,pagsize)!=-1)){
                    NovelContentPage page = new NovelContentPage();
                    String title = "第"+count+"章";
                    page.setTitle(title);
                    page.setBelong_to_chapID(count-1);
                    page.setPage_content(new String(chars));
                    page.setIsTempPage2(1);
                    page.setBookId(firstbookId);
                    pages.add(page);
                    count++;
                }
                pageDao.addNovelContentPage(pages);
                Log.d("add方法","被调用");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        List<NovelContentPage> pagesTest = new ArrayList<>();
        //从数据库取数据
        pagesTest = pageDao.findByBookId(firstbookId);
        chapList = new ArrayList<>();
        for (int i = 0; i < pagesTest.size(); i++) {
            if (pagesTest.get(i).getIsTempPage2()==1){
                pagesTest.get(i).setTempPage(true);
            }
            NovelPageWindow chap = new NovelPageWindow();
            List<NovelContentPage> pageuse = new ArrayList<>();
            pageuse.add(pagesTest.get(i));
            chap.setPages(pageuse);
            chapList.add(chap);
        }
        chap_index = 0;
        chap_page_index = 0;
        window_page_index = chap_page_index;
        currentNovelChap = chapList.get(chap_index);
        windowType = WindowType.match;
        currentNovelChap.setSingleWindow(true);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 用户已授予SD卡读写权限
                } else {
                    // 用户拒绝了SD卡读写权限
                }
                return;
            }
        }
    }

}