package com.dao;

import com.domain.Song;
import com.domain.SongList;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Liu
 * Created on 2020/8/28.
 */
@Repository("songDao")
public interface ISongDao {
    
    /**
     * 查找用户的全部歌单
     *
     * @param uid 用户 uid
     * @return 用户的歌单列表
     */
    @Results(id = "songMap", value = {
            @Result(column = "fullName", property = "songs", many = @Many(select = "com.dao.ISongDao.findAllSongInList", fetchType = FetchType.EAGER))
    })
    @Select("select * from SongList where uid = #{uid}")
    List<SongList> findAllSongList (Integer uid);
    
    /**
     * 查找用户指定的歌单
     *
     * @param uid       用户 uid
     * @param listIndex 歌单索引
     * @return 用户的歌单，若不存在则返回 null
     */
    @Select("select * from SongList where uid = #{uid} and listIndex = #{listIndex}")
    SongList findSongList (@Param("uid") Integer uid, @Param("listIndex") Integer listIndex);
    
    /**
     * 查找用户歌单在数据库中对应的表名
     *
     * @param uid       用户 uid
     * @param listIndex 用户歌单索引
     * @return 歌单表全名
     */
    @Select("select fullName from SongList where uid = #{uid} and listIndex = #{listIndex}")
    String findSongListFullName (@Param("uid") Integer uid, @Param("listIndex") Integer listIndex);
    
    /**
     * 查找用户歌单中全部歌曲
     *
     * @param fullName 歌单在数据库中的表名
     * @return 歌单中的全部歌曲列表
     */
    @Select("select * from `${fullName}`")
    List<Song> findAllSongInList (String fullName);
    
    /**
     * 查找用户歌单中全部歌曲的 id
     *
     * @param fullName 歌单表全名
     * @return 用户歌单中的歌曲 id
     */
    @Select("select url from `${fullName}`")
    List<String> findAllSongUrl (String fullName);
    
    /**
     * 查找用户的歌单后缀
     *
     * @param uid 用户 uid
     * @return 用户当前歌单表名数字后缀
     */
    @Select("select listCount from User where uid = #{uid}")
    Integer findUserListCount (Integer uid);
    
    /**
     * 创建歌单
     *
     * @param uid      用户 uid
     * @param listId   歌单 id
     * @param listName 歌单名
     */
    @Update("insert into SongList values(#{uid}, #{listId}, #{listName}, '${uid}_Songs_${listId}');" +
            "update User set listCount = listCount + 1 where uid = #{uid};" +
            "create table #{uid}_Songs_${listId} (" +
                    "songIndex int not null," +
                    "name varchar(30) not null," +
                    "singer varchar(20)," +
                    "url varchar(500) not null);")
    void createSongList (@Param("uid") Integer uid, @Param("listId") Integer listId, @Param("listName") String listName);
    
    /**
     * 删除歌单
     *
     * @param fullName 歌单表全名
     */
    @Update("delete from SongList where fullName = #{fullName};" +
            "drop table if exists #{fullName}")
    void removeSongList (String fullName);
    
    /**
     * 添加歌曲到歌单中
     *
     * @param fullName 歌单表的全名
     * @param song     歌曲
     */
    @Insert("insert into `${fullName}` values(#{song.songIndex}, #{song.name}, #{song.singer}, #{song.url})")
    void addSong (@Param("fullName") String fullName, @Param("song") Song song);
    
    /**
     * 将歌曲从歌单中移除
     *
     * @param fullName  歌单表全名
     * @param songIndex 歌曲索引
     */
    @Delete("delete from `${fullName}` where songIndex = #{songIndex}")
    void removeSong (@Param("fullName") String fullName, @Param("songIndex") Integer songIndex);
    
    /**
     * 更新歌单名
     *
     * @param uid       用户 uid
     * @param listIndex 歌单索引
     * @param listName  新的歌单名
     */
    @Update("update SongList set listName = #{listName} where uid = #{uid} and listIndex = #{listIndex}")
    void updateSongListName (@Param("uid") Integer uid, @Param("listIndex") Integer listIndex, @Param("listName") String listName);
    
}
