package com.student.zhaokangwei.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 * 视图工作类
 */
public class ViewUtils {

    /**
     * 返回JSON数据
     * @param message
     * @param value
     * @param state
     * @return
     */
    public static String view(String message, Object value, int state) {
        JSONObject object = new JSONObject();
        object.put("state", state);
        if (message != null) {
            object.put("message", message);
        }
        if (value != null) {
            object.put("value", JSON.toJSON(value));
        }
        object.put("timestamp", LocalDateTime.now());
        return object.toString(SerializerFeature.WriteMapNullValue);
    }

    /**
     * 向响应写入内容
     * @param message
     * @param value
     * @param state
     */
    public static void print(HttpServletResponse response, String message, Object value, int state) {
        response.setStatus(state);  //给当前响应设置状态码
        response.setHeader("Content-Type", "application/json;charset=utf-8");
        PrintWriter printWriter = null;
        try {
            printWriter = response.getWriter(); //获取响应的“打印笔”
        } catch (Exception e) {
            e.printStackTrace();
        }
        printWriter.write(view(message, value, state)); //使用“打印笔”写入内容
        printWriter.flush();
        printWriter.close();
    }

}