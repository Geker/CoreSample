package org.corejava.nosql;

import java.util.Map;

import org.corejava.bean.Computer;
import org.dizitart.no2.Document;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.NitriteCollection;
import org.dizitart.no2.mapper.JacksonMapper;
import org.dizitart.no2.mapper.NitriteMapper;
import org.dizitart.no2.objects.ObjectRepository;

import com.alibaba.fastjson.JSON;

public class NitriteUse {
	public static void main(String[] args) {
		Nitrite db = Nitrite.builder().compressed().filePath("test.db").openOrCreate("user", "password");
		NitriteCollection collection = db.getCollection("test");
		Computer c = new Computer();
		c.setCpu("intel");
		c.setKeyboard("coolmaster");
		c.setMem("1g");
		c.setMouse("logitech");
		// Create an Object Repository
		ObjectRepository<Map> repository = db.getRepository(Map.class);

		JacksonMapper nitriteMapper = new JacksonMapper();
		Document doc = 	nitriteMapper.asDocument(c);
//		Document doc = nitriteMapper.parse(JSON.toJSONString(c));
		collection.insert(doc);
	}
}
