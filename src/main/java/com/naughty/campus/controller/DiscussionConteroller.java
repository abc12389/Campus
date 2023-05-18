package com.naughty.campus.controller;

import com.alibaba.fastjson.JSON;
import com.naughty.campus.bean.DiscussionsDTO;
import com.naughty.campus.bean.QueryInfo;
import com.naughty.campus.bean.Discussions;
import com.naughty.campus.dao.DiscussionDao;
import com.naughty.campus.util.ResponseResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;


@RestController
public class DiscussionConteroller {

    @Autowired
    DiscussionDao discussionDao;

    @CrossOrigin
    @RequestMapping("/allDiscussions")
    public String getDiscussionsList(QueryInfo queryInfo) {
        if (queryInfo.getStudentName() != "" && queryInfo.getStudentName() != null) {
            queryInfo.setStudentName("%" + queryInfo.getStudentName() + "%");
        }
        queryInfo.setStudentName(null);
        if (queryInfo.getDisContent() != "" && queryInfo.getDisContent() != null) {
            queryInfo.setDisContent("%" + queryInfo.getDisContent() + "%");
        }
        queryInfo.setDisContent(null);
        int numbers = discussionDao.getDiscussionsCounts(queryInfo);// 获取数据总数
        int pageStart = (queryInfo.getPageNum() - 1) * queryInfo.getPageSize();
        queryInfo.setPageNum(pageStart);
        List<Discussions> discussions = discussionDao.getAllDiscussions(queryInfo);
        HashMap<String, Object> res = new HashMap<>();
        res.put("numbers", numbers);
        res.put("data", discussions);
        System.out.println("总条数：" + numbers);
        String discussions_json = JSON.toJSONString(res);
        return discussions_json;
    }

    @RequestMapping("/addDiscussions")
    public ResponseResult<String> addDis(@RequestBody DiscussionsDTO discussionsDTO) {
        Discussions discussions = new Discussions();
        BeanUtils.copyProperties(discussionsDTO, discussions);
        int insert = discussionDao.insert(discussions);
        if (insert <= 0) {
            return new ResponseResult<String>(500, "添加失败", null);
        }
        return new ResponseResult<String>(200, "添加成功", null);
    }

    @RequestMapping("/getUpdateDis")
    public ResponseResult<DiscussionsDTO> getUpdateDis(int id) {
        Discussions updateDis = discussionDao.selectById(id);
        DiscussionsDTO discussionsDTO = new DiscussionsDTO();
        BeanUtils.copyProperties(updateDis, discussionsDTO);
        return new ResponseResult<DiscussionsDTO>(200, discussionsDTO);
    }

    @RequestMapping("/updateDiscussions")
    public ResponseResult<String> updateDis(@RequestBody DiscussionsDTO discussionsDTO) {
        Discussions discussions = new Discussions();
        BeanUtils.copyProperties(discussionsDTO, discussions);
        int i = discussionDao.updateById(discussions);
        return new ResponseResult<String>(200, "修改成功", null);
    }

    @RequestMapping("/deleteDiscussions")
    public ResponseResult<String> deleteDiscussions(int id) {
        int i = discussionDao.deleteById(id);
        return new ResponseResult<String>(200, "删除成功", null);
    }
}
