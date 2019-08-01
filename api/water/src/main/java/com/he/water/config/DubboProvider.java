package com.he.water.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by Administrator on 2018/1/20.
 */

@Configuration
@ImportResource({"classpath:appcontext-consumer.xml"})
public class DubboProvider {
}
