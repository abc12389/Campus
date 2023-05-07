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
}
