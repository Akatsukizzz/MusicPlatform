package com.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author Liu
 * Created on 2020/7/25.
 */
public class SongList implements Serializable {
    
    private static final long serialVersionUID = -5836869806778079781L;
    
    private Integer uid;
    
    private Integer listIndex;
    
    private String listName;
    
    private String fullName;
    
    private List<Song> songs;
    
    public Integer getUid () {
        return uid;
    }
    
    public void setUid (Integer uid) {
        this.uid = uid;
    }
    
    public Integer getListIndex () {
        return listIndex;
    }
    
    public void setListIndex (Integer listIndex) {
        this.listIndex = listIndex;
    }
    
    public String getListName () {
        return listName;
    }
    
    public String getFullName () {
        return fullName;
    }
    
    public void setFullName (String fullName) {
        this.fullName = fullName;
    }
    
    public List<Song> getSongs () {
        return songs;
    }
    
    public void setSongs (List<Song> songs) {
        this.songs = songs;
    }
    
    @Override
    public boolean equals (Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SongList songList = (SongList) o;
        return uid.equals(songList.uid) && listIndex.equals(songList.listIndex) && fullName.equals(songList.fullName) && Objects.equals(songs, songList.songs);
    }
    
    @Override
    public int hashCode () {
        return Objects.hash(uid, listIndex, fullName, songs);
    }
    
}
