package com.naughty.campus.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeamMatchesDTO {

    private int id;
    private int matchId;
    private String matchTitle;
    private String teamId;
    private String teamScore;
    private String teamName;
    private String status;
}
