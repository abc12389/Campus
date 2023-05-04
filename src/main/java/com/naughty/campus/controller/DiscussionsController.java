package com.naughty.campus.controller;

import com.alibaba.fastjson.JSON;
import com.naughty.campus.bean.QueryInfo;
import com.naughty.campus.bean.Discussions;
import com.naughty.campus.dao.DiscussionsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;


@RestController
public class DiscussionsController {
    
    @Autowired
    DiscussionsDao discussionsDao;

    @CrossOrigin
    @RequestMapping("/allDiscussions")
    public String getDiscussionsList(QueryInfo queryInfo){
        if(queryInfo.getDepartment()==""||queryInfo.getDepartment()==null){
            queryInfo.setDepartment(null);
        }
        if(queryInfo.getId()!=""||queryInfo.getId()!=null){
            queryInfo.setId("%"+queryInfo.getId()+"%");
        }else {
            queryInfo.setId(null);
        }
        int numbers = discussionsDao.getDiscussionsCounts(queryInfo);// 获取数据总数
        int pageStart = (queryInfo.getPageNum()-1)*queryInfo.getPageSize();
        queryInfo.setPageNum(pageStart);
        List<Discussions> discussionss = discussionsDao.getAllDiscussions(queryInfo);
        HashMap<String, Object> res = new HashMap<>();
        res.put("numbers",numbers);
        res.put("data",discussionss);
        System.out.println("总条数："+numbers);
        String discussions_json = JSON.toJSONString(res);
        return discussions_json;
    }

   /* @RequestMapping("/discussionsState")
    public String updateDiscussionsState(@RequestParam("id") Integer  id,
                                  @RequestParam("state") Boolean state){
        int i = discussionsDao.updateState(id, state);
        System.out.println("用户编号:"+id);
        System.out.println("用户状态:"+state);
        String str = i >0?"success":"error";
        return str;
    }*/

/*    @RequestMapping("/addDiscussions")
    public String addDiscussions(@RequestBody Discussions discussions){
        System.out.println(discussions);
        discussions.setRole("普通管理员");
        int i = discussionsDao.addDiscussions(discussions);
        String str = i >0?"success":"error";
        return str;
    }*/

/*
    @RequestMapping("/getUpdate")
    public String getUpdateDiscussions(int id){
        System.out.println("编号:"+id);
        Discussions updateDiscussions = discussionsDao.getUpdateDiscussions(id);
        String discussionss_json = JSON.toJSONString(updateDiscussions);
        return discussionss_json;
    }
*/

/*
    @RequestMapping("/editDiscussions")
    public String editDiscussions(@RequestBody Discussions discussions){
        System.out.println(discussions);
        int i = discussionsDao.editDiscussions(discussions);
        String str = i >0?"success":"error";
        return str;
    }
*/

/*    @RequestMapping("/deleteDiscussions")
    public String deleteDiscussions(int id){
        System.out.println(id);
        int i = discussionsDao.deleteDiscussions(id);
        String str = i >0?"success":"error";
        return str;
    }*/
}
