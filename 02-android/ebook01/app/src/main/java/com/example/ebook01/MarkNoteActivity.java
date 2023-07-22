package com.example.ebook01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.example.ebook01.adapter.MarkNoteAdapter;
import com.example.ebook01.dao.BookDao;
import com.example.ebook01.ui.CatlogFragment;
import com.example.ebook01.ui.MarkFragment;
import com.example.ebook01.ui.NoteFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Arrays;
import java.util.List;

public class MarkNoteActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    MarkFragment markFragment;
    NoteFragment noteFragment;
    CatlogFragment catlogFragment;
    ImageView toshelf;
    int bookId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_mark_note);
        toshelf = findViewById(R.id.return_shelf);

        Intent read_intent = getIntent();
        bookId = read_intent.getIntExtra("bookId",0);

        tabLayout = findViewById(R.id.top_nav);
        viewPager2 = findViewById(R.id.myviewpage2);
        viewPager2.setSaveEnabled(false);
        List<String> title = initTile();
        MarkNoteAdapter adapter = new MarkNoteAdapter(this,initFragments());

        //viewpage2绑定adapter
        viewPager2.setAdapter(adapter);
        //tablayout 和viewpage2关联
        new TabLayoutMediator(tabLayout,viewPager2,true,
                ((tab, position) -> tab.setText(title.get(position)))).attach();
        toshelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MarkNoteActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private List<String> initTile() {
        return Arrays.asList("目录","书签","笔记");
    }

    public List<Fragment> initFragments(){
        catlogFragment =CatlogFragment.newInstance("目录",String.valueOf(bookId));
        markFragment = MarkFragment.newInstance("书签",String.valueOf(bookId));
        noteFragment = NoteFragment.newInstance("笔记",String.valueOf(bookId));
        return Arrays.asList(catlogFragment,markFragment,noteFragment);
    }

}