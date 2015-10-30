package edu.towson.cis.cosc455.fsaint1.project1.implementation;

import java.io.*;

import edu.towson.cis.cosc455.fsaint1.project1.interfaces.LexicalAnalyzer;

public class MyLexicalAnalyzer  implements LexicalAnalyzer  {
	public String currentLine = "";
	
	public int lineNum = 1;
	public String currentToken = "";
	public boolean error = false; // if there is an error
	public boolean lineState = false; // if the line still has characters
	public boolean fileState = true; // if the still has lines
	public BufferedReader br;
	
	
	public char nextCharacter = 'a';

    /** The current position. */
	public int currentPosition = 0;
	
	public MyLexicalAnalyzer (String filename) {
		try {
			br = new BufferedReader(new FileReader(filename));
		
		} catch (FileNotFoundException e) {
			System.out.println("Wrong file name");
			exit();
		}
	}
	
	
	public void exit() {
		System.exit(0);
	}
	
	
	@Override
	public void getNextToken() {
	    if(!lineState) {
	    	getNextLine();
	    }
	    
	    if(isSpace(currentLine.charAt(currentPosition))) {
			skipBlank();
		}
		
		while(!isSpace(currentLine.charAt(currentPosition))) {
			getCharacter();
			addCharacter();
		}
		
		if(!lookupToken()) {
			System.out.println("Lexical error at " + lineNum);
			exit();
		}
		
		

	}

	@Override
	public void getCharacter() {
		if (currentPosition < currentLine.length()) {
			nextCharacter = currentLine.charAt(currentPosition);
			currentPosition ++;
        } else {
        	nextCharacter = '\n';
        	lineState = false;
        }

	}
	
	
	// skips all the blanks characters in a line makes it easier to get the next token;
	public void skipBlank() {
		while(isSpace(currentLine.charAt(currentPosition))) {
			currentPosition++;
			
		}
	}

	@Override
	public void addCharacter() {
		currentToken += nextCharacter;

	}

	@Override
	public boolean isSpace(char c) {
		return c == ' ';
	}

	@Override
	public boolean lookupToken() {
		char c = currentToken.charAt(0);
		boolean r = true;
		if(Symbols.search(c)) {
			   if(c == Symbols.HASH) {
				   r = mkdDown(currentLine);
			   }
			   if(c == Symbols.HASH) {
				   r = variable(currentLine);
			   }
		}
		return r;
	}
	
	public void getNextLine() {
		
		if(lineState) {
			try {
				currentLine =  br.readLine();
				currentPosition = 0;
				lineNum++;
				lineState = true;
			} catch (IOException e) {
				fileState = false;
				
			}
		}
		
		
		
	}
	
    public boolean mkdDown(String line) {
		boolean b = true;
		// very first token
		if(lineNum == 1) {
			
			if(!Tokens.search(currentToken)) {
				System.out.println("lexical error at " + lineNum + " character number " + currentPosition);
				error = true;
				exit();
			}
			else {
				b = true;
			}
			
			
		}
		
		// last token
		else {
			
			if(!Tokens.search(currentToken)) {
				System.out.println("lexical error at " + lineNum + " character number " + currentPosition);
				error = true;
				exit();
			}
			else {
				fileState = false; // this marks the end of the file;
				b = true;
			}
			
		}
		
		return b;
	}
	
	/**
	 * @param String line
	 * @return void
	 * check to see if the Token beginning with $ is valid
	 */
	
	public boolean variable(String line) {
		boolean b = false;;
		
		if(!Tokens.search(currentToken)) {
			System.out.println("lexical error at " + lineNum + " character number " + currentPosition);
			error = true;
			exit();
		}
		else {
			b = true;
		}
		
		return b;
		
		
		
	}

}
