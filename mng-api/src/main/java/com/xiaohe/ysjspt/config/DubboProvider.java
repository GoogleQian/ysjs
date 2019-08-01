package com.xiaohe.ysjspt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @author
 * Administrator
 */
@Configuration
@ImportResource({ "classpath:appcontext-provider.xml", "classpath:appcontext-consumer.xml" })
public class DubboProvider {
}
