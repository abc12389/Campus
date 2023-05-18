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
@TableName("feedbacks")
public class SysManager {

    @TableId(value = "feedbackId",type = IdType.AUTO)
    private String feedbackId;
    @TableField("studentId")
    private String studentId;
    @TableField("matchId")
    private String matchId;
    @TableField("content")
    private String content;
    @TableField("reply")
    private String reply;
}
