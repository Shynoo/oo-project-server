package com.github.shynoo.entity.book;


import com.github.shynoo.entity.user.User;
import lombok.Data;

import java.util.Date;

@Data
public class Book{
    
    private String bookId;
    
    private String name;
    
    private BookType bookType;
    
    private BookStatus bookStatus;
    
    private User user;
    
    private Date borrowedDate;
    
    public User getReader(){
        if (this.bookStatus==BookStatus.BORROWING_OUT)
            return this.user;
        else throw new RuntimeException("This Book Not Borrowing Out");
    }
    
    public static BookBuilder newBuilder(){
        return new BookBuilder();
    }
    
}
