package com.example.Hangman;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Service;

/**
 * The service class of the Hangman app
 * @author Yuvaraj
 *
 */
@Service
public class HangmanService {

	public HangmanService() {
	}

	/**
	 * This method is used to select a word randomly for the game from the list of available words
	 * @param wordList
	 * @param selectedSet
	 * @return
	 */
	public String selectRandomWord(ArrayList<byte[]> wordList, Set<Character> selectedSet){
		Random rand = new Random();
		int n = rand.nextInt(wordList.size());
		byte[] byteArr = wordList.get(n);
		char[] wordArr = new char[byteArr.length];
		int i = 0;
		int val = 0;
		for (byte b : byteArr) {
			val = (int) b;
			wordArr[i] = (char) val;
			selectedSet.add(new Character((char) val));
			i++;
		}
		return new String(wordArr);
	}

	/**
	 * This method is used to check whether the user entered character 
	 * is present in the word (decide whether its hit or miss)
	 * Also,this method updates the ouput character to be displayed 
	 * to the user if its a hit
	 * @param word
	 * @param letter
	 * @param outputArr
	 * @return
	 */
	public boolean isHit(String word,char letter,char[] outputArr){
		boolean hit=false;
		char[] wordArr = word.toCharArray();
		for (int i = 0; i < wordArr.length; i++) {
			if (wordArr[i] == letter) {
				outputArr[i * 2] = letter;
				hit=true;
			}
		}
		return hit;
	}

}
