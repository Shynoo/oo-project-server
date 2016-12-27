package com.github.shynoo.controller;

import com.github.shynoo.entity.book.Book;
import com.github.shynoo.entity.book.BookType;
import com.github.shynoo.entity.result.ResultStatus;
import com.github.shynoo.entity.user.User;
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
import java.util.Random;

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
        System.out.println(books);
        System.out.println(books.size());
        for (Book book : books) {
            System.out.println(book.getName());
        }
        return books;
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

    @RequestMapping("addBook")
    boolean addBook(@RequestParam String account, @RequestParam String bookName, @RequestParam String bookType) {
        return userService
                .addBook(userService.getUserById(account),
                        Book.newBuilder().name(bookName).type(BookType.of(bookType)).build())
                .equals(ResultStatus.SUCCESS);
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
