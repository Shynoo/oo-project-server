package com.github.shynoo.entity.book;

public class BookBuilder{
    
    Book book=new Book();
    
    BookBuilder(){
        
    }
    
    public BookBuilder name(String name){
        book.setName(name);
        return this;
    }
    
    public BookBuilder id(String id){
        book.setBookId(id);
        return this;
    }
    
    public BookBuilder type(BookType type){
        book.setBookType(type);
        return this;
    }
    
    public BookBuilder status(BookStatus status){
        book.setBookStatus(status);
        return this;
    }
    
    public Book build(){
        if (book.getName()!=null&&book.getBookId()!=null)
            return book;
        else throw new RuntimeException("Illegal Book Name or Book Id!");
    }
}
