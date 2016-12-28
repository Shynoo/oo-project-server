package com.github.shynoo.controller;

import com.github.shynoo.entity.book.Book;
import com.github.shynoo.entity.book.BookType;
import com.github.shynoo.entity.result.ResultStatus;
import com.github.shynoo.entity.user.Admin;
import com.github.shynoo.entity.user.User;
import com.github.shynoo.entity.user.UserType;
import com.github.shynoo.service.BookService;
import com.github.shynoo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.websocket.server.PathParam;

@RestController
public class SampleController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @RequestMapping("/login")
    String loginPage() {
        try {
            return htmlCompress(fileRead("static/login.html"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    @RequestMapping("/doLogin")
    HashMap<String, Object> doLogin(@RequestParam String account, @RequestParam String password) {
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
    String userPage() {
        try {
            return htmlCompress(fileRead("static/user.html"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    @RequestMapping("user/{account}")
    HashMap<String, Object> getProfile(@PathVariable String account) {
        User user = userService.getUserById(account);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("userName", user.getName());
        map.put("userType", user.userType.toString());
        map.put("maxBooks", user.userType.maxBorrowingBookNumber);
        map.put("maxDays", user.userType.maxBorrowingDay);
        return map;
    }

    @RequestMapping("user/{account}/books")
    List<Book> getBooks(@PathVariable String account) {
        List<Book> books = userService.getUserAllBorrowingBooks(account);
        return books;
    }

    @RequestMapping("isAdmin")
    boolean isAdmin(@RequestParam String account) {
        return userService.getUserById(account).userType.equals(UserType.ADMIN);
    }

    @RequestMapping("allUsers")
    Map<String, User> getAllUsers() {
        return userService.getAllUsers();
    }

    @RequestMapping("allBooks")
    Map<String, Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @RequestMapping("deleteUser/{id}")
    boolean deleteUser(@PathVariable String id) {
        return userService.deletUser(userService.getUserById("admin"), userService.getUserById(id))
                .equals(ResultStatus.SUCCESS);
    }

    @RequestMapping("deleteBook/{id}")
    boolean deleteBook(@PathVariable String id) {
        return userService.deleteBook(userService.getUserById("admin"), bookService.getBookById(id))
                .equals(ResultStatus.SUCCESS);
    }

    @RequestMapping("search")
    List<Book> search(@RequestParam String q) {
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
    List<Book> feelLucky() {
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
    boolean borrow(@RequestParam String account, @RequestParam String bookId) {
        return userService.borrowBook(account, bookId).equals(ResultStatus.SUCCESS);
    }

    @RequestMapping("return")
    boolean returnBook(@RequestParam String bookId) {
        return userService.giveBackBook(bookService.getBookById(bookId)).equals(ResultStatus.SUCCESS);
    }

    @RequestMapping("addUser")
    boolean addUsesr(@RequestParam String account, @RequestParam String userName, @RequestParam String password,
            @RequestParam String userType) {
        User user = null;
        switch (userType) {
        case "normal":
            user = User.getFactory().createNormalUser().id(account).name(userName).password(password).build();
        case "advanced":
            user = User.getFactory().createAdvanceUser().id(account).name(userName).password(password).build();
        case "admin":
            user = Admin.newAdmin().id(account).name(userName).password(password).build();
        }
        return userService.addUser(userService.getUserById("admin"), user).equals(ResultStatus.SUCCESS);
    }

    @RequestMapping("addBook")
    boolean addBook(@RequestParam String bookName, @RequestParam String bookType) {
        return userService.addBook(userService.getUserById("admin"),
                Book.newBook().name(bookName).type(BookType.of(bookType)).build()).equals(ResultStatus.SUCCESS);
    }

    // **********
    // * Others
    // **********
    public boolean checkAccount(String account, String password) {
        return userService.checkPassword(account, password).status.equals(ResultStatus.SUCCESS);
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
