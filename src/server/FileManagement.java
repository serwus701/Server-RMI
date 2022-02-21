package server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileManagement {

    File myLogs;
    PrintWriter writing;

    public FileManagement() throws IOException {

        File tempFile = new File("ServerLogs.txt");

        if (!tempFile.exists())
            Files.createFile(Paths.get("ServerLogs.txt"));

        myLogs = new File("ServerLogs.txt");

        writing = new PrintWriter(new FileOutputStream(myLogs, true), true);
    }

    public void write(String log) {

        writing.println(log);
    }

    public void close() {
        writing.close();
    }
}