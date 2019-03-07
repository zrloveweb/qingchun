package com.zr.qingchun.test;

/**
 * @ClassName Vip
 * @Decription TODO
 * @Author Administrator
 * @Date 2018/12/25 0025 17:13
 * Version 1.0
 **/
@PriceRegion(max = 20000)
public class Vip implements CalPrice {
    @Override
    public Double calPrice(Double orgnicPrice) {
        return orgnicPrice * 0.9;
    }
}
