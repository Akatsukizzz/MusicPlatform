package com.dao;

import com.domain.FollowUser;
import com.domain.News;
import com.domain.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Liu
 * Created on 2020/8/28.
 */
@Repository("followDao")
public interface IFollowDao {
    
    /**
     * 查找用户所有的关注
     *
     * @param uid 用户 uid
     * @return 用户关注列表
     */
    @Select("select * from #{uid}_Following")
    List<FollowUser> findAllFollowing (Integer uid);
    
    /**
     * 查找用户关注的 uid
     *
     * @param uid 用户 uid
     * @return 用户关注列表的 uid 列表
     */
    @Select("select uid from #{uid}_Following")
    List<Integer> findAllFollowingUid (Integer uid);
    
    /**
     * 查找用户所有的粉丝
     *
     * @param uid 用户 uid
     * @return 用户粉丝列表
     */
    @Select("select * from #{uid}_Followers")
    List<FollowUser> findAllFollower (Integer uid);
    
    /**
     * 查找用户粉丝的 uid
     *
     * @param uid 用户 uid
     * @return 用户粉丝的 uid 列表
     */
    @Select("select uid from #{uid}_Followers")
    List<Integer> findAllFollowersUid (Integer uid);
    
    /**
     * 添加关注
     *
     * @param uid  进行操作的用户 uid
     * @param fUid 关注的用户的 uid
     */
    @Insert("insert into #{uid}_Following select uid, username, sex, birthday, location from User " +
                    "where uid = #{fUid};" +
            "insert into #{fUid}_Followers select uid, username, sex, birthday, location from User where uid = #{uid}")
    void addFollow (@Param("uid") Integer uid, @Param("fUid") Integer fUid);
    
    /**
     * 取消关注
     *
     * @param uid  进行操作的用户 uid
     * @param fUid 被取消关注的用户 uid
     */
    @Delete("delete from #{uid}_Following where uid = #{fUid};" +
            "delete from #{fUid}_Followers where uid = #{uid}")
    void removeFollow (@Param("uid") Integer uid, @Param("fUid") Integer fUid);
    
    /**
     * 更新用户的粉丝表中的用户的关注表中该用户的信息
     *
     * @param fUid 粉丝的 uid
     * @param user 用户信息
     */
    @Update("update #{fUid}_Following " +
                    "set username = #{user.username}, sex = #{user.sex}, birthday = #{user.birthday}, location = #{user.location}" +
                    "where uid = #{user.uid}")
    void updateFollowersListInfo (@Param("fUid") Integer fUid, @Param("user") User user);
    
    /**
     * 查找用户的全部动态
     *
     * @param uid 用户 uid
     * @return uid 对应用户的全部动态
     */
    @Results(id = "usernameMap", value = {
            @Result(column = "uid", property = "username", one = @One(select = "com.dao.IUserDao.findUsername", fetchType = FetchType.EAGER))
    })
    @Select("select * from #{uid}_news")
    List<News> findAllNews (Integer uid);
    
    /**
     * 查找用户的全部动态的 id
     *
     * @param uid 用户 uid
     * @return uid 对应用户的全部动态的 id
     */
    @Select("select newsId from #{uid}_news")
    List<Integer> findAllNewsId (Integer uid);
    
    /**
     * 发布动态
     *
     * @param uid  用户 uid
     * @param info 动态内容
     * @param time 发布时间
     */
    @Insert("insert into #{uid}_news(uid, info, time) values(#{uid}, #{info}, #{time})")
    void addNews (@Param("uid") Integer uid, @Param("info") String info, @Param("time") Date time);
    
    /**
     * 删除动态
     *
     * @param uid    用户 uid
     * @param newsId 动态 id
     */
    @Delete("delete from #{uid}_news where newsId = #{newsId}")
    void deleteNews (@Param("uid") Integer uid, @Param("newsId") Integer newsId);
    
}
