package cn.hui.vote;

import cn.hui.vote.domain.VoteContentBO;
import cn.hui.vote.domain.VoteFormBO;
import cn.hui.vote.domain.VoteRecordBO;

import java.util.List;

public interface VoteRecordBizService {

    void vote(VoteRecordBO voteRecordBO);
}
