package com.service;

import com.domain.Song;
import com.domain.SongList;
import com.exception.*;

import java.util.List;

/**
 * @author Liu
 * Created on 2020/8/28.
 */
public interface ISongService {
    
    /**
     * 查询 uid 对应的用户的歌单列表
     *
     * @param uid 需要查询歌单列表的用户 uid
     * @return 用户的歌单列表
     * @throws UidErrorException    若 uid 不合法，抛出该异常
     * @throws NullUserException    若 uid 对应的用户不存在，抛出该异常
     * @throws SystemErrorException 查询数据库的过程中可能出现的异常
     */
    List<SongList> findAllSongList (Integer uid) throws UidErrorException, NullUserException, SystemErrorException;
    
    /**
     * 查询用户某个歌单中的全部歌曲
     *
     * @param uid       用户 uid
     * @param listIndex 用户的歌单索引
     * @return 存放 uid 对应用户的 listIndex 索引的歌单的全部歌曲
     * @throws UidErrorException      若 uid 不合法，抛出该异常
     * @throws NullUserException      若 uid 对应的用户不存在，抛出该异常
     * @throws SongListErrorException 若 listIndex 不合法，抛出该异常
     *                                若 uid 对应的用户不存在该歌单，抛出该异常
     * @throws SystemErrorException   查询数据库的过程中可能出现的异常
     */
    List<Song> findAllSongInList (Integer uid, Integer listIndex) throws UidErrorException, NullUserException, SongListErrorException, SystemErrorException;
    
    /**
     * 查找歌曲 url
     *
     * @param uid       用户 uid
     * @param listIndex 用户歌单的索引号
     * @param songIndex 歌曲在歌单中的索引号
     * @return 歌曲 url
     * @throws SongListErrorException 若歌单索引超出范围，抛出此异常
     * @throws SongErrorException     若歌曲索引超出范围，抛出此异常
     * @throws SystemErrorException   查询数据库的过程中可能出现的异常
     */
    String findSongUrl (Integer uid, Integer listIndex, Integer songIndex) throws SongListErrorException, SongErrorException, SystemErrorException;
    
    /**
     * 创建歌单
     *
     * @param uid      需要添加歌单的用户的 uid
     * @param listName 添加的歌单名
     * @return 若创建歌单未出现异常则返回 true，否则返回 false
     * @throws UidErrorException      若用户 uid 不合法，抛出该异常
     * @throws NullUserException      若 uid 对应的用户不存在，抛出该异常
     * @throws SongListErrorException 若用户输入的歌单名为空，抛出该异常
     * @throws SystemErrorException   查询数据库的过程中可能出现的异常
     */
    boolean createSongList (Integer uid, String listName) throws UidErrorException, NullUserException, SongListErrorException, SystemErrorException;
    
    /**
     * 删除歌单
     *
     * @param uid       需要删除歌单的用户的 uid
     * @param listIndex 删除的歌单 id
     * @return 若删除歌单未出现异常则返回 true，否则返回 false
     * @throws UidErrorException      若传入的 uid 不合法，抛出该异常
     * @throws NullUserException      若 uid 对应的用户不存在，抛出该异常
     * @throws SongListErrorException 若歌单索引不合法，抛出该异常
     * @throws SystemErrorException   查询数据库的过程中可能出现的异常
     */
    boolean removeSongList (Integer uid, Integer listIndex) throws UidErrorException, NullUserException, SongListErrorException, SystemErrorException;
    
    /**
     * 向用户歌单中添加歌曲
     *
     * @param uid       需要操作歌单的用户 uid
     * @param listIndex 歌单索引
     * @param name      歌曲名
     * @param singer    歌手名
     * @param url       歌曲 url
     * @return 若添加歌曲没有出现异常，返回 true
     * @throws UidErrorException      若传入的 uid 不合法，抛出该异常
     * @throws NullUserException      若 uid 对应的用户不存在，抛出该异常
     * @throws SongListErrorException 若用户向同一个歌单中添加同一首歌曲时，抛出该异常；
     *                                若 listIndex 不合法，抛出该异常
     * @throws SongErrorException     若歌曲信息不合法，抛出该异常
     * @throws SystemErrorException   查询数据库的过程中可能出现的异常
     * @throws UnknownActionException 若 action 参数的值不合法，抛出该异常
     */
    boolean addSong (Integer uid, Integer listIndex, String name, String singer, String url) throws UidErrorException, NullUserException, SongListErrorException, SongErrorException, SystemErrorException;
    
    /**
     * 从用户歌单中移除歌曲
     *
     * @param uid       用户 uid
     * @param listIndex 歌单索引
     * @param songIndex 歌曲索引
     * @return 若删除歌曲没有出现异常，返回 true
     * @throws UidErrorException      若传入的 uid 不合法，抛出该异常
     * @throws NullUserException      若 uid 对应的用户不存在，抛出该异常
     * @throws SongListErrorException 若 listIndex 不合法，抛出该异常
     * @throws SongErrorException     若 songIndex 不合法，抛出该异常
     * @throws SystemErrorException   查询数据库的过程中可能出现的异常
     */
    boolean removeSong (Integer uid, Integer listIndex, Integer songIndex) throws UidErrorException, NullUserException, SongListErrorException, SongErrorException, SystemErrorException;
    
    /**
     * 更新用户歌单名称
     *
     * @param uid       用户 uid
     * @param listIndex 需要更新名称的歌单 id
     * @param listName  新的歌单名称
     * @return 若修改歌单名称未出现异常，返回 true
     * @throws SongListErrorException 若修改的是 0 号默认歌单，抛出该异常；
     *                                若新的歌单名称为空，抛出该异常；
     *                                若 listIndex 不合法，抛出该异常；
     * @throws UidErrorException      若用户 uid 不合法，抛出该异常
     * @throws NullUserException      若 uid 对应的用户不存在，抛出该异常
     * @throws SystemErrorException   查询数据库的过程中可能出现的异常
     */
    boolean updateSongListName (Integer uid, Integer listIndex, String listName) throws SongListErrorException, UidErrorException, NullUserException, SystemErrorException;
    
}
