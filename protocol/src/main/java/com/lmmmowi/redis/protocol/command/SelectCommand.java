package com.lmmmowi.redis.protocol.command;

import com.lmmmowi.redis.protocol.CommandSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SelectCommand extends AbstractCommand {

    private Integer dbIndex;

    @Override
    public String serialize() {
        String result = super.serialize();
        if (result != null) {
            return result;
        } else {
            return CommandSerializer.serialize(new String[]{"SELECT", String.valueOf(dbIndex)});
        }
    }
}
