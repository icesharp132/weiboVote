package cn.hui.vote.web.domain;

import lombok.Data;

/**
 * @author: Lu Zhaohui
 * @description:
 * @date: Created on 2018/6/29
 * @modified By:
 */
@Data
public class ResponseBean<T> {

    private int    code = -1;
    private String msg;
    private T      data;

}
