package com.ctoutweb.lalamiam.infra.config;

import com.ctoutweb.lalamiam.core.annotation.CoreService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages =  "com.ctoutweb.lalamiam.core",
        includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {CoreService.class})}
)
public class CoreConfiguration {
}
