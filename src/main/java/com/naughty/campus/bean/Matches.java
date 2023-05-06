package com.naughty.campus.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;
import java.sql.Timestamp;



@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Matches {


    private long matchId;
    private String matchTitle;
    private String matchInfo;
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date enrollStartTime;
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date enrollEndTime;
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date matchStartTime;
    @JsonFormat(pattern="yyyy-MM-dd ",timezone="GMT+8")
    private Date matchEndTime;
    private String matchStatus;
    private String sponsor;
    private long maxNum;
    private long allowFirst;
    private long allowSecond;
    private long allowThird;
    private long allowFourth;
    private long teamPersonNum;
    private String matchType;
    private String prizeFirst;
    private String prizeSecond;
    private String prizeThird;
    private String matchSex;
    private String matchEntry;
    private String contactPerson;
}
