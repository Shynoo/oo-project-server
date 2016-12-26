package com.github.shynoo.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.shynoo.entity.result.ResultStatus;
import com.github.shynoo.service.BookService;
import com.github.shynoo.service.UserService;

@RestController
public class SampleController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @RequestMapping("/login")
    // @ResponseBody
    String loginPage() {
        try {
            return htmlCompress(fileRead("static/login.html"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    @RequestMapping("/doLogin")
    // @ResponseBody
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
    // @ResponseBody
    String userPage() {
        try {
            return htmlCompress(fileRead("static/user.html"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    @RequestMapping("user/{account}")
    // @ResponseBody
    HashMap<String, Object> getProfile(@PathVariable String account) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("userType", "admin");
        // map.put("status", "normal");
        return map;
    }

    @RequestMapping("search")
    // @ResponseBody
    ArrayList<HashMap<String, String>> search(@RequestParam String q) {
        ArrayList<HashMap<String, String>> results = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("bookName", "Book Name");
        map.put("bookID", "57158652");
        map.put("status", "...");
        results.add(map);
        results.add(map);
        return results;
    }

    @RequestMapping("feelLucky")
    // @ResponseBody
    String feelLucky() {
        return "lucky";
    }

    @RequestMapping("borrow")
    // @ResponseBody
    String borrow(@RequestParam String account, @RequestParam String bookID) {
        return "success";
    }

    // **********
    // * Others
    // **********
    public boolean checkAccount(String account, String password) {
        return userService.userLogIn(account, password).status.equals(ResultStatus.SUCCESS);
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
