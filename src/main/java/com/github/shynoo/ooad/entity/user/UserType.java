package com.github.shynoo.ooad.entity.user;

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
    
    boolean isAllowManageBooks(){
        return isAllowAddBooks;
    }
    
    boolean isAllowManageUsers(){
        return isAllowManageUsers;
    }
    
    public static UserType of(String s) {
        // UserType ms= UserType.valueOf(s);
        // if (ms!=null){
        //     return ms;
        // }
        for (UserType userType: UserType.values()){
            if (userType.type.equalsIgnoreCase(s)){
                return userType;
            }
        }
        return null;
    }
    
    @Override
    public String toString() {
        return this.type;
    }
    
}
