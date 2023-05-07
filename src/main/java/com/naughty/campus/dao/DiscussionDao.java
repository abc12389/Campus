package com.naughty.campus.dao;

import com.naughty.campus.bean.Discussions;
import com.naughty.campus.bean.QueryInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscussionDao {

    public List<Discussions> getAllDiscussion(@Param("info") QueryInfo info);
    public int getDiscussionsCounts(@Param("info") QueryInfo info);

}
