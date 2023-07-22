package com.example.ebook01.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ebook01.R;
import com.example.ebook01.entity.NovelContentPage;
import com.example.ebook01.entity.NovelPageWindow;
import com.example.ebook01.utils.PageSplit;


import java.util.List;

public class Page2Adapter extends RecyclerView.Adapter<Page2Adapter.PageViewHolder> {

    private List<NovelContentPage> pages;
    private Context context;
    private NovelPageWindow chap;
    private PageListener pageListener;

    public Page2Adapter(Context context, NovelPageWindow novelPageWindow){
        this.chap = novelPageWindow;
        this.context = context;
    }

    public void setPageListener(PageListener pageListener) {
        this.pageListener = pageListener;
    }

    @NonNull
    @Override
    public PageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PageViewHolder pageViewHolder = new PageViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_read1_page, parent, false));
        return pageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PageViewHolder holder, int position) {
        pages = chap.getPages();
        NovelContentPage novelContentPage = pages.get(position);
        holder.tv_content.setText(novelContentPage.getPage_content());
        if (novelContentPage.isTempPage()) {
            Log.d("bind view","found temp page: "+position);
            holder.tv_content.post(()->{
                List<NovelContentPage> page = PageSplit.getPage(novelContentPage, holder.tv_content);
                if (chap.isSingleWindow())pages = page;
                else {
                    int i = pages.indexOf(novelContentPage);
                    pages.addAll(i,page);
                    pages.remove(novelContentPage);
                }
                chap.setPages(pages);
                if (pageListener!=null)pageListener.onPageSplitDone(chap,page,novelContentPage.getBelong_to_chapID());
            });
        }

    }

    @Override
    public int getItemCount() {
        return chap.getPageNum();
    }

    class PageViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_content;

        public PageViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_content = itemView.findViewById(R.id.tv_txtcontent);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("你点击了我","");
                    if (pageListener!=null){
                        pageListener.onMenu();
                    }

                }
            });
        }
    }

    public interface PageListener{
        void onPageSplitDone(NovelPageWindow update_chap, List<NovelContentPage> new_pages, int chapID);

        void onMenu();
    }
}

