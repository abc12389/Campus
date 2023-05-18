package com.naughty.campus.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DiscussionsDTO {

    private int discussionId;
    private String studentId;
    private String studentName;
    private java.sql.Date publishTime;
    private String disContent;
}
