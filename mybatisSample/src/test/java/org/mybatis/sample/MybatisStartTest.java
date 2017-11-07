package org.mybatis.sample;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MybatisStartTest {

	private static final Logger logger = LoggerFactory.getLogger(MybatisStartTest.class);
	private SqlSessionFactory sqlSessionFactory;

	@Before
	public void init() throws IOException {
		String resource = "mybatis/sample/conf/mybatisconfig.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}

	@Test
	public void testSessionCache() throws Exception {

		SqlSession session = sqlSessionFactory.openSession();
		Object r = session.selectOne("selectCity", 2);
		logger.error("1 " + r.toString());
		Object r2 = session.selectOne("selectCity", 2);
		logger.error("2 " + r2.toString());

	}

	@Test
	public void testgloblaStart() throws Exception {

		SqlSession session = sqlSessionFactory.openSession();
		Object r = session.selectOne("selectCity", 2);
		logger.error("1 " + r.toString());
		session.commit();
		SqlSession session2 = sqlSessionFactory.openSession();
		Object r2 = session2.selectOne("selectCity", 2);
		logger.error("2 " + r2.toString());

	}

	@Test
	public void testCountry() throws Exception {

		SqlSession session = sqlSessionFactory.openSession();
		Object r = session.selectOne("selectCountry", 2);
		Object r1 = session.selectOne("selectCountry", 2);
		logger.error("1 " + r.toString());
		logger.error("2 " + r1.toString());

		session.commit();

	}

	@Test
	public void testCityMapper() throws Exception {

		SqlSession session = sqlSessionFactory.openSession();
		CityMapper mapper = session.getMapper(CityMapper.class);
        City r = mapper.selectCity(2);
        logger.warn(r.toString());
		Object i = mapper.updateCity("abha update", Integer.valueOf(2));
		logger.warn("update rows:" + i);

        // r = mapper.selectCity(2);
        // logger.warn(r.toString());

	}

	@Test
	public void testCityLike() throws Exception {

		SqlSession session = sqlSessionFactory.openSession();
		CityMapper mapper = session.getMapper(CityMapper.class);
		City r = mapper.selectCity(2);
		City c = mapper.selectCityName("Abh%");

		System.out.println(r);
		System.out.println(c);

	}
}
