package com.utils;

import com.dao.IFollowDao;
import com.dao.ISongDao;
import com.dao.IUserDao;
import com.domain.Song;
import com.domain.SongList;
import com.domain.User;
import com.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Liu
 * Created on 2020/8/28.
 */
@Service("utils")
public class ServiceCheckUtil {
    
    private IUserDao userDao;
    
    private IFollowDao followDao;
    
    private ISongDao songDao;
    
    protected final int validUidLow = 100000;
    
    protected final int validUidHigh = 1000000;
    
    @Autowired
    public void setIUserDao (IUserDao userDao) {
        this.userDao = userDao;
    }
    
    @Autowired
    public void setIFollowDao (IFollowDao followDao) {
        this.followDao = followDao;
    }
    
    @Autowired
    public void setISongDao (ISongDao songDao) {
        this.songDao = songDao;
    }
    
    /**
     * 检查 uid 对应的用户是否不存在
     *
     * @param uid 要检查的 uid
     * @return 布尔值，为 true 则 uid 对应用户不存在，为 false 则对应用户存在
     * @throws SystemErrorException 查询数据库的过程中可能出现的异常
     */
    protected boolean checkUserIsNotExistByUid (Integer uid) throws SystemErrorException {
        User user;
        try {
            user = userDao.findUserById(uid);
        } catch (Exception exception) {
            throw new SystemErrorException("系统异常");
        }
        return user == null;
    }
    
    /**
     * 检查电话号对应的用户是否存在
     *
     * @param phone 要检查的电话号码
     * @return 布尔值，为 true 则该号码对应用户存在，为 false 则对应用户不存在
     * @throws SystemErrorException 查询数据库的过程中可能出现的异常
     */
    protected boolean checkUserIsExistByPhone (String phone) throws SystemErrorException {
        User user;
        try {
            user = userDao.findUserByPhone(phone);
        } catch (Exception exception) {
            throw new SystemErrorException("系统异常");
        }
        return user != null;
    }
    
    /**
     * 检查 uid 对应的用户是否正在关注 fUid 对应的用户
     *
     * @param uid  粉丝用户 uid
     * @param fUid 被关注用户 uid
     * @return 布尔值，为 true 则未关注，为 false 则正在关注
     * @throws SystemErrorException 查询数据库的过程中可能出现的异常
     */
    protected boolean checkIsFollowing (Integer uid, Integer fUid) throws SystemErrorException {
        List<Integer> ids;
        try {
            ids = followDao.findAllFollowingUid(uid);
        } catch (Exception exception) {
            throw new SystemErrorException("系统异常");
        }
        return ids != null && ids.contains(fUid);
    }
    
    /**
     * 检查歌曲是否在歌单中
     *
     * @param fullName 歌单表全名
     * @param url      歌曲 url
     * @return 布尔值，为 true 则存在，为 false 则不存在
     * @throws SystemErrorException 查询数据库的过程中可能出现的异常
     */
    protected boolean checkSongIsExistInList (String fullName, String url) throws SystemErrorException {
        List<String> urls;
        try {
            urls = songDao.findAllSongUrl(fullName);
        } catch (Exception exception) {
            throw new SystemErrorException("系统异常");
        }
        return urls != null && urls.contains(url);
    }
    
    /**
     * 检查动态 id 是否存在
     *
     * @param uid    用户 uid
     * @param newsId 动态的 id
     * @return 布尔值，为 true 则存在，为 false 则不存在
     * @throws SystemErrorException 查询数据库的过程中可能出现的异常
     */
    protected boolean checkNewsIdIsExist (Integer uid, Integer newsId) throws SystemErrorException {
        List<Integer> ids;
        try {
            ids = followDao.findAllNewsId(uid);
        } catch (Exception exception) {
            throw new SystemErrorException("系统异常");
        }
        return ids != null && ids.contains(newsId);
    }
    
    /**
     * 检查用户 uid 是否合法或者是否存在
     *
     * @param uid 需要检查的 uid
     * @throws UidErrorException    若 uid 不合法，抛出该异常
     * @throws NullUserException    若 uid 对应的用户不存在，抛出该异常
     * @throws SystemErrorException 查询数据库的过程中可能出现的异常
     */
    protected void checkUidValid (Integer uid) throws UidErrorException, NullUserException, SystemErrorException {
        if (uid == null || uid <= validUidLow || uid >= validUidHigh) {
            throw new UidErrorException("uid 错误");
        }
        if (checkUserIsNotExistByUid(uid)) {
            throw new NullUserException("用户不存在");
        }
    }
    
    /**
     * 检查用户的电话号码、用户名、生日的数据是否合法，若不合法，抛出异常
     *
     * @param user 需要检查的用户对象
     * @throws UserInfoNotValidException 若用户用户名为 null 或者为空字符串，抛出该异常；
     *                                   若用户的生日对象字符串不满足 yyyy-MM-dd 的格式，抛出该异常
     */
    protected void checkInfoValid (User user) throws UserPhoneNotValidException, UserInfoNotValidException {
        if (user.getUsername() == null || user.getUsername().length() == 0) {
            throw new UserInfoNotValidException("用户名不可为空");
        }
        if (user.getBirthday() != null) {
            try {
                String birth = user.getBirthday();
                if (birth.length() != 10) {
                    throw new Exception();
                }
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date date = format.parse(birth);
                user.setBirthday(format.format(date));
            } catch (Exception e) {
                throw new UserInfoNotValidException("生日数据异常");
            }
        }
    }
    
    /**
     * 检查用户的电话是否合法，若不合法，抛出异常
     *
     * @param phone 需要检查的电话字符串
     * @throws UserPhoneNotValidException 若传入的电话字符串对象为 null，抛出该异常；
     *                                    若传入的电话字符串对象长度不为 11，抛出该异常；
     *                                    若传入的电话字符串对象解析为数字对象后不为 11 位或者无法解析为数字对象，抛出该异常
     */
    protected void checkPhoneValid (String phone) throws UserPhoneNotValidException {
        if (phone == null) {
            throw new UserPhoneNotValidException("电话不可为空");
        }
        if (phone.length() != 11) {
            throw new UserPhoneNotValidException("号码不合法");
        }
        else {
            try {
                long phoneNum = Long.parseLong(phone);
                if (phoneNum <= 10000000000L || phoneNum >= 20000000000L) {
                    throw new Exception();
                }
            } catch (Exception e) {
                throw new UserPhoneNotValidException("号码不合法");
            }
        }
    }
    
    /**
     * 检查密码是否符合要求
     *
     * @param password 需要检查的密码字符串
     * @throws UserPasswordNotValidException 若传入的字符串为 null 或者密码长度为 0，抛出该异常；
     *                                       若传入的字符串长度小于 8，抛出该异常
     */
    protected void checkPasswordValid (String password) throws UserPasswordNotValidException {
        if (password == null || password.length() == 0) {
            throw new UserPasswordNotValidException("密码不可为空");
        }
        if (password.length() < 8) {
            throw new UserPasswordNotValidException("密码长度过短，至少需要设置 8 位的密码");
        }
    }
    
    /**
     * 检查歌单是否存在或者歌单 id 是否合法
     *
     * @param uid       用户 uid
     * @param listIndex 歌单索引
     * @throws UidErrorException      若 uid 不合法，抛出该异常
     * @throws NullUserException      若 uid 对应的用户不存在，抛出该异常
     * @throws SongListErrorException 若歌单索引不合法，抛出该异常；
     * @throws SystemErrorException   查询数据库的过程中可能出现的异常
     */
    protected void checkSongListValid (Integer uid, Integer listIndex) throws UidErrorException, NullUserException, SongListErrorException, SystemErrorException {
        checkUidValid(uid);
        List<SongList> songLists = songDao.findAllSongList(uid);
        if (listIndex == null || listIndex < 0 || listIndex >= songLists.size()) {
            throw new SongListErrorException("歌单索引错误");
        }
    }
    
    /**
     * 检查歌曲信息是否合法
     *
     * @param name   歌曲名
     * @param singer 歌曲的歌手
     * @param url    歌曲的 url
     * @return 若歌曲信息合法，返回歌曲对象
     * @throws SongErrorException   若歌曲名为空或者歌曲 url 为空，抛出该异常
     * @throws SystemErrorException 查询数据库的过程中可能出现的异常
     */
    protected Song checkSongValid (String name, String singer, String url) throws SongErrorException, SystemErrorException {
        if (name == null || name.length() == 0) {
            throw new SongErrorException("歌曲名为空");
        }
        if (url == null || url.length() == 0) {
            throw new SongErrorException("歌曲 url 为空");
        }
        return new Song(name, singer, url);
    }
    
    /**
     * 根据用户 uid 获得用户密码哈希值
     *
     * @param uid 需要获取密码哈希值的用户 uid
     * @return uid 对应用户的密码哈希值
     * @throws SystemErrorException 查询数据库的过程中可能出现的异常
     */
    protected Integer findUserPasswordByUid (Integer uid) throws SystemErrorException {
        Integer password;
        try {
            password = userDao.findUserPasswordByUid(uid);
        } catch (Exception exception) {
            throw new SystemErrorException("系统异常");
        }
        return password;
    }
    
    /**
     * 根据用户电话号码获得用户密码哈希值
     *
     * @param phone 需要获取密码哈希值的用户电话号码
     * @return phone 对应的用户的密码哈希值
     * @throws SystemErrorException 查询数据库的过程中可能出现的异常
     */
    protected Integer findUserPasswordByPhone (String phone) throws SystemErrorException {
        Integer password;
        try {
            password = userDao.findUserPasswordByPhone(phone);
        } catch (Exception exception) {
            throw new SystemErrorException("系统异常");
        }
        return password;
    }
    
    /**
     * 查找用户当前歌单 id 后缀到达了多少号
     *
     * @param uid 用户 uid
     * @return 当前最大的歌单 id 后缀
     * @throws SystemErrorException 查询数据库的过程中可能出现的异常
     */
    protected int findUserListCount (Integer uid) throws SystemErrorException {
        int listCount;
        try {
            listCount = songDao.findUserListCount(uid);
        } catch (Exception exception) {
            throw new SystemErrorException("系统异常");
        }
        return listCount;
    }
    
    /**
     * 查找歌曲的索引
     *
     * @param uid       用户 uid
     * @param listIndex 歌单索引
     * @return 歌曲应该被赋予的索引值
     * @throws SystemErrorException 查询数据库的过程中可能出现的异常
     */
    protected int findSongIndex (Integer uid, Integer listIndex) throws SystemErrorException {
        int songIndex;
        try {
            String fullName = findListFullName(uid, listIndex);
            List<String> urls = songDao.findAllSongUrl(fullName);
            songIndex = urls.size();
        } catch (Exception exception) {
            throw new SystemErrorException("系统异常");
        }
        return songIndex;
    }
    
    /**
     * 查找歌单表的全名
     *
     * @param uid       用户 uid
     * @param listIndex 歌单索引
     * @return 歌单表的全名
     * @throws SystemErrorException 查询数据库的过程中可能出现的异常
     */
    protected String findListFullName (Integer uid, Integer listIndex) throws SystemErrorException {
        String fullName;
        try {
            fullName = songDao.findSongListFullName(uid, listIndex);
        } catch (Exception exception) {
            throw new SystemErrorException("系统异常");
        }
        return fullName;
    }
    
}
