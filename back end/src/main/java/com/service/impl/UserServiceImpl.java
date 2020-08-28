package com.service.impl;

import com.dao.IFollowDao;
import com.dao.IUserDao;
import com.domain.User;
import com.exception.*;
import com.service.IUserService;
import com.utils.ServiceCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

/**
 * @author Liu
 * Created on 2020/7/20.
 */
@Service("userService")
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = RuntimeException.class)
public class UserServiceImpl extends ServiceCheckUtil implements IUserService {
    
    private IUserDao userDao;
    
    private IFollowDao followDao;
    
    @Autowired
    public void setUserDao (IUserDao userDao) {
        this.userDao = userDao;
    }
    
    @Autowired
    public void setFollowDao (IFollowDao followDao) {
        this.followDao = followDao;
    }
    
    @Override
    public User login (String account, String password) throws AccountNotValidException, UserPasswordNotValidException, UidErrorException, NullUserException, UserPhoneNotValidException, SystemErrorException {
        int rPassword;
        User user;
        if (account == null) {
            throw new AccountNotValidException("账号不可为空");
        }
        if (password == null) {
            throw new UserPasswordNotValidException("密码不可为空");
        }
        if (account.length() == 6) {
            Integer uid;
            try {
                uid = Integer.valueOf(account);
            } catch (Exception e) {
                throw new UidErrorException("uid 错误");
            }
            checkUidValid(uid);
            try {
                user = userDao.findUserByUid(uid);
            } catch (Exception exception) {
                throw new SystemErrorException("系统异常");
            }
            rPassword = findUserPasswordByUid(uid);
        }
        else if (account.length() == 11) {
            try {
                checkPhoneValid(account);
            } catch (Exception e) {
                throw new UserPhoneNotValidException("电话号码错误");
            }
            if (!checkUserIsExistByPhone(account)) {
                throw new NullUserException("用户不存在");
            }
            else {
                try {
                    user = userDao.findUserByPhone(account);
                } catch (Exception exception) {
                    throw new SystemErrorException("系统异常");
                }
            }
            rPassword = findUserPasswordByPhone(account);
        }
        else {
            throw new AccountNotValidException("账号错误");
        }
        int passwordVal = password.hashCode();
        if (rPassword != passwordVal) {
            throw new UserPasswordNotValidException("密码错误");
        }
        else {
            return user;
        }
    }
    
    @Override
    public User findUserByUid (Integer uid) throws UidErrorException, SystemErrorException, NullUserException {
        if (uid == null || uid <= validUidLow || uid >= validUidHigh) {
            throw new UidErrorException("uid 错误");
        }
        User user;
        try {
            user = userDao.findUserById(uid);
        } catch (Exception exception) {
            throw new SystemErrorException("系统异常");
        }
        if (user == null) {
            throw new NullUserException("找不到该用户");
        }
        else {
            return user;
        }
    }
    
    @Override
    public List<User> findUserByName (String username) throws SystemErrorException {
        if (username == null || username.length() == 0) {
            throw new NullUserException("用户名为空");
        }
        String name = "%" + username + "%";
        List<User> res;
        try {
            res = userDao.findUserByUsername(name);
        } catch (Exception exception) {
            throw new SystemErrorException("系统异常");
        }
        return res;
    }
    
    @Override
    public boolean addUser (User user, String password) throws NullUserException, UidErrorException, UserPhoneNotValidException, UserPasswordNotValidException, UserInfoNotValidException, SystemErrorException {
        if (user == null) {
            throw new NullUserException("用户为空");
        }
        if (user.getUid() != null) {
            throw new UidErrorException("用户 uid 异常");
        }
        if (checkUserIsExistByPhone(user.getPhone())) {
            throw new UserPhoneNotValidException("该号码已被注册");
        }
        checkPasswordValid(password);
        checkPhoneValid(user.getPhone());
        checkInfoValid(user);
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        while (true) {
            int uid = random.nextInt(999999);
            if (uid > validUidLow && checkUserIsNotExistByUid(uid)) {
                user.setUid(uid);
                break;
            }
        }
        user.setPassword(password.hashCode());
        try {
            userDao.addUser(user);
        } catch (Exception e) {
            try {
                userDao.deleteUser(user.getUid());
            } catch (Exception exception) {
                throw new SystemErrorException("系统异常");
            }
            return false;
        }
        return true;
    }
    
    @Override
    public boolean updateUser (User user) throws NullUserException, UidErrorException, UserPhoneNotValidException, UserInfoNotValidException, SystemErrorException {
        if (user == null) {
            throw new NullUserException("用户不存在");
        }
        int uid = user.getUid();
        checkUidValid(uid);
        checkInfoValid(user);
        try {
            userDao.updateUserInfo(user);
        } catch (Exception exception) {
            throw new SystemErrorException("系统异常");
        }
        List<Integer> ids;
        try {
            ids = followDao.findAllFollowersUid(uid);
        } catch (Exception exception) {
            throw new SystemErrorException("系统异常");
        }
        for (Integer id : ids) {
            try {
                followDao.updateFollowersListInfo(id, user);
            } catch (Exception exception) {
                throw new SystemErrorException("系统异常");
            }
        }
        return true;
    }
    
    @Override
    public boolean updateUserPhone (Integer uid, String phone) throws UidErrorException, NullUserException, UserPhoneNotValidException, SystemErrorException {
        checkUidValid(uid);
        checkPhoneValid(phone);
        boolean isExist = checkUserIsExistByPhone(phone);
        if (isExist) {
            throw new UserPhoneNotValidException("该号码已被注册");
        }
        else {
            try {
                userDao.updateUserPhone(uid, phone);
            } catch (Exception exception) {
                throw new SystemErrorException("系统异常");
            }
            return true;
        }
    }
    
    @Override
    public boolean updateUserPassword (Integer uid, String oldPassword, String newPassword) throws UidErrorException, NullUserException, UserPasswordNotValidException, SystemErrorException {
        if (oldPassword == null) {
            throw new UserPasswordNotValidException("旧密码为空");
        }
        checkUidValid(uid);
        checkPasswordValid(newPassword);
        int oldPasswordVal = oldPassword.hashCode();
        int passwordVal = findUserPasswordByUid(uid);
        if (oldPasswordVal != passwordVal) {
            throw new UserPasswordNotValidException("旧密码输入错误");
        }
        int newPasswordVal = newPassword.hashCode();
        if (newPasswordVal == oldPasswordVal) {
            throw new UserPasswordNotValidException("新密码不可以与当前密码相同");
        }
        else {
            try {
                userDao.updateUserPassword(uid, newPasswordVal);
            } catch (Exception exception) {
                throw new SystemErrorException("系统异常");
            }
            return true;
        }
    }
    
}
