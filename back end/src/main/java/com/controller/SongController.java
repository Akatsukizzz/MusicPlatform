package com.controller;

import com.domain.Song;
import com.domain.SongList;
import com.exception.SongListErrorException;
import com.exception.SystemErrorException;
import com.exception.TransactionException;
import com.service.ISongService;
import com.utils.ControllerUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Comparator;
import java.util.List;

/**
 * @author Liu
 * Created on 2020/8/28.
 */
@Controller
public class SongController extends ControllerUtil {
    
    private ISongService songService;
    
    @Autowired
    public void setSongService (ISongService songService) {
        this.songService = songService;
    }
    
    @ResponseBody
    @RequestMapping(value = "/playlist", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String findAllSongList (@RequestBody String json) {
        jsonObject = new JSONObject();
        dataJson = new JSONObject();
        try {
            JSONObject jsonMap = getJsonMap(json);
            Integer uid = jsonMap.getInt("uid");
            List<SongList> lists = songService.findAllSongList(uid);
            if (lists != null) {
                lists.sort(Comparator.comparingInt(SongList::getListIndex));
                for (SongList list : lists) {
                    transfer(list);
                }
                if (!jsonObject.isEmpty()) {
                    jsonObject.accumulate("data", dataJson);
                }
                jsonObject.put("success", true);
            }
            else {
                throw new SongListErrorException("歌单数据异常");
            }
        } catch (TransactionException e) {
            txException(e);
        } catch (Exception e) {
            e.printStackTrace();
            exception(e);
        }
        return jsonObject.toString();
    }
    
    @ResponseBody
    @RequestMapping(value = "/songs", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String findAllSongInList (@RequestBody String json) {
        jsonObject = new JSONObject();
        dataJson = new JSONObject();
        try {
            JSONObject jsonMap = getJsonMap(json);
            Integer uid = jsonMap.getInt("uid");
            Integer listIndex = jsonMap.getInt("listIndex");
            List<Song> songs = songService.findAllSongInList(uid, listIndex);
            if (songs != null) {
                songs.sort((song1, song2) -> song2.getSongIndex() - song1.getSongIndex());
                for (Song song : songs) {
                    transfer(song);
                }
                if (!jsonObject.isEmpty()) {
                    jsonObject.accumulate("data", dataJson);
                }
                jsonObject.put("success", true);
            }
            else {
                throw new SongListErrorException("歌单数据异常");
            }
        } catch (TransactionException e) {
            txException(e);
        } catch (Exception e) {
            exception(e);
        }
        return jsonObject.toString();
    }
    
    @ResponseBody
    @RequestMapping(value = "/play", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String playSong (@RequestBody String json) {
        jsonObject = new JSONObject();
        dataJson = new JSONObject();
        try {
            JSONObject jsonMap = getJsonMap(json);
            Integer uid = jsonMap.getInt("uid");
            Integer listIndex = jsonMap.getInt("listIndex");
            Integer songIndex = jsonMap.getInt("songIndex");
            String url = songService.findSongUrl(uid, listIndex, songIndex);
            dataJson.put("url", url);
            jsonObject.put("data", dataJson);
            jsonObject.put("success", true);
        } catch (TransactionException e) {
            txException(e);
        } catch (Exception e) {
            exception(e);
        }
        return jsonObject.toString();
    }
    
    @ResponseBody
    @RequestMapping(value = "/createPlaylist", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String createSongList (@RequestBody String json) {
        jsonObject = new JSONObject();
        try {
            JSONObject jsonMap = getJsonMap(json);
            Integer uid = jsonMap.getInt("uid");
            String listName = jsonMap.getString("listName");
            boolean flag = songService.createSongList(uid, listName);
            if (flag) {
                jsonObject.put("success", true);
            }
            else {
                throw new SongListErrorException("创建歌单失败");
            }
        } catch (TransactionException e) {
            txException(e);
        } catch (Exception e) {
            exception(e);
        }
        return jsonObject.toString();
    }
    
    @ResponseBody
    @RequestMapping(value = "/removePlaylist", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String removeSongList (@RequestBody String json) {
        jsonObject = new JSONObject();
        try {
            JSONObject jsonMap = getJsonMap(json);
            Integer uid = jsonMap.getInt("uid");
            Integer listIndex = jsonMap.getInt("listIndex");
            boolean flag = songService.removeSongList(uid, listIndex);
            if (flag) {
                jsonObject.put("success", true);
            }
            else {
                throw new SystemErrorException("歌单删除失败");
            }
        } catch (TransactionException e) {
            txException(e);
        } catch (Exception e) {
            exception(e);
        }
        return jsonObject.toString();
    }
    
    @ResponseBody
    @RequestMapping(value = "/addSong", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String addSong (@RequestBody String json) {
        jsonObject = new JSONObject();
        try {
            JSONObject jsonMap = getJsonMap(json);
            Integer uid = jsonMap.getInt("uid");
            Integer listIndex = jsonMap.getInt("listIndex");
            String name = jsonMap.getString("name");
            String singer = jsonMap.getString("singer");
            String url = jsonMap.getString("url");
            boolean flag = songService.addSong(uid, listIndex, name, singer, url);
            if (flag) {
                jsonObject.put("success", true);
            }
            else {
                throw new SystemErrorException("系统异常，添加歌曲失败");
            }
        } catch (TransactionException e) {
            txException(e);
        } catch (Exception e) {
            exception(e);
        }
        return jsonObject.toString();
    }
    
    @ResponseBody
    @RequestMapping(value = "/removeSong", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String removeSong (@RequestBody String json) {
        jsonObject = new JSONObject();
        try {
            JSONObject jsonMap = getJsonMap(json);
            Integer uid = jsonMap.getInt("uid");
            Integer listIndex = jsonMap.getInt("listIndex");
            Integer songIndex = jsonMap.getInt("songIndex");
            boolean flag = songService.removeSong(uid, listIndex, songIndex);
            if (flag) {
                jsonObject.put("success", true);
            }
            else {
                throw new SystemErrorException("系统异常，添加歌曲失败");
            }
        } catch (TransactionException e) {
            txException(e);
        } catch (Exception e) {
            exception(e);
        }
        return jsonObject.toString();
    }
    
    @ResponseBody
    @RequestMapping(value = "/updateListName", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String updateSongListName (@RequestBody String json) {
        jsonObject = new JSONObject();
        try {
            JSONObject jsonMap = getJsonMap(json);
            Integer uid = jsonMap.getInt("uid");
            Integer listIndex = jsonMap.getInt("listIndex");
            String listName = jsonMap.getString("listName");
            boolean flag = songService.updateSongListName(uid, listIndex, listName);
            if (flag) {
                jsonObject.put("success", true);
            }
            else {
                throw new SystemErrorException("系统异常，歌单名称修改失败");
            }
        } catch (TransactionException e) {
            txException(e);
        } catch (Exception e) {
            exception(e);
        }
        return jsonObject.toString();
    }
    
}
