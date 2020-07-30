package cn.hui.vote.common.exception;

/**
 * @author Lu Zhaohui
 * @version $Id: BizException, v 0.1 2018年06月14日 下午10:41 Lu Zhaohui Exp $
 */
public class BizException extends RuntimeException {

    private final int errorCode;

    public BizException(IExceptionCode errorCode, String msgDetail) {
        super(errorCode.getMsg() + "," + msgDetail);
        this.errorCode = errorCode.getCode();
    }

    public BizException(IExceptionCode errorCode) {
        super(errorCode.getMsg());
        this.errorCode = errorCode.getCode();
    }

    public BizException(IExceptionCode errorCode, String msgDetail, Throwable e) {
        super(errorCode.getMsg() + "," + msgDetail, e);
        this.errorCode = errorCode.getCode();
    }

    public BizException(IExceptionCode errorCode, Throwable e) {
        super(errorCode.getMsg(), e);
        this.errorCode = errorCode.getCode();
    }

    public BizException(Throwable e) {
        super(ApiErrorCode.SYSTEM_ERROR.getMsg(), e);
        this.errorCode = ApiErrorCode.SYSTEM_ERROR.getCode();
    }

    public int getErrorCode() {
        return errorCode;
    }

}
