package com.naughty.campus.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QueryInfo {
    private int pageNum=1;
    private int pageSize=1;
    private String id;
    private String department;//学院
    private String matchStatus;//赛事状态
    private String matchTitle;//赛事名称
    private String disContent;//发表内容
    private String Content;//发表内容
    private String studentName;//学生姓名
    private String studentId;//学生学号
    private int IsAdmin;
    private String status;//审核状态
}
