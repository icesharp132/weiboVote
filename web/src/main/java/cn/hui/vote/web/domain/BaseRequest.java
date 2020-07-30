package cn.hui.vote.web.domain;

import java.io.Serializable;
import java.util.Arrays;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import lombok.Data;

/**
 * @author: Lu Zhaohui
 * @description:
 * @date: Created on 2018/6/28
 * @modified By:
 */
@Data
public  class BaseRequest implements Serializable {

    private static final long serialVersionUID = -5404292701729828048L;
    
    protected String            token;

    protected long              timestamp;

}
