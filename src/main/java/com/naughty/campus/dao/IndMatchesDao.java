package com.naughty.campus.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.naughty.campus.bean.QueryInfo;
import com.naughty.campus.bean.IndMatches;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndMatchesDao extends BaseMapper<IndMatches> {

    public List<IndMatches> getAllIndMatches(@Param("info") QueryInfo info);
    public int getIndMatchesCounts(@Param("info") QueryInfo info);


}
