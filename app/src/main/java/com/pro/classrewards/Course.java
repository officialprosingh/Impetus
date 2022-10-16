package com.pro.classrewards;

import android.widget.EditText;

public class Course {

    private String cnumber, cname, cteacher, chour, clocation, check;

    public Course(){

    }


    public Course(String cname, String cteacher, String chour, String clocation, String cnumber, String check) {
        this.cname = cname;
        this.cteacher = cteacher;
        this.chour = chour;
        this.clocation = clocation;
        this.cnumber = cnumber;
        this.check = check;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public String getCname() {
        return cname;
    }

    public String getCteacher() {
        return cteacher;
    }

    public String getChour() {
        return chour;
    }

    public String getClocation() {
        return clocation;
    }

    public String getCnumber(){
        return cnumber;
    }

    public void setCnumber(String cnumber) {
        this.cnumber = cnumber;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public void setCteacher(String cteacher) {
        this.cteacher = cteacher;
    }

    public void setChour(String chour) {
        this.chour = chour;
    }

    public void setClocation(String clocation) {
        this.clocation = clocation;
    }
}
