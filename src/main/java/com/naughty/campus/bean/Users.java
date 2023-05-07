package com.naughty.campus.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("users")
public class Users {
    @TableId("userId")
    @TableField(value = "userId")
    private String userId;
     @TableField("userPwd")
    private String userPwd;
    @TableField("studentName")
    private String studentName;
    @TableField("studentPhoneNumber")
    private String studentPhoneNumber;
    @TableField("studentDepartment")
    private String studentDepartment;
    @TableField("studentGrade")
    private String studentGrade;
    @TableField("studentClass")
    private String studentClass;
    @TableField("studentSex")
    private String studentSex;
    @TableField("teamId")
    private int teamId;
    @TableField("isAdmin")
    private int isAdmin;
}
