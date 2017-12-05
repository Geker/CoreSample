package org.corejava.bean.mapbean.convert;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.corejava.bean.Computer;
import org.corejava.bean.NoteBook;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Map2BeanConvertTest {

    private static final Logger logger = LoggerFactory.getLogger(Map2BeanConvertTest.class);
    WorkPc pc;

    @Before
    public void init() {
        pc = new WorkPc();
        Computer computer = new Computer();
        computer.setCpu("Intel 8700K");
        computer.setKeyboard("CoolMaster");
        computer.setMem("8Gb");
        computer.setMouse("Logitech");
        computer.setPrice(new BigDecimal("7999.2"));
        pc.setComputer(computer);
        NoteBook nb = new NoteBook();
        nb.setCpu("Intel 7200U");
        nb.setKeyboard("include");
        nb.setSpeed(2700);
        nb.setMem("16G");
        nb.setMouse(null);
        nb.setTouchPad("tpad");
        pc.setNb(nb);
    }

    @Test
    public void bean2Map_BeanUtilsTest() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        // 不处理内部的类，直接作为property属性。
        Map<String, String> map = BeanUtils.describe(pc);

        // 展开内部的类结构。
        Map<String, Object> pmap = PropertyUtils.describe(pc);
        logger.error("beanUtils:" + JSON.toJSONString(map));
        logger.error("PropertyUtils:" + JSON.toJSONString(pmap));

    }

    @Test
    public void bean2Map_JackSonTest() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        ObjectMapper obm = new ObjectMapper();

        Map<String, Object> pmap = obm.convertValue(pc, new TypeReference<Map<String, Object>>() {
        });

        logger.error("ObjectMapper:" + JSON.toJSONString(pmap));

        pc = obm.convertValue(pmap, WorkPc.class);

        logger.error("ObjectMapper   Map2bean:" + JSON.toJSONString(pmap));

    }
}
