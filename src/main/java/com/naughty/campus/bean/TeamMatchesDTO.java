package com.naughty.campus.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeamMatchesDTO {

    private String matchId;
    private String matchTitle;
    private String teamId;
    private String teamScore;
    private String teamName;
    private String status;
}
