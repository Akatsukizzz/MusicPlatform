package com.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Liu
 * Created on 2020/7/25.
 */
public class Song implements Serializable {
    
    private static final long serialVersionUID = -7046460509293843393L;
    
    private Integer songIndex;
    
    private String name;
    
    private String singer;
    
    private String url;
    
    public Song () {
    
    }
    
    public Song (String name, String singer, String url) {
        this.name = name;
        this.singer = singer;
        this.url = url;
    }
    
    public Integer getSongIndex () {
        return songIndex;
    }
    
    public void setSongIndex (Integer songIndex) {
        this.songIndex = songIndex;
    }
    
    public String getName () {
        return name;
    }
    
    public void setName (String name) {
        this.name = name;
    }
    
    public String getSinger () {
        return singer;
    }
    
    public void setSinger (String singer) {
        this.singer = singer;
    }
    
    public String getUrl () {
        return url;
    }
    
    public void setUrl (String url) {
        this.url = url;
    }
    
    @Override
    public boolean equals (Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Song song = (Song) o;
        return Objects.equals(url, song.url);
    }
    
    @Override
    public int hashCode () {
        return Objects.hash(url);
    }
    
}
