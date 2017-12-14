package cn.openlo.protocol.dubbo;


import java.io.Serializable;
import java.util.Map;

/**
 * <p> Title: DubboGenericResult </p>
 * <p> Description: Dubbo通用响应参数 </p>
 * <p> Copyright: openlo.cn Copyright (C) 2016 </p>
 *
 * @author duanjunfeng
 * @version 1.0.0
 * @since 2016年9月2日
 */
public class DubboGenericResult implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * map形式响应内容
     */
    private Map<?, ?> result = null;

    public Map<?, ?> getResult() {
        return result;
    }

    public void setResult(Map<?, ?> result) {
        this.result = result;
    }

}
