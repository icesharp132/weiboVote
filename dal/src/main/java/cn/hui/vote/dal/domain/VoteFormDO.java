package cn.hui.vote.dal.domain;

import lombok.Data;

/**
 * VoteFormDO 
 *
 * @author Auto generated by luzhaohui
 * @since 2020-03-16
 * @version 1.0
 */
@Data
public class VoteFormDO extends BaseBean {

    private static final long serialVersionUID = -1L;


    /**
    * vote_name
    */
    private String voteName;

    /**
    * creator_weibo_id
    */
    private Long creatorWeiboId;

    private int multiVote;

}
