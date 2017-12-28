package conn;

import org.redisson.Redisson;
import org.redisson.api.RAtomicLong;
import org.redisson.config.Config;

public class RedissonConn {
    public static void main(String[] args) {

        Config config = new Config();

        config.useSingleServer().setAddress("redis://localhost:6379").setRetryAttempts(3);
        Redisson redisson = (Redisson) Redisson.create(config);
        RAtomicLong l = redisson.getAtomicLong("hello long");
        long c = l.incrementAndGet();
        System.err.println(c);
    }
}
