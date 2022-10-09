package com.qianfeng.pojo;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private Integer id;

    private String usercode;

    private String username;

    private String userpassword;

    private Integer age;

    private Integer gender;

    private Date birthday;

    private String phone;

    private String address;

    private String profile;

    private Integer userrole;

    private Date creationdate;

    private String roleName;

    private String sex;

    private Integer page;

    private Integer limit;

    private boolean bool;

    private static final long serialVersionUID = 1L;

    public User() {
    }

    public User(String usercode, String username, String userpassword, Integer gender, Date birthday,
                     String phone, String address, String profile, Integer userrole, Date creationdate) {
        this.usercode = usercode;
        this.username = username;
        this.userpassword = userpassword;
        this.gender = gender;
        this.birthday = birthday;
        this.phone = phone;
        this.address = address;
        this.profile = profile;
        this.userrole = userrole;
        this.creationdate = creationdate;
    }

    public User(Integer id, String usercode, String username, String userpassword, int gender,
                     Date birthday, String phone, String address, String profile, int userrole, Date creationdate) {
        this.id = id;
        this.usercode = usercode;
        this.username = username;
        this.userpassword = userpassword;
        this.gender = gender;
        this.birthday = birthday;
        this.phone = phone;
        this.address = address;
        this.profile = profile;
        this.userrole = userrole;
        this.creationdate = creationdate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode == null ? null : usercode.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword == null ? null : userpassword.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile == null ? null : profile.trim();
    }

    public Integer getUserrole() {
        return userrole;
    }

    public void setUserrole(Integer userrole) {
        this.userrole = userrole;
    }

    public Date getCreationdate(){
        /*Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");//年月日时分秒毫秒
        creationdate = simpleDateFormat.parse("date");*/
        return creationdate;
    }

    public void setCreationdate(Date creationdate) {
        this.creationdate = creationdate;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public boolean isBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", usercode='" + usercode + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}