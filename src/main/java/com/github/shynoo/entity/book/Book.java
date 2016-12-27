package com.github.shynoo.entity.book;


import lombok.Data;

import java.util.Date;
import java.util.Random;

@Data
public class Book{
    
    private String bookId;
    
    private String name;
    
    private BookType bookType;
    
    private BookStatus bookStatus = BookStatus.IN_LIBIRARY;
    
    private Date borrowedDate;
    
    public void giveBack(){
        this.bookStatus = BookStatus.IN_LIBIRARY;
    }
    
    public static BookBuilder newBuilder(){
        return new BookBuilder();
    }
    
    public static String generateBookId(Book book){
//        return (book.getBookType().typeName+book.hashCode()).substring(0, 16);
        return book.getBookType().typeName+(""+new Random().nextInt(1<<10));
    }
    
    @Override
    public String toString(){
        return "name="+name+",id="+bookId+",type="+bookType.typeName;
    }
    
}
