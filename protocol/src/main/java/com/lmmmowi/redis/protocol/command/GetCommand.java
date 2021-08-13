package com.lmmmowi.redis.protocol.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetCommand extends AbstractCommand {

    private String key;
}
