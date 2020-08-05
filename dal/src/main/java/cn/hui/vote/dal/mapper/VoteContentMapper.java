package cn.hui.vote.dal.mapper;

import cn.hui.vote.dal.domain.VoteContentDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * VoteContentMapper
 *
 * @author Auto generated by luzhaohui
 * @since 2020-03-16
 * @version 1.0
 */
public interface VoteContentMapper extends BaseMapper<VoteContentDO> {

    // custom method
    List<VoteContentDO> listByFormId(@Param("formId") Long formId);

    Integer getLastLineNum(@Param("formId") Long formId);
}