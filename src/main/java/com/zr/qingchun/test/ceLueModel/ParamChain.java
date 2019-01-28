package com.zr.qingchun.test.ceLueModel;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName ParamChain
 * @Decription TODO
 * @Author Administrator
 * @Date 2018/12/27 0027 18:21
 * Version 1.0
 **/
@Component
public class ParamChain {
    private List<Fruit>  fruits;


    public ParamChain(List<Fruit> fruits) {
        this.fruits = fruits;
    }

    public void get(){
        for(Fruit fruit : fruits){
            fruit.eat();
        }
    }
}
