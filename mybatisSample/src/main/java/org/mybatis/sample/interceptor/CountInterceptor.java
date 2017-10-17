package org.mybatis.sample.interceptor;

import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

@Intercepts({ @Signature(method = "query", type = Executor.class, args = { MappedStatement.class, Object.class, RowBounds.class,
                                                                           ResultHandler.class }) })
public class CountInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object result = invocation.proceed();
        MappedStatement stmt = (MappedStatement) invocation.getArgs()[0];
        String sqlIdWithNamespace = stmt.getId();

        if (sqlIdWithNamespace.endsWith("countByExample"))
        {
            return null;
        }
        System.out.println("Invocation.proceed()");
        return result;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        String prop1 = properties.getProperty("prop1");
        String prop2 = properties.getProperty("prop2");
        System.out.println(prop1 + "------" + prop2);
    }
}
