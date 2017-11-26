package org.corejava.serializer;

import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;


public class FastJsonTest {

    @Test
    public void JsonToString() throws Exception {

        // 基本类型不会添加"";

        System.out.println(JSON.toJSONString(Integer.valueOf(1900)));
        // String 会添加"",然后后续的再次toJSonString会添加\\
        System.out.println(JSON.toJSONString(JSON.toJSONString("hello world")));
        System.out.println(JSON.toJSONString(Long.valueOf(1000)));

    }

	@Test
	public void ObjToString() throws Exception {

		// 多层JSON嵌套
		Box box = new Box();
		box.setName("box01");
		box.setLen(100);
		box.setWidth(200);
		System.out.println(JSON.toJSONString(box));
		MutableObject<Box> mb = new MutableObject<>();
		mb.setValue(box);
		MutableObject<String> mobj = new MutableObject<>();
		mobj.setValue(JSON.toJSONString(mb));

		System.out.println(JSON.toJSON(box).toString());
		System.out.println(JSON.toJSON(mb).toString());
		MutableObject<String> s = JSON.parseObject(JSON.toJSONString(mobj), new TypeReference<MutableObject<String>>() {
		});
		System.out.println(s);
		MutableObject<Box> w = JSON.parseObject(s.getValue(), new TypeReference<MutableObject<Box>>() {
		});
		Box ww = w.getValue();
		System.out.println(ww);

	}

	public static class Box {

		private String name;
		private int len;
		private int width;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getLen() {
			return len;
		}

		public void setLen(int len) {
			this.len = len;
		}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		@Override
		public String toString() {
			return "Box [name=" + name + ", len=" + len + ", width=" + width + "]";
		}

	}

}
