package com.zr.wechat.mapper;

import com.zr.qingchun.WechatApplication;
import com.zr.qingchun.mapper.SysUserMapper;
import com.zr.qingchun.model.SysUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @program: qingchun
 * @description:
 * @author: zhaoRui
 * @create: 2019-07-15 13:12
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WechatApplication.class})
public class SysUserMapperTest {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Test
    public void test(){
        SysUser sysUser = sysUserMapper.selectById(1);

        System.out.println(sysUser);
    }
}
