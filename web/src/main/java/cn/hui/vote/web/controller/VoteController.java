package cn.hui.vote.web.controller;

import cn.hui.vote.VoteRecordBizService;
import cn.hui.vote.domain.VoteFormBO;
import cn.hui.vote.domain.VoteRecordBO;
import cn.hui.vote.web.domain.VoteDetailReq;
import cn.hui.vote.web.domain.VoteDetailResp;
import cn.hui.vote.web.domain.VoteReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.hui.vote.VoteBizService;
import cn.hui.vote.common.utils.BeanUtil;
import cn.hui.vote.domain.VoteContentBO;
import cn.hui.vote.web.domain.AddContentReq;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vote")
public class VoteController {

    private static Logger        LOGGER = LoggerFactory.getLogger(VoteController.class);

    @Autowired
    private VoteBizService       voteBizService;

    @Autowired
    private VoteRecordBizService voteRecordBizService;

    @RequestMapping("/detail")
    public VoteDetailResp voteDetail(@RequestBody VoteDetailReq voteDetailReq) {
        Long formId = voteDetailReq.getFormId();
        VoteFormBO voteFormBO = voteBizService.getVoteForm(formId);
        if (voteFormBO == null) {
            return null;
        }
        List<VoteContentBO> contentBOList = voteBizService.listContentByForm(formId);
        List<VoteDetailResp.VoteContentVO> voList = contentBOList.stream().map(voteContentBO -> {
            VoteDetailResp.VoteContentVO vo = new VoteDetailResp.VoteContentVO();
            vo.setId(voteContentBO.getId());
            vo.setLinePic(voteContentBO.getLinePic());
            vo.setLineText(voteContentBO.getLineText());
            return vo;
        }).collect(Collectors.toList());
        VoteDetailResp detailResp = new VoteDetailResp();
        detailResp.setId(voteFormBO.getId());
        detailResp.setGmtCreate(voteFormBO.getGmtCreate());
        detailResp.setGmtModify(voteFormBO.getGmtModify());
        detailResp.setCreatorWeiboId(voteFormBO.getCreatorWeiboId());
        detailResp.setMultiVote(voteFormBO.getMultiVote());
        detailResp.setVoteName(voteFormBO.getVoteName());
        detailResp.setContentList(voList);
        return detailResp;
    }

    @RequestMapping("/list")
    public List<VoteFormBO> listForm() {
        List<VoteFormBO> formBOList = voteBizService.listVoteForm();
        return formBOList;
    }

    @PostMapping("/vote")
    public Map voteSingle(@RequestBody VoteReq voteReq) {
        boolean pass = voteRecordBizService.check(voteReq.getFormId(), voteReq.getUid());
        Map<String, String> result = new HashMap<>();
        if (pass) {
            VoteRecordBO recordBO = BeanUtil.copy(voteReq, VoteRecordBO.class);
            recordBO.setUserId(voteReq.getUid());
            recordBO.setVote(voteReq.getId().toString());
            voteRecordBizService.vote(recordBO);
            result.put("msg", "投票成功");

        } else {
            result.put("msg", "一个账号只能投票一次");
        }
        return result;

    }
}
