package conn;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class ConnSample {
    public static void main(String[] args) {
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");
        Jedis res = pool.getResource();
        res.close();
        res.set("key", "1000");
        System.out.println(res.get("key"));

    }
}
