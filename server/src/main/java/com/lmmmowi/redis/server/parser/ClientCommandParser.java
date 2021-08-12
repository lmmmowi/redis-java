package com.lmmmowi.redis.server.parser;

import com.lmmmowi.redis.protocol.command.ClientGetNameCommand;
import com.lmmmowi.redis.protocol.command.ClientListCommand;
import com.lmmmowi.redis.protocol.command.ClientSetNameCommand;
import com.lmmmowi.redis.protocol.command.RedisCommand;
import com.lmmmowi.redis.server.RedisCommandLine;
import com.lmmmowi.redis.server.exception.CommandParseException;
import com.lmmmowi.redis.server.exception.WrongNumberOfArgumentsException;

class ClientCommandParser implements RedisCommandParser {

    @Override
    public String getCommandKey() {
        return "client";
    }

    @Override
    public RedisCommand parse(RedisCommandLine redisCommandLine) throws CommandParseException {
        String[] parts = redisCommandLine.getParts();
        if (parts.length < 2 || parts[1] == null) {
            throw new WrongNumberOfArgumentsException(getCommandKey());
        }

        String subCommand = parts[1].toLowerCase();
        switch (subCommand) {
            case "setname":
                if (parts.length != 3 || parts[2] == null) {
                    throw new WrongNumberOfArgumentsException(subCommand);
                } else {
                    return new ClientSetNameCommand(parts[2]);
                }
            case "getname":
                return new ClientGetNameCommand();
            case "list":
                return new ClientListCommand();
            case "kill":
            default:
                throw new WrongNumberOfArgumentsException(subCommand);
        }
    }
}
