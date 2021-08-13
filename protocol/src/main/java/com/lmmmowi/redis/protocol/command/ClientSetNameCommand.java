package com.lmmmowi.redis.protocol.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClientSetNameCommand extends AbstractCommand {

    private String name;
}
