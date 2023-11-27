package com.xiong.myprojectbackend.Utils;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 10371
 * @Description
 * @create 2023/11/16 10:41:48
 */
@Component
public class TimeUtils {
    public String toDay(){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }
}
