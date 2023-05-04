package com.naughty.campus.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QueryInfo {
    private int pageNum=1;
    private int pageSize=1;
    private String id;
    private String department;//学院
    private String matchStatus;//赛事状态
    private String matchTitle;//赛事名称

}
