package com.naughty.campus.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class IndMatchesDTO {

    private int id;
    private int matchId;
    private String matchTitle;
    private String studentId;
    private String studentName;
    private String studentScore;
    private String studentPlace;
    private String status;
}
