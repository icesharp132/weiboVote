package cn.hui.vote.dal.mapper;


import cn.hui.vote.dal.domain.BaseBean;

public interface BaseMapper<T extends BaseBean> {

    int insert(T record);

    int insertSelective(T record);

    int deleteByPrimaryKey(Long id);

    T selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(T record);

}