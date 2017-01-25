package com.company;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        try {
            File normalDir = new File("C:\\Users\\Eric\\IdeaProjects\\InteractiveLearner\\src\\corpus-mails\\corpus\\training\\normal");
            File[] normalFiles = normalDir.listFiles();
            if (normalFiles != null) {
                for (File child : normalFiles) {
                    String normalstr = readFile(child.getPath(), StandardCharsets.UTF_8);

                }
            } else {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFile(String path, Charset encoding) throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public static String[] tokenizer(String file) {

        return null;
    }


}
