package com.company;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Created by Eric on 25-Jan-17.
 */
public class Test {

    public static void main(String[] testFilepath) {
        try {
            String string = Train.readFile(testFilepath[0]);
            String[] words = Train.tokenizer(string);
            int spamCount = 0;
            int normalCount = 0;
            for (String word : words) {
                spamCount = Train.spamMap.containsKey(word) ? Train.spamMap.get(word).intValue() + spamCount : spamCount;
                normalCount = Train.normalMap.containsKey(word) ? Train.normalMap.get(word).intValue() + normalCount : normalCount;
            }
            System.out.printf(spamCount > normalCount ? "spam" : "normal");
            Scanner in = new Scanner(System.in);
            System.out.println("Please enter the true class of the text by typing 'spam' or 'normal'");
            boolean gotAnswer = false;
            while (!gotAnswer) {
                if (in.hasNextLine()) {

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
