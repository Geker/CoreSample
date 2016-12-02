package org.corejava.map;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

@SuppressWarnings("rawtypes")
public class MapPerfTest {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println();

            int entries = i * 100 * 1000;
            long t0 = test(entries, new FakeMap());
            long t1 = test(entries, new HashMap<>());
            // long t2 = test(entries, new ConcurrentHashMap());
            long t2 = test(entries, Collections.synchronizedMap(new HashMap<>()));
            long diff = (t2 - t1) * 100 / (t1 - t0);
            System.out.printf("entries=%,d time diff= %d%% %n", entries, diff);
        }
    }

    @SuppressWarnings("unchecked")
    static long test(int ENTRIES, Map map) {
        long SEED = 0;
        Random random = new Random(SEED);

        int RW_RATIO = 10;

        long t0 = System.nanoTime();

        for (int i = 0; i < ENTRIES; i++)
            map.put(random.nextInt(), random.nextInt());

        for (int i = 0; i < RW_RATIO; i++) {
            random.setSeed(SEED);
            for (int j = 0; j < ENTRIES; j++) {
                map.get(random.nextInt());
                random.nextInt();
            }
        }
        long t = System.nanoTime() - t0;
        System.out.printf("%,d ns %s %n", t, map.getClass());
        return t;
    }

    static class FakeMap implements Map {
        @Override
        public Object get(Object key) {
            return null;
        }

        @Override
        public Object put(Object key, Object value) {
            return null;
        }
        // etc. etc.

        @Override
        public int size() {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public boolean isEmpty() {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean containsKey(Object key) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean containsValue(Object value) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public Object remove(Object key) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void putAll(Map m) {
            // TODO Auto-generated method stub

        }

        @Override
        public void clear() {
            // TODO Auto-generated method stub

        }

        @Override
        public Set keySet() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Collection values() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Set entrySet() {
            // TODO Auto-generated method stub
            return null;
        }
    }
}
