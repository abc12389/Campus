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
@TableName("teammatches")
public class TeamMatches {
    @TableId("matchId")
    @TableField(value = "matchId")
    private int matchId;
     @TableField("matchTitle")
    private String matchTitle;
    @TableField("teamId")
    private String teamId;
    @TableField("teamName")
    private String teamName;
    @TableField("teamScore")
    private String teamScore;
    @TableField("status")
    private String status;
}
