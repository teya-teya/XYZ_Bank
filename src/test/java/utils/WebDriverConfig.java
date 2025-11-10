package utils;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:base.properties")

public interface WebDriverConfig extends Config {
    @Key("webdriver.baseUrl")
    String getBaseUrl();
}