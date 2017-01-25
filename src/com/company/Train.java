package com.company;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Train {

    public static Map<String, Integer> normalMap = new HashMap<>();
    public static Map<String, Integer> spamMap = new HashMap<>();

    public static void main(String[] args) {
        try {
            File normalDir = new File("C:\\Users\\Eric\\IdeaProjects\\InteractiveLearner\\src\\corpus-mails\\corpus\\training\\normal");
            File[] normalFiles = normalDir.listFiles();
            wordCount(normalFiles, normalMap);
            File spamDir = new File("C:\\Users\\Eric\\IdeaProjects\\InteractiveLearner\\src\\corpus-mails\\corpus\\training\\spam");
            File[] spamFiles = spamDir.listFiles();
            wordCount(spamFiles,spamMap);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public static void wordCount(File[] files, Map<String,Integer> countlst) throws IOException {
        if (files != null) {
            for (File child : files) {
                String string = readFile(child.getPath(), StandardCharsets.UTF_8);
                String[] words = tokenizer(string);
                List<String> exists = new ArrayList<>();
                for (String word : words) {
                    if (!countlst.containsKey(word)) {
                        countlst.put(word, 1);
                        exists.add(word);
                    } else if (!exists.contains(word)) {
                        int initValue = countlst.get(word).intValue();
                        countlst.put(word, initValue + 1);
                    }
                }
            }
        }
        else {
            throw new NullPointerException();
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
