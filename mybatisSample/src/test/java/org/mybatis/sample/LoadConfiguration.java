package org.mybatis.sample;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

public class LoadConfiguration {

    @Test
    public void testStart() throws Exception {
        String resource = "mybatis/sample/conf/localdb-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
        Object r = session.selectOne("selectCity", 2);
        System.err.println(r.toString());

    }
}
