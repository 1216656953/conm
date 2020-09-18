package com.scmc.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class JsonData {
    private Boolean success;
    private String url;
    private String token;
    private String msg;
    public JsonData(Boolean success,String url,String token){
        this.success=success;
        this.url=url;
        this.token=token;
    }
}
