package com.naughty.campus.controller;

import com.alibaba.fastjson.JSON;
import com.naughty.campus.bean.SysManager;
import com.naughty.campus.bean.SysManagerDTO;
import com.naughty.campus.bean.QueryInfo;
import com.naughty.campus.dao.SysManagerDao;
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
public class SysManagerConteroller {

    @Autowired
    SysManagerDao sysManagerDao;

    @CrossOrigin
    @RequestMapping("/allSysManager")
    public String getSysManagerList(QueryInfo queryInfo) {
        if (queryInfo.getStudentId() != "" && queryInfo.getStudentId() != null) {
            queryInfo.setStudentId("%" + queryInfo.getStudentId() + "%");
        }
        queryInfo.setStudentName(null);
        if (queryInfo.getContent() != "" && queryInfo.getContent() != null) {
            queryInfo.setContent("%" + queryInfo.getContent() + "%");
        }
        queryInfo.setContent(null);
        int numbers = sysManagerDao.getSysManagerCounts(queryInfo);// 获取数据总数
        int pageStart = (queryInfo.getPageNum() - 1) * queryInfo.getPageSize();
        queryInfo.setPageNum(pageStart);
        List<SysManager> sysManager = sysManagerDao.getAllSysManager(queryInfo);
        HashMap<String, Object> res = new HashMap<>();
        res.put("numbers", numbers);
        res.put("data", sysManager);
        System.out.println("总条数：" + numbers);
        String sysManager_json = JSON.toJSONString(res);
        return sysManager_json;
    }

    @RequestMapping("/addSysManager")
    public ResponseResult<String> addDis(@RequestBody SysManagerDTO sysManagerDTO) {
        SysManager sysManager = new SysManager();
        BeanUtils.copyProperties(sysManagerDTO, sysManager);
        int insert = sysManagerDao.insert(sysManager);
        if (insert <= 0) {
            return new ResponseResult<String>(500, "添加失败", null);
        }
        return new ResponseResult<String>(200, "添加成功", null);
    }

    @RequestMapping("/getUpdateSysManager")
    public ResponseResult<SysManagerDTO> getUpdateSysManager(int id) {
        SysManager updateDis = sysManagerDao.selectById(id);
        SysManagerDTO sysManagerDTO = new SysManagerDTO();
        BeanUtils.copyProperties(updateDis, sysManagerDTO);
        return new ResponseResult<SysManagerDTO>(200, sysManagerDTO);
    }


    @RequestMapping("/updateSysManager")
    public ResponseResult<String> updateDis(@RequestBody SysManagerDTO sysManagerDTO) {
        SysManager sysManager = new SysManager();
        BeanUtils.copyProperties(sysManagerDTO, sysManager);
        int i = sysManagerDao.updateById(sysManager);
        return new ResponseResult<String>(200, "修改成功", null);
    }


    @RequestMapping("/deleteSysManager")
    public ResponseResult<String> deleteSysManager(int id) {
        int i = sysManagerDao.deleteById(id);
        return new ResponseResult<String>(200, "删除成功", null);
    }
}
