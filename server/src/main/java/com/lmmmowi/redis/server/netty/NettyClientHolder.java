package com.lmmmowi.redis.server.netty;

import com.lmmmowi.redis.server.ClientInfo;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

public class NettyClientHolder {

    private static final NettyClientHolder INSTANCE = new NettyClientHolder();

    private Map<Channel, ClientInfo> clients = new HashMap<>();
    private static final ThreadLocal<Channel> clientChannel = new ThreadLocal<>();

    private NettyClientHolder() {
    }

    public static NettyClientHolder getInstance() {
        return INSTANCE;
    }

    synchronized void put(Channel channel, ClientInfo clientInfo) {
        clients.put(channel, clientInfo);
    }

    public ClientInfo get(Channel channel) {
        return clients.get(channel);
    }

    Channel getClientChannel() {
        return clientChannel.get();
    }

    void setClientChannel(Channel channel) {
        clientChannel.set(channel);
    }

    void reset() {
        clientChannel.remove();
    }
}
