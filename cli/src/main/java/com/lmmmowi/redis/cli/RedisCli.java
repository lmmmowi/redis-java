package com.lmmmowi.redis.cli;

import com.lmmmowi.redis.client.ClientConfiguration;
import com.lmmmowi.redis.client.DefaultRedisClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RedisCli {

    private static final String QUIT_COMMAND = "quit";

    private DefaultRedisClient client;

    public static void main(String[] args) throws Exception {
        new RedisCli().run();
    }

    public void run() throws IOException {
        ClientConfiguration configuration = new ClientConfiguration();
        configuration.setHost("localhost");
        configuration.setPort(6379);

        this.client = new DefaultRedisClient();
        client.setConfiguration(configuration);
        client.startup();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String command = reader.readLine();
            if (QUIT_COMMAND.equals(command)) {
                this.quit();
                break;
            } else {
                client.send(command);
            }
        }
    }

    public void quit() {
        client.shutdown();
    }
}
