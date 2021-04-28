package com.wanfangdata.grpc.server.query.chain.util;


import com.wanfangdata.api.chain.Request;
import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @Author FLY
 * @Date 2019-11-1
 *
 * */
public class DateUtil{

    private static SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);

    /**
     * solr时间的转换
     *
     * @param strDate  原字符串日期
     */
    public static String formatDateView(Object strDate){

        String stringDate = "";
        try {
            Date lDate = format.parse(strDate.toString());
            SimpleDateFormat lFormat = new SimpleDateFormat("yyyy年MM月");
            stringDate = lFormat.format(lDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return stringDate;
    }

}
