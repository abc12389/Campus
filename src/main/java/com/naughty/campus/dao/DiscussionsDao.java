package com.naughty.campus.dao;

import com.naughty.campus.bean.QueryInfo;
import com.naughty.campus.bean.Discussions;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscussionsDao {

   // public int getDiscussionByMassage(@Param("discussionname") String discussionname, @Param("password") String password);
    public List<Discussions> getAllDiscussions(@Param("info") QueryInfo info);
    public int getDiscussionCounts(@Param("info") QueryInfo info);

    int getDiscussionsCounts(QueryInfo queryInfo);
    // public int updateState(Integer id, Boolean state);
    //public int addDiscussion(Discussion discussion);
   // public Discussion getUpdateDiscussion(int id);
   // public int editDiscussion(Discussion discussion);
    //public int deleteDiscussion(int id);
}
