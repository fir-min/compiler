package edu.towson.cis.cosc455.fsaint1.project1.implementation;
/**
*
* @author Firmin Saint-Amour
* 
* 
*/
import java.io.*;

import edu.towson.cis.cosc455.fsaint1.project1.interfaces.LexicalAnalyzer;

public class MyLexicalAnalyzer  implements LexicalAnalyzer  {
	public String currentLine = ""; // the line we're working with
	public int tokenCount = 0; // # of tokens
	public int lineNum = 0; // which line we're on
	public String currentToken; // the current token
	public boolean error = false; // if there is an error
	public boolean lineState = false; // if the line still has characters
	public boolean fileState = true; // if the still has lines
	public BufferedReader br; // buffered reader
	public char nextCharacter; // the next character
	public int currentPosition = 0; // current position
	
	/** 
     * @param the name of the mkd file to be analyzed. 
     */
	public MyLexicalAnalyzer (String filename) {
		try {
			br = new BufferedReader(new FileReader(filename));
		
		} catch (FileNotFoundException e) {
			System.out.println("Wrong file name, please double check the file name");
			
		}
	}
	
	
	@Override
	/**
	 * This is the public method to be called when the Syntax Analyzer needs a new
	 * token to be parsed.
	 * @param void
	 * @return void
	 * @throws CompilerException 
	 */
	public void getNextToken() throws CompilerException {
		tokenCount++;
		currentToken = "";
		boolean f = false;
	    //System.out.println("Line State " + lineState);
	    if(fileState) {
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
				f = true;
			}
			
			if(f) {
				if(!lookupToken()) {
					
					throw new CompilerException("lexical error at " + lineNum + 
							" character number " + currentPosition);
				}
			}
			
	    }
	    
		
		
		
	}

	
	@Override
	/**
	 * This is method gets the next character from the input and places it in
	 * the nextCharacter class variable.
	 * @param void
	 * @return void
	 */
	public void getCharacter() {
		if (currentPosition >= currentLine.length()) {
			//System.out.println("dfhsdhsdth sagah dhdh");
        	nextCharacter = '\n';
        	lineState = false;
        } else {
        	nextCharacter = currentLine.charAt(currentPosition);
    		currentPosition ++;
    		//System.out.println(nextCharacter);
        }
		

	}
	
	
	/**
	 * Skips all the blank characters
	 * @param void
	 * @return void
	 */
	public void skipBlank() {
		while(currentPosition < currentLine.length() && isSpace(currentLine.charAt(currentPosition))) {
			currentPosition++;
			
		}
		
		
	}

	@Override
	 /**
     * This method adds the current character the nextToken.
     * @param void
     * @param void
     */
	public void addCharacter() {
		currentToken += nextCharacter;

	}

	@Override
	/**
     * checks if the character is a space
     * @param void
     * @param void
     */
	public boolean isSpace(char c) {
		return c == ' ';
	}

	@Override
	/**
     * Looks up the token, returns true if valid, false otherwise
     * @param void
     * @return boolean
     */
	public boolean lookupToken() {
		boolean r = true;
		
		
		if(currentToken.length() < 1 || currentToken.isEmpty() || currentToken.equals("")) {
			return true;
		}
		
		
		else {
			//System.out.println(currentToken + "*this is my token right now");
			//System.out.println("1st char: " + currentToken.charAt(0));
			try {
				char c = currentToken.charAt(0);
				
				//System.out.println("***1st char: " + currentToken.charAt(0));
				
				if(Symbols.search(c)) {
					
					//System.out.println("***made it");
					   if(c == Symbols.HASH) {
						   r = mkdDown();
					   }
					   else if(c == Symbols.HASH) {
						   r = variable();
					   }
				}
			
			} catch (StringIndexOutOfBoundsException e) {
				return true;
			}
		}
		
		
		
		
		return r;
	}
	
	/**
     * gets the next line and puts it in the currentLine class variable
     * @param void
     * @return void
     */
	public void getNextLine() {
		
		if(!lineState) {
			try {
				currentLine =  br.readLine();
				//System.out.println("*** current line: " + currentLine + "line #: " + (lineNum + 1));
				currentPosition = 0;
				lineNum++;
				lineState = true;
			} catch (IOException e) {
				fileState = false;
				
			}
		}
		
		
		
	}
	
	/**
     * checks to see if any token starting with # is valid
     * @param void
     * @throws CompilerException
     * @return true if valid false otherwise
     */
    public boolean mkdDown() {
		boolean b = true;
		// very first token
		if(lineNum == 1) {
			
			if(!Tokens.search(currentToken)) {
				//System.out.println("Lexical error at line # " + lineNum + " & character # " + currentPosition);
				error = true;
				b = false;
			}
			else {
				b = true;
			}
			
			
		}
		
		// last token
		else {
			
			if(!Tokens.search(currentToken)) {
				//System.out.println("lexical error at line #" + lineNum + " & character # " + currentPosition);
				error = true;
				b = false;
			}
			else {
				fileState = false; // this marks the end of the file;
				b = true;
			}
			
		}
		
		return b;
	}
	
	/**
	 * check to see if the Token beginning with $ is valid
	 * @param void
	 * @return true if valid false otherwise
	 * 
	 */
	
	public boolean variable() {
		boolean b = false;;
		
		if(!Tokens.search(currentToken)) {
			System.out.println("lexical error at " + lineNum + " character number " + currentPosition);
			error = true;
			b = false;
		}
		else {
			b = true;
		}
		
		return b;
		
		
		
	}

}
