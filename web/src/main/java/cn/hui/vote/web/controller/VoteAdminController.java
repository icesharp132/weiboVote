package cn.hui.vote.web.controller;

import cn.hui.vote.VoteBizService;
import cn.hui.vote.VoteRecordBizService;
import cn.hui.vote.common.utils.BeanUtil;
import cn.hui.vote.domain.VoteContentBO;
import cn.hui.vote.domain.VoteRecordBO;
import cn.hui.vote.web.domain.AddContentReq;
import cn.hui.vote.web.domain.AddFormReq;
import cn.hui.vote.web.domain.AddFormResp;
import cn.hui.vote.web.domain.DelContentReq;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/vote/admin")
public class VoteAdminController {

    private static Logger        LOGGER = LoggerFactory.getLogger(VoteAdminController.class);

    @Autowired
    private VoteBizService       voteBizService;

    @Autowired
    private VoteRecordBizService voteRecordBizService;

    @RequestMapping("/addForm")
    public AddFormResp addForm(@RequestBody AddFormReq addContentReq, HttpServletResponse response) throws IOException {
        AddFormResp resp = new AddFormResp();
        String voteName = addContentReq.getVoteName();
        if (StringUtils.isNotBlank(voteName)) {
            Long formId = voteBizService.createVoteForm(voteName);
            resp.setId(formId);
        }
        return resp;
    }

    @RequestMapping("/addContent")
    public Map addContent(@RequestBody AddContentReq addContentReq) {
        VoteContentBO voteContentBO = BeanUtil.copy(addContentReq, VoteContentBO.class);
        voteBizService.addVoteContent(voteContentBO);
        return Collections.EMPTY_MAP;
    }

    @RequestMapping("/deleteContent")
    public Map deleteContent(@RequestBody DelContentReq delContentReq) {
        voteBizService.delVoteContent(delContentReq.getId());
        return Collections.EMPTY_MAP;
    }

    @RequestMapping("/result")
    public Map result(@RequestParam("formId") Long formId) {
        List<VoteRecordBO> recordBOList = voteRecordBizService.listRecordByFormId(formId);
        Map<String, Integer> result = new HashMap<>();
        for (VoteRecordBO recordBO : recordBOList) {
            String vote = recordBO.getVote();
            String[] voteArr = vote.split(",");
            for (String contenIdStr : voteArr) {
                long contentId = Long.parseLong(contenIdStr);
                VoteContentBO contentBO = voteBizService.getVoteFormContent(contentId);
                Integer count = result.get(contentBO.getLineText());
                if (count == null) {
                    count = 1;
                } else {
                    count++;
                }
                result.put(contentBO.getLineText(), count);
            }
        }
        return result;
    }
}
