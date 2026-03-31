package com.suzao.net.speed.netspeed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @name NetSpeedApplication
 * @author mc
 * @date 2022/4/9 22:27
 * @version 1.0
 **/
@SpringBootApplication
public class NetSpeedApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(NetSpeedApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(NetSpeedApplication.class);
    }
}
