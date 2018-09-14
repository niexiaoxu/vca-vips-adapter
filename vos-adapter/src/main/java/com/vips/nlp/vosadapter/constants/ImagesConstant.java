package com.vips.nlp.vosadapter.constants;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("image-config.properties")
@ConfigurationProperties(prefix = "image-config")
public class ImagesConstant {
    private String storageUrl;

    public String getStorageUrl() {
        return storageUrl;
    }

    public void setStorageUrl(String storageUrl) {
        this.storageUrl = storageUrl;
    }
}
