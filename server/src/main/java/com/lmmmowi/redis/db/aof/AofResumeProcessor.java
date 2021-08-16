package com.lmmmowi.redis.db.aof;

import com.lmmmowi.redis.server.RedisServerRuntime;
import com.lmmmowi.redis.server.client.ClientHolder;
import com.lmmmowi.redis.server.client.ClientInfo;
import com.lmmmowi.redis.server.commandline.CommandLineProcessor;
import com.lmmmowi.redis.server.commandline.RedisCommandLine;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AofResumeProcessor {

    private File appendOnlyFile;

    void run() throws IOException {
        if (appendOnlyFile == null || !appendOnlyFile.exists()) {
            return;
        }

        ClientHolder clientHolder = RedisServerRuntime.get().getClientHolder();
        ClientInfo clientInfo = clientHolder.newClient("redis aof resume processor");
        clientInfo.setSystemClient(true);

        try {
            List<String[]> commands = this.parseCommands(appendOnlyFile);
            this.execute(commands);
        } finally {
            clientHolder.clear();
        }
    }

    private List<String[]> parseCommands(File appendFile) throws IOException {
        List<String[]> commands = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(appendFile))) {
            String s;
            while ((s = reader.readLine()) != null) {
                if (s.startsWith("*")) {
                    int n = Integer.parseInt(s.substring(1));
                    String[] parts = new String[n];
                    for (int i = 0; i < n; i++) {
                        // ignore
                        reader.readLine();
                        parts[i] = reader.readLine();
                    }

                    commands.add(parts);
                }
            }
            return commands;
        }
    }

    private void execute(List<String[]> commands) {
        for (String[] commandParts : commands) {
            RedisCommandLine redisCommandLine = new RedisCommandLine(commandParts);

            CommandLineProcessor commandLineProcessor = RedisServerRuntime.get().getCommandLineProcessor();
            commandLineProcessor.process(redisCommandLine);
        }
    }
}
