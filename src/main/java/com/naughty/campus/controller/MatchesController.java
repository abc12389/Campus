package com.naughty.campus.controller;

import com.alibaba.fastjson.JSON;
import com.naughty.campus.bean.QueryInfo;
import com.naughty.campus.bean.Matches;
import com.naughty.campus.dao.MatchesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;


@RestController
public class MatchesController {
    
    @Autowired
    MatchesDao matchesDao;

    @CrossOrigin
    @RequestMapping("/allMatches")
    public String getMatchesList(QueryInfo queryInfo){
        if(queryInfo.getMatchStatus()==""||queryInfo.getMatchStatus()==null){
            queryInfo.setMatchStatus(null);
        }
        if(queryInfo.getMatchTitle()!=""||queryInfo.getMatchTitle()!=null){
            queryInfo.setMatchTitle("%"+queryInfo.getMatchTitle()+"%");
        }else {
            queryInfo.setMatchTitle(null);
        }
        int numbers = matchesDao.getMatchesCounts(queryInfo);// 获取数据总数
        int pageStart = (queryInfo.getPageNum()-1)*queryInfo.getPageSize();
        queryInfo.setPageNum(pageStart);
        List<Matches> matches = matchesDao.getAllMatches(queryInfo);
        HashMap<String, Object> res = new HashMap<>();
        res.put("numbers",numbers);
        res.put("data",matches);
        System.out.println("总条数："+numbers);
        String matches_json = JSON.toJSONString(res);
        return matches_json;
    }

   /* @RequestMapping("/matchesState")
    public String updatematchesState(@RequestParam("id") Integer  id,
                                  @RequestParam("state") Boolean state){
        int i = matchesDao.updateState(id, state);
        System.out.println("用户编号:"+id);
        System.out.println("用户状态:"+state);
        String str = i >0?"success":"error";
        return str;
    }*/

/*    @RequestMapping("/addmatches")
    public String addMatches(@RequestBody Matches matches){
        System.out.println(matches);
        matches.setRole("普通管理员");
        int i = matchesDao.addMatches(matches);
        String str = i >0?"success":"error";
        return str;
    }*/

/*
    @RequestMapping("/getUpdate")
    public String getUpdateMatches(int id){
        System.out.println("编号:"+id);
        Matches updateMatches = matchesDao.getUpdateMatches(id);
        String matchess_json = JSON.toJSONString(updateMatches);
        return matchess_json;
    }
*/

/*
    @RequestMapping("/editMatches")
    public String editMatches(@RequestBody Matches matches){
        System.out.println(matches);
        int i = matchesDao.editMatches(matches);
        String str = i >0?"success":"error";
        return str;
    }
*/

/*    @RequestMapping("/deleteMatches")
    public String deleteMatches(int id){
        System.out.println(id);
        int i = matchesDao.deleteMatches(id);
        String str = i >0?"success":"error";
        return str;
    }*/
}
