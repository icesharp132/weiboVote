package cn.hui.vote.impl;

import cn.hui.vote.VoteRecordBizService;
import cn.hui.vote.common.exception.ApiErrorCode;
import cn.hui.vote.common.exception.BizException;
import cn.hui.vote.common.utils.BeanUtil;
import cn.hui.vote.dal.domain.VoteContentDO;
import cn.hui.vote.dal.domain.VoteFormDO;
import cn.hui.vote.dal.domain.VoteRecordDO;
import cn.hui.vote.dal.mapper.VoteContentMapper;
import cn.hui.vote.dal.mapper.VoteFormMapper;
import cn.hui.vote.dal.mapper.VoteRecordMapper;
import cn.hui.vote.domain.VoteRecordBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service("voteRecordBizService")
public class VoteRecordBizServiceImpl implements VoteRecordBizService {

    @Autowired
    private VoteFormMapper voteFormMapper;

    @Autowired
    private VoteRecordMapper voteRecordMapper;

    @Override
    public boolean check(long formId, long userId) {
        List<VoteRecordDO> recordDOList = voteRecordMapper.listByFormIdAndUserId(formId, userId);
        return CollectionUtils.isEmpty(recordDOList);
    }

    @Override
    public void vote(VoteRecordBO voteRecordBO) {
        Long formId = voteRecordBO.getFormId();
        VoteFormDO voteFormDO = voteFormMapper.selectByPrimaryKey(formId);
        if (voteFormDO.getMultiVote() == 0) {
            String vote = voteRecordBO.getVote();
            if (vote.contains(",")) {
                throw new BizException(ApiErrorCode.VOTE_NOT_ALLOW_MULTI_ERORR);
            }
        }
        VoteRecordDO voteRecordDO = BeanUtil.copy(voteRecordBO, VoteRecordDO.class);
        voteRecordMapper.insertSelective(voteRecordDO);
    }

}
