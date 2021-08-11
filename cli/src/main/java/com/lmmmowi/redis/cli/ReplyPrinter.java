package com.lmmmowi.redis.cli;

import com.lmmmowi.redis.client.ClientProcessor;
import com.lmmmowi.redis.protocol.reply.RedisReply;
import com.lmmmowi.redis.protocol.reply.*;

import java.util.stream.Collectors;

public class ReplyPrinter implements ClientProcessor {

    @Override
    public void process(RedisReply redisReply) {
        String output = format(redisReply);
        System.out.println(output);
    }

    private String format(RedisReply redisReply) {
        if (redisReply instanceof StatusReply) {
            return ((StatusReply) redisReply).getContent();
        } else if (redisReply instanceof ErrorReply) {
            return ((ErrorReply) redisReply).getContent();
        } else if (redisReply instanceof IntegerReply) {
            return ((IntegerReply) redisReply).getValue() + "";
        } else if (redisReply instanceof FullBulkStringReply) {
            return ((FullBulkStringReply) redisReply).getContent();
        } else if (redisReply instanceof ArrayReply) {
            return ((ArrayReply) redisReply).getChildren()
                    .stream()
                    .map(this::format)
                    .collect(Collectors.joining("\n"));
        } else {
            return redisReply.toString();
        }
    }
}
