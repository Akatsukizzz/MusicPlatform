package com.service;

import com.domain.FollowUser;
import com.domain.News;
import com.enums.FollowActionEnum;
import com.enums.ShowNewsActionEnum;
import com.exception.*;

import java.util.List;

/**
 * @author Liu
 * Created on 2020/8/28.
 */
public interface IFollowService {
    
    /**
     * 查询 uid 对应的用户的关注列表
     *
     * @param uid 需要查询关注列表的用户 uid
     * @return 用户的关注列表
     * @throws UidErrorException    若 uid 不合法，抛出该异常
     * @throws NullUserException    若传入的 uid 对应的用户不存在，抛出该异常
     * @throws SystemErrorException 查询数据库的过程中可能出现的异常
     */
    List<FollowUser> findAllFollowing (Integer uid) throws UidErrorException, NullUserException, SystemErrorException;
    
    /**
     * 查询 uid 对应的用户的粉丝列表
     *
     * @param uid 需要查询粉丝列表的用户 uid
     * @return 用户的粉丝列表
     * @throws UidErrorException    若 uid 不合法，抛出该异常
     * @throws NullUserException    若 uid 对应的用户不存在，抛出该异常
     * @throws SystemErrorException 查询数据库的过程中可能出现的异常
     */
    List<FollowUser> findAllFollower (Integer uid) throws UidErrorException, NullUserException, SystemErrorException;
    
    /**
     * 添加关注或取消关注
     *
     * @param uid    关注者 uid
     * @param fUid   被关注着 uid
     * @param action 枚举类的值，合法值为 ADD_FOLLOW 或 REMOVE_FOLLOW
     * @return 若添加关注或取消关注没有出现异常，返回 true
     * @throws UidErrorException      若 uid 不合法，抛出该异常；
     *                                若 uid 等于 fUid，抛出该异常
     * @throws NullUserException      若 uid 或 fUid 对应的用户不存在，抛出该异常
     * @throws SystemErrorException   查询数据库的过程中可能出现的异常
     * @throws FollowUserException    若重复关注同一用户，抛出该异常；
     *                                若试图取消关注一个没有关注的用户，抛出该异常
     * @throws UnknownActionException 若 action 参数的值不合法，抛出该异常
     */
    boolean updateFollow (Integer uid, Integer fUid, FollowActionEnum action) throws UidErrorException, NullUserException, SystemErrorException, FollowUserException, UnknownActionException;
    
    /**
     * 根据 ShowNewsActionEnum 的值判断是仅查看自己的动态还是查看所有关注的人的动态
     *
     * @param uid    用户 uid
     * @param action 枚举类的值，合法值为 ALL 或者 SELF
     * @return 动态列表
     * @throws UidErrorException      若 uid 不合法，抛出该异常
     * @throws NullUserException      若 uid 对应的用户不存在，抛出该异常
     * @throws SystemErrorException   查询数据库的过程中可能出现的异常
     * @throws UnknownActionException 若 action 参数的值不合法，抛出该异常
     */
    List<News> findAllNews (Integer uid, ShowNewsActionEnum action) throws UidErrorException, NullUserException, SystemErrorException, UnknownActionException;
    
    /**
     * 发布动态
     *
     * @param uid  发布动态的用户的 uid
     * @param info 动态内容
     * @return 若发布动态没有异常，返回 true
     * @throws UidErrorException    若 uid 不合法，抛出该异常
     * @throws NullUserException    若 uid 对应的用户不存在，抛出该异常
     * @throws NewsErrorException   若动态内容为空，抛出该异常
     * @throws SystemErrorException 查询数据库的过程中可能出现的异常
     */
    boolean addNews (Integer uid, String info) throws UidErrorException, NullUserException, NewsErrorException, SystemErrorException;
    
    /**
     * 删除动态
     *
     * @param uid       需要删除动态的用户的 uid
     * @param newsId 需要删除的动态的 id
     * @return 若删除动态没有异常，返回 true
     * @throws UidErrorException    若 uid 不合法，抛出该异常
     * @throws NullUserException    若 uid 对应的用户不存在，抛出该异常
     * @throws NewsErrorException   若 newsId 不合法，抛出该异常
     * @throws SystemErrorException 查询数据库的过程中可能出现的异常
     */
    boolean deleteNews (Integer uid, Integer newsId) throws UidErrorException, NullUserException, NewsErrorException, SystemErrorException;
    
}
