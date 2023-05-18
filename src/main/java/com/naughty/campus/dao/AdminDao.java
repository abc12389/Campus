package com.naughty.campus.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.naughty.campus.bean.Discussions;
import com.naughty.campus.bean.QueryInfo;
import com.naughty.campus.bean.Admin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminDao extends BaseMapper<Admin> {

    public int getAdminByMassage(@Param("name") String username, @Param("pwd") String password);
    public Admin getAdmin(@Param("name") String username,@Param("pwd") String password);
    public int addAdmin(Admin admin);
    public Admin getUpdateAdmin(int id);
    public int deleteAdmin(int id);
}
