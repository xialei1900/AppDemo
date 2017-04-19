package com.example.administrator.appdemo;

/**
 * Created by Administrator on 2017/4/16.
 */

public class UserBean {

    /**
     * Id : 1
     * userName : admin
     * loginId : 123
     * loginPwd : 123
     * sex : 男
     * age : 22
     * height : 168
     * birthday : 1994/10/12 0:00:00
     * expectedDate : 2017/12/28 0:00:00
     */

    private int Id;
    private String userName;
    private String loginId;
    private String loginPwd;
    private String sex;
    private int age;
    private int height;
    private String birthday;
    private String expectedDate;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(String expectedDate) {
        this.expectedDate = expectedDate;
    }
}
