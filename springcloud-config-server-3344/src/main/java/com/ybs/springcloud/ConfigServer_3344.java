package com.ybs.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * ConfigServer_3344
 *
 * @author Paulson
 * @date 2020/3/8 1:26
 */

@SpringBootApplication
@EnableConfigServer
public class ConfigServer_3344 {
    public static void main(String[] args) {
        SpringApplication.run(ConfigServer_3344.class, args);
    }
}
