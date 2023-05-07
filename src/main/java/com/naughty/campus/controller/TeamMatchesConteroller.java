package com.naughty.campus.controller;

import com.alibaba.fastjson.JSON;
import com.naughty.campus.bean.TeamMatches;
import com.naughty.campus.bean.TeamMatchesDTO;
import com.naughty.campus.bean.QueryInfo;
import com.naughty.campus.dao.TeamMatchesDao;
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
public class TeamMatchesConteroller {
    
    @Autowired
    TeamMatchesDao teamMatchesDao;

    @CrossOrigin
    @RequestMapping("/allTeamMatches")
    public String getTeamMatchesList(QueryInfo queryInfo){
        if(queryInfo.getStatus()==""||queryInfo.getStatus()==null){
            queryInfo.setStatus(null);
        }
        if (queryInfo.getMatchTitle() != "" || queryInfo.getMatchTitle() != null) {
            queryInfo.setMatchTitle("%" + queryInfo.getMatchTitle() + "%");
        }
        else {
            queryInfo.setMatchTitle(null);
        }
        int numbers = teamMatchesDao.getTeamMatchesCounts(queryInfo);// 获取数据总数
        int pageStart = (queryInfo.getPageNum()-1)*queryInfo.getPageSize();
        queryInfo.setPageNum(pageStart);
        List<TeamMatches> teamMatches = teamMatchesDao.getAllTeamMatches(queryInfo);
        HashMap<String, Object> res = new HashMap<>();
        res.put("numbers",numbers);
        res.put("data",teamMatches);
        System.out.println("总条数："+numbers);
        String teamMatches_json = JSON.toJSONString(res);
        return teamMatches_json;
    }

    @RequestMapping("/getUpdateTeamMatches")
    public ResponseResult<TeamMatchesDTO> getUpdateTeamMatches(int id) {
        TeamMatches updateTeamMatches  = teamMatchesDao.selectById(id);
        TeamMatchesDTO teamMatchesDTO= new TeamMatchesDTO();
        BeanUtils.copyProperties(updateTeamMatches, teamMatchesDTO);
        return new ResponseResult<TeamMatchesDTO>(200, teamMatchesDTO);
    }

    @RequestMapping("/updateTeamMatches")
    public ResponseResult<String> updateTeamMatches(@RequestBody  TeamMatchesDTO teamMatchesDTO) {
        TeamMatches teamMatches  = new TeamMatches();
        BeanUtils.copyProperties(teamMatchesDTO, teamMatches);
        int i = teamMatchesDao.updateById(teamMatches);
        return new ResponseResult<String>(200, "修改成功", null);
    }


    @RequestMapping("/deleteTeamMatches")
    public ResponseResult<String> deleteTeamMatches(int id) {
        int i = teamMatchesDao.deleteById(id);
        return new ResponseResult<String>(200, "删除成功", null);
    }

}
