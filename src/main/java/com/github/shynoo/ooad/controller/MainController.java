package com.github.shynoo.ooad.controller;

import com.github.shynoo.ooad.entity.book.Book;
import com.github.shynoo.ooad.entity.book.BookType;
import com.github.shynoo.ooad.entity.result.ResultStatus;
import com.github.shynoo.ooad.entity.user.Admin;
import com.github.shynoo.ooad.entity.user.User;
import com.github.shynoo.ooad.entity.user.UserType;
import com.github.shynoo.ooad.service.BookService;
import com.github.shynoo.ooad.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@RestController
public class MainController{

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @RequestMapping("/login")
    public String loginPage() {
        try {
            return htmlCompress(fileRead("static/login.html"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    @RequestMapping("/doLogin")
    public HashMap<String, Object> doLogin(@RequestParam String account, @RequestParam String password) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (checkAccount(account, password)) {
            map.put("result", true);
            map.put("account", account);
        } else {
            map.put("result", false);
        }
        return map;
    }

    @RequestMapping("user")
    public String userPage() {
        try {
            return htmlCompress(fileRead("static/user.html"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    @RequestMapping("user/{account}")
    public HashMap<String, Object> getProfile(@PathVariable String account) {
        User user = userService.getUserById(account);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("userName", user.getName());
        map.put("userType", user.getUserType().toString());
        map.put("maxBooks", user.getUserType().maxBorrowingBookNumber);
        map.put("maxDays", user.getUserType().maxBorrowingDay);
        return map;
    }

    @RequestMapping("user/{account}/books")
    public List<Book> getBooks(@PathVariable String account) {
        List<Book> books = userService.getUserAllBorrowingBooks(account);
        return books;
    }

    @RequestMapping("isAdmin")
    public boolean isAdmin(@RequestParam String account) {
        return userService.getUserById(account).getUserType().equals(UserType.ADMIN);
    }

    @RequestMapping("allUsers")
    public Map<String, User> getAllUsers() {
        return userService.getAllUsers();
    }

    @RequestMapping("allBooks")
    public Map<String, Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @RequestMapping("deleteUser/{id}")
    public boolean deleteUser(@PathVariable String id) {
        return userService.deletUser(userService.getUserById("admin"), userService.getUserById(id))
                .equals(ResultStatus.SUCCESS);
    }

    @RequestMapping("deleteBook/{id}")
    public boolean deleteBook(@PathVariable String id) {
        return userService.deleteBook(userService.getUserById("admin"), bookService.getBookById(id))
                .equals(ResultStatus.SUCCESS);
    }

    @RequestMapping("changeUserType")
    boolean changeUserType(@RequestParam String account, @RequestParam String userType) {
        userService.changeUserType(userService.getUserById(account), UserType.of(userType));
        return true;
    }

    @RequestMapping("search")
    public List<Book> search(@RequestParam String q) {
        if (!userService.isInited){
            userService.isInited=true;
            userService.initData();
        }
        List<Book> results = new ArrayList<>();
        if (q.startsWith("type:")) {
            results.addAll(bookService.searchBookByType(BookType.of(q.substring(5).trim())));
        } else if (q.startsWith("t:")) {
            results.addAll(bookService.searchBookByType(BookType.of(q.substring(2).trim())));
        } else {
            results.addAll(bookService.searchBookByName(q));
        }
        return results;
    }

    @RequestMapping("feelLucky")
    public List<Book> feelLucky() {
        if (!userService.isInited){
            userService.isInited=true;
            userService.initData();
        }
        List<Book> results = new ArrayList<>();
        int num = new Random().nextInt(2) + 2;
        for (int i = 0; i < num; i++) {
            Book book = bookService.getRandomBook();
            if (!results.contains(book)) {
                results.add(book);
            }
        }
        return results;
    }

    @RequestMapping("borrow")
    public boolean borrow(@RequestParam String account, @RequestParam String bookId) {
        return userService.borrowBook(account, bookId).equals(ResultStatus.SUCCESS);
    }

    @RequestMapping("return")
    public boolean returnBook(@RequestParam String bookId) {
        return userService.giveBackBook(bookService.getBookById(bookId)).equals(ResultStatus.SUCCESS);
    }

    @RequestMapping("addUser")
    public boolean addUsesr(@RequestParam String account, @RequestParam String userName, @RequestParam String password,
            @RequestParam String userType) {
        User user = null;
        switch (userType) {
        case "normal":
            user = User.createNormalUser().id(account).name(userName).password(password).build();
            break;
        case "advanced":
            user = User.createAdvanceUser().id(account).name(userName).password(password).build();
            break;
        case "admin":
            user = Admin.createAdminUser().id(account).name(userName).password(password).build();
            break;
        }
        return userService.addUser(userService.getUserById("admin"), user).equals(ResultStatus.SUCCESS);
    }

    @RequestMapping("addBook")
    public boolean addBook(@RequestParam String bookName, @RequestParam String bookType) {
        return userService.addBook(userService.getUserById("admin"),
                Book.newBook().name(bookName).type(BookType.of(bookType)).build()).equals(ResultStatus.SUCCESS);
    }

    // **********
    // * Others
    // **********
    public boolean checkAccount(String account, String password) {
        return userService.checkUserPassword(account, password).status.equals(ResultStatus.SUCCESS);
    }

    private String fileRead(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        StringBuilder strBuilder = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
            strBuilder.append(line + "\n");
        }
        reader.close();
        return strBuilder.toString();
    }

    private String htmlCompress(String html) {
        return html.replaceAll(">\\s+<", "> <").replaceAll("> <", "><");
    }
}
