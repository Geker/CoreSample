package org.corejava.bean.hessian;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

public class HessianSerializer {

    public static byte[] serialize(Object obj) throws IOException {
        if (obj == null)
            throw new NullPointerException();

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        HessianOutput ho = new HessianOutput(os);
        ho.getSerializerFactory().setAllowNonSerializable(true);
        ho.writeObject(obj);
        return os.toByteArray();
    }

    public static Object deserialize(byte[] by) throws IOException {
        if (by == null)
            throw new NullPointerException();

        ByteArrayInputStream is = new ByteArrayInputStream(by);
        HessianInput hi = new HessianInput(is);
        return hi.readObject();
    }

    public static void main(String[] args) throws IOException {
        Bean b = new Bean();
        b.setDt(new Date());
        b.setI(100);
        b.setL(1000L);
        b.setName("test");
        Map<String, Object> map = new HashMap<>();
        map.put("dt", new Date());
        map.put("age", 29);
        b.setMap(map);
        byte[] bytes = serialize(b);
        String s = new String(bytes);
        System.err.println(s);
        Object obj = deserialize(bytes);
        System.err.println(obj);

    }
    

    static class Bean {

        Date dt;
        String name;
        Integer i;
        Long l;
        
        Map<String, Object> map;

        public Date getDt() {
            return dt;
        }

        public void setDt(Date dt) {
            this.dt = dt;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getI() {
            return i;
        }

        public void setI(Integer i) {
            this.i = i;
        }

        public Long getL() {
            return l;
        }

        public void setL(Long l) {
            this.l = l;
        }

        public Map<String, Object> getMap() {
            return map;
        }

        public void setMap(Map<String, Object> map) {
            this.map = map;
        }

        @Override
        public String toString() {
            return "Bean [dt=" + dt + ", name=" + name + ", i=" + i + ", l=" + l + ", map=" + map + "]";
        }

    }

}
