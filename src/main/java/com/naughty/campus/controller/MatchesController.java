package com.naughty.campus.controller;

import com.alibaba.fastjson.JSON;
import com.naughty.campus.bean.MatchesDTO;
import com.naughty.campus.bean.QueryInfo;
import com.naughty.campus.bean.Matches;
import com.naughty.campus.dao.MatchesDao;
import com.naughty.campus.util.ResponseResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@RestController
public class MatchesController {

    @Autowired
    MatchesDao matchesDao;

    @CrossOrigin
    @RequestMapping("/allMatches")
    public String getMatchesList(QueryInfo queryInfo) {
        if (queryInfo.getMatchStatus() == "" || queryInfo.getMatchStatus() == null) {
            queryInfo.setMatchStatus(null);
        }
        if (queryInfo.getMatchTitle() != "" || queryInfo.getMatchTitle() != null) {
            queryInfo.setMatchTitle("%" + queryInfo.getMatchTitle() + "%");
        }
       else {
            queryInfo.setMatchTitle(null);
        }

        int numbers = matchesDao.getMatchesCounts(queryInfo);// 获取数据总数
        int pageStart = (queryInfo.getPageNum() - 1) * queryInfo.getPageSize();
        queryInfo.setPageNum(pageStart);
        List<Matches> matches = matchesDao.getAllMatches(queryInfo);
        HashMap<String, Object> res = new HashMap<>();
        res.put("numbers", numbers);
        res.put("data", matches);
        System.out.println("总条数：" + numbers);
        String matches_json = JSON.toJSONString(res);
        return matches_json;
    }

    @RequestMapping("/addMatches")
    public ResponseResult<String> addMatches(@RequestBody MatchesDTO matchesDto) {
        Matches matches = new Matches();
        BeanUtils.copyProperties(gradeHandler(matchesDto), matches);
        matches.setMatchStatus("报名中");
        int insert = matchesDao.insert(matches);
        if (insert <= 0) {
            return new ResponseResult<String>(500, "添加失败", null);
        }
        return new ResponseResult<String>(200, "添加成功", null);
    }

    public MatchesDTO gradeHandler(MatchesDTO matchesDTO) {
        List<String> checkList = matchesDTO.getCheckList();
        for (String s : checkList) {
            if (s.equals("大一")) {
                matchesDTO.setAllowFirst(1);
            }
            if (s.equals("大二")) {
                matchesDTO.setAllowSecond(1);
            }
            if (s.equals("大三")) {
                matchesDTO.setAllowThird(1);
            }
            if (s.equals("大四")) {
                matchesDTO.setAllowFourth(1);
            }
        }
        return matchesDTO;

    }

    @RequestMapping("/getUpdate")
    public ResponseResult<MatchesDTO> getUpdateMatches(int id) {
        Matches updateMatches = matchesDao.selectById(id);
        MatchesDTO matchesDTO = new MatchesDTO();
        BeanUtils.copyProperties(updateMatches, matchesDTO);
        ArrayList<String> list = new ArrayList<>();
        if (updateMatches.getAllowFirst() == 1) {
            list.add("大一");
        }
        if (updateMatches.getAllowSecond() == 1) {
            list.add("大二");
        }
        if (updateMatches.getAllowThird() == 1) {
            list.add("大三");
        }
        if (updateMatches.getAllowFourth() == 1) {
            list.add("大四");
        }
        matchesDTO.setCheckList(list);
        return new ResponseResult<MatchesDTO>(200, matchesDTO);
    }


    @RequestMapping("/updateMatches")
    public ResponseResult<String> editMatches(@RequestBody MatchesDTO matchesDTO) {
        Matches matches = new Matches();
        BeanUtils.copyProperties(gradeHandler(matchesDTO), matches);
        int i = matchesDao.updateById(matches);
        return new ResponseResult<String>(200, "修改成功", null);
    }


    @RequestMapping("/deleteMatch")
    public ResponseResult<String> deleteMatches(int id) {
        int i = matchesDao.deleteById(id);
        return new ResponseResult<String>(200, "删除成功", null);
    }
}
