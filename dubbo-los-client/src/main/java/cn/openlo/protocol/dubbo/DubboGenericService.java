package cn.openlo.protocol.dubbo;


import java.util.Locale;
import java.util.Map;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.service.GenericException;
import com.alibaba.dubbo.rpc.service.GenericService;

/**
 * 
 * <p>
 * Title: DubboGenericService
 * </p>
 * <p>
 * Description: dubbo通用服务
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
public abstract class DubboGenericService implements GenericService {

    /**
     * 通过特定的Attachment的interface属性来约定服务名称
     */
    private static final String DUBBO_ATTACHEMENT_INTERFACE = "interface";

    /**
     * Dubbo通用服务调用
     * 
     * @see com.alibaba.dubbo.rpc.service.GenericService#$invoke(java.lang.String, java.lang.String[], java.lang.Object[])
     * @return DubboGenericResult
     */
    @Override
    public Object $invoke(String method, String[] parameterTypes, Object[] args) throws GenericException {
        RpcContext context = RpcContext.getContext();
        String serviceName = context.getAttachment(DUBBO_ATTACHEMENT_INTERFACE);
        if (serviceName == null) {
            throw new java.lang.IllegalArgumentException("Requried <interface>  in RpcContext.");
        }
        Object args0 = (args != null && args.length > 0) ? args[0] : null;
        DubboGenericResult result = null;
        if (args0 == null) {
            result = doInvoke(serviceName, (Locale) null, (Map<?, ?>) null);
        }
        else {
            DubboGenericParams dubboGenericParams = (DubboGenericParams) args0;
            Locale locale = dubboGenericParams.getLocale();
            result = doInvoke(serviceName, locale, dubboGenericParams.getParams());
        }
        return result;
    }

    /**
     * 具体的服务调用
     * 
     * @param serviceName 服务名称
     * @param locale 请求方locale信息
     * @param params 服务参数
     * @return DubboGenericResult
     */
    protected abstract DubboGenericResult doInvoke(String serviceName, Locale locale, Map<?, ?> params);

}
