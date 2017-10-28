package com.example.Hangman;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.example.Hangman.HangmanConstants.*;


/**
 * This  is the controller class of the Hangman App
 * @author Yuvaraj
 *
 */
@Controller
public class HangmanController {

	@Autowired
	ServletContext context;

	@Autowired
	HangmanService hangmanService;

	/**
	 * This method gets executed whenever a new game is started
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/newGame")
	public String newGame(HttpServletRequest request, Map<String, Object> model) {
		int wins = (int) request.getSession().getAttribute(WINS);
		int loses = (int) request.getSession().getAttribute(LOSES);
		//selecting a random word from the list of words
		selectDictionaryWord(request, model, wins, loses);
		return GAME;
	}

	/**
	 * This method gets executed when the user starts the application for the first time
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/home")
	public String loadGame(HttpServletRequest request, Map<String, Object> model) {
		//selecting a random word from the list of words
		selectDictionaryWord(request, model, 0, 0);
		return GAME;
	}

	@RequestMapping("/home/{id}")
	public String updateOutput(@PathVariable String id, HttpServletRequest request, Map<String, Object> model) {

		String output = (String) request.getSession().getAttribute(OUTPUT);
		String word = (String) request.getSession().getAttribute(WORD);
		Set<Character> charSet = (HashSet<Character>) request.getSession().getAttribute(CHAR_SET);
		Set<Character> selectedSet = (HashSet<Character>) request.getSession().getAttribute(SELECTED_SET);
		
		char letter = id.charAt(0);//character entered by the user
		charSet.remove(letter);//removing from the character set since the user has entered the character
		selectedSet.remove(letter);//removing from the selected set. This set contains the remaining list of characters
								   //user has to select to win the game
		
		request.getSession().setAttribute(CHAR_SET, charSet);

		char[] outputArr = output.toCharArray();
		
		//finding whether the user has guessed correctly or incorrectly(Hit or Miss)
		//and forming the output string to be displayed to the user
		boolean hit=hangmanService.isHit(word,letter,outputArr);
		output = new String(outputArr);

		if(hit){
			request.getSession().setAttribute(OUTPUT, output);
			//If the user has selected all the unique chars in the string he wins
			if (selectedSet.isEmpty()) {
				model.put(WON, true);
				int wins = (int) request.getSession().getAttribute(WINS);
				request.getSession().setAttribute(WINS, wins + 1);
				return GAME;
			}
		}else{
			int missCount = (int) request.getSession().getAttribute(MISS_COUNT) + 1;
			request.getSession().setAttribute(MISS_COUNT, missCount);
			int drawCount = (int) request.getSession().getAttribute(DRAW_COUNT) - 1;
			request.getSession().setAttribute(DRAW_COUNT, drawCount);
			//If the user guesses incorrectly he loses
			if (missCount >= 10) {
				model.put(LOST, true);
				int loses = (int) request.getSession().getAttribute(LOSES);
				request.getSession().setAttribute(LOSES, loses + 1);
				return GAME;
			}
		}
		request.getSession().setAttribute(OUTPUT, output);
		return GAME;
	}
	public void selectDictionaryWord(HttpServletRequest request, Map<String, Object> model, int wins, int loses) {

		ArrayList<byte[]> wordList = (ArrayList<byte[]>) context.getAttribute(WORD_LIST);
		Set<Character> selectedSet = new HashSet<Character>();
		Set<Character> charSet = new HashSet<Character>();
		
		//selecting a random word from the list of available words
		String word=hangmanService.selectRandomWord(wordList,selectedSet);

		request.getSession().setAttribute(WORD, word);
		request.getSession().setAttribute(MISS_COUNT, 0);
		request.getSession().setAttribute(DRAW_COUNT, 10);

		System.out.println("The word is " + word);

		//Initial Output string formation logic
		String output = null;
		StringBuffer sb = new StringBuffer();
		for (int j = 0; j < word.length(); j++) {
			sb.append(DASH);
			sb.append(SPACE);
		}
		output = sb.toString();
		request.getSession().setAttribute(OUTPUT, output);

		//adding all the characters to be displayed to the user
		for (char c = 'a'; c <= 'z'; c++)
			charSet.add(new Character(c));

		request.getSession().setAttribute(CHAR_SET, charSet);
		request.getSession().setAttribute(WINS, wins);
		request.getSession().setAttribute(LOSES, loses);
		request.getSession().setAttribute(SELECTED_SET, selectedSet);

	}
}
