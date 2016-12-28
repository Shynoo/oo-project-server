package com.github.shynoo.ooad.dao;

import com.github.shynoo.ooad.entity.book.Book;
import com.github.shynoo.ooad.entity.book.BookType;

import java.util.List;
import java.util.Map;

public interface BookDao{
    
    int addBook(Book book);
    
    Book getBookById(String id);
    
    List<Book> searchBooksByName(String name);
    
    List<Book> getBooksByName(String name);
    
    List<Book> searchBooksByType(BookType bookType);
    
    int deleteBook(Book book);
    
    Book getRandomUnborrowedBook();
    
    Map getAllBooks();
    
    void initData();
}
