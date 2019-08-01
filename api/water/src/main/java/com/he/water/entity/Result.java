package com.he.water.entity;

public class Result {
    public  Integer ret;
    public  String msg;

    public Result  error(Integer ret, String msg){
        this.ret = ret;
        this.msg = msg;
        return  this;
    }

    public Result ok(){
        ret = 0;
        msg = "success";
        return  this;
    }

    public Integer getRet() {
        return ret;
    }

    public void setRet(Integer ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
