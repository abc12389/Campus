package com.naughty.campus.controller;

import com.alibaba.fastjson.JSON;
import com.naughty.campus.bean.QueryInfo;
import com.naughty.campus.bean.Discussions;
import com.naughty.campus.dao.DiscussionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;


@RestController
public class DiscussionConteroller {
    
    @Autowired
    DiscussionDao discussionDao;

    @CrossOrigin
    @RequestMapping("/allDiscussion")
    public String getDiscussionsList(QueryInfo queryInfo){
        if(queryInfo.getStudentName()!=""||queryInfo.getStudentName()!=null){
            queryInfo.setStudentName("%"+queryInfo.getStudentName()+"%");
        }
      if(queryInfo.getContent()!=""||queryInfo.getContent()!=null){
            queryInfo.setContent("%"+queryInfo.getContent()+"%");
        }else {
            queryInfo.setContent(null);
        }
        int numbers = discussionDao.getDiscussionsCounts(queryInfo);// 获取数据总数
        int pageStart = (queryInfo.getPageNum()-1)*queryInfo.getPageSize();
        queryInfo.setPageNum(pageStart);
        List<Discussions> discussions = discussionDao.getAllDiscussion(queryInfo);
        HashMap<String, Object> res = new HashMap<>();
        res.put("numbers",numbers);
        res.put("data",discussions);
        System.out.println("总条数："+numbers);
        String discussions_json = JSON.toJSONString(res);
        return discussions_json;
    }

   /* @RequestMapping("/DiscussionState")
    public String updateDiscussionState(@RequestParam("id") Integer  id,
                                  @RequestParam("state") Boolean state){
        int i = discussionDao.updateState(id, state);
        System.out.println("用户编号:"+id);
        System.out.println("用户状态:"+state);
        String str = i >0?"success":"error";
        return str;
    }*/

/*    @RequestMapping("/addDiscussion")
    public String addDiscussion(@RequestBody Discussion discussion){
        System.out.println(discussion);
        discussion.setRole("普通管理员");
        int i = discussionDao.addDiscussion(discussion);
        String str = i >0?"success":"error";
        return str;
    }*/

/*
    @RequestMapping("/getUpdate")
    public String getUpdateDiscussion(int id){
        System.out.println("编号:"+id);
        Discussion updateDiscussion = discussionDao.getUpdateDiscussion(id);
        String discussions_json = JSON.toJSONString(updateDiscussion);
        return discussions_json;
    }
*/

/*
    @RequestMapping("/editDiscussion")
    public String editDiscussion(@RequestBody Discussion discussion){
        System.out.println(discussion);
        int i = discussionDao.editDiscussion(discussion);
        String str = i >0?"success":"error";
        return str;
    }
*/

/*    @RequestMapping("/deleteDiscussion")
    public String deleteDiscussion(int id){
        System.out.println(id);
        int i = discussionDao.deleteDiscussion(id);
        String str = i >0?"success":"error";
        return str;
    }*/
}
