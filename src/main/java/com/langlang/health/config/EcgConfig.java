package com.langlang.health.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

//import com.langlang.DrawEcg;

@Configuration
@ComponentScan(basePackages = "com.langlang")
public class EcgConfig {

	public EcgConfig() {
        System.out.println("EcgConfig容器启动初始化。。。");
    }
	
//	private static final String ACCESS_ID = "LTAIOZRF2VpwzApi";
//	private static final String ACCESS_KEY = "oMcdYQEnEVI9X5QEKaWIb3SIa3kdMK";
//	private static final String OSS_ENDPOINT = "oss-cn-shanghai.aliyuncs.com";
//	private static final String BUCKET = "jj-production";
	
	
//	@Bean
//	public DrawEcg drawEcg(){
////		return new DrawEcg(new Aliyun(ACCESS_ID, ACCESS_KEY, OSS_ENDPOINT, BUCKET));
//		try {
//			return new DrawEcg(new ClassPathResource("lfs.properties").getInputStream());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	
}
