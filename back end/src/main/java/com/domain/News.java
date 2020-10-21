package com.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author Liu
 * Created on 2020/7/25.
 */
public class News implements Serializable {
    
    private static final long serialVersionUID = -4164267629327439207L;
    
    private Integer newsId;
    
    private Integer uid;
    
    private String username;
    
    private String info;
    
    private Date time;
    
    public Integer getNewsId () {
        return newsId;
    }
    
    public void setNewsId (Integer newsId) {
        this.newsId = newsId;
    }
    
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
    
    public String getInfo () {
        return info;
    }
    
    public void setInfo (String info) {
        this.info = info;
    }
    
    public Date getTime () {
        return time;
    }
    
    public void setTime (Date time) {
        this.time = time;
    }
    
    @Override
    public boolean equals (Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        News news = (News) o;
        return newsId.equals(news.newsId) && uid.equals(news.uid) && Objects.equals(username, news.username) && Objects.equals(info, news.info) && time.equals(news.time);
    }
    
    @Override
    public int hashCode () {
        return Objects.hash(newsId, uid, username, info, time);
    }
    
}
