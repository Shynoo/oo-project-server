package com.github.shynoo.dao;

import com.github.shynoo.entity.book.Book;
import com.github.shynoo.entity.book.BookStatus;
import com.github.shynoo.entity.book.BookType;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service("bookDao")
public class LocalBookDao implements BookDao{
    
    private Map<String, Book> books;
    
    private Map<String, List<Book>> nameToBook;
    
    
    
    public LocalBookDao(){
        books = new ConcurrentHashMap<>();
        nameToBook = new ConcurrentHashMap<>();
        initData();
    }
    
    @Override
    public void initData(){
        Book book;
        book = Book.newBook().id("CS121").name("算法导论").type(BookType.COMPUTER_SCIENCE).build();addBook(book);
        book = Book.newBook().name("细胞结构分析").id("BIO223").type(BookType.BIOLOGY).build();addBook(book);
        book = Book.newBook().name("数值分析").type(BookType.MATH).build();addBook(book);
        book = Book.newBook().name("常用英语语法").type(BookType.ENGLISH).build();addBook(book);
        book = Book.newBook().name("力学分析").type(BookType.PHYSICS).build();addBook(book);
        book = Book.newBook().name("有机化学基础").type(BookType.CHEMISTRY).build();addBook(book);
        book = Book.newBook().name("数据结构与算法").type(BookType.COMPUTER_SCIENCE).build();addBook(book);
        book = Book.newBook().name("无机化学入门").type(BookType.CHEMISTRY).build();addBook(book);
        book = Book.newBook().name("Java从入门到放弃").type(BookType.COMPUTER_SCIENCE).build();addBook(book);
        book = Book.newBook().name("精通C++").type(BookType.COMPUTER_SCIENCE).build();addBook(book);
        book = Book.newBook().name("物理化学基础").type(BookType.CHEMISTRY).build();addBook(book);
        book = Book.newBook().name("活着的细胞").type(BookType.BIOLOGY).build();addBook(book);
        book = Book.newBook().name("量子力学").type(BookType.PHYSICS).build();addBook(book);
        book = Book.newBook().name("高等数学基础").type(BookType.MATH).build();addBook(book);
        
    }
    
    @Override
    public int addBook(Book book){
        books.put(book.getBookId(),book);
        Object o = nameToBook.get(book.getName());
        if (o == null) {
            nameToBook.put(book.getName(), new LinkedList<>());
        }
        nameToBook.get(book.getName()).add(book);
        book.setBookStatus(BookStatus.IN_LIBIRARY);
        return 0;
    }
    
    @Override
    public Book getBookById(String id){
        return books.get(id);
    }
    
    
    @Override
    public List<Book> searchBooksByName(String name){
        List list = new LinkedList();
        for (Book book : books.values()){
            for (String s2 : name.split(" ")){
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
        List list = new LinkedList();
        for (Book book : books.values()){
            if (book.getBookType().equals(bookType)) {
                list.add(book);
            }
        }
        return list;
    }
    
    @Override
    public int deleteBook(Book book){
        books.remove(book.getBookId(), book);
        try{
            nameToBook.get(book.getName()).remove(book);
            return 0;
        } catch(NullPointerException e){
            return 400;
        }
    }
    
    @Override
    public Book getRandomUnBorrowedBook(){
        LinkedList ls=new LinkedList();
        for (Book b:books.values()){
            if (b.getBookStatus().equals(BookStatus.IN_LIBIRARY)){
                ls.add(b);
            }
        }
        if (ls==null||ls.size()==0){
            throw new NullPointerException("No new Book to choose!");
        }
        int size=new Random().nextInt(ls.size());
        return (Book) ls.get(size);
    
    }
    
    @Override
    public Map getAllBooks(){
        return books;
    }
    
    
}
