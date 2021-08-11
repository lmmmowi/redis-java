package com.lmmmowi.redis.server.parser;

import com.lmmmowi.redis.protocol.command.ClientGetNameCommand;
import com.lmmmowi.redis.protocol.command.ClientListCommand;
import com.lmmmowi.redis.protocol.command.ClientSetNameCommand;
import com.lmmmowi.redis.protocol.command.RedisCommand;
import com.lmmmowi.redis.server.exception.WrongNumberOfArgumentsException;
import com.lmmmowi.redis.server.RedisCommandLine;

public class ClientCommandParser implements RedisCommandParser {

    @Override
    public String getCommandKey() {
        return "client";
    }

    @Override
    public RedisCommand parse(RedisCommandLine redisCommandLine) {
        String[] parts = redisCommandLine.getParts();
        if (parts.length < 2 || parts[1] == null) {
            throw new WrongNumberOfArgumentsException(getCommandKey());
        }

        String subCommand = parts[1].toLowerCase();
        switch (subCommand) {
            case "setname":
                return this.parseClientSetNameCommand(subCommand, parts);
            case "getname":
                return new ClientGetNameCommand();
            case "list":
                return new ClientListCommand();
            case "kill":
            default:
                throw new WrongNumberOfArgumentsException(subCommand);
        }
    }

    private ClientSetNameCommand parseClientSetNameCommand(String subCommand, String[] parts) {
        if (parts.length != 3 || parts[2] == null) {
            throw new WrongNumberOfArgumentsException(subCommand);
        }

        return new ClientSetNameCommand(parts[2]);
    }
}
