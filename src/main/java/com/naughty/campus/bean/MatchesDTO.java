package com.naughty.campus.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MatchesDTO {
    private long matchId;
    private String matchTitle;
    private String matchInfo;
    private java.sql.Date enrollStartTime;
    private java.sql.Date enrollEndTime;
    private java.sql.Date matchStartTime;
    private java.sql.Date matchEndTime;
    private String sponsor;
    private long maxNum;
    private String textarea;//赛事描述
    private int allowFirst;
    private int allowSecond;
    private int allowThird;
    private int allowFourth;
    private long teamPersonNum;
    private String matchType;
    private String prizeFirst;
    private String prizeSecond;
    private String prizeThird;
    private String matchSex;
    private String matchEntry;
    private String contactPerson;
    private List<String> checkList;
    private String competitionScale;
}
