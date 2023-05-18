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
import org.springframework.web.bind.annotation.RequestBody;
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
    @RequestMapping("/getUpdateStuManager")
    public ResponseResult<UsersDTO> getUpdateUser(String id) {
        Users updateUser  = stumanagerDao.selectById(id);
        UsersDTO usersDTO = new UsersDTO();
        BeanUtils.copyProperties(updateUser, usersDTO);
        if(updateUser.getIsAdmin()==1){
            usersDTO.setIsAdmin("是");
        }else {
            usersDTO.setIsAdmin("否");
        }
        return new ResponseResult<UsersDTO>(200, usersDTO);
    }


    @RequestMapping("/updateStuManager")
    public ResponseResult<String> updateUser(@RequestBody UsersDTO usersDTO) {
        Users users  = new Users();
        BeanUtils.copyProperties(usersDTO, users);
        int i = stumanagerDao.updateById(users);
        return new ResponseResult<String>(200, "修改成功", null);
    }
    @RequestMapping("/deleteStuManager")
    public ResponseResult<String> deleteStuManager(String id) {
        int i = stumanagerDao.deleteById(id);
        return new ResponseResult<String>(200, "删除成功", null);
    }

}
