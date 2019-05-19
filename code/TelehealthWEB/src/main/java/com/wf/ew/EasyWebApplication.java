package com.wf.ew;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.wf.jwtp.configuration.EnableJwtPermission;
import org.springframework.boot.web.servlet.support.*;

@EnableJwtPermission
@SpringBootApplication
public class EasyWebApplication extends SpringBootServletInitializer{

    public static void main(String[] args) {
        SpringApplication.run(EasyWebApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(EasyWebApplication.class);
    }

}
