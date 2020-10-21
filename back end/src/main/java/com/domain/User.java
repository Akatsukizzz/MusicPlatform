package com.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Liu
 * Created on 2020/7/20.
 */
public class User implements Serializable {
    
    private static final long serialVersionUID = 3411648621834672921L;
    
    private Integer uid;
    
    private String phone;
    
    private String username;
    
    private Integer password;
    
    private Boolean sex;
    
    private String birthday;
    
    private String location;

    public Integer getUid () {
        return uid;
    }

    public void setUid (Integer uid) {
        this.uid = uid;
    }

    public String getPhone () {
        return phone;
    }

    public void setPhone (String phone) {
        this.phone = phone;
    }

    public String getUsername () {
        return username;
    }

    public void setUsername (String username) {
        this.username = username;
    }

    public Integer getPassword () {
        return password;
    }

    public void setPassword (Integer password) {
        this.password = password;
    }

    public Boolean getSex () {
        return sex;
    }

    public void setSex (Boolean sex) {
        this.sex = sex;
    }
    
    public String getBirthday () {
        return birthday;
    }
    
    public void setBirthday (String birthday) {
        this.birthday = birthday;
    }
    
    public String getLocation () {
        return location;
    }

    public void setLocation (String location) {
        this.location = location;
    }
    
    @Override
    public boolean equals (Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return uid.equals(user.uid) && phone.equals(user.phone);
    }
    
    @Override
    public int hashCode () {
        return Objects.hash(uid, phone);
    }
    
}
