package com.naughty.campus.bean;

import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("discussions")
public class Discussions {
    @TableId(value = "discussionId",type = IdType.AUTO)
    private String discussionId;
    @TableField("studentId")
    private String studentId;
    @TableField("studentName")
    private String studentName;
    @TableField("publishTime")
    private java.sql.Date publishTime;
    @TableField("disContent")
    private String disContent;
    @TableField("responder")
    private String responder;
    @TableField("reply")
    private String reply;
}
