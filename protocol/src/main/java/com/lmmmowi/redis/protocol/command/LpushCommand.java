package com.lmmmowi.redis.protocol.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LpushCommand extends AbstractCommand {

    private String key;
    private String[] values;
}
