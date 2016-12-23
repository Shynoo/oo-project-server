package com.github.shynoo.entity.result;

public enum ResultStatus{
    
    SUCCESS(0),UNKNOWN_RESULT(300),FAILURE(400);
    
    public final int status;
   
    ResultStatus(int n){
       this.status=n;
    }
    
}
