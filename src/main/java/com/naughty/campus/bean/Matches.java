package com.naughty.campus.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Matches {


    private long matchId;
    private String matchTitle;
    private String matchInfo;
    private String enrollStartTime;
    private String enrollEndTime;
    private String matchStartTime;
    private String matchEndTime;
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
