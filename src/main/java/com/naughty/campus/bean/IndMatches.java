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
@TableName("individualmatches")
public class IndMatches {
    @TableId("matchId")
    @TableField(value = "matchId")
    private String matchId;
     @TableField("matchTitle")
    private String matchTitle;
    @TableField("studentId")
    private String studentId;
    @TableField("studentScore")
    private String studentScore;
    @TableField("status")
    private String status;
}
