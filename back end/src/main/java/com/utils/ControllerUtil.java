package com.utils;

import com.domain.*;
import com.exception.TransactionException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

/**
 * @author Liu
 * Created on 2020/8/28.
 */
public class ControllerUtil {
    
    protected JSONObject jsonObject;
    
    protected JSONObject dataJson;
    
    protected void transfer (Object object) {
        JSONObject subObject = new JSONObject();
        if (object instanceof User) {
            User user = (User) object;
            subObject.put("uid", user.getUid());
            subObject.put("phone", user.getPhone());
            subObject.put("username", user.getUsername());
            subObject.put("sex", user.getSex());
            subObject.put("birthday", user.getBirthday());
            subObject.put("location", user.getLocation());
            dataJson.accumulate("User", subObject);
        }
        else if (object instanceof FollowUser) {
            FollowUser user = (FollowUser) object;
            subObject.put("uid", user.getUid());
            subObject.put("username", user.getUsername());
            subObject.put("sex", user.getSex());
            subObject.put("birthday", user.getBirthday());
            subObject.put("location", user.getLocation());
            dataJson.accumulate("FollowUser", subObject);
        }
        else if (object instanceof SongList) {
            SongList songList = (SongList) object;
            subObject.put("uid", songList.getUid());
            subObject.put("listIndex", songList.getListIndex());
            subObject.put("listName", songList.getListName());
            dataJson.accumulate("SongList", subObject);
        }
        else if (object instanceof Song) {
            Song song = (Song) object;
            subObject.put("songIndex", song.getSongIndex());
            subObject.put("name", song.getName());
            subObject.put("singer", song.getSinger());
            subObject.put("url", song.getUrl());
            dataJson.accumulate("Song", subObject);
        }
        else if (object instanceof News) {
            News news = (News) object;
            subObject.put("newsId", news.getNewsId());
            subObject.put("uid", news.getUid());
            subObject.put("username", news.getUsername());
            subObject.put("info", news.getInfo());
            subObject.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(news.getTime()));
            dataJson.accumulate("News", subObject);
        }
        jsonObject.put("success", true);
    }
    
    protected JSONObject getJsonMap (String json) {
        return new JSONObject(json);
    }
    
    protected void txException (TransactionException e) {
        jsonObject = new JSONObject();
        jsonObject.put("success", false);
        jsonObject.put("err", e.getClass().getSimpleName() + " : " + e.getMessage());
    }
    
    protected void exception (Exception e) {
        jsonObject = new JSONObject();
        jsonObject.put("success", false);
        jsonObject.put("err", "系统异常：" + e.getMessage());
    }

}
