package com.naughty.campus.controller;

import com.alibaba.fastjson.JSON;
import com.naughty.campus.bean.IndMatches;
import com.naughty.campus.bean.IndMatchesDTO;
import com.naughty.campus.bean.QueryInfo;
import com.naughty.campus.dao.IndMatchesDao;
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
public class IndMatchesConteroller {
    
    @Autowired
    IndMatchesDao indMatchesDao;

    @CrossOrigin
    @RequestMapping("/allIndMatches")
    public String getIndMatchesList(QueryInfo queryInfo){
        if(queryInfo.getStatus()==""||queryInfo.getStatus()==null){
            queryInfo.setStatus(null);
        }
        if (queryInfo.getMatchTitle() != "" || queryInfo.getMatchTitle() != null) {
            queryInfo.setMatchTitle("%" + queryInfo.getMatchTitle() + "%");
        }
        else {
            queryInfo.setMatchTitle(null);
        }
        int numbers = indMatchesDao.getIndMatchesCounts(queryInfo);// 获取数据总数
        int pageStart = (queryInfo.getPageNum()-1)*queryInfo.getPageSize();
        queryInfo.setPageNum(pageStart);
        List<IndMatches> indMatches = indMatchesDao.getAllIndMatches(queryInfo);
        HashMap<String, Object> res = new HashMap<>();
        res.put("numbers",numbers);
        res.put("data",indMatches);
        System.out.println("总条数："+numbers);
        String indMatches_json = JSON.toJSONString(res);
        return indMatches_json;
    }

    @RequestMapping("/getUpdateIndMatches")
    public ResponseResult<IndMatchesDTO> getUpdateIndMatches(int id) {
        IndMatches updateIndMatches  = indMatchesDao.selectById(id);
        IndMatchesDTO indMatchesDTO= new IndMatchesDTO();
        BeanUtils.copyProperties(updateIndMatches, indMatchesDTO);
        return new ResponseResult<IndMatchesDTO>(200, indMatchesDTO);
    }

    @RequestMapping("/updateIndMatches")
    public ResponseResult<String> updateIndMatches(@RequestBody  IndMatchesDTO indMatchesDTO) {
        IndMatches indMatches  = new IndMatches();
        BeanUtils.copyProperties(indMatchesDTO, indMatches);
        int i = indMatchesDao.updateById(indMatches);
        return new ResponseResult<String>(200, "修改成功", null);
    }


    @RequestMapping("/deleteIndMatches")
    public ResponseResult<String> deleteIndMatches(int id) {
        int i = indMatchesDao.deleteById(id);
        return new ResponseResult<String>(200, "删除成功", null);
    }

}
