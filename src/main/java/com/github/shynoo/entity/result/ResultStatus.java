package com.github.shynoo.entity.result;

public enum ResultStatus{
    
    SUCCESS(0),UNKNOWN_RESULT(500),FAILURE(400);
    
    public final int status;
   
    ResultStatus(int n){
       this.status=n;
    }
    
//    ResultStatus(int n, Object o){
//        this(n,"",o);
//    }
//
//    ResultStatus(int n,String s,Object o){
//        this.status=n;
//        this.dis=s;
//        this.object =o;
//    }
    
    
}
