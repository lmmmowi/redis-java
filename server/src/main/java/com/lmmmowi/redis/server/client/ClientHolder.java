package com.lmmmowi.redis.server.client;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientHolder {

    private final ThreadLocal<ClientInfo> currentClientInfo = new ThreadLocal<>();

    public static ClientHolder create() {
        return new ClientHolder();
    }

    public ClientInfo newClient(String name) {
        ClientInfo clientInfo = new ClientInfo(name);
        currentClientInfo.set(clientInfo);
        return clientInfo;
    }

    public ClientInfo getClientInfo() {
        return currentClientInfo.get();
    }

    public void clear() {
        currentClientInfo.remove();
    }
}
