package com.xiaohe.ysjspt.entity;
/**
 * @author
 * Administrator
 */
public class Result {
    private static final long serialVersionUID = -5875371379845226068L;
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
    public Result(Integer ret, String msg){
        this.ret = ret;
        this.msg = msg;
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

    public Result() {
    }
}
