package com.naughty.campus.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Discussions {

    private String discussion;
    private String studentId;
    private String studentName;
    private String publishTime;
    private String content;

}
