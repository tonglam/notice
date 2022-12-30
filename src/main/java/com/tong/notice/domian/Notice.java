package com.tong.notice.domian;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * Create by tong on 2022/12/30
 */
@Data
@Accessors(chain = true)
public class Notice {

    private String id;
    private String name;
    private String img;
    private String href;
    private String price;
    private String updateTime;

}
