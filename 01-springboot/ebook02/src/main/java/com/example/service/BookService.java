package com.example.service;

import com.example.dao.BookMapper;
import com.example.entity.Book;
import com.example.entity.Notice;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service("BookService")
public class BookService {
    @Resource(name = "BookMapper")
    private BookMapper bookMapper;

    public PageInfo<Book> books(int pageNum){
        PageHelper.startPage(pageNum,5);
        List<Book> bookList = bookMapper.findAll();
        PageInfo<Book> pageInfo = new PageInfo<>(bookList);
        return pageInfo;
    }

    public Book findbyId(int bookId){
        return bookMapper.findbyId(bookId);
    }
    public int updateBook(Book book){
        return bookMapper.updateBook(book);
    }
    public int addBookandFile(Book book, MultipartFile file) throws IOException {
        //保存到静态资源下
        ApplicationHome applicationHome = new ApplicationHome(this.getClass());
        String path1 = applicationHome.getDir().getParentFile().getParentFile().getAbsolutePath() +
                "\\src\\main\\resources\\static\\file";
        File uploadDir = new File(path1);
        if (!uploadDir.exists()){
            uploadDir.mkdir();
        }
        //获得文件全称,取出文件后缀
        String originalFilename = file.getOriginalFilename();
        //文件保存路径
        String saveFilepath = path1+"\\"+originalFilename;
        File saveFile =new File(saveFilepath);
        file.transferTo(saveFile);
        book.setBookPath(saveFilepath);
        System.out.println(saveFilepath);
        return bookMapper.addBook(book);
    }
    public List<Book> findbyKeyword(String keyword){
        return bookMapper.findBookList(keyword);
    }

    public int addBook(Book book){
        return bookMapper.addBook(book);
    }

    public int deleteBook(int bookId){
        return bookMapper.deleteBook(bookId);
    }
}
