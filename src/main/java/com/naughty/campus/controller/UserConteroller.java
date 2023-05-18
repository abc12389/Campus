package com.naughty.campus.controller;

import com.alibaba.fastjson.JSON;
import com.naughty.campus.bean.Users;
import com.naughty.campus.bean.UsersDTO;
import com.naughty.campus.dao.UserDao;
import com.naughty.campus.util.ResponseResult;
import org.springframework.beans.BeanUtils;
import com.naughty.campus.bean.QueryInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;


@RestController
public class UserConteroller {
    
    @Autowired
    UserDao userDao;

    @CrossOrigin
    @RequestMapping("/allUser")
    public String getUserList(QueryInfo queryInfo){
        if(queryInfo.getDepartment()==""||queryInfo.getDepartment()==null){
            queryInfo.setDepartment(null);
        }
        if(queryInfo.getStudentName()!=""||queryInfo.getStudentName()!=null){
            queryInfo.setStudentName("%"+queryInfo.getStudentName()+"%");
        }else {
            queryInfo.setStudentName(null);
        }

        int numbers = userDao.getUserCounts(queryInfo);// 获取数据总数
        int pageStart = (queryInfo.getPageNum()-1)*queryInfo.getPageSize();
        queryInfo.setPageNum(pageStart);
        List<Users> users = userDao.getAllUser(queryInfo);
        HashMap<String, Object> res = new HashMap<>();
        res.put("numbers",numbers);
        res.put("data",users);
        System.out.println("总条数："+numbers);
        String users_json = JSON.toJSONString(res);
        return users_json;
    }

    @RequestMapping("/addUser")
    public ResponseResult<String> addUser(@RequestBody UsersDTO usersDTO) {
        Users users = new Users();
        BeanUtils.copyProperties(usersDTO, users);
        String isAdmin = usersDTO.getIsAdmin();
        if(isAdmin.equals("否")){
            users.setIsAdmin(0);
        }else {
            users.setIsAdmin(1);
        }
        int insert = userDao.insert(users);
        if (insert <= 0) {
            return new ResponseResult<String>(500, "添加失败", null);
        }
        return new ResponseResult<String>(200, "添加成功", null);
    }

    @RequestMapping("/getUpdateUser")
    public ResponseResult<UsersDTO> getUpdateUser(String id) {
        Users updateUser  = userDao.selectById(id);
        UsersDTO usersDTO = new UsersDTO();
        BeanUtils.copyProperties(updateUser, usersDTO);
        if(updateUser.getIsAdmin()==1){
            usersDTO.setIsAdmin("是");
        }else {
            usersDTO.setIsAdmin("否");
        }
        return new ResponseResult<UsersDTO>(200, usersDTO);
    }


    @RequestMapping("/updateUser")
    public ResponseResult<String> updateUser(@RequestBody  UsersDTO usersDTO) {
        Users users  = new Users();
        BeanUtils.copyProperties(usersDTO, users);
        int i = userDao.updateById(users);
        return new ResponseResult<String>(200, "修改成功", null);
    }


    @RequestMapping("/deleteUser")
    public ResponseResult<String> deleteUser(String id) {
        int i = userDao.deleteById(id);
        return new ResponseResult<String>(200, "删除成功", null);
    }




}
