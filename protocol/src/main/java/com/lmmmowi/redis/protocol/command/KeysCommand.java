package com.lmmmowi.redis.protocol.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KeysCommand extends AbstractCommand {

    private String pattern;
}
