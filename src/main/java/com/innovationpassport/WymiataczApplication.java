package com.innovationpassport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * User: trojnaradam@gmail.com
 * Date: 03.08.16
 * Time: 20:08
 */

@EnableAutoConfiguration
@ComponentScan
public class WymiataczApplication extends SpringBootServletInitializer {
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
    return builder.sources(WymiataczApplication.class);
  }

  public static void main(String[] args){
    SpringApplication.run(WymiataczApplication.class, args);
  }
}
