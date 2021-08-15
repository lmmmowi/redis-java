package com.lmmmowi.redis.server;

public class ClientHolder {

    private static final ClientHolder INSTANCE = new ClientHolder();

    private final ThreadLocal<ClientInfo> currentClientInfo = new ThreadLocal<>();

    private ClientHolder() {
    }

    public static ClientHolder getInstance() {
        return INSTANCE;
    }

    public ClientInfo init(String name) {
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
