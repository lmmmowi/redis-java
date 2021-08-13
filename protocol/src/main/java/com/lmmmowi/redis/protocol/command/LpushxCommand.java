package com.lmmmowi.redis.protocol.command;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LpushxCommand extends AbstractCommand {

    private String key;
    private String[] values;
}
