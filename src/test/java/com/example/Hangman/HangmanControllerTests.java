package com.example.Hangman;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@WebMvcTest(HangmanController.class)
@ComponentScan(basePackages = {
"com.example.Hangman" }, basePackageClasses = HangmanService.class)
public class HangmanControllerTests {

	@Autowired
	HangmanService hangmanService;

	@Test
	public void testIsHit() throws Exception {

		String word="hello";
		char letter='l';
		char[] outArr=new char[word.length()*2];

		//testing postive scenario
		assertEquals(this.hangmanService.isHit(word,letter, outArr),true);

		letter='d';
		//testing negative scenario
		assertEquals(this.hangmanService.isHit(word,letter, outArr),false);

	}

	@Test
	public void testSelectRandomWord() throws Exception {

		//mock data set up starts here
		String word="hello";
		char ch[]=word.toCharArray();
		int asciiVal = 0;
		byte[] byteArr=new byte[ch.length];
		for (int i = 0; i < ch.length; i++) {
			asciiVal = ch[i];
			byteArr[i] = (byte) asciiVal;
		}
		ArrayList<byte[]> wordList = new ArrayList<byte[]>();
		wordList.add(byteArr);
		wordList.add(byteArr);
		Set<Character> selectedSet = new HashSet<Character>();
		Set<Character> testSet=new HashSet<Character>();
		for(char c:ch){
			testSet.add(c);
		}
		//mock data set up ends here

		//testing whether the word is selected from the list of words
		assertThat(this.hangmanService.selectRandomWord(wordList, selectedSet))
		.isEqualTo(("hello"));

		//testing whether the selected set contains all the characters of the word
		assertThat(testSet).containsAll(selectedSet);
	}

}