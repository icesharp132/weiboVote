package cn.hui.vote.impl;

import cn.hui.vote.VoteBizService;
import cn.hui.vote.common.utils.BeanUtil;
import cn.hui.vote.dal.domain.VoteContentDO;
import cn.hui.vote.dal.domain.VoteFormDO;
import cn.hui.vote.dal.mapper.VoteContentMapper;
import cn.hui.vote.dal.mapper.VoteFormMapper;
import cn.hui.vote.domain.VoteContentBO;
import cn.hui.vote.domain.VoteFormBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("voteBizService")
public class VoteBizServiceImpl implements VoteBizService {

    @Autowired
    private VoteFormMapper voteFormMapper;

    @Autowired
    private VoteContentMapper voteContentMapper;

    @Override
    public long createVoteForm(VoteFormBO voteFormBO) {
        VoteFormDO voteFormDO = BeanUtil.copy(voteFormBO, VoteFormDO.class);
        voteFormMapper.insertSelective(voteFormDO);
        return voteFormDO.getId();
    }

    @Override
    public List<VoteFormBO> listVoteForm() {
        List<VoteFormDO> voteFormDOList = voteFormMapper.selectAll();
        return voteFormDOList.stream().map(voteFormDO -> {
            VoteFormBO voteFormBO = BeanUtil.copy(voteFormDO, VoteFormBO.class);
            return voteFormBO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<VoteContentBO> listContentByForm(long formId) {
        List<VoteContentDO> contentDOList = voteContentMapper.listByFormId(formId);
        return contentDOList.stream().map(voteContentDO -> {
            VoteContentBO voteContentBO = BeanUtil.copy(voteContentDO, VoteContentBO.class);
            return voteContentBO;
        }).collect(Collectors.toList());
    }

    @Override
    public long upsertVoteContent(VoteContentBO voteContentBO) {
        VoteContentDO voteContentDO = BeanUtil.copy(voteContentBO, VoteContentDO.class);
        voteContentMapper.insertSelective(voteContentDO);
        return voteContentDO.getId();
    }
}