package com.naughty.campus.controller;

import com.naughty.campus.bean.Admin;
import com.naughty.campus.dao.AdminDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    AdminDao adminDao;

    @CrossOrigin
    @RequestMapping("/login")
    public String adminLogin(@RequestBody Admin admin) {
        System.out.println("Admin : " + admin);
        String str = "error";
        int count = adminDao.getAdminByMassage(admin.getName(), admin.getPwd());
        if (count > 0) {
            str = "ok";
        }
        return str;
    }
}
