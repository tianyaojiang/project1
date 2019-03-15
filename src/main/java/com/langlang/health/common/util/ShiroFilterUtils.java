package com.langlang.health.common.util;

import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by tyj on 2018/08/15.
 */
public class ShiroFilterUtils {
	private static final Logger logger = LoggerFactory
			.getLogger(ShiroFilterUtils.class);
	private final static ObjectMapper objectMapper = new ObjectMapper();
    /**
     * 
     * @描述：判断请求是否是ajax
     * @param request
     * @return
     */
    public static boolean isAjax(ServletRequest request){
    	String header = ((HttpServletRequest) request).getHeader("X-Requested-With");
    	if("XMLHttpRequest".equalsIgnoreCase(header)){
    		logger.debug("shiro工具类【ShiroFilterUtils.isAjax】当前请求,为Ajax请求");
    		return Boolean.TRUE;
    	}
    	logger.debug("shiro工具类【ShiroFilterUtils.isAjax】当前请求,非Ajax请求");
    	return Boolean.FALSE;
    }




}
