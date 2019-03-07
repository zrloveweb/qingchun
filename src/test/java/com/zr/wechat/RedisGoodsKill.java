package com.zr.wechat;

import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.config.Config;
import redis.clients.jedis.Jedis;
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
        Jedis jedis1 = new Jedis("localhost", 6379);
        jedis1.set("goodsnum","10");
        jedis1.close();





        ExecutorService executorService = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 1000; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    Jedis jedis = new Jedis("localhost", 6379);
                    try {

                        //if (jedis.setnx("qingchunLock", "lock") == 1) {

                            //jedis.expire("qingchunLock", 1);
                            int goodsPhoneP9Num = Integer.parseInt(jedis.get("goodsnum"));
                            if (goodsPhoneP9Num > 0) {
                                jedis.decr("goodsnum");
                                System.err.println(Thread.currentThread().getName() + " 抢购成功！");
                            } else {
                                System.out.println(Thread.currentThread().getName() + " 剩余商品不足！");
                            }
                       // } else {
                         //   System.out.println(Thread.currentThread().getName() + "抢购失败!");
                       // }
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

    /**
     * 采用redis事务做秒杀活动活动
     * @param args
     */
    public static void main33(String[] args) {
        Jedis jedis1 = new Jedis("localhost", 6379);
        jedis1.set("goodsPhoneP9Num","10");
        jedis1.close();



        ExecutorService executorService = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 100; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    Jedis jedis = new Jedis("localhost", 6379);
                    try {
                        jedis.watch("goodsPhoneP9Num");
                        int goodsPhoneP9Num = Integer.parseInt(jedis.get("goodsPhoneP9Num"));
                        if (goodsPhoneP9Num > 0) {
                            Transaction multi = jedis.multi();
                            multi.decr("goodsPhoneP9Num");
                            List<Object> list = multi.exec();
                            if (list != null && list.size() > 0) {

                                System.err.println(Thread.currentThread().getName() + " 抢购成功!" + list.get(0));
                            } else {
                                System.out.println(Thread.currentThread().getName() + " 抢购失败~" + list);

                            }
                        }else{
                            System.out.println("抢购失败");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });

        }
    }
    public static  RedissonClient getClient(){

        Config config = new Config();

        config.useSingleServer().setAddress("redis://127.0.0.1:6379");

        RedissonClient redisson = Redisson.create(config);

        return redisson;

    }

    public static void main55(String[] args) {

        RedissonClient client1 = RedisGoodsKill.getClient();
        RAtomicLong atomicLock = client1.getAtomicLong("huaWeiP9Num");
        atomicLock.set(10);

        ExecutorService executorService = Executors.newFixedThreadPool(100);
        for(int i = 0; i< 100 ;i++){
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    RedissonClient client = RedisGoodsKill.getClient();
                    RLock redissionLock = client.getLock("redissionLock");
                    redissionLock.lock();
                    try {
                        RAtomicLong atomicLock = client.getAtomicLong("huaWeiP9Num");
                        long andDecrement = atomicLock.get();
                        if(andDecrement <= 0){

                            System.err.println("抢购失败!");
                        }else{
                            long andDecrement1 = atomicLock.getAndDecrement();
                            System.out.println("抢购成功!剩余数：" + andDecrement1);
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    } finally{
                        redissionLock.unlock();
                    }
                }
            });
        }
    }


    @Test
    public void lockTest01() {
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        for (int i = 0; i < 100; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    Jedis jedis = new Jedis("localhost",6379);
                    System.out.println(jedis);

                }
            });
        }
    }




        /*jedis.hset("hsetKey","set001","001");
        jedis.hset("hsetKey","set002","002");
        jedis.hset("hsetKey","set003","003");

        String hget = jedis.hget("hsetKey", "set001");
        System.out.println(hget);
        Map<String, String> hsetKey = jedis.hgetAll("hsetKey");
        System.out.println(hsetKey);
        Set<String> hsetKey = jedis.hkeys("hsetKey");
        System.out.println(hsetKey);
        List<String> hsetKey = jedis.hvals("hsetKey");
        System.out.println(hsetKey);
        jedis.close();
        jedis.lpush("listData","10","20","20","30","40");

        System.out.println(jedis.llen("listData"));
        System.out.println(jedis.lindex("listData",jedis.llen("listData")-1));
        System.out.println(jedis.lrange("listData",0,-1));
        //jedis.blpop("listData");
        List<String> listData = jedis.blpop(1, "listData");
        System.out.println(listData);

        jedis.sadd("setData","你好","你好","早上好");
        Set<String> setData = jedis.smembers("setData");
        System.out.println(jedis.scard("setData"));
        System.out.println(jedis.sismember("setData","你好"));
        System.out.println(setData);



        jedis.zadd("zSetAdd",10,"十");
        jedis.zadd("zSetAdd",9,"9");
        jedis.zadd("zSetAdd",9,"6.2");
        jedis.zadd("zSetAdd",8,"8");
        jedis.zadd("zSetAdd",7,"6.1");
        jedis.zadd("zSetAdd",6,"6");

        System.out.println(jedis.zrange("zSetAdd",0,-1));*/


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
