package com.example.controller;

import com.example.entity.Book;
import com.example.service.BookService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("book")
public class BookController {
    @Resource(name = "BookService")
    private BookService bookService;
    @RequestMapping("list")
    public String Booklist(Model model,@RequestParam(value = "pageNum",defaultValue = "1")int pageNum){
        PageInfo<Book> pageInfo = bookService.books(pageNum);
        List<Book> bookList = pageInfo.getList();
        if(bookList.size() >0){
            model.addAttribute("pageInfo",pageInfo);
            model.addAttribute("bookList",bookList);
        }else{
            model.addAttribute("msg","暂无数据");
        }
        return "book/book-list";
    }
    @PostMapping("search")
    public String Booksearch(String keyword,Model model,@RequestParam(value = "pageNum",defaultValue = "1")int pageNum){
        List<Book> searchBookList = bookService.findbyKeyword(keyword);
        if(searchBookList.size() > 0){
            model.addAttribute("bookList",searchBookList);
        }else{
            model.addAttribute("msg","暂无数据请重新查询");
        }
        return "book/book-list";
     }

    @GetMapping("add")
    public String Bookadd(){
        return "book/book-add";
    }
    @PostMapping("add")
    public String Bookup(Book book,Model model,@RequestParam(value = "file") MultipartFile file) throws IOException {
        if (bookService.addBookandFile(book,file)==1){
            return "redirect:/book/list";
        }else {
            model.addAttribute("str","书籍添加失败");
            return "error";
        }
    }
    //    public String Bookadd(Book book,Model model){
//        System.out.println(book);
//        if (bookService.addBook(book)==1){
//            return "redirect:/book/list";
//        }else {
//            model.addAttribute("str","书籍添加失败");
//            return "error";
//        }
//    }
    @GetMapping("update")
    public String Bookupdate(int bookId,Model model){
        Book book = bookService.findbyId(bookId);
        model.addAttribute("book",book);
        return "book/book-update";
    }
    @PostMapping("update")
    public String Bookupdate(Book book,Model model){
        if(bookService.updateBook(book)==1){
            return "redirect:/book/list";
        }else{
            model.addAttribute("str","书籍信息编辑失败");
            return "error";
        }
    }

    @RequestMapping("delete")
    public String Bookdelete(int bookId, Model model){
        Book delbook = bookService.findbyId(bookId);
        String path = delbook.getBookPath();
        File file = new File(path);
        if (file.exists()){
            file.delete();
        }
        if(bookService.deleteBook(bookId)==1){
            return "redirect:/book/list";
        }else{
            model.addAttribute("str","书籍信息删除失败");
            return "error";
        }
    }
}
