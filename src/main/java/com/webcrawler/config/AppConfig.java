package com.webcrawler.config;

import com.webcrawler.enums.ConfigKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@AnAppConfig
public class AppConfig implements IAppConfig {

    @Autowired
    private Environment env;
    @Override
    public Integer getConfigInt(ConfigKey key) {
        String value = getConfig(key);
        return Integer.valueOf(value);
    }

    @Override
    public String getConfig(ConfigKey key) {
        return env.getProperty(key.getKey(), key.getDefaultValue());
    }
}
