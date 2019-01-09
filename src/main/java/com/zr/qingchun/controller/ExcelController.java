package com.zr.qingchun.controller;

import com.zr.qingchun.test.CalPriceFactory;
import com.zr.qingchun.test.ceLueModel.ParamChain;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ExcelController
 * @Author Administrator
 * @Date 2018/12/5 0005 16:32
 * Version 1.0
 **/
@Controller
public class ExcelController {


    @Autowired
    private CalPriceFactory cl;

    @Autowired
    private ParamChain paramChain;
    @RequestMapping("info")
    public void info(HttpServletRequest request, HttpServletResponse response){
        paramChain.get();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sequence", "0001");
        map.put("date", "2018/01/04");
        map.put("chetaihao", "1#");
        map.put("productName", "产品名称");
        map.put("specification", "规格");
        map.put("memo", "备注");
        map.put("inspectRecordBizList", "一个list");

        HSSFWorkbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("测试表");
        Row row = sheet.createRow(0);
        int i = 0;
        for(String key : map.keySet()){
            Cell cell = row.createCell(i);
            cell.setCellValue((String) map.get(key));
            i++;
        }
        OutputStream fos = null;
        try {
            fos = response.getOutputStream();
            String userAgent = request.getHeader("USER-AGENT");
            String fileName = "test";
            try {
                if(StringUtils.contains(userAgent, "Mozilla")){
                    fileName = new String(fileName.getBytes(), "ISO8859-1");
                }else {
                    fileName = URLEncoder.encode(fileName, "utf8");
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/vnd.ms-excel;charset=utf-8");// 设置contentType为excel格式
            response.setHeader("Content-Disposition", "Attachment;Filename="+ fileName+".xls");
            wb.write(fos);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
