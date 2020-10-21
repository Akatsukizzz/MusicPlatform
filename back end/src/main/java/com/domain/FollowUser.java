package com.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Liu
 * Created on 2020/7/24.
 */
public class FollowUser implements Serializable {
    
    private static final long serialVersionUID = -7401758115680084955L;
    
    private Integer uid;
    
    private String username;
    
    private Boolean sex;
    
    private String birthday;
    
    private String location;
    
    public Integer getUid () {
        return uid;
    }
    
    public void setUid (Integer uid) {
        this.uid = uid;
    }
    
    public String getUsername () {
        return username;
    }
    
    public void setUsername (String username) {
        this.username = username;
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
        FollowUser that = (FollowUser) o;
        return uid.equals(that.uid);
    }
    
    @Override
    public int hashCode () {
        return Objects.hash(uid);
    }
    
}
