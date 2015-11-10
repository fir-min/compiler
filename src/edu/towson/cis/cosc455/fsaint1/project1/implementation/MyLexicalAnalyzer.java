package edu.towson.cis.cosc455.fsaint1.project1.implementation;

import java.io.*;

import edu.towson.cis.cosc455.fsaint1.project1.interfaces.LexicalAnalyzer;

public class MyLexicalAnalyzer  implements LexicalAnalyzer  {
	public String currentLine = "";
	public int tokenCount = 0;
	public int lineNum = 0;
	public String currentToken;
	public boolean error = false; // if there is an error
	public boolean lineState = false; // if the line still has characters
	public boolean fileState = true; // if the still has lines
	public BufferedReader br;
	
	
	public char nextCharacter;

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
		tokenCount++;
		currentToken = "";
	    System.out.println("Line State " + lineState);
	    
	    if(!currentLine.equals("")) {
	    	if(currentPosition >= currentLine.length()) {
	    		lineState = false;
	    	}
	    }
		if(!lineState) {
	    	getNextLine();
	    }
	    
	    if(currentPosition < currentLine.length() && isSpace(currentLine.charAt(currentPosition))) {
			skipBlank();
		}
		
		while(currentPosition < currentLine.length() && !isSpace(currentLine.charAt(currentPosition)) && fileState) {
			getCharacter();
			addCharacter();
		}
		
		
		if(!lookupToken()) {
			System.out.println("Lexical error at line #" + lineNum + " & character # " + currentPosition);
			exit();
		}
		
		
		
	}

	
	@Override
	public void getCharacter() {
		if (currentPosition >= currentLine.length()) {
			System.out.println("dfhsdhsdth sagah dhdh");
        	nextCharacter = '\n';
        	lineState = false;
        } else {
        	nextCharacter = currentLine.charAt(currentPosition);
    		currentPosition ++;
    		System.out.println(nextCharacter);
        }
		

	}
	
	
	// skips all the blanks characters in a line makes it easier to get the next token;
	public void skipBlank() {
		while(currentPosition < currentLine.length() && isSpace(currentLine.charAt(currentPosition))) {
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
		boolean r = true;
		System.out.println(currentToken + "   this is my token right now");
		
		if(currentToken.length() < 1) {
			return true;
		}
		
		
		
		try {
			char c = currentToken.charAt(0);
			
			
			
			if(Symbols.search(c)) {
				   if(c == Symbols.HASH) {
					   r = mkdDown(currentLine);
				   }
				   else if(c == Symbols.HASH) {
					   r = variable(currentLine);
				   }
			}
		
		} catch (StringIndexOutOfBoundsException e) {
			// do nothing
		}
		
		return r;
	}
	
	public void getNextLine() {
		
		if(!lineState) {
			try {
				currentLine =  br.readLine();
				System.out.println(currentLine + " current line" + "  L :" + currentLine.length());
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
