package com.github.shynoo.entity.book;

import java.util.Map;

public enum BookType{
    
    MATH(0,"Math"),ENGLISH(2,"English"),BIOLOGY(5,"Biology"),CHEMISTRY(4,"Chemistry"),PHYSICS(3,"Physics");
    
    public final long id;
    
    public final String typeName;
    
    static Map<Long,BookType> bookTypeMap;
    
    
    BookType(long id,String typeName){
        this.id=id;
        this.typeName=typeName;
    }
    
    
    public static BookType of(long typeId){
        for (BookType type:BookType.values()){
            if (type.id==typeId){
                return type;
            }
        }
        return null;
    }
    
    public static BookType of(String name){
        for (BookType type:BookType.values()){
            if (type.typeName.equals(name)){
                return type;
            }
        }
        return null;
    }
}
