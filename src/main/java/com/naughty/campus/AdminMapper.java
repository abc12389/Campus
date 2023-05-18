package com.naughty.campus;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.naughty.campus.bean.Admin;

public interface AdminMapper extends BaseMapper<Admin> {
    int updatePassword(String username, String password);
}
