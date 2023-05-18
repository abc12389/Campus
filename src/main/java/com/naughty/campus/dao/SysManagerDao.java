package com.naughty.campus.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.naughty.campus.bean.Discussions;
import com.naughty.campus.bean.QueryInfo;
import com.naughty.campus.bean.SysManager;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysManagerDao extends BaseMapper<SysManager> {

    public List<SysManager> getAllSysManager(@Param("info") QueryInfo info);
    public int getSysManagerCounts(@Param("info") QueryInfo info);

}
