package com.company;

import java.io.IOException;
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
                spamCount = Train.spamMap.containsKey(word) && chi(word) ? Train.spamMap.get(word).intValue() + spamCount : spamCount;
                normalCount = Train.normalMap.containsKey(word) && chi(word) ? Train.normalMap.get(word).intValue() + normalCount : normalCount;
            }
            System.out.printf(spamCount > normalCount ? "spam" : "normal");
            Scanner in = new Scanner(System.in);
            System.out.println("Please enter the true class of the text by typing 'spam' or 'normal'");
            boolean gotAnswer = false;
            String answer = null;
            while (!gotAnswer) {
                if (in.hasNextLine()) {
                    answer = in.nextLine();
                    if (answer.equals("spam") || answer.equals("normal")) {
                        gotAnswer = true;
                    }
                }
            }
            for (String word : words) {
                if (answer.equals("spam") && Train.spamMap.containsKey(word)) {
                    Train.spamMap.put(word, Train.spamMap.get(word) + 1);
                }
                else if (answer.equals("normal") && Train.normalMap.containsKey(word)) {
                    Train.normalMap.put(word, Train.normalMap.get(word) +1);
                }
            }
            if (answer.equals("spam")) {
                Train.amountSpam++;
            }
            else {
                Train.amountNormal++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean chi(String wordToTest) {
        // Set all expected values
        int w = Train.spamMap.get(wordToTest)+Train.normalMap.get(wordToTest);
        int c = Train.amountSpam;
        int n = Train.amountNormal + Train.amountSpam;
        int e1 = w*c/n;
        c = Train.amountNormal;
        int e2 = w*c/n;
        w = (Train.amountNormal + Train.amountSpam) - w;
        int e4 = w*c/n;
        c = Train.amountSpam;
        int e3 = w*c/n;
        long[] expected = {e1, e2, e3, e4};

        // Set all observed values
        int a = Train.spamMap.get(wordToTest);
        int b = Train.normalMap.get(wordToTest);
        int d = Train.amountSpam - a;
        int e = Train.amountNormal - b;
        long[] observed = {a, b, d, e};

        // Chitest
        //return chiSquareTestTest.(expected, observed, (double) 5);
        return false;
    }

}
