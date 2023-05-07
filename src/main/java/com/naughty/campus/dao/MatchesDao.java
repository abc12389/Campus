package com.naughty.campus.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.naughty.campus.bean.QueryInfo;
import com.naughty.campus.bean.Matches;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchesDao extends BaseMapper<Matches> {

    public List<Matches> getAllMatches(@Param("info") QueryInfo info);
    public int getMatchesCounts(@Param("info") QueryInfo info);
}
