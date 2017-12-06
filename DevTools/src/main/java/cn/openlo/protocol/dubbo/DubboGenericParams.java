package cn.openlo.protocol.dubbo;

/**
*
*/


import java.io.Serializable;
import java.util.Locale;
import java.util.Map;

/**
 * <p>
 * Title: DubboGenericParams
 * </p>
 * <p>
 * Description: Dubbo通用服务参数
 * </p>
 * <p>
 * Copyright: openlo.cn Copyright (C) 2016
 * </p>
 *
 * @author duanjunfeng
 *
 * @version 1.0.0
 * @since 2016年3月12日
 */
public class DubboGenericParams implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 参数传递Locale信息
     */
    private Locale locale;

    /**
     * 泛接口调用使用Map来传递参数
     */
    private Map<?, ?> params;

    public DubboGenericParams() {
        super();
    }

    public DubboGenericParams(Map<?, ?> params) {
        super();
        this.params = params;
    }

    public DubboGenericParams(Locale locale, Map<?, ?> params) {
        super();
        this.locale = locale;
        this.params = params;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Map<?, ?> getParams() {
        return params;
    }

    public void setParams(Map<?, ?> params) {
        this.params = params;
    }

}
