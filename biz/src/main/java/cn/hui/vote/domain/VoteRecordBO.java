package cn.hui.vote.domain;

import lombok.Data;

import java.util.Date;

/**
 * VoteRecordDO 
 *
 * @author Auto generated by luzhaohui
 * @since 2020-03-16
 * @version 1.0
 */
@Data
public class VoteRecordBO {

    private Long    id;
    private Date gmtCreate;
    private Date    gmtModify;

    /**
    * user_id
    */
    private Long userId;

    /**
    * form_id
    */
    private Long formId;

    /**
    * vote
    */
    private String vote;

}
