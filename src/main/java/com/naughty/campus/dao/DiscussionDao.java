package com.naughty.campus.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.naughty.campus.bean.Discussions;
import com.naughty.campus.bean.Matches;
import com.naughty.campus.bean.QueryInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscussionDao  extends BaseMapper<Discussions> {

    public List<Discussions> getAllDiscussions(@Param("info") QueryInfo info);
    public int getDiscussionsCounts(@Param("info") QueryInfo info);

}
