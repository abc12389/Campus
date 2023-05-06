package com.naughty.campus.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MatchesDTO {
    private String matchTitle;
    private String matchInfo;
    private Date enrollStartTime;
    private Date enrollEndTime;
    private Date matchStartTime;
    private Date matchEndTime;
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
    private List<String> checkList;
    private String competitionScale;
}
