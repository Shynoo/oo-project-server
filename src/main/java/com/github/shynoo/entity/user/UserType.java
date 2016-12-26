package com.github.shynoo.entity.user;

public enum UserType{
    
    NORMAL("Normal"), ADVANCE("Advanced"), ADMIN("Admin");
    
    public int maxBorrowingBookNumber;
    
    public int maxBorrowingDay;
    
    boolean isAllowAddBooks = false;
    
    boolean isAllowManageUsers = false;

    String type;
    
    UserType(){
        
    }
    
    UserType(String type) {
        this.type = type;
    }

    static{
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

    @Override
    public String toString() {
        return this.type;
    }
    
}
