package com.service.impl;

import com.dao.IFollowDao;
import com.domain.FollowUser;
import com.domain.News;
import com.enums.FollowActionEnum;
import com.enums.ShowNewsActionEnum;
import com.exception.*;
import com.service.IFollowService;
import com.utils.ServiceCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Liu
 * Created on 2020/8/28.
 */
@Service("followService")
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = RuntimeException.class)
public class FollowServiceImpl extends ServiceCheckUtil implements IFollowService {
    
    private IFollowDao followDao;
    
    @Autowired
    public void setFollowDao (IFollowDao followDao) {
        this.followDao = followDao;
    }
    
    @Override
    public List<FollowUser> findAllFollowing (Integer uid) throws UidErrorException, NullUserException, SystemErrorException {
        checkUidValid(uid);
        List<FollowUser> following;
        try {
            following = followDao.findAllFollowing(uid);
        } catch (Exception exception) {
            throw new SystemErrorException("系统异常");
        }
        return following;
    }
    
    @Override
    public List<FollowUser> findAllFollower (Integer uid) throws UidErrorException, NullUserException, SystemErrorException {
        checkUidValid(uid);
        List<FollowUser> followers;
        try {
            followers = followDao.findAllFollower(uid);
        } catch (Exception exception) {
            throw new SystemErrorException("系统异常");
        }
        return followers;
    }
    
    @Override
    public boolean updateFollow (Integer uid, Integer fUid, FollowActionEnum action) throws UidErrorException, NullUserException, SystemErrorException, FollowUserException, UnknownActionException {
        checkUidValid(uid);
        checkUidValid(fUid);
        if (uid.equals(fUid)) {
            throw new UidErrorException("不可以关注自己");
        }
        if (checkUserIsNotExistByUid(uid) || checkUserIsNotExistByUid(fUid)) {
            throw new NullUserException("找不到用户");
        }
        if (FollowActionEnum.ADD_FOLLOW.equals(action)) {
            if (checkIsFollowing(uid, fUid)) {
                throw new FollowUserException("不能重复关注同一用户");
            }
            else {
                try {
                    followDao.addFollow(uid, fUid);
                } catch (Exception exception) {
                    throw new SystemErrorException("系统异常");
                }
            }
        }
        else if (FollowActionEnum.REMOVE_FOLLOW.equals(action)) {
            if (!checkIsFollowing(uid, fUid)) {
                throw new FollowUserException("还没有关注该用户");
            }
            else {
                try {
                    followDao.removeFollow(uid, fUid);
                } catch (Exception exception) {
                    throw new SystemErrorException("系统异常");
                }
            }
        }
        else {
            throw new UnknownActionException("未知操作");
        }
        return true;
    }
    
    @Override
    public List<News> findAllNews (Integer uid, ShowNewsActionEnum action) throws UidErrorException, NullUserException, SystemErrorException, UnknownActionException {
        checkUidValid(uid);
        List<News> news;
        List<News> self;
        try {
            self = followDao.findAllNews(uid);
        } catch (Exception exception) {
            throw new SystemErrorException("系统异常");
        }
        if (ShowNewsActionEnum.SELF.equals(action)) {
            news = self;
        }
        else if (ShowNewsActionEnum.ALL.equals(action)) {
            List<Integer> ids;
            try {
                ids = followDao.findAllFollowingUid(uid);
            } catch (Exception exception) {
                throw new SystemErrorException("系统异常");
            }
            news = new ArrayList<>(ids.size() * 10);
            news.addAll(self);
            for (Integer id : ids) {
                List<News> newsList;
                try {
                    newsList = followDao.findAllNews(id);
                } catch (Exception exception) {
                    throw new SystemErrorException("系统异常");
                }
                news.addAll(newsList);
            }
        }
        else {
            throw new UnknownActionException("未知操作");
        }
        return news;
    }
    
    @Override
    public boolean addNews (Integer uid, String info) throws UidErrorException, NullUserException, NewsErrorException, SystemErrorException {
        checkUidValid(uid);
        if (info == null || info.length() == 0) {
            throw new NewsErrorException("动态内容不可为空");
        }
        try {
            followDao.addNews(uid, info, new Date());
        } catch (Exception exception) {
            throw new SystemErrorException("系统异常");
        }
        return true;
    }
    
    @Override
    public boolean deleteNews (Integer uid, Integer newsId) throws UidErrorException, NullUserException, NewsErrorException, SystemErrorException {
        checkUidValid(uid);
        if (newsId == null || newsId <= 0) {
            throw new NewsErrorException("动态 id 错误");
        }
        if (!checkNewsIdIsExist(uid, newsId)) {
            throw new NewsErrorException("动态不存在");
        }
        else {
            try {
                followDao.deleteNews(uid, newsId);
            } catch (Exception exception) {
                throw new SystemErrorException("系统异常");
            }
            return true;
        }
    }
    
}
