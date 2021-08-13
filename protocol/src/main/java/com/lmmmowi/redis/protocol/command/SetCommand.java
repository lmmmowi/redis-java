package com.lmmmowi.redis.protocol.command;

import com.lmmmowi.redis.protocol.annotation.AOF;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AOF
@Getter
@AllArgsConstructor
public class SetCommand extends AbstractCommand {

    private String key;
    private String value;
}
