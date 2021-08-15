package com.lmmmowi.redis.db.aof;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AofResume {

    private AofResumeExecutor aofResumeExecutor;

    public AofResume(AofResumeExecutor aofResumeExecutor) {
        this.aofResumeExecutor = aofResumeExecutor;
    }

    public void run(File appendFile) throws IOException {
        if (!appendFile.exists()) {
            return;
        }

        List<String[]> commands = this.parseCommands(appendFile);
        aofResumeExecutor.execute(commands);
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
}
