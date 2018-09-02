package org.cxf.ws.server;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.ContextConfiguration;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:cxf-spring.xml" })
public class SpringCxfTest {

	@Test
	public void test() throws IOException {
		System.in.read();
	}

}
