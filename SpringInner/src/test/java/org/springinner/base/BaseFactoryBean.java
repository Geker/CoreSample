package org.springinner.base;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Title: BaseFactoryBean
 * </p>
 * <p>
 * Description: 定义为FactoryBean之后，bean的名称为baseFactoryBean，直接获取T类型的Bean
 * <br/>
 * 另外,通过&baseFactoryBean，可以获得这个factorybean的对象。factorybean自行调用getObject方法，不会有单例的效果
 * </p>
 * <p>
 * Copyright: ufo Copyright (C) 2017
 * </p>
 *
 * @author SONGQQ
 * @version
 * @since 2017年5月2日
 */
@Component
public class BaseFactoryBean implements FactoryBean<BaseBean> {
    static int i;
    @Override
    public BaseBean getObject() throws Exception {
        BaseBean bean = new BaseBean();
        bean.setName("single " + i++);
        return bean;

    }

    @Override
    public Class<?> getObjectType() {
        return BaseBean.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
