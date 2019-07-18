package com.zr.qingchun.mapper;

import com.zr.qingchun.model.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author:zhaorui
 */
public interface SysUserMapper  extends SuperMapper<SysUser> {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    List<SysUser> selectAllSysUser();

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    SysUser selectByNameAndPwd(@Param("userName") String userName, @Param("passWord") String passWord);

}