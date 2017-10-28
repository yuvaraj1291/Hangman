package com.example.Hangman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * This class loads all the words from the file to the servlet configuration
 * 
 * @author Yuvaraj
 *
 */
@Configuration
public class Initializer implements ServletContextInitializer {

	@Autowired
	ResourceLoader resourceLoader;

	/* (non-Javadoc)
	 * 
	 * This method is invoked during the start of the application
	 * @see org.springframework.boot.web.servlet.ServletContextInitializer#onStartup(javax.servlet.ServletContext)
	 */
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		ArrayList<byte[]> wordList = loadDictionaryWords();
		servletContext.setAttribute("wordList", wordList);
	}

	/**
	 * This method is used to read the input file & load the words into application in bytes format representing 
	 * character of the word in ascii code
	 * @return wordList
	 */
	public ArrayList<byte[]> loadDictionaryWords() {

		ArrayList<byte[]> wordList = new ArrayList<byte[]>();

		Resource resource = resourceLoader.getResource("classpath:words.txt");
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
			String currentLine;
			char[] ch = null;
			//reading the input file & converting the characters of words into ascii code 
			while ((currentLine = reader.readLine()) != null) {
				currentLine = currentLine.toLowerCase();
				ch = currentLine.toCharArray();
				byte[] byteArr = new byte[ch.length];
				int asciiVal = 0;
				for (int i = 0; i < ch.length; i++) {
					asciiVal = ch[i];
					byteArr[i] = (byte) asciiVal;
				}
				wordList.add(byteArr);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return wordList;
	}

}