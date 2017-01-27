package com.company;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Train {

	public static Map<String, Integer> normalMap = new HashMap<>();
	public static Map<String, Integer> spamMap = new HashMap<>();
	public static int amountSpam;
	public static int amountNormal;
	public static int correct = 0;
	public static int incorrect = 0;
	public static List<String> vocabulary = new ArrayList<>();

	public static void main(String[] args) {
		try {
			File normalDir = new File("C:\\Users\\Eric\\IdeaProjects\\InteractiveLearner\\src\\corpus-mails\\corpus\\training\\normal");
			File[] normalFiles = normalDir.listFiles();
			amountNormal = normalFiles.length;
			wordCount(normalFiles, normalMap);
			File spamDir = new File("C:\\Users\\Eric\\IdeaProjects\\InteractiveLearner\\src\\corpus-mails\\corpus\\training\\spam");
			File[] spamFiles = spamDir.listFiles();
			amountSpam = spamFiles.length;
			wordCount(spamFiles,spamMap);
			for (Map.Entry<String,Integer> e : normalMap.entrySet()) {
				if (e.getValue() < 5) {
					normalMap.remove(e);
				}
			}
			for (Map.Entry<String,Integer> e : spamMap.entrySet()) {
				if (e.getValue() < 5) {
					spamMap.remove(e);
				}
			}
			System.out.println("Training done");
//			System.out.println("Begin testing...");
//
//			File normalTestDir = new File("C:\\Users\\Eric\\IdeaProjects\\InteractiveLearner\\src\\corpus-mails\\corpus\\test\\normal");
//			File[] normalTestFiles = normalTestDir.listFiles();
//			File spamTestDir = new File("C:\\Users\\Eric\\IdeaProjects\\InteractiveLearner\\src\\corpus-mails\\corpus\\test\\spam");
//			File[] spamTestFiles = spamTestDir.listFiles();
//			testFiles(normalTestFiles, false);
//			testFiles(spamTestFiles, true);
//			double accuracy = ((double)correct)/((double)correct+(double)incorrect);
//			System.out.println("Accuracy: " + accuracy);
//			System.out.println("Vocabulary size: " + vocabulary.size());
//			System.out.println("Baseline: " + ((double)normalTestFiles.length)/(double)(normalTestFiles.length+spamTestFiles.length));
		} catch (IOException | NullPointerException e) {
			e.printStackTrace();
		}
	}

	public static void testFiles(File[] testFiles, boolean isSpam) throws IOException {
		for (File file : testFiles) {
			boolean suggestSpam = Test.isSpam(tokenizer(readFile(file.getPath())));
			if (suggestSpam == isSpam) {
				correct++;
			}
			else {
				incorrect++;
			}
			if (isSpam) {
				Test.updateKnowledge("spam", tokenizer(readFile(file.getPath())));
			}
			else {
				Test.updateKnowledge("normal", tokenizer(readFile(file.getPath())));
			}
		}
	}

	public static void wordCount(File[] files, Map<String,Integer> countlst) throws IOException {
		if (files != null) {
			for (File child : files) {
				String string = readFile(child.getPath());
				String[] words = tokenizer(string);
				List<String> exists = new ArrayList<>();
				for (String word : words) {
					if (!countlst.containsKey(word)) {
						countlst.put(word, 1);
						vocabulary.add(word);
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

	public static String readFile(String path) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, StandardCharsets.UTF_8);
	}

	public static String[] tokenizer(String file) {
		//Replace all capital letters with lowercase letters
		String result = file.toLowerCase();

		//Delete all stopwords
		List<String> stopwords = Arrays.asList("subject:", "a", "above", "about", "after", "again", "against", "all", "am", "an", "and", "any", "are", "aren't", "as", "at", "be", "because", "been", "before", "being", "below", "between", "both", "but", "by", "can't", "cannot", "could", "couldn't", "did", "didn't", "do", "does", "doesn't", "doing", "don't", "down", "during", "each", "few", "for", "from", "further", "had", "hadn't", "has", "has", "hasn't", "have", "haven't", "having", "he", "he'd", "he'll", "he's", "her", "here", "here's", "hers", "herself", "him", "himself", "his", "how", "how's", "i", "i'd", "i'll", "i'm", "i've", "if", "in", "intro", "is", "isn't", "it", "it's", "its", "itself", "let's", "me", "more", "most", "mustn't", "my", "myself", "no", "nor", "not", "of", "off", "on", "once", "only", "or", "other", "ought", "our", "ours", "ourselves", "out", "over","own", "same", "shan't", "she", "she'd", "she'll", "she's", "should", "shouldn't", "so", "some", "such", "than", "that", "that's", "the", "their", "theirs", "them", "themselves", "then", "there", "there's", "these", "they", "they'd", "they'll", "they're", "they've", "this", "those", "through", "to", "too", "under", "until", "up", "very", "was", "wasn't", "we", "we'd", "we'll", "we're", "we've", "were", "weren't", "what",  "what's", "when", "when's", "where", "where's", "which", "while", "who", "who's", "whom", "why", "why's", "with", "won't", "would", "wouldn't", "you", "you'd", "you'll",  "you're", "you've", "your", "yours", "yourself", "yourselves");
		List<String> words = new LinkedList<>(Arrays.asList(result.split(" ")));
//		System.out.println("BEFORE DELETING STOPWORDS: " + words.toString());
		words.removeAll(stopwords);
//		System.out.println("AFTER DELETING STOPWORDS: " + words.toString());

		// Delete all special characters
		int a = Character.getNumericValue("a".charAt(0));
		int z = Character.getNumericValue("z".charAt(0));
		for (ListIterator<String> iterator = words.listIterator() ; iterator.hasNext();) {
            String word = iterator.next();
            if (!(a<=Character.getNumericValue(word.charAt(0))&&z>=Character.getNumericValue(word.charAt(0)))) {
                iterator.remove();
            }
        }
//        System.out.println("AFTER DELETING SPECIAL SYMBOLS: " + words.toString());
		return words.toArray(new String[words.size()]);
	}


}
