package com.zr.qingchun.test;

/**
 * @ClassName Vip
 * @Decription TODO
 * @Author Administrator
 * @Date 2018/12/25 0025 17:13
 * Version 1.0
 **/
@PriceRegion(min=20000,max=30000)
public class SuperVip implements CalPrice {
    @Override
    public Double calPrice(Double orgnicPrice) {
        return orgnicPrice * 0.9;
    }
}
