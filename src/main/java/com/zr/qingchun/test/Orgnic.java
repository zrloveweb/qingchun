package com.zr.qingchun.test;

/**
 * @ClassName Orgnic
 * @Decription TODO
 * @Author Administrator
 * @Date 2018/12/25 0025 17:17
 * Version 1.0
 **/
@PriceRegion(max = 10000)
public class Orgnic implements CalPrice {

    @Override
    public Double calPrice(Double orgnicPrice) {
        return orgnicPrice;
    }
}
