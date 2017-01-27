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
            System.out.println(isSpam(words) ? "The text is classified as spam\n" : "The text is classified as normal\n");
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
            updateKnowledge(answer, words);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isSpam(String[] words) {
        int spamCount = 0;
        int normalCount = 0;
        for (String word : words) {
            if (Train.spamMap.containsKey(word)) {
                spamCount = chi(word) ? Train.spamMap.get(word).intValue() + spamCount : spamCount;
            }
            if (Train.normalMap.containsKey(word)) {
                normalCount = chi(word) ? Train.normalMap.get(word).intValue() + normalCount : normalCount;
            }
        }
        return spamCount > normalCount ? true : false;
    }

    public static void updateKnowledge(String answer, String[] words) {
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
    }

    public static boolean chi(String wordToTest) {
        // Set all expected values
        int w = 0;
        if (Train.spamMap.containsKey(wordToTest)) {
            w += Train.spamMap.get(wordToTest).intValue();
        }
        if (Train.normalMap.containsKey(wordToTest)) {
            w += Train.normalMap.get(wordToTest).intValue();
        }
        int c = Train.amountSpam;
        int n = Train.amountNormal + Train.amountSpam;
        int e1 = w*c/n;
        c = Train.amountNormal;
        int e2 = w*c/n;
        w = (Train.amountNormal + Train.amountSpam) - w;
        int e4 = w*c/n;
        c = Train.amountSpam;
        int e3 = w*c/n;
        int[] expected = {e1, e2, e3, e4};


        // Set all observed values
        int a = 0;
        int b = 0;
        if (Train.spamMap.containsKey(wordToTest)) {
            a = Train.spamMap.get(wordToTest);
        }
        if (Train.normalMap.containsKey(wordToTest)) {
            b = Train.normalMap.get(wordToTest);
        }
        int d = Train.amountSpam - a;
        int e = Train.amountNormal - b;
        int[] observed = {a, b, d, e};

        // Chitest for df = 1 and alfa = 0.05
        int chi = (int) (Math.pow(a-e1, 2)/e1 + Math.pow(b-e2, 2)/e2 + Math.pow(d-e3, 2)/e3 + Math.pow(e-e4, 2)/e4);
        if (chi >= 3.84){
            return true;
        }
        else {
            return false;
        }
    }

}
