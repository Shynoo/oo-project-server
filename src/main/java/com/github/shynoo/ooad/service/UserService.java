package com.github.shynoo.ooad.service;

import com.github.shynoo.ooad.dao.BookDao;
import com.github.shynoo.ooad.dao.BorrowingDao;
import com.github.shynoo.ooad.dao.UserDao;
import com.github.shynoo.ooad.entity.book.Book;
import com.github.shynoo.ooad.entity.book.BookStatus;
import com.github.shynoo.ooad.entity.result.Result;
import com.github.shynoo.ooad.entity.result.ResultStatus;
import com.github.shynoo.ooad.entity.user.User;
import com.github.shynoo.ooad.entity.user.UserType;
import com.github.shynoo.ooad.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service("userService")
public class UserService{
    
    @Autowired
    private UserDao userDao;
    @Autowired
    private BookDao bookDao;
    @Autowired
    private BorrowingDao borrowingDao;
    
    UserService(){
        
    }
    
    public void initData(){
        borrowBook("foo",bookDao.getRandomUnborrowedBook().getBookId());
        borrowBook("foo",bookDao.getRandomUnborrowedBook().getBookId());
        borrowBook("11310057",bookDao.getRandomUnborrowedBook().getBookId());
        borrowBook("11310057",bookDao.getRandomUnborrowedBook().getBookId());
        borrowBook("admin",bookDao.getRandomUnborrowedBook().getBookId());
    }
    
    public Result checkUserPassword(String id, String password){
        String realPasswd = userDao.getUserPassword(id);
        if (password.equals(realPasswd)) {
            User user = userDao.getUserById(id);
            return new Result(ResultStatus.SUCCESS, user);
        }
        return new Result(ResultStatus.FAILURE);
        
    }
    
    public boolean isInited=false;
    
    
    public User getUserById(String id){
        if (!isInited){
            isInited=true;
            initData();
        }
        User user = userDao.getUserById(id);
//        return new Result(ResultStatus.SUCCESS, user);
        return user;
    }
    
    
    public ResultStatus addUser(User user, User newUser){
        if (user.couldManageUser()) {
            if (getUserById(newUser.getId())!=null){
                return ResultStatus.FAILURE;
            }
            userDao.addUser(newUser);
            return ResultStatus.SUCCESS;
        }
        return ResultStatus.FAILURE;
    }
    
    
    public ResultStatus deletUser(User user, User delUser){
        try{
            if (user.couldManageUser()) {
                userDao.deleteUser(delUser);
                return ResultStatus.SUCCESS;
            }
        } catch(Exception e){
            return (ResultStatus.UNKNOWN_RESULT);
        }
        return (ResultStatus.FAILURE);
    }
    
    public ResultStatus borrowBook(User user,Book book){
        if ((!checkUserCouldBorrowBook(user))||(!checkBookCouldBorrowed(book))){
            return ResultStatus.FAILURE;
        }
        borrowingDao.borrowOut(user.getId(),book.getBookId());
        book.setBookStatus(BookStatus.BORROWING_OUT);
        book.setBorrowedDate(LocalDate.now());
        return ResultStatus.SUCCESS;
    }
    
    public ResultStatus borrowBook(String user,String book){
        return borrowBook(userDao.getUserById(user),bookDao.getBookById(book));
    }
        
        
    public ResultStatus giveBackBook(Book book){
        book.setBookStatus(BookStatus.IN_LIBIRARY);
        borrowingDao.giveBack(book.getBookId());
        return ResultStatus.SUCCESS;
    }
    
    public boolean checkBookCouldBorrowed(Book book){
        if (book.getBookStatus().equals(BookStatus.IN_LIBIRARY)){
            return true;
        }
        return false;
    }
    
    
    public ResultStatus addBook(User user, Book book){
        if (user.couldManageBook()) {
            if (bookDao.getBookById(book.getBookId())!=null){
                return ResultStatus.FAILURE;
            }
            bookDao.addBook(book);
            return ResultStatus.SUCCESS;
        }
        return ResultStatus.FAILURE;
    }
    
    
    public ResultStatus deleteBook(User user, Book book){
        if (user.couldManageBook()) {
            bookDao.deleteBook(book);
            return ResultStatus.SUCCESS;
        }
        return ResultStatus.UNKNOWN_RESULT;
    }
    
    
    public void changeUserType(User user, UserType userType){
        user.setUserType(userType);
        userDao.updateUser(user.getId(),user);
    }
    
    public boolean checkUserCouldBorrowBook(User user){
        List<String> ls=borrowingDao.getUserAllBorrowedBooks(user.getId());
        if (ls!=null&&ls.size()>=user.getUserType().maxBorrowingBookNumber){
            return false;
        }
        Date now=new Date();
        for (String bookId:ls){
            if (DateUtil.defferentNumber(borrowingDao.bookOutDay(bookId),now)>=user.getUserType().maxBorrowingDay){
                return false;
            }
        }
        return true;
    }
    
    public List<Book> getUserAllBorrowingBooks(User user){
        return getUserAllBorrowingBooks(user.getId());
    }
    
    public List<Book> getUserAllBorrowingBooks(String id){
        if (!isInited){
            isInited=true;
            initData();
        }
        List<String> ls=borrowingDao.getUserAllBorrowedBooks(id);
        if (ls==null){
            return null;
        }
        List nls=new LinkedList();
        for (String bookId:ls){
            nls.add(bookDao.getBookById(bookId));
        }
        return nls;
    }
    
    public Map<String, User> getAllUsers(){
        return userDao.getAllUsers();
    }
    
    
}
