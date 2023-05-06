package com.naughty.campus.controller;

import com.alibaba.fastjson.JSON;
import com.naughty.campus.bean.QueryInfo;
import com.naughty.campus.bean.Users;
import com.naughty.campus.dao.StuManagerDao;
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
    public String getStuManagerList(QueryInfo queryInfo){
        if(queryInfo.getDepartment()==""||queryInfo.getDepartment()==null){
            queryInfo.setDepartment(null);
        }
        if(queryInfo.getStudentName()!=""||queryInfo.getStudentName()!=null){
            queryInfo.setStudentName("%"+queryInfo.getStudentName()+"%");
        }else {
            queryInfo.setStudentName(null);
        }
        int numbers = stumanagerDao.getStuManagerCounts(queryInfo);// 获取数据总数
        int pageStart = (queryInfo.getPageNum()-1)*queryInfo.getPageSize();
        queryInfo.setPageNum(pageStart);
        List<Users> stumanagers = stumanagerDao.getAllStuManager(queryInfo);
        HashMap<String, Object> res = new HashMap<>();
        res.put("numbers",numbers);
        res.put("data",stumanagers);
        System.out.println("总条数："+numbers);
        String stumangers_json = JSON.toJSONString(res);
        return stumangers_json;
    }

   /* @RequestMapping("/stumangerState")
    public String updateStuMangerState(@RequestParam("id") Integer  id,
                                  @RequestParam("state") Boolean state){
        int i = stumangerDao.updateState(id, state);
        System.out.println("用户编号:"+id);
        System.out.println("用户状态:"+state);
        String str = i >0?"success":"error";
        return str;
    }*/

/*    @RequestMapping("/addStuManger")
    public String addStuManger(@RequestBody StuManger stumanger){
        System.out.println(stumanger);
        stumanger.setRole("普通管理员");
        int i = stumangerDao.addStuManger(stumanger);
        String str = i >0?"success":"error";
        return str;
    }*/

/*
    @RequestMapping("/getUpdate")
    public String getUpdateStuManger(int id){
        System.out.println("编号:"+id);
        StuManger updateStuManger = stumangerDao.getUpdateStuManger(id);
        String stumangers_json = JSON.toJSONString(updateStuManger);
        return stumangers_json;
    }
*/

/*
    @RequestMapping("/editStuManger")
    public String editStuManger(@RequestBody StuManger stumanger){
        System.out.println(stumanger);
        int i = stumangerDao.editStuManger(stumanger);
        String str = i >0?"success":"error";
        return str;
    }
*/

/*    @RequestMapping("/deleteStuManger")
    public String deleteStuManger(int id){
        System.out.println(id);
        int i = stumangerDao.deleteStuManger(id);
        String str = i >0?"success":"error";
        return str;
    }*/
}
