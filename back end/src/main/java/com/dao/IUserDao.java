package com.dao;

import com.domain.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Liu
 * Created on 2020/7/20.
 */
@Repository("userDao")
public interface IUserDao {
    
    /**
     * 根据 uid 查询用户，返回用户除密码外的全部个人信息
     *
     * @param uid 需要查询的目标 id
     * @return 根据 uid 在数据库中查找的结果，如果该 uid 对应的用户不存在则返回 null
     */
    @Select("select uid, phone, username, sex, birthday, location from User where uid = #{uid}")
    User findUserByUid (Integer uid);
    
    /**
     * 根据 uid 查询用户名
     *
     * @param uid 用户 uid
     * @return uid 用户的用户名
     */
    @Select("select username from User where uid = #{uid}")
    String findUsername (Integer uid);
    
    /**
     * 根据 uid 查询用户，仅返回用户公开信息：uid，用户名，性别，生日和地区
     *
     * @param uid 用户 uid
     * @return uid 对应的用户，若不存在则返回 null
     */
    @Select("select uid, username, sex, birthday, location from User where uid = #{id}")
    User findUserById (Integer uid);
    
    /**
     * 根据用户名模糊查询用户
     *
     * @param username 要查询的用户名
     * @return 模糊查询的结果
     */
    @Select("select uid, username, sex, birthday, location from User where username like #{username}")
    List<User> findUserByUsername (String username);
    
    /**
     * 检索 User 表，检查电话号码对应的用户是否存在
     *
     * @param phone 需要检查的用户电话号码
     * @return 若该号码对应的用户存在则返回用户对象，若不存在则返回 null
     */
    @Select("select uid, phone, username, sex, birthday, location from User where phone = #{phone}")
    User findUserByPhone (String phone);
    
    /**
     * 根据用户 uid 查询用户密码的哈希值
     *
     * @param uid 需要查询用户密码的 uid
     * @return uid 对应用户的密码的哈希值
     */
    @Select("select password from User where uid = #{uid}")
    Integer findUserPasswordByUid (Integer uid);
    
    /**
     * 根据用户电话号码获得当前用户密码的哈希值
     *
     * @param phone 需要查询密码的用户的电话号码
     * @return 用户的密码的哈希值
     */
    @Select("select password from User where phone = #{phone}")
    Integer findUserPasswordByPhone (String phone);
    
    /**
     * 保存用户信息
     * 将用户的全部信息保存到 User 表中，同时向歌单表中插入该用户默认歌单的信息
     * 动态创建四张与用户相关的表
     *
     * @param user 需要保存的用户对象
     */
    @Update("insert into User(uid, phone, username, password, sex, birthday, location) " +
                    "values(#{uid}, #{phone}, #{username}, #{password}, #{sex}, #{birthday}, #{location});" +
            "insert into SongList values(#{uid}, 0, '我喜欢的音乐', '${uid}_Songs_0');" +
            "create table #{uid}_Following (" +
                    "uid int primary key, " +
                    "username varchar(20) not null, " +
                    "sex tinyint(1) null, " +
                    "birthday date null, " +
                    "location varchar(30) null);" +
            "create table #{uid}_Followers (" +
                    "uid int primary key, " +
                    "username varchar(20) not null, " +
                    "sex tinyint(1) null, " +
                    "birthday date null, " +
                    "location varchar(30) null);" +
            "create table #{uid}_Songs_0 (" +
                    "songIndex int not null, " +
                    "name varchar(30) not null, " +
                    "singer varchar(20)," +
                    "url varchar(500) not null);" +
            "create table #{uid}_news (" +
                    "newsId int auto_increment primary key," +
                    "uid int not null," +
                    "info varchar(200) not null," +
                    "time datetime not null)")
    void addUser (User user);
    
    /**
     * 修改 uid 对应的用户的 username 用户名、sex 性别、location 地区、birthday 生日的信息
     *
     * @param user 需要修改的用户对象
     */
    @Update("update User set username = #{username}, sex = #{sex}, location = #{location}, birthday = #{birthday} where uid = #{uid};")
    void updateUserInfo (User user);
    
    /**
     * 更新 uid 对应的用户的电话号码属性
     *
     * @param uid      需要修改电话属性的用户的 uid
     * @param newPhone 新的电话号码
     */
    @Update("update User set phone = #{newPhone} where uid = #{uid}")
    void updateUserPhone (@Param("uid") Integer uid, @Param("newPhone") String newPhone);
    
    /**
     * 更新 uid 对应的用户的密码
     *
     * @param uid         需要修改密码的用户的 uid
     * @param newPassword 用户的新密码
     */
    @Update("update User set password = #{newPassword} where uid = #{uid}")
    void updateUserPassword (@Param("uid") Integer uid, @Param("newPassword") Integer newPassword);
    
    /**
     * 删除用户信息以及用户对应的表，用于事务控制
     *
     * @param uid 用户 uid
     */
    @Delete("delete from User where uid = #{uid};" +
            "delete from SongList where uid = #{uid};" +
            "drop table if exists #{uid}_Following;" +
            "drop table if exists #{uid}_Followers;" +
            "drop table if exists #{uid}_Songs_0;" +
            "drop table if exists #{uid}_news")
    void deleteUser (Integer uid);
    
    @Select("select * from Administrator")
    List<User> addAdministrator ();
    
    @Select("select * from User")
    List<User> findAll ();
    
}
