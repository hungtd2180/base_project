package com.base.base_project.core.configuration;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by hungtd
 * Date: 11/10/2024
 * Time: 2:59 CH
 * for all issues, contact me: hungtd2180@gmail.com
 */

@Component
@ConfigurationProperties(prefix = "logging")
public class IgnoreLogConfiguration {
    private Map<String, String> ignoreLog;
    public Map<String, String> getIgnoreLog() {
        return ignoreLog;
    }
    public void setIgnoreLog(Map<String, String> ignoreLog) {
        this.ignoreLog = ignoreLog;
    }
}
