package com.github.shynoo.ooad.service;

import com.github.shynoo.ooad.dao.BookDao;
import com.github.shynoo.ooad.entity.book.Book;
import com.github.shynoo.ooad.entity.book.BookType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
        return bookDao.getRandomUnborrowedBook();
    }
    
    public Map<String,Book> getAllBooks(){
        return bookDao.getAllBooks();
    }
}
