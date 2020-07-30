package cn.hui.vote.dal.domain;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageDO extends BaseBean {
    private static final Logger LOGGER        = LoggerFactory.getLogger(PageDO.class);

    /**
     * 分页数量
     */
    private static final int    MAX_PAGE_SIZE = 20;
    private Integer             skip;
    private Integer             max;

    /**
     * 分页号
     */
    private Integer             pageIndex     = 1;

    /**
     * 分页数目
     */
    private Integer             pageSize      = MAX_PAGE_SIZE;

    /**
     * 排序字段
     */
    private String              sortField;

    /**
     * 是否降序
     */
    private Boolean             isDesc;

    public void setSkip(Integer skip) {
        this.skip = skip;
    }

    public Integer getSkip() {
        return skip == null ? (pageIndex - 1) * pageSize : skip;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Integer getMax() {
        Integer result = max == null ? pageSize : (max > pageSize ? max : pageSize);
        if (result > 500) {
            result = 500;
            LOGGER.error("## Page size overlimits,pageSize:{}", pageSize);
        }
        return result;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        if (!Objects.isNull(pageIndex) && pageIndex <= 0) {
            pageIndex = 1;
        }

        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public Boolean getDesc() {
        return isDesc;
    }

    public void setDesc(Boolean desc) {
        isDesc = desc;
    }
}
