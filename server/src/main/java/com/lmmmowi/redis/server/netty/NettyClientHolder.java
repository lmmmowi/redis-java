package com.lmmmowi.redis.server.netty;

import com.lmmmowi.redis.server.ClientInfo;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

public class NettyClientHolder {

    private static final NettyClientHolder INSTANCE = new NettyClientHolder();

    private Map<Channel, ClientInfo> clients = new HashMap<>();

    private NettyClientHolder() {
    }

    public static NettyClientHolder getInstance() {
        return INSTANCE;
    }

    synchronized void put(Channel channel, ClientInfo clientInfo) {
        clients.put(channel, clientInfo);
    }
}
