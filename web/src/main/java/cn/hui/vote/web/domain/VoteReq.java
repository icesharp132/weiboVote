package cn.hui.vote.web.domain;

import lombok.Data;

/**
 * @author luzhaohui@didiglobal.com
 * @description 进行投票
 * @date 2020/8/5 02:10
 */
@Data
public class VoteReq extends BaseRequest {
    /**
     * form_id
     */
    private Long    formId;

    private Integer lineNum;

}
