package com.naughty.campus.dao;

import com.naughty.campus.bean.QueryInfo;
import com.naughty.campus.bean.User;
import com.naughty.campus.bean.Users;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {

    public int getUserByMassage(@Param("username") String username, @Param("password") String password);
    public List<Users> getAllUser(@Param("info") QueryInfo info);
    public int getUserCounts(@Param("info") QueryInfo info);
    public int updateState(Integer id, Boolean state);
    public int addUser(User user);
    public User getUpdateUser(int id);
    public int editUser(User user);
    public int deleteUser(int id);
}
