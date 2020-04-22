package com.zr.qingchun.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zr.qingchun.common.ResultDto;
import com.zr.qingchun.model.SysUser;
import com.zr.qingchun.service.SysUserService;
import com.zr.qingchun.util.BASE64DecodedMultipartFile;
import com.zr.qingchun.util.MultipartFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * @ClassName SysUserController
 * @Decription
 * @Author Administrator
 * @Date 2018/9/20 0020 14:49
 * Version 1.0
 **/
@RestController
@RequestMapping("/sysUser")
public class SysUserController {


    @Autowired
    private SysUserService userService;


    /**
     * 登录
     *
     * @param loginParam
     * @return
     */
    @RequestMapping("/login")
    public ResultDto<SysUser> login(@RequestBody String loginParam) {
        ResultDto<SysUser> resultDto = new ResultDto<>();
        JSONObject jsonObject = JSONObject.parseObject(loginParam);
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        SysUser sysUser = userService.selectByNameAndPwd(username, password);
        if (sysUser == null) {
            resultDto.setCode("10001");
            resultDto.setMessage("密码错误或用户不存在");
        } else {
            resultDto.setCode("10000");
            resultDto.setMessage("登陆成功");
        }
        return resultDto;
    }


    /**
     * 添加系统用户
     *
     * @return
     */
    @RequestMapping(value = "/addSysUser", method = RequestMethod.POST)
    public ResultDto<List<SysUser>> addSysUser(@RequestBody SysUser sysUser) {
        ResultDto<List<SysUser>> resultDto = new ResultDto<>();
        resultDto.success();
        sysUser.setCreator("admin");
        sysUser.setUpdator("admin");
        sysUser.setDelFlag(SysUser.DEL_FLAG_N);
        boolean b = userService.save(sysUser);
        if (!b) {
            resultDto.failure();
        }
        return resultDto;
    }

    /**
     * 查询系统用户
     *
     * @return
     */
    @RequestMapping(value = "/sysUserList",method = RequestMethod.GET)
    public ResultDto<IPage<SysUser>> sysUserList(Integer currentSize, Integer pageNum ,String username) {
        ResultDto<IPage<SysUser>> resultDto = new ResultDto<>();
        IPage<SysUser> page = userService.selectAllSysUser(currentSize, pageNum,username);
        return resultDto.success(page);
    }

    SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");

    @CrossOrigin(value = "*")
    @PostMapping("/upload")
    public ResultDto importData(MultipartFile file, HttpServletRequest req) throws IOException {
        String format = sdf.format(new Date());
        String realPath = req.getServletContext().getRealPath("/upload") + format;
        File folder = new File("d://upload");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String oldName = file.getOriginalFilename();
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."));
        file.transferTo(new File(folder,newName));
        String url = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + "/upload" + format + newName;
        System.out.println(url);
        return new ResultDto().success();
    }

    @PostMapping("/uploadCropImg")
    public ResultDto uploadCropImg(@RequestBody String uploadBase, HttpServletRequest req) throws IOException {
        JSONObject jsonObject = JSON.parseObject(uploadBase);
        String base64 = jsonObject.getString("imgBase64");
        String[] split = base64.split(",");
        String base64Data = split[1];
        System.out.println(base64Data);
/**
 * 2.解码成字节数组
 */
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] bytes = decoder.decode(base64Data);
        String newName = UUID.randomUUID().toString() + ".jpg";
        String url = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + "/upload"  + newName;
        MultipartFile multipartFile1 = MultipartFileUtil.base64ToMultipart(base64);
        File folder = new File("d://upload");

        multipartFile1.transferTo(new File(folder,newName));

        //String oldName = file.getOriginalFilename();
        //String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."));
        //file.transferTo(new File(folder,newName));
        //String url = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + "/upload" + format + newName;
        //System.out.println(url);
        return new ResultDto().success(newName);
    }
    /**
     * 查询系统用户
     *
     * @return
     */
    @RequestMapping(value = "/updatesUserById", method = RequestMethod.POST)
    public ResultDto<List<SysUser>> updatesUserById(@RequestBody SysUser sysUser) {
        ResultDto<List<SysUser>> resultDto = new ResultDto<>();
        resultDto.success();
        boolean b = userService.updateById(sysUser);
        if (!b) {
            resultDto.failure();
        }
        return resultDto;
    }


    /**
     * 删除系统用户
     *
     * @return
     */
    @RequestMapping(value = "/delSysUser", method = RequestMethod.POST)
    public ResultDto delSysUser(@RequestBody String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        ResultDto resultDto = new ResultDto<>();
        resultDto.failure();
        IPage<SysUser> page = userService.deleteSysUserById(jsonObject.getInteger("id"));
        if (page != null) {
            resultDto.success(page);
        }
        return resultDto;
    }

    /**
     * 删除系统用户
     *
     * @return
     */
    @RequestMapping(value = "/batchDelSysUser", method = RequestMethod.POST)
    public ResultDto batchDelSysUser(@RequestBody Integer[] ids) {
        ResultDto resultDto = new ResultDto<>();
        resultDto.failure();

        IPage<SysUser> sysUserIPage = userService.batchDeleteSysUser(ids);
        if (sysUserIPage != null) {
            resultDto.success(sysUserIPage);
        }

        return resultDto;
    }


}
