package org.corejava.bean;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class GuavaCacheTest {

    public static void main(String[] args) throws InterruptedException {
        CacheBuilder<Object, Object> builder = CacheBuilder.newBuilder().maximumSize(100).expireAfterAccess(5, TimeUnit.SECONDS);
        Cache<String, CacheObj> cachePm = builder.build();
        cachePm.put("1", new CacheObj());
        CacheObj obj = cachePm.getIfPresent("1");
        System.out.println(obj);
        Thread.sleep(10000);
        obj = cachePm.getIfPresent("1");
        System.out.println(obj); // 5后 cache消失 获得null
    }

    public static class CacheObj

    {
        public static int index = 0;

        public CacheObj() {
            index++;
            System.out.println("index:" + index);
        }

        @Override
        public String toString() {
            return "CacheObj toString index:" + index;
        }



    }
}
