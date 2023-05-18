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
@TableName("individualmatches")
public class IndMatches {

    @TableId(value = "id",type = IdType.AUTO)
    private int id;
    @TableField(value = "matchId")
    private int matchId;
     @TableField("matchTitle")
    private String matchTitle;
    @TableField("studentId")
    private String studentId;
    @TableField("studentName")
    private String studentName;
    @TableField("studentScore")
    private String studentScore;
    @TableField("studentPlace")
    private String studentPlace;
    @TableField("status")
    private String status;
}
