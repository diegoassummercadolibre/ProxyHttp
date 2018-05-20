package com.company;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class LogHelper{

    private static final String FILE_NAME = "ProxyHttp.log";
    private static final String NEW_LINE = System.getProperty("line.separator");
    private static final String SEPARATOR = "----------------------------------------------------------";
    private static final File _file = new File(FILE_NAME);

    public static void writeLog(String log) throws IOException {
        StringBuilder logAux = new StringBuilder();

        if(!_file.exists())
            _file.createNewFile();

        logAux.append(getLogHead());
        logAux.append(log);

        Files.write(Paths.get(_file.getName()), logAux.toString().getBytes(), StandardOpenOption.APPEND);
        System.out.println(logAux.toString());
    }

    private static String getLogHead(){
        return SEPARATOR + NEW_LINE + "REQUEST" + NEW_LINE + SEPARATOR + NEW_LINE;
    }
}