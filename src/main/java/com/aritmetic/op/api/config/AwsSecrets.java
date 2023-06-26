package com.aritmetic.op.api.config;

import lombok.Data;

@Data
public class AwsSecrets {
    private String username;
    private String password;
    private String host;
    private String engine;
    private String port;
    private String dbInstanceIdentifier;

}
