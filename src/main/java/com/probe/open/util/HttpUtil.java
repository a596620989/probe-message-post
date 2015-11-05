package com.probe.open.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class HttpUtil {

	/**
     * @author yin.xu
     * @param result
     * @param servletResponse
     * @desc 将字符串输出到输出流里面。
     */
    public static void outPut(String result, HttpServletResponse servletResponse) {
        servletResponse.setContentType("text/html;charset=utf-8");
        PrintWriter writer = null;
        try {
            writer = servletResponse.getWriter();
            writer.print(result);
            writer.flush();
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}

