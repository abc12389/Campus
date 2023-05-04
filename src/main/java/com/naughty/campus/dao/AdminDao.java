package com.naughty.campus.dao;

import com.naughty.campus.bean.QueryInfo;
import com.naughty.campus.bean.Admin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminDao {

    public int getAdminByMassage(@Param("name") String username, @Param("pwd") String password);
    /*public List<Admins> getAllAdmin(@Param("info") QueryInfo info);*/
/*    public int getAdminCounts(@Param("info") QueryInfo info);*/
    public int addAdmin(Admin admin);
    public Admin getUpdateAdmin(int id);
    /*public int editAdmin(Admin user);*/
    public int deleteAdmin(int id);
}
