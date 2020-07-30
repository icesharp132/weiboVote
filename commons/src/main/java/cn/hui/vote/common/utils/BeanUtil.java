package cn.hui.vote.common.utils;

import cn.hui.vote.common.exception.ApiErrorCode;
import cn.hui.vote.common.exception.BizException;
import org.springframework.beans.BeanUtils;

/**
 * @Author: jiangyanze
 * @Description:
 * @Date: Created in 2018/2/19
 * @Modified By:
 */
public class BeanUtil {

    /**
     * Bean拷贝
     *
     * @param source
     * @param targetClazz
     * @param <T>
     * @return
     */
    public static <T> T copy(Object source, Class<T> targetClazz) {
        if (source == null) {
            return null;
        }
        try {
            T targetObj = targetClazz.newInstance();
            BeanUtils.copyProperties(source, targetObj);
            return targetObj;
        } catch (Exception e) {
            throw new BizException(ApiErrorCode.SYSTEM_ERROR, e);
        }
    }

}
