package com.github.shynoo.entity.user;

public enum UserType{
    
    GUEST(), NORMAL(), ADVANCE(), ADMIN();
    
    int maxBorrowingBookNumber;
    
    int maxBorrowingDay;
    
    boolean isAllowAddBooks = false;
    
    boolean isAllowManageUsers = false;
    
    UserType(){
        
    }
    
    static{
        GUEST.maxBorrowingBookNumber = 0;
        GUEST.maxBorrowingDay = 0;
        
        NORMAL.maxBorrowingBookNumber = 10;
        NORMAL.maxBorrowingDay = 15;
        
        ADVANCE.maxBorrowingBookNumber = 20;
        ADVANCE.maxBorrowingDay = 30;
        
        ADMIN.maxBorrowingBookNumber = 100;
        ADMIN.maxBorrowingDay = 365;
        
        ADMIN.isAllowAddBooks = true;
        ADMIN.isAllowManageUsers = true;
    }
    
    public boolean isAllowAddBooks(){
        return isAllowAddBooks;
    }
    
    public boolean isAllowManageUsers(){
        return isAllowManageUsers;
    }
    
}
