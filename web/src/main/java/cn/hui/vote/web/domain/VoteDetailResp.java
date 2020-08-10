package cn.hui.vote.web.domain;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author luzhaohui@didiglobal.com
 * @date 2020/8/5 02:10
 */
@Data
public class VoteDetailResp {

    private Long        id;
    private Date        gmtCreate;
    private Date        gmtModify;
    /**
     * vote_name
     */
    private String      voteName;

    /**
     * creator_weibo_id
     */
    private Long        creatorWeiboId;

    private Integer     multiVote;

    List<VoteContentVO> contentList;

    @Data
    public static class VoteContentVO {

        private Long id;
        /**
         * line_text
         */
        private String  lineText;

        /**
         * line_pic
         */
        private String  linePic;

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("VoteContentVO{");
            sb.append("id=").append(id);
            sb.append(", lineText='").append(lineText).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }
}
