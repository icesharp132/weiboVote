package cn.hui.vote.web.domain;

import lombok.Data;

/**
 * @author luzhaohui@didiglobal.com
 * @date 2020/8/5 02:10
 */
@Data
public class AddContentReq extends BaseRequest {
    /**
     * form_id
     */
    private Long formId;

    /**
     * line_text
     */
    private String lineText;

    /**
     * line_pic
     */
    private String linePic;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AddContentReq{");
        sb.append("formId=").append(formId);
        sb.append(", lineText='").append(lineText).append('\'');
        sb.append(", token='").append(token).append('\'');
        sb.append(", uid=").append(uid);
        sb.append(", timestamp=").append(timestamp);
        sb.append('}');
        return sb.toString();
    }
}
