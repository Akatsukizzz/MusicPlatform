package com.controller;

import com.domain.FollowUser;
import com.domain.News;
import com.enums.FollowActionEnum;
import com.enums.ShowNewsActionEnum;
import com.exception.SystemErrorException;
import com.exception.TransactionException;
import com.service.IFollowService;
import com.utils.ControllerUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Liu
 * Created on 2020/8/28.
 */
@Controller
public class FollowController extends ControllerUtil {
    
    private IFollowService followService;
    
    @Autowired
    public void setFollowService (IFollowService followService) {
        this.followService = followService;
    }
    
    @ResponseBody
    @RequestMapping(value = "/follow", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String findAllFollow (@RequestBody String json) {
        jsonObject = new JSONObject();
        dataJson = new JSONObject();
        try {
            JSONObject jsonMap = getJsonMap(json);
            Integer uid = jsonMap.getInt("uid");
            List<FollowUser> users;
            String type = jsonMap.getString("type");
            if ("following".equals(type)) {
                users = followService.findAllFollowing(uid);
            }
            else if ("followers".equals(type)) {
                users = followService.findAllFollower(uid);
            }
            else {
                throw new TransactionException("错误的请求");
            }
            if (users != null) {
                for (FollowUser user : users) {
                    transfer(user);
                }
                if (!jsonObject.isEmpty()) {
                    jsonObject.accumulate("data", dataJson);
                }
                jsonObject.put("success", true);
            }
            else {
                throw new TransactionException("关注模块异常");
            }
        } catch (TransactionException e) {
            txException(e);
        } catch (Exception e) {
            exception(e);
        }
        return jsonObject.toString();
    }
    
    @ResponseBody
    @RequestMapping(value = "/news", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String news (@RequestBody String json) {
        jsonObject = new JSONObject();
        dataJson = new JSONObject();
        try {
            JSONObject jsonMap = getJsonMap(json);
            Integer uid = jsonMap.getInt("uid");
            String type = jsonMap.getString("type");
            List<News> newsList;
            if ("selfzone".equals(type)) {
                newsList = followService.findAllNews(uid, ShowNewsActionEnum.SELF);
            }
            else if ("friends".equals(type)) {
                newsList = followService.findAllNews(uid, ShowNewsActionEnum.ALL);
            }
            else {
                throw new TransactionException("错误的请求");
            }
            if (newsList != null) {
                newsList.sort((news1, news2) -> news2.getTime().compareTo(news1.getTime()));
                for (News news : newsList) {
                    transfer(news);
                }
                if (!dataJson.isEmpty()) {
                    jsonObject.accumulate("data", dataJson);
                }
                jsonObject.put("success", true);
            }
            else {
                throw new SystemErrorException("动态加载失败");
            }
        } catch (TransactionException e) {
            txException(e);
        } catch (Exception e) {
            exception(e);
        }
        return jsonObject.toString();
    }
    
    @ResponseBody
    @RequestMapping(value = "/updateFollow", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String updateFollow (@RequestBody String json) {
        jsonObject = new JSONObject();
        try {
            JSONObject jsonMap = getJsonMap(json);
            Integer uid = jsonMap.getInt("uid");
            Integer fUid = jsonMap.getInt("fUid");
            String type = jsonMap.getString("type");
            boolean flag;
            if ("addFollow".equals(type)) {
                flag = followService.updateFollow(uid, fUid, FollowActionEnum.ADD_FOLLOW);
            }
            else if ("removeFollow".equals(type)) {
                flag = followService.updateFollow(uid, fUid, FollowActionEnum.REMOVE_FOLLOW);
            }
            else {
                throw new TransactionException("错误的请求");
            }
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
    @RequestMapping(value = "/addNews", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String addNews (@RequestBody String json) {
        jsonObject = new JSONObject();
        try {
            JSONObject jsonMap = getJsonMap(json);
            Integer uid = jsonMap.getInt("uid");
            String info = jsonMap.getString("info");
            boolean flag = followService.addNews(uid, info);
            if (flag) {
                jsonObject.put("success", true);
            }
            else {
                throw new SystemErrorException("系统异常，动态发布失败");
            }
        } catch (TransactionException e) {
            txException(e);
        } catch (Exception e) {
            exception(e);
        }
        return jsonObject.toString();
    }
    
    @ResponseBody
    @RequestMapping(value = "/deleteNews", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String deleteNews (@RequestBody String json) {
        jsonObject = new JSONObject();
        try {
            JSONObject jsonMap = getJsonMap(json);
            Integer uid = jsonMap.getInt("uid");
            Integer newsIndex = jsonMap.getInt("newsIndex");
            boolean flag = followService.deleteNews(uid, newsIndex);
            if (flag) {
                jsonObject.put("success", true);
            }
            else {
                throw new SystemErrorException("系统异常，动态删除失败");
            }
        } catch (TransactionException e) {
            txException(e);
        } catch (Exception e) {
            exception(e);
        }
        return jsonObject.toString();
    }

}
