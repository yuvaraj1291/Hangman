package com.example.Hangman;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import com.github.ulisesbocchio.jar.resources.JarResourceLoader;

/**
 * This class is used to start the application
 * @author Yuvaraj
 *
 */
@SpringBootApplication
public class HangmanApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		new SpringApplicationBuilder()
		.sources(new Class[] { HangmanApplication.class, Initializer.class})
		.resourceLoader(new JarResourceLoader())
		.run(args);
	}
}
