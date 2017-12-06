/**
*
*/
package cn.openlo.gw.proxy;

import java.util.Map;

/**
 * <p>
 * Title: GenericHttpRequest
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: openlo.cn Copyright (C) 2016
 * </p>
 *
 * @author gaozhi
 *
 * @version
 * @since 2016年6月10日
 */
public class GenericRequest {

    private Map<String, String> config;
    private Object param;


    public Map<String, String> getConfig() {
        return config;
    }

    public Object getParam() {
        return param;
    }

    public void setConfig(Map<String, String> config) {
        this.config = config;
    }

    public void setParam(Object param) {
        this.param = param;
    }
}
