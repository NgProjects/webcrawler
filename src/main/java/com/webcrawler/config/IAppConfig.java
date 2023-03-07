package com.webcrawler.config;

import com.webcrawler.configkeyenum.IConfigKey;

public interface IAppConfig {
    Integer getConfigInt(IConfigKey key);
    String getConfig(IConfigKey key);
}
