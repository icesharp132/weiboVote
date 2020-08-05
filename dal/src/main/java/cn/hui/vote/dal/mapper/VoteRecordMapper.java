package cn.hui.vote.dal.mapper;

import cn.hui.vote.dal.domain.VoteRecordDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * VoteRecordMapper
 *
 * @author Auto generated by luzhaohui
 * @since 2020-03-16
 * @version 1.0
 */
public interface VoteRecordMapper extends BaseMapper<VoteRecordDO> {

    // custom method
    List<VoteRecordDO> listByFormIdAndUserId(@Param("formId") Long formId, @Param("userId") Long userId);
}