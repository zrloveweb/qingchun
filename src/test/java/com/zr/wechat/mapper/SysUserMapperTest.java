/*
package com.zr.wechat.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zr.qingchun.WechatApplication;
import com.zr.qingchun.mapper.SysUserMapper;
import com.zr.qingchun.model.SysUser;
import com.zr.qingchun.service.impl.SysUserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

*/
/**
 * @program: qingchun
 * @description:
 * @author: zhaoRui
 * @create: 2019-07-15 13:12
 **//*

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WechatApplication.class})
public class SysUserMapperTest {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserServiceImpl sysUserService;

    @Test
    public void test(){
        SysUser sysUser = sysUserMapper.selectById(1);

        System.out.println(sysUser);
    }
        @Bean
        public PaginationInterceptor paginationInterceptor() {
             return new PaginationInterceptor();
          }
    @Test
    public void pageHelper(){
        */
/*QueryWrapper<SysUser> queryWrapper =  new QueryWrapper<>();
        queryWrapper.select("user_name");
        queryWrapper.eq(SysUser.DEL_FLAG,SysUser.DEL_FLAG_N);

        Page<SysUser> page = new Page<>(1,5);
        IPage<SysUser> sysUserIPage = sysUserMapper.selectPage(page, queryWrapper);*//*


        Page<SysUser> page = new Page<>(1, 5);
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();

        IPage<SysUser> userIPage = sysUserMapper.selectPage(page, queryWrapper);
        System.out.println("1" + userIPage.getRecords());
        System.out.println("2" + userIPage.getPages());
        System.out.println("3" + userIPage.getTotal());


    }
}
*/
