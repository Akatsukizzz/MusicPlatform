package com.controller;

import com.domain.User;
import com.exception.SystemErrorException;
import com.exception.TransactionException;
import com.service.IUserService;
import com.utils.ControllerUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Liu
 * Created on 2020/7/27.
 */
@Controller
public class UserController extends ControllerUtil {
    
    private IUserService userService;
    
    @Autowired
    public void setUserService (IUserService userService) {
        this.userService = userService;
    }
    
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String login (@RequestBody String json) {
        jsonObject = new JSONObject();
        dataJson = new JSONObject();
        try {
            JSONObject jsonMap = new JSONObject(json);
//            String account = jsonMap.getString("account");
            String account = jsonMap.getString("username");
            String password = jsonMap.getString("password");
            User user = userService.login(account, password);
            if (user != null) {
                transfer(user);
                jsonObject.accumulate("data", dataJson);
            }
            else {
                throw new TransactionException("登陆失败");
            }
        } catch (TransactionException e) {
            txException(e);
        } catch (Exception e) {
            exception(e);
        }
        return jsonObject.toString();
    }
    
    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String findUser (@RequestBody String json) {
        jsonObject = new JSONObject();
        dataJson = new JSONObject();
        try {
            JSONObject jsonMap = getJsonMap(json);
            String account = jsonMap.getString("account");
            if (account == null) {
                throw new TransactionException("账户为空");
            }
            else {
                List<User> users = new LinkedList<>();
                if (account.length() == 6) {
                    try {
                        int uid = Integer.parseInt(account);
                        User user = userService.findUserByUid(uid);
                        if (user != null) {
                            users.add(user);
                        }
                    } catch (Exception ignored) {
                        //尝试解析 uid，若可以解析为 uid 则将 uid 查找结果加入到结果集中，否则只按照查找用户名处理
                    }
                }
                users.addAll(userService.findUserByName(account));
                for (User user : users) {
                    transfer(user);
                }
                if (!dataJson.isEmpty()) {
                    jsonObject.accumulate("data", dataJson);
                }
                jsonObject.put("success", true);
            }
        } catch (TransactionException e) {
            txException(e);
        } catch (Exception e) {
            exception(e);
        }
        return jsonObject.toString();
    }
    
    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String addUser (@RequestBody String json) {
        jsonObject = new JSONObject();
        dataJson = new JSONObject();
        try {
            JSONObject jsonMap = getJsonMap(json);
            String phone = jsonMap.getString("phone");
            String username = jsonMap.getString("username");
            String password = jsonMap.getString("password");
            boolean sex = jsonMap.getBoolean("sex");
            String birthday = jsonMap.getString("birthday");
            String location = jsonMap.getString("location");
            User user = new User();
            user.setPhone(phone);
            user.setUsername(username);
            user.setSex(sex);
            user.setBirthday(birthday);
            user.setLocation(location);
            boolean flag = userService.addUser(user, password);
            if (flag) {
                transfer(user);
                jsonObject.accumulate("data", dataJson);
            }
            else {
                throw new SystemErrorException("用户添加失败");
            }
        } catch (TransactionException e) {
            txException(e);
        } catch (Exception e) {
            exception(e);
        }
        return jsonObject.toString();
    }
    
    @ResponseBody
    @RequestMapping(value = "/user/update", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String updateUser (@RequestBody String json) {
        jsonObject = new JSONObject();
        try {
            JSONObject jsonMap = getJsonMap(json);
            Integer uid = jsonMap.getInt("uid");
            User user = userService.findUserByUid(uid);
            boolean flag;
            String username = jsonMap.getString("username");
            Boolean sex = jsonMap.getBoolean("sex");
            String birthday = jsonMap.getString("birthday");
            String location = jsonMap.getString("location");
            user.setUsername(username);
            user.setSex(sex);
            user.setBirthday(birthday);
            user.setLocation(location);
            flag = userService.updateUser(user);
            if (flag) {
                jsonObject.put("success", true);
            }
            else {
                throw new SystemErrorException("系统异常");
            }
        } catch (TransactionException e) {
            txException(e);
        } catch (Exception e) {
            exception(e);
        }
        return jsonObject.toString();
    }
    
    @ResponseBody
    @RequestMapping(value = "/updatePhone", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String updateUserPhone (@RequestBody String json) {
        jsonObject = new JSONObject();
        try {
            JSONObject jsonMap = getJsonMap(json);
            Integer uid = jsonMap.getInt("uid");
            String phone = jsonMap.getString("phone");
            boolean flag = userService.updateUserPhone(uid, phone);
            if (flag) {
                jsonObject.put("success", true);
            }
            else {
                throw new SystemErrorException("系统异常");
            }
        } catch (TransactionException e) {
            txException(e);
        } catch (Exception e) {
            exception(e);
        }
        return jsonObject.toString();
    }
    
    @ResponseBody
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String updateUserPassword (@RequestBody String json) {
        jsonObject = new JSONObject();
        try {
            JSONObject jsonMap = getJsonMap(json);
            Integer uid = jsonMap.getInt("uid");
            String oldPassword = jsonMap.getString("oldPassword");
            String newPassword = jsonMap.getString("newPassword");
            boolean flag = userService.updateUserPassword(uid, oldPassword, newPassword);
            if (flag) {
                jsonObject.put("success", true);
            }
            else {
                throw new SystemErrorException("系统异常");
            }
        } catch (TransactionException e) {
            txException(e);
        } catch (Exception e) {
            exception(e);
        }
        return jsonObject.toString();
    }
    
}
