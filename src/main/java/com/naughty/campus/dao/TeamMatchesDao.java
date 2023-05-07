package com.naughty.campus.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.naughty.campus.bean.TeamMatches;
import com.naughty.campus.bean.QueryInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamMatchesDao extends BaseMapper<TeamMatches> {

    public List<TeamMatches> getAllTeamMatches(@Param("info") QueryInfo info);
    public int getTeamMatchesCounts(@Param("info") QueryInfo info);


}
