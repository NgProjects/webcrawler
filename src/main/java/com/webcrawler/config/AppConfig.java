package com.webcrawler.config;

import com.webcrawler.configkeyenum.IConfigKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@AnAppConfig
public class AppConfig implements IAppConfig {

    @Autowired
    private Environment env;
    @Override
    public Integer getConfigInt(IConfigKey key) {
        String value = getConfig(key);
        return Integer.valueOf(value);
    }

    @Override
    public String getConfig(IConfigKey key) {
        return env.getProperty(key.getKey(), key.getDefaultValue());
    }
}
