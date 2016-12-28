package com.github.shynoo.ooad.entity.book;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum BookType{
    
    MATH(0, "MA"), ENGLISH(2, "EN"), BIOLOGY(5, "BIO"), CHEMISTRY(4, "CHE"), PHYSICS(3, "PHY"), COMPUTER_SCIENCE(10, "CS");
    
    public final long id;
    
    public final String typeName;
    
    
    BookType(long id, String typeName){
        this.id = id;
        this.typeName = typeName;
    }
    
    
    public static BookType of(long typeId){
        for (BookType type : BookType.values()){
            if (type.id == typeId) {
                return type;
            }
        }
        return null;
    }
    
    public static BookType of(String name){
        
        for (BookType type : BookType.values()){
            if (type.toString().equalsIgnoreCase(name)||type.typeName.equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }
}