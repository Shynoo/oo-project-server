package com.github.shynoo.dao;

import java.util.Date;
import java.util.List;

public interface BorrowingDao{
    int borrowOut(String userId, String bookId);
    int giveBack(String bookId);
    List<String> getUserAllBorrowedBooks(String userId);
    String getBookOwnerId(String bookId);
    Date bookOutDay(String id);
}
