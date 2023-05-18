package com.naughty.campus.controller;

import com.alibaba.fastjson.JSON;
import com.naughty.campus.AdminMapper;
import com.naughty.campus.bean.*;
import com.naughty.campus.dao.AdminDao;
import com.naughty.campus.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class LoginController{
    @Autowired
    AdminDao adminDao;

    @RequestMapping("/login")
    public ResponseResult<Integer> adminLogin(@RequestBody Admin admin) {
        Admin adminData = adminDao.getAdmin(admin.getName(), admin.getPwd());
//        int count = adminDao.getAdminByMassage(admin.getName(), admin.getPwd());
        if (adminData != null) {
            return new ResponseResult<Integer>(200,"返回成功",adminData.getId());
        }else{
            return new ResponseResult(404,"当前用户名找不到");
        }
    }
    @RequestMapping("/user")
    public ResponseResult<String> getUserName(String id){
        Admin admin = adminDao.selectById(id);
        if(admin==null){
            return new ResponseResult(404,"当前用户名找不到");
        }else {
            return new ResponseResult<String>(200,"用户名获取成功",admin.getName());
        }
    }

}
