package com.naughty.campus.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.naughty.campus.bean.Matches;
import com.naughty.campus.bean.QueryInfo;
import com.naughty.campus.bean.Users;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends BaseMapper<Users> {

    public List<Users> getAllUser(@Param("info") QueryInfo info);
    public int getUserCounts(@Param("info") QueryInfo info);


}
