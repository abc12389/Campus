package com.naughty.campus.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.naughty.campus.bean.QueryInfo;
import com.naughty.campus.bean.Users;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StuManagerDao extends BaseMapper<Users> {

    public List<Users> getAllStuManager(@Param("info") QueryInfo info);
    public int getStuManagerCounts(@Param("info") QueryInfo info);

}
