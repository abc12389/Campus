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
@TableName("matches")
public class Matches {
    @TableId(value = "matchId",type = IdType.AUTO)
    private long matchId;
    @TableField("matchTitle")
    private String matchTitle;
    @TableField("matchInfo")
    private String matchInfo;
    @TableField("enrollStartTime")
    private java.sql.Date enrollStartTime;
    @TableField("enrollEndTime")
    private java.sql.Date enrollEndTime;
    @TableField("matchStartTime")
    private java.sql.Date  matchStartTime;
    @TableField("matchEndTime")
    private java.sql.Date  matchEndTime;
    @TableField("matchStatus")
    private String matchStatus;
    @TableField("sponsor")
    private String sponsor;
    @TableField("maxNum")
    private long maxNum;
    @TableField("allowFirst")
    private int allowFirst;
    @TableField("allowSecond")
    private int allowSecond;
    @TableField("allowThird")
    private int allowThird;
    @TableField("allowFourth")
    private int allowFourth;
    @TableField("teamPersonNum")
    private int teamPersonNum;
    @TableField("matchType")
    private String matchType;
    @TableField("prizeFirst")
    private String prizeFirst;
    @TableField("prizeSecond")
    private String prizeSecond;
    @TableField("prizeThird")
    private String prizeThird;
    @TableField("matchSex")
    private String matchSex;
    @TableField("matchEntry")
    private String matchEntry;
    @TableField("contactPerson")
    private String contactPerson;
}
