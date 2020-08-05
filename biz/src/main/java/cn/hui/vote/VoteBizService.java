package cn.hui.vote;

import cn.hui.vote.domain.VoteContentBO;
import cn.hui.vote.domain.VoteFormBO;

import java.util.List;

public interface VoteBizService {
    long createVoteForm(String  voteName);

    List<VoteFormBO> listVoteForm();

    List<VoteContentBO> listContentByForm(long formId);

    VoteFormBO getVoteForm(long formId);

    VoteContentBO getVoteFormContent(long contentId);

    long addVoteContent(VoteContentBO voteContentBO);

    void delVoteContent(Long id);
}
