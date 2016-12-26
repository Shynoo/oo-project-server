package com.github.shynoo.service;

import com.github.shynoo.dao.BookDao;
import com.github.shynoo.entity.book.Book;
import com.github.shynoo.entity.book.BookType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("bookService")
public class BookService{
    
    @Autowired
    private BookDao bookDao;
    
    
    public List<Book> searchBookByName(String name){
        return bookDao.searchBooksByName(name);
    }
    
    public List<Book> searchBookByType(BookType type){
        return bookDao.searchBooksByType(type);
    }
    
    public Book getBookById(String id){
        return bookDao.getBookById(id);
    }
    
    public Book getRandomBook(){
        return bookDao.getRandomUnBorrowedBook();
    }
    
}
