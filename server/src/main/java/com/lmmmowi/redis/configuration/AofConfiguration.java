package com.lmmmowi.redis.configuration;

import lombok.Data;

@Data
public class AofConfiguration {

    private Boolean enabled;
    private String appendOnlyFilePath;
}
