package cn.hui.vote.web.controller;

import cn.hui.vote.VoteBizService;
import cn.hui.vote.common.utils.BeanUtil;
import cn.hui.vote.domain.VoteContentBO;
import cn.hui.vote.web.domain.AddContentReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class VoteAdminController {

    private static Logger LOGGER = LoggerFactory.getLogger(VoteAdminController.class);

    @Autowired
    private VoteBizService voteBizService;

    @RequestMapping("/addContent")
    public String addContent(@RequestBody AddContentReq addContentReq) {
        VoteContentBO voteContentBO = BeanUtil.copy(addContentReq, VoteContentBO.class);
        voteBizService.addVoteContent(voteContentBO);
        return "success";
    }
}
