package com.webcrawler.config.interfaces;

import com.webcrawler.enums.ConfigKey;

public interface IAppConfig {
    Integer getConfigInt(ConfigKey key);
    String getConfig(ConfigKey key);
}
