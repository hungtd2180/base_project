package com.base.base_project.entities.event;


import com.base.base_project.constants.Constant;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by hungtd
 * Date: 10/10/2024
 * Time: 2:28 CH
 * for all issues, contact me: hungtd2180@gmail.com
 */

public class Event implements Serializable {
    public String id;
    public String method;
    public Integer errorCode;
    public String token;
    public String payload;

    public Event(){
        this.id = UUID.randomUUID().toString();
        this.errorCode = Constant.ResultStatus.SUCCESS;
    }
}
