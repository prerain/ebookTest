package com.example.ebook01.utils;

import android.graphics.Rect;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.ebook01.entity.NovelContentPage;

import java.util.ArrayList;
import java.util.List;

//用于给长文本分页
public class PageSplit {
    public static int getPageLineCount(TextView view){
        /*
         * The first row's height is different from other row.
         */
        int h = view.getBottom() - view.getTop();
        int firstH=getLineHeight(0,view);
        int otherH=getLineHeight(1,view);
        int lines = 1;
        if (otherH!=0)lines = (h-firstH)/otherH + 1;//仅一行时返回1
        return lines;
    }
    //获取某一行的行高
    public static int getLineHeight(int line,TextView view){
        Rect rect=new Rect();
        view.getLineBounds(line,rect);
        return rect.bottom-rect.top;
    }
    public static List<NovelContentPage> getPage(@NonNull NovelContentPage tempPage, TextView textView){
        String mContent = tempPage.getPage_content();
        textView.setText(mContent);
        int count=textView.getLineCount();
        int pCount=getPageLineCount(textView);
        int pageNum = (int) Math.ceil((double) ((double) count/(double) pCount));
        List<NovelContentPage> pages = new ArrayList<>();
        for(int i=0;i < pageNum;i++){
            NovelContentPage current_page = new NovelContentPage();
            int end_pos = mContent.length();
            int start_pos = 0;
            if (i!=0)start_pos = pages.get(i-1).getEnd_pos();
            if (i!=pageNum-1)end_pos = textView.getLayout().getLineEnd((i+1)*pCount-1);
            current_page.setStart_pos(start_pos);
            current_page.setEnd_pos(end_pos);
            current_page.setPage_id(i);
            if (i==0){
                current_page.setFirstPage(true);
                current_page.setTitle(tempPage.getTitle());
            }
            current_page.setPage_content(mContent.substring(start_pos,end_pos));
            current_page.setTempPage(false);
            pages.add(current_page);
        }
        return pages;
    }

}
