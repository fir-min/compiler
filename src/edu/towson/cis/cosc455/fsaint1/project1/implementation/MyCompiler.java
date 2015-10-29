package edu.towson.cis.cosc455.fsaint1.project1.implementation;

import java.io.*;

// lexical checks only for valid tokens

public class MyCompiler {
	public static String currentLine = "";
	public static int count = 0;
	public static int currentCharNum = 0;
	public static int lineNum = 1;
	public static String currentToken = "";
	public static boolean error = false;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	   BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("textfile.txt"));
			try {
				while ((currentLine = br.readLine()) != null && !error)  {
					   
					int len = currentLine.length();
					   
					   for(int i = 0; i < len; i++) {
						   currentCharNum = i;
						   
						   char c = currentLine.charAt(i);
						   // if it is a special character
						   
						   if(Symbols.search(c)) {
							   if(c == '^' || c == '<' || c == '>' || c == '@') {
								   continue;
							   }
							   if(c == '[' || c == ']' || c == '(' || c == ')') {
								   continue;
							   }
							   if(c == '%' || c == '=' || c == '+' || c == ';') {
								   continue;
							   }
							   if(c == '~' || c == '{' || c == '}' || c == '*') {
								   continue;
							   }
							   
							   if(c == '#') {
								   hashtag(currentLine);
							   }
							   if(c == '$') {
								   hashtag(currentLine);
							   }
						   }
						   // if it's regular text
						   else {
							   continue;
						   }
					   }
					   
					   
					   lineNum++;
				   }
			} catch (IOException e) {
				System.out.println("Invalid file");
			}
		} catch (FileNotFoundException e1) {
			System.out.println("Invalid file, please try again");
		}
	}
	
	/**
	 * @param String line
	 * @return void
	 * check to see if the Token beginning with # is valid
	 */
	public static void hashtag(String line) {
		
		// very first token
		if(count == 0) {
			for(int i = currentCharNum; i < Tokens.DOCB.length(); i++) {
				currentToken = currentToken + line.charAt(i);
			}
			if(Tokens.search(currentToken)) {
				System.out.println("lexical error at " + lineNum + " character number " + currentCharNum);
				error = true;
			}
			else {
				currentToken = "";
			}
		}
		
		// last token
		else {
			for(int i = currentCharNum; i < Tokens.DOCE.length(); i++) {
				currentToken = currentToken + line.charAt(i);
			}
			if(Tokens.search(currentToken)) {
				System.out.println("lexical error at " + lineNum + " character number " + currentCharNum);
				error = true;
			}
			else {
				currentToken = "";
			}
		}
	}
	
	/**
	 * @param String line
	 * @return void
	 * check to see if the Token beginning with $ is valid
	 */
	
	public static void dollar(String line) {
		for(int i = currentCharNum; i < Tokens.VARU.length(); i++) {
			currentToken = currentToken + line.charAt(i);
		}
		if(Tokens.search(currentToken)) {
			System.out.println("lexical error at " + lineNum + " character number " + currentCharNum);
			error = true;
		}
		else {
			currentToken = "";
		}
		
	}
	

	
	
	

}
