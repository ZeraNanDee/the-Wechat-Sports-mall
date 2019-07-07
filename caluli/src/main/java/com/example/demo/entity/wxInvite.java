package com.example.demo.entity;

public class wxInvite {

    private int rid;
    private String openid;
    private int userid;

    public wxInvite(String openid,int userid){
        super();
        this.openid=openid;
        this.userid=userid;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) { this.openid = openid; }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
