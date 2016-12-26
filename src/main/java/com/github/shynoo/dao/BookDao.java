package com.github.shynoo.dao;

import com.github.shynoo.entity.book.Book;
import com.github.shynoo.entity.book.BookType;

import java.util.List;

public interface BookDao{
    
    int addBook(Book book);
    
    Book getBookById(String id);
    
    List<Book> searchBooksByName(String name);
    
    List<Book> searchBooksByType(BookType bookType);
    
    int deleteBook(Book book);
    
    Book getRandomUnBorrowedBook();
    
    void initData();
}
