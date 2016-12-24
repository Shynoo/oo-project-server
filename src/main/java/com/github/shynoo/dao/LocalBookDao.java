package com.github.shynoo.dao;

import com.github.shynoo.entity.book.Book;
import com.github.shynoo.entity.book.BookStatus;
import com.github.shynoo.entity.book.BookType;
import com.github.shynoo.entity.result.ResultStatus;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service("bookDao")
public class LocalBookDao implements BookDao{
    
    private Map<String,Book> books;
    
    private Map<String,List<Book>> nameToBook;
    
    public LocalBookDao(){
        books=new ConcurrentHashMap<>();
        nameToBook =new ConcurrentHashMap<>();
    }
    
    @Override
    public ResultStatus addBook(Book book){
        Object o= nameToBook.get(book.getName());
        
        if (o==null){
            nameToBook.put(book.getName(),new LinkedList<Book>());
        }
        
        nameToBook.get(book.getName()).add(book);
        
        book.setBookStatus(BookStatus.IN_LIBIRARY);
        
        return ResultStatus.SUCCESS;
    }
    
    @Override
    public Book getBookById(String id){
        return books.get(id);
    }
    
    @Override
    public List<Book> searchBooksByName(String name){
        List list=new LinkedList();
        
        for (Book book:books.values()){
            for (String s2:name.split(" ")){
                if (book.getName().contains(s2)) {
                    list.add(book);
                    break;
                }
            }
        }
        return list;
    }
    
    @Override
    public List<Book> searchBooksByType(BookType bookType){
        List list=new LinkedList();
        
        for (Book book:books.values()){
            if (book.getBookType().equals(bookType)){
                list.add(book);
            }
        }
        return list;
    }
    
    @Override
    public ResultStatus delBook(Book book){
        books.remove(book.getBookId(),book);
        try{
            nameToBook.get(book.getName()).remove(book);
            return ResultStatus.SUCCESS;
        }catch(NullPointerException e){
            return ResultStatus.SUCCESS;
        }
    }
    
    
}
