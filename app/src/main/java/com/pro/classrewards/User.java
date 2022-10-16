package com.pro.classrewards;

public class User {

    private String uname,uteacher, unumber;

    public User(){

    }

    public User(String uname, String uteacher, String unumber ) {
        this.uname = uname;
        this.uteacher = uteacher;
        this.unumber = unumber;
    }

    public String getUname() {
        return uname;
    }

    public String getUteacher() {
        return uteacher;
    }

    public String getUnumber() {
        return unumber;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public void setUteacher(String uteacher) {
        this.uteacher = uteacher;
    }

    public void setUnumber(String unumber) {
        this.unumber = unumber;
    }
}
