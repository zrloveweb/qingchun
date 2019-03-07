package com.zr.wechat;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName RedisGoodsKill
 * @Decription TODO
 * @Author Administrator
 * @Date 2018/11/21 0021 10:55
 * Version 1.0
 **/
public class RedisGoodsKill {
    public static void a(String[] args) {
        final String watchkeys = "watchkeys";
        ExecutorService executor = Executors.newFixedThreadPool(20);

        final Jedis jedis = new Jedis("119.29.0.105", 6379);
        jedis.auth("root");
        System.out.println(jedis);
        jedis.set(watchkeys, "0");// 重置watchkeys为0
        jedis.del("setsucc", "setfail");// 清空抢成功的，与没有成功的
        jedis.close();

        for (int i = 0; i < 1000; i++) {// 测试一万人同时访问
            executor.execute(new MyRunnable());
        }
        executor.shutdown();
    }

    static int goodsNum = 20;

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 10000; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    Jedis jedis = new Jedis("localhost", 6379);
                    try {

                        if (jedis.setnx("qingchunLock", "lock") == 1) {

                            jedis.expire("qingchunLock", 1);
                            int goodsPhoneP9Num = Integer.parseInt(jedis.get("goodsPhoneP9Num"));
                            if (goodsPhoneP9Num > 0) {
                                jedis.decr("goodsPhoneP9Num");
                                System.err.println(Thread.currentThread().getName() + " 抢购成功！");
                            } else {
                                System.out.println(Thread.currentThread().getName() + " 剩余商品不足！");
                            }
                        } else {
                            System.out.println(Thread.currentThread().getName() + "抢购失败!");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        jedis.del("qingchunLock");
                        jedis.close();
                    }

                }
            });

        }

    }

    @Test
    public void lockTest01() {
        final Jedis jedis = new Jedis("localhost", 6379);
        jedis.set("goodsPhoneP9Num", "10");
        jedis.close();

    }
}


class MyRunnable implements Runnable {

    String watchkeys = "watchkeys";// 监视keys
    Jedis jedis = new Jedis("119.29.0.105", 6379);


    public MyRunnable() {
    }

    @Override
    public void run() {
        try {
            jedis.auth("root");
            jedis.watch(watchkeys);// watchkeys

            String val = jedis.get(watchkeys);
            int valint = Integer.valueOf(val);
            String userifo = UUID.randomUUID().toString();
            if (valint < 10) {
                Transaction tx = jedis.multi();// 开启事务

                tx.incr("watchkeys");
                List<Object> list = tx.exec();// 提交事务，如果此时watchkeys被改动了，则返回null
                System.out.println("list:" + list);
                if (list != null && list.size() > 0) {
                    System.out.println("用户：" + userifo + "抢购成功，当前抢购成功人数:"
                            + (valint + 1));
                    /* 抢购成功业务逻辑 */
                    jedis.sadd("setsucc", userifo);
                } else {
                    System.out.println("用户：" + userifo + "抢购失败");
                    /* 抢购失败业务逻辑 */
                    jedis.sadd("setfail", userifo);
                }

            } else {
                System.out.println("用户：" + userifo + "抢购失败");
                jedis.sadd("setfail", userifo);
                // Thread.sleep(500);
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }

    }

}
