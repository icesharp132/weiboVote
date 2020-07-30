package cn.hui.vote.common.exception;

/**
 * 异常码接口
 * @author Lu Zhaohui
 * @version $Id: IExceptionCode, v 0.1 2018年06月14日 下午10:38 Lu Zhaohui Exp $
 */
public interface IExceptionCode {

    /**
     * 获取错误码
     *
     * @return
     */
    int getCode();

    /**
     * 获取错误信息
     *
     * @return
     */
    String getMsg();
}
