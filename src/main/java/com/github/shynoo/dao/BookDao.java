package com.github.shynoo.dao;

import com.github.shynoo.entity.book.Book;
import com.github.shynoo.entity.book.BookType;
import com.github.shynoo.entity.result.ResultStatus;

import java.util.List;

public interface BookDao{
    
    ResultStatus addBook(Book book);
    
    Book getBookById(String id);
    
    List<Book> searchBooksByName(String name);
    
    List<Book> searchBooksByType(BookType bookType);
    
    ResultStatus delBook(Book book);
}
