package cn.hui.vote.web.domain;

import lombok.Data;

/**
 * @author luzhaohui@didiglobal.com
 * @date 2020/8/5 02:10
 */
@Data
public class VoteDetailReq extends BaseRequest {
    /**
     * form_id
     */
    private Long formId;

}
