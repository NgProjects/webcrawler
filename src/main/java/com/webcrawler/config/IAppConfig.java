package com.webcrawler.config;

import com.webcrawler.enums.ConfigKey;

public interface IAppConfig {
    Integer getConfigInt(ConfigKey key);
    String getConfig(ConfigKey key);
}
