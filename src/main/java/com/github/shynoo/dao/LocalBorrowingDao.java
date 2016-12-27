package com.github.shynoo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service("borrowingDao")
public class LocalBorrowingDao implements BorrowingDao{
    
    private Map<String,List<String>> userBooks;
    
    private Map<String,String> bookToUser;
    
    private Map<String,Date> bookOutDate;
    
    @Autowired
    private BookDao bookDao;
    
    LocalBorrowingDao(){
        userBooks=new ConcurrentHashMap<>();
        bookToUser=new ConcurrentHashMap<>();
        bookOutDate=new ConcurrentHashMap<>();
//        initData();
    }
    
    public Date bookOutDay(String id){
        return bookOutDate.get(id);
    }
    
    private void initData(){
        borrowOut("1",bookDao.getRandomUnBorrowedBook().getBookId());
        borrowOut("1",bookDao.getRandomUnBorrowedBook().getBookId());
        borrowOut("1",bookDao.getRandomUnBorrowedBook().getBookId());
        borrowOut("11310057",bookDao.getRandomUnBorrowedBook().getBookId());
        borrowOut("11310057",bookDao.getRandomUnBorrowedBook().getBookId());
        borrowOut("1",bookDao.getRandomUnBorrowedBook().getBookId());
    }
    
    @Override
    public int borrowOut(String user, String book){
        List ls=userBooks.get(user);
        if (ls==null){
            ls=new LinkedList();
            userBooks.put(user,ls);
        }
        ls.add(book);
        bookOutDate.put(book,new Date());
        bookToUser.put(book,user);
        return 0;
    }
    
    
    @Override
    public int giveBack(String book){
        userBooks.get(bookToUser.get(book)).remove(book);
        bookToUser.remove(book);
        bookOutDate.remove(book);
        return 0;
    }
    
    
    @Override
    public List<String> getUserAllBorrowedBooks(String user){
        List ls= userBooks.get(user);
        if (ls==null){
            ls=new LinkedList();
            userBooks.put(user,ls);
        }
        return ls;
    }
    
    @Override
    public String getBookOwnerId(String book){
        return bookToUser.get(book);
    }
    
    
}
