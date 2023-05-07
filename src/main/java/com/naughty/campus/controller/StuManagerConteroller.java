package com.naughty.campus.controller;

import com.alibaba.fastjson.JSON;
import com.naughty.campus.bean.QueryInfo;
import com.naughty.campus.bean.Users;
import com.naughty.campus.bean.UsersDTO;
import com.naughty.campus.dao.StuManagerDao;
import com.naughty.campus.util.ResponseResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;


@RestController
public class StuManagerConteroller {

    @Autowired
    StuManagerDao stumanagerDao;

    @CrossOrigin
    @RequestMapping("/allStuManager")
    public String getStuManagerList(QueryInfo queryInfo) {
        if (queryInfo.getDepartment() == "" || queryInfo.getDepartment() == null) {
            queryInfo.setDepartment(null);
        }
        if (queryInfo.getStudentName() != "" || queryInfo.getStudentName() != null) {
            queryInfo.setStudentName("%" + queryInfo.getStudentName() + "%");
        } else {
            queryInfo.setStudentName(null);
        }
        int numbers = stumanagerDao.getStuManagerCounts(queryInfo);// 获取数据总数
        int pageStart = (queryInfo.getPageNum() - 1) * queryInfo.getPageSize();
        queryInfo.setPageNum(pageStart);
        List<Users> stumanagers = stumanagerDao.getAllStuManager(queryInfo);
        HashMap<String, Object> res = new HashMap<>();
        res.put("numbers", numbers);
        res.put("data", stumanagers);
        System.out.println("总条数：" + numbers);
        String stumangers_json = JSON.toJSONString(res);
        return stumangers_json;
    }
}
