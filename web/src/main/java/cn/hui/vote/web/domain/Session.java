package cn.hui.vote.web.domain;

import lombok.Data;

/**
 * @author luzhaohui@didiglobal.com
 * @date 2020/8/5 02:16
 */
@Data
public class Session {
    private String accessToken;
    private long uid;
    private String userName;

    private long createTime = System.currentTimeMillis();
}
