package cn.hui.vote;

import cn.hui.vote.domain.VoteContentBO;
import cn.hui.vote.domain.VoteFormBO;

import java.util.List;

public interface VoteBizService {
    long createVoteForm(VoteFormBO voteFormBO);

    List<VoteFormBO> listVoteForm();

    List<VoteContentBO> listContentByForm(long formId);

    VoteFormBO getVoteForm(long formId);

    long addVoteContent(VoteContentBO voteContentBO);
}
