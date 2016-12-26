package com.github.shynoo.entity.result;

public class Result{
    
    public final ResultStatus status;
    public final String dis;
    public final Object object;
    
    public Result(ResultStatus n){
        this(n, "", null);
    }
    
    public Result(ResultStatus n, Object o){
        this(n, "", o);
    }
    
    public Result(ResultStatus n, String s, Object o){
        this.status = n;
        this.dis = s;
        this.object = o;
    }
    
    public Object get(){
        return this.object;
    }
}
