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
    public long createVoteForm(String voteName) {
        VoteFormDO voteFormDO = new VoteFormDO();
        voteFormDO.setVoteName(voteName);
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
    public VoteFormBO getVoteForm(long formId) {
        VoteFormDO voteFormDO = voteFormMapper.selectByPrimaryKey(formId);
        return BeanUtil.copy(voteFormDO, VoteFormBO.class);
    }

    @Override
    public VoteContentBO getVoteFormContent(long contentId) {
        VoteContentDO voteContentDO = voteContentMapper.selectByPrimaryKey(contentId);
        return BeanUtil.copy(voteContentDO, VoteContentBO.class);
    }

    @Override
    public long addVoteContent(VoteContentBO voteContentBO) {
        VoteContentDO voteContentDO = BeanUtil.copy(voteContentBO, VoteContentDO.class);
        voteContentMapper.insertSelective(voteContentDO);
        return voteContentDO.getId();
    }

    @Override
    public void delVoteContent(Long id) {
        voteContentMapper.deleteByPrimaryKey(id);
    }
}
