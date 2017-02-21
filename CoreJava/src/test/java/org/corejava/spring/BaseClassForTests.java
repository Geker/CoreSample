package org.corejava.spring;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringContextConfig.class })
public class BaseClassForTests
{

    protected final Logger log = LoggerFactory.getLogger(getClass());


    static
    {

    }

    @BeforeClass
    static public void beforeClass()
    {

    }

    @Before
    public void setup() throws Exception
    {

    }

    @After
    public void teardown() throws Exception
    {

    }





}
