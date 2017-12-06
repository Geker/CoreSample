package cn.openlo.exception;

/**
 *
 * <p>
 * Title: LOSException
 * </p>
 * <p>
 * Description: 应用开发人员写服务时向外抛的异常
 * </p>
 * <p>
 * Copyright: openlo.cn Copyright (C) 2016
 * </p>
 *
 * @author wangzq
 *
 * @version
 * @since 2016年3月10日
 */
public class LOSException extends Exception {

    private static final long serialVersionUID = -3086818783739039500L;

    public LOSException() {
        super();
    }

    public LOSException(String message) {
        super(message);
    }

    public LOSException(Throwable cause) {
        super(cause);
    }

    public LOSException(String message, Throwable cause) {
        super(message, cause);
    }
}
