package com.zr.qingchun.lottery;

import java.util.*;

/**
 * @ClassName SimpleLottery
 * @Decription TODO
 * @Author Administrator
 * @Date 2019/2/15 0015 14:51
 * Version 1.0
 **/
public class SimpleLottery {



    public static void main(String[] args) {
        /**
         * 奖品概率放入list（顺序-> 概率从小到大) 、通过概率和随机生成的概率比较(后者大于前者则说明抽到当前奖品)
         */
        double b = 0.2;
        double c = 0.3;
        double m = 1;
        List<Double> list = new ArrayList<>();
        list.add(b);
        list.add(c);
        list.add(m);

        Random random = new Random();
        double i = random.nextDouble();

        System.out.println(i);
        for(Double value : list){
            if(i < value){
                System.out.println(value + "中奖");
                break;
            }
        }

    }
}
