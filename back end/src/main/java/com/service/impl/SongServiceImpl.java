package com.service.impl;

import com.dao.ISongDao;
import com.domain.Song;
import com.domain.SongList;
import com.exception.*;
import com.service.ISongService;
import com.utils.ServiceCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Liu
 * Created on 2020/8/28.
 */
@Service("songService")
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = RuntimeException.class)
public class SongServiceImpl extends ServiceCheckUtil implements ISongService {
    
    private ISongDao songDao;
    
    @Autowired
    public void setSongDao (ISongDao songDao) {
        this.songDao = songDao;
    }
    
    @Override
    public List<SongList> findAllSongList (Integer uid) throws UidErrorException, NullUserException, SystemErrorException {
        checkUidValid(uid);
        List<SongList> songLists;
        try {
            songLists = songDao.findAllSongList(uid);
        } catch (Exception exception) {
            throw new SystemErrorException("系统异常");
        }
        return songLists;
    }
    
    @Override
    public List<Song> findAllSongInList (Integer uid, Integer listIndex) throws UidErrorException, NullUserException, SongListErrorException, SystemErrorException {
        checkSongListValid(uid, listIndex);
        String fullName = uid + "_Songs_" + listIndex;
        List<Song> songs;
        try {
            songs = songDao.findAllSongInList(fullName);
        } catch (Exception exception) {
            throw new SystemErrorException("系统异常");
        }
        return songs;
    }
    
    @Override
    public String findSongUrl (Integer uid, Integer listIndex, Integer songIndex) throws SongListErrorException, SongErrorException, SystemErrorException {
        checkUidValid(uid);
        if (listIndex < 0) {
            throw new SongListErrorException("歌单索引错误");
        }
        if (songIndex < 0) {
            throw new SongErrorException("歌曲索引错误");
        }
        List<SongList> songLists;
        try {
            songLists = songDao.findAllSongList(uid);
        } catch (Exception exception) {
            throw new SystemErrorException("系统异常");
        }
        if (listIndex >= songLists.size()) {
            throw new SongListErrorException("歌单索引错误");
        }
        SongList songList = songLists.get(listIndex);
        List<Song> songs = songList.getSongs();
        if (songIndex >= songs.size()) {
            throw new SongErrorException("歌曲索引错误");
        }
        return songs.get(songIndex).getUrl();
    }
    
    @Override
    public boolean createSongList (Integer uid, String listName) throws UidErrorException, NullUserException, SongListErrorException, SystemErrorException {
        checkUidValid(uid);
        int listId = findUserListCount(uid);
        if (listName == null || listName.length() == 0) {
            throw new SongListErrorException("歌单名不可为空");
        }
        try {
            songDao.createSongList(uid, listId, listName);
        } catch (Exception e) {
            try {
                songDao.removeSongList(uid + "_Songs_" + listId);
            } catch (Exception exception) {
                throw new SystemErrorException("系统异常");
            }
            return false;
        }
        return true;
    }
    
    @Override
    public boolean removeSongList (Integer uid, Integer listIndex) throws UidErrorException, NullUserException, SongListErrorException, SystemErrorException {
        checkSongListValid(uid, listIndex);
        if (listIndex == 0) {
            throw new SongListErrorException("当前歌单不支持删除");
        }
        SongList songList;
        List<Song> songs;
        String fullName;
        try {
            songList = songDao.findSongList(uid, listIndex);
            fullName = songList.getFullName();
            songs = songDao.findAllSongInList(fullName);
        } catch (Exception exception) {
            throw new SystemErrorException("系统异常");
        }
        try {
            songDao.removeSongList(fullName);
        } catch (Exception e) {
            try {
                songDao.createSongList(uid, listIndex, songList.getListName());
                for (Song song : songs) {
                    songDao.addSong(fullName, song);
                }
            } catch (Exception exception) {
                throw new SystemErrorException("系统异常");
            }
            return false;
        }
        return true;
    }
    
    @Override
    public boolean addSong (Integer uid, Integer listIndex, String name, String singer, String url) throws UidErrorException, NullUserException, SongListErrorException, SongErrorException, SystemErrorException, UnknownActionException {
        checkSongListValid(uid, listIndex);
        String fullName = findListFullName(uid, listIndex);
        if (checkSongIsExistInList(fullName, url)) {
            throw new SongListErrorException("不可向一个歌单中重复添加一首歌");
        }
        else {
            Song song = checkSongValid(name, singer, url);
            song.setSongIndex(findSongIndex(uid, listIndex));
            try {
                songDao.addSong(fullName, song);
            } catch (Exception exception) {
                throw new SystemErrorException("系统异常");
            }
        }
        return true;
    }
    
    @Override
    public boolean removeSong (Integer uid, Integer listIndex, Integer songIndex) throws UidErrorException, NullUserException, SongListErrorException, SongErrorException, SystemErrorException {
        checkSongListValid(uid, listIndex);
        String fullName = findListFullName(uid, listIndex);
        SongList songList = songDao.findSongList(uid, listIndex);
        List<Song> songs = songList.getSongs();
        if (songIndex == null || songIndex < 0 || songIndex >= songs.size()) {
            throw new SongErrorException("歌曲索引错误");
        }
        try {
            songDao.removeSong(fullName, songIndex);
        } catch (Exception exception) {
            throw new SystemErrorException("系统异常");
        }
        return true;
    }
    
    @Override
    public boolean updateSongListName (Integer uid, Integer listIndex, String listName) throws SongListErrorException, UidErrorException, NullUserException, SystemErrorException {
        if (Integer.valueOf(0).equals(listIndex)) {
            throw new SongListErrorException("当前歌单不支持修改名称");
        }
        if (listName == null || listName.length() == 0) {
            throw new SongListErrorException("歌单名不可为空");
        }
        checkSongListValid(uid, listIndex);
        try {
            songDao.updateSongListName(uid, listIndex, listName);
        } catch (Exception exception) {
            throw new SystemErrorException("系统异常");
        }
        return true;
    }
    
}
