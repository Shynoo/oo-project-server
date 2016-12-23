package com.github.shynoo.entity.book;

import lombok.Getter;

public enum BookStatus{
    
    IN_LIBIRARY(),BORROWING_OUT();
    
    @Getter
    private String place;
    
    BookStatus(){
        
    }
    
    public void setPlace(String place){
        this.place=place;
    }
    
    
}
