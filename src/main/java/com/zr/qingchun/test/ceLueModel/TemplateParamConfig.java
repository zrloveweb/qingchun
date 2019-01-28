package com.zr.qingchun.test.ceLueModel;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @ClassName TemplateParamConfig
 * @Decription TODO
 * @Author Administrator
 * @Date 2018/12/27 0027 18:22
 * Version 1.0
 **/

@Configuration
public class TemplateParamConfig {




    @Bean
    public ParamChain getFruit(List<Fruit> list){
        return new ParamChain(list);
    }
}
