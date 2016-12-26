package com.github.shynoo.entity.book;


import com.github.shynoo.entity.user.User;
import lombok.Data;

import java.util.Date;
import java.util.Random;

@Data
public class Book{
    
    private String bookId;
    
    private String name;
    
    private BookType bookType;
    
    private BookStatus bookStatus = BookStatus.IN_LIBIRARY;
    
    private User user;
    
    private Date borrowedDate;
    
    public User getReader(){
        if (this.bookStatus == BookStatus.BORROWING_OUT)
            return this.user;
        return null;
    }
    
    public void borrowOut(User user){
        borrowedDate = new Date();
        this.bookStatus = BookStatus.BORROWING_OUT;
//        this.user = user;
    }
    
    public void giveBack(){
        this.bookStatus = BookStatus.IN_LIBIRARY;
        
    }
    
    
    public static BookBuilder newBuilder(){
        return new BookBuilder();
    }
    
    public static String generateBookId(Book book){
//        return (book.getBookType().typeName+book.hashCode()).substring(0, 16);
        return book.getBookType().typeName+(new Random().nextInt(1<<12));
    }
    
    public String toString(){
        return "name="+name+",id="+bookId+",type="+bookType.typeName;
    }
    
}
