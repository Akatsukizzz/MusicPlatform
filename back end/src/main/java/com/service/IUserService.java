package com.service;

import com.domain.User;
import com.exception.*;

import java.util.List;

/**
 * @author Liu
 * Created on 2020/7/20.
 */
public interface IUserService {
    
    /**
     * 登陆检查，传入用户账号与密码
     * 若账号为 6 位字符串则视为 uid 登陆，若为 11 位字符串则视为电话号码登陆
     *
     * @param account  用户账号，为 uid 或者电话号码字符串
     * @param password 用户输入的密码
     * @return 若用户账户密码对应无误且无异常发生则返回登陆的用户对象
     * @throws AccountNotValidException      若输入的账户为 null 或者长度不是 6 位的 uid 的长度或者不是 11 位的电话号码长度，抛出该异常
     * @throws UserPasswordNotValidException 若用户输入的密码为 null 或者密码哈希值与数据库中保存的密码哈希值不一致，抛出该异常
     * @throws UidErrorException             若传入的为 6 位的账号，视为 uid 登陆，检查 uid 后如果不合法，抛出该异常
     * @throws NullUserException             若账户不存在，抛出该异常
     * @throws UserPhoneNotValidException    若传入的为 11 位的账号，视为电话号码登陆，检查电话号码后如果不合法，抛出该异常
     * @throws SystemErrorException          查询数据库的过程中可能出现的异常
     */
    User login (String account, String password) throws AccountNotValidException, UserPasswordNotValidException, UidErrorException, NullUserException, UserPhoneNotValidException, SystemErrorException;
    
    /**
     * 根据 uid 查询用户
     *
     * @param uid 需要查询的 uid
     * @return 根据 uid 查询到的用户
     * @throws UidErrorException    若 uid 不合法，抛出该异常
     * @throws SystemErrorException 查询数据库的过程中可能出现的异常
     * @throws NullUserException    若 uid 对应的用户不存在，抛出该异常
     */
    User findUserByUid (Integer uid) throws UidErrorException, SystemErrorException, NullUserException;
    
    /**
     * 根据用户名模糊查询用户
     *
     * @param username 需要查询的用户名
     * @return 根据模糊查询得到的对象集合
     * @throws SystemErrorException 查询数据库的过程中可能出现的异常
     */
    List<User> findUserByName (String username) throws SystemErrorException;
    
    /**
     * 保存用户信息
     * 为传入的用户对象生成一个唯一存在的 uid，将 uid 赋给该用户对象后将该对象存入数据库中，其中密码存放的为用户输入密码的哈希值
     *
     * @param user     要保存的新用户的对象
     * @param password 要保存的新用户的密码
     * @return 若保存用户未遇到异常则返回 true，若保存失败则返回 false
     * @throws NullUserException             若传入的 User 对象为 null，抛出该异常
     * @throws UidErrorException             若传入的 User 对象已拥有 uid，抛出该异常
     * @throws UserPhoneNotValidException    若传入的 User 对象的电话号码已被注册，抛出该异常
     * @throws UserPasswordNotValidException 若传入的密码不合法，抛出该异常
     * @throws UserInfoNotValidException     若传入的 User 对象的信息存在不合法信息，抛出该异常
     * @throws SystemErrorException          查询数据库的过程中可能出现的异常
     */
    boolean addUser (User user, String password) throws NullUserException, UidErrorException, UserPhoneNotValidException, UserPasswordNotValidException, UserInfoNotValidException, SystemErrorException;
    
    /**
     * 修改用户的用户名、性别、地区和生日信息
     *
     * @param user 需要修改信息的用户对象
     * @return 若更新用户信息未遇到异常则返回 true
     * @throws NullUserException          若传入的用户对象为 null，抛出该异常；
     *                                    若传入的用户对象的 uid 在数据库中对应的对象不存在，抛出该异常
     * @throws UidErrorException          若传入的用户对象的 uid 为 null 或者不是 6 位的 uid，抛出该异常
     * @throws UserPhoneNotValidException 若传入的用户电话不合法，抛出该异常
     * @throws UserInfoNotValidException  若传入的用户对象的信息不合法，抛出该异常
     * @throws SystemErrorException       查询数据库的过程中可能出现的异常
     */
    boolean updateUser (User user) throws NullUserException, UidErrorException, UserPhoneNotValidException, UserInfoNotValidException, SystemErrorException;
    
    /**
     * 修改用户电话号码
     *
     * @param uid   要修改电话号码的用户的 uid
     * @param phone 修改的目标电话号码
     * @return 若更新用户电话未遇到异常则返回 true
     * @throws UidErrorException          若传入的 uid 为 null 或者不是 6 位的 uid，抛出该异常
     * @throws NullUserException          若 uid 对应的用户不存在，抛出该异常
     * @throws UserPhoneNotValidException 若传入的电话不合法，抛出该异常；
     *                                    若修改的目标电话号码已被注册，抛出该异常
     * @throws SystemErrorException       查询数据库的过程中可能出现的异常
     */
    boolean updateUserPhone (Integer uid, String phone) throws UidErrorException, NullUserException, UserPhoneNotValidException, SystemErrorException;
    
    /**
     * 修改用户密码
     *
     * @param uid         要修改密码的用户的 uid
     * @param oldPassword 用户输入的旧密码字符串，用以比对旧密码是否正确
     * @param newPassword 用户输入的新密码字符串
     * @return 若更新密码未遇到异常则返回 true
     * @throws UidErrorException             若 uid 为 null 或者不是 6 位的 uid，抛出该异常
     * @throws NullUserException             若 uid 对应的用户不存在，抛出该异常
     * @throws UserPasswordNotValidException 若新密码不合法，抛出该异常；
     *                                       若旧密码的哈希值与数据库中 uid 对应的用户的密码不一致；抛出该异常；
     *                                       若新密码与旧密码相同，抛出该异常
     * @throws SystemErrorException          查询数据库的过程中可能出现的异常
     */
    boolean updateUserPassword (Integer uid, String oldPassword, String newPassword) throws UidErrorException, NullUserException, UserPasswordNotValidException, SystemErrorException;
    
}
