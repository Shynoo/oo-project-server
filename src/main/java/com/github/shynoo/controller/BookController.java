package com.github.shynoo.controller;


import com.github.shynoo.entity.book.Book;
import com.github.shynoo.entity.book.BookType;
import com.github.shynoo.entity.result.Result;
import com.github.shynoo.entity.result.ResultStatus;
import com.github.shynoo.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class BookController{
    
    @Autowired
    private BookService bookService;
    
    
    @RequestMapping(value="/search")
    public Result searchBookByName(@RequestParam String q){
        try{
            List<Book> ls=bookService.searchBookByName(q);
            return new Result(ResultStatus.SUCCESS,ls);
        } catch(Exception e){
            log.error(e.getMessage());
        }
        return new Result(ResultStatus.FAILURE);
    }
    
    
    @RequestMapping(value="/search")
    public Result searchBookByName(@RequestParam(name="type") int typeId){
        try{
            BookType type=BookType.of(typeId);
            
            if (type==null){
                return new Result(ResultStatus.FAILURE,"Unknown type id!","");
            }
            
            List<Book> ls=bookService.searchBookByType(type);
            
            return new Result(ResultStatus.SUCCESS,ls);
            
        } catch(Exception e){
            log.error(e.getMessage());
        }
        return new Result(ResultStatus.FAILURE);
    }
    
    
    @RequestMapping(value ="/book/{id}")
    public Result getBookById(String id){
        
        Book book=bookService.getBookById(id);
        if (book==null){
            return new Result(ResultStatus.FAILURE,"Unknown Book Id","");
        }
        
        return new Result(ResultStatus.SUCCESS,book);
    }
    
    
    
}
