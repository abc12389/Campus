package com.naughty.campus.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Users {

    private String userId;
    private String userImg;
    private String userPwd;
    private String studentName;
    private String studentPhoneNumber;
    private String studentDepartment;
    private String studentGrade;
    private String studentClass;
    private String studentSex;
    private long teamId;
    private long isAdmin;
}
